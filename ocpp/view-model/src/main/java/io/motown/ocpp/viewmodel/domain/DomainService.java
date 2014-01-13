/**
 * Copyright (C) 2013 Motown.IO (info@motown.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.motown.ocpp.viewmodel.domain;

import com.google.common.collect.Maps;
import io.motown.domain.api.chargingstation.*;
import io.motown.domain.api.chargingstation.RequestStatus;
import io.motown.ocpp.viewmodel.persistence.entities.ChargingStation;
import io.motown.ocpp.viewmodel.persistence.entities.ReservationIdentifier;
import io.motown.ocpp.viewmodel.persistence.entities.TransactionIdentifier;
import io.motown.ocpp.viewmodel.persistence.repostories.ChargingStationRepository;
import io.motown.ocpp.viewmodel.persistence.repostories.ReservationIdentifierRepository;
import io.motown.ocpp.viewmodel.persistence.repostories.TransactionIdentifierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class DomainService {

    private static final Logger log = LoggerFactory.getLogger(DomainService.class);
    public static final int CHARGING_STATION_CONNECTOR_ID = 0;
    public static final String ERROR_CODE_KEY = "ERROR_CODE";
    public static final String INFO_KEY = "INFO";
    public static final String VENDOR_ID_KEY = "VENDOR_ID";
    public static final String VENDOR_ERROR_CODE_KEY = "VENDOR_ERROR_CODE";

    @Resource(name = "domainCommandGateway")
    private DomainCommandGateway commandGateway;

    @Autowired
    private ChargingStationRepository chargingStationRepository;

    @Autowired
    private TransactionIdentifierRepository transactionIdentifierRepository;

    @Autowired
    private ReservationIdentifierRepository reservationIdentifierRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Value("${io.motown.ocpp.viewmodel.heartbeat.interval}")
    private int heartbeatInterval;

    @Value("${io.motown.ocpp.viewmodel.authorize.timeout}")
    private int authorizeTimeout;

    public BootChargingStationResult bootChargingStation(final ChargingStationId chargingStationId, final String chargingStationAddress, final String vendor, final String model, final String protocol) {
        // In case there is no charging station address specified there is no point in continuing, since we will not be able to reach the chargingstation later on
        if(chargingStationAddress == null || chargingStationAddress.isEmpty()) {
            log.error("Rejecting bootnotification, no charging station address has been specified.");
            return new BootChargingStationResult(false, heartbeatInterval, new Date());
        }

        // Check if we already know the charging station, or have to create one
        ChargingStation chargingStation = chargingStationRepository.findOne(chargingStationId.getId());

        if(chargingStation == null) {
            log.debug("Not a known charging station on boot notification, we send a CreateChargingStationCommand.");

            commandGateway.send(new CreateChargingStationCommand(chargingStationId), new CreateChargingStationCommandCallback(
                    chargingStationId, chargingStationAddress, vendor, model, protocol, chargingStationRepository, this
            ));

            // we didn't know the charging station when this bootNotification occurred so we reject it.
            return new BootChargingStationResult(false, heartbeatInterval, new Date());
        }

        // Keep track of the address on which we can reach the charging station
        chargingStation.setIpAddress(chargingStationAddress);
        chargingStationRepository.save(chargingStation);

        Map<String, String> attributes = Maps.newHashMap();
        attributes.put("vendor", vendor);
        attributes.put("model", model);
        attributes.put("address", chargingStationAddress);

        commandGateway.send(new BootChargingStationCommand(chargingStationId, protocol, attributes));

        return new BootChargingStationResult(chargingStation.isRegistered(), heartbeatInterval, new Date());
    }

    public void dataTransfer(ChargingStationId chargingStationId, String data, String vendorId, String messageId) {
        commandGateway.send(new IncomingDataTransferCommand(chargingStationId, vendorId, messageId, data));
    }

    public void heartbeat(ChargingStationId chargingStationId) {
        commandGateway.send(new HeartbeatCommand(chargingStationId));
    }

    public void meterValues(ChargingStationId chargingStationId, TransactionId transactionId, ConnectorId connectorId, List<MeterValue> meterValues) {
        commandGateway.send(new ProcessMeterValueCommand(chargingStationId, transactionId, connectorId, meterValues));
    }

    public void diagnosticsFileNameReceived(ChargingStationId chargingStationId, String diagnosticsFileName) {
        commandGateway.send(new DiagnosticsFileNameReceivedCommand(chargingStationId, diagnosticsFileName));
    }

    public void authorisationListVersionReceived(ChargingStationId chargingStationId, int currentVersion) {
        commandGateway.send(new AuthorisationListVersionReceivedCommand(chargingStationId, currentVersion));
    }

    public AuthorizationResult authorize(ChargingStationId chargingStationId, String idTag){
        AuthorizeCommand command = new AuthorizeCommand(chargingStationId, idTag);
        AuthorizationResultStatus resultStatus = commandGateway.sendAndWait(command, authorizeTimeout, TimeUnit.SECONDS);

        return new AuthorizationResult(idTag, resultStatus);
    }
    
    public void configureChargingStation(ChargingStationId chargingStationId, Map<String, String> configurationItems) {
        ConfigureChargingStationCommand command = new ConfigureChargingStationCommand(chargingStationId, configurationItems);
        commandGateway.send(command);
    }

    public void diagnosticsUploadStatusUpdate(ChargingStationId chargingStationId, boolean diagnosticsUploaded) {
        UpdateDiagnosticsUploadStatusCommand command = new UpdateDiagnosticsUploadStatusCommand(chargingStationId, diagnosticsUploaded);
        commandGateway.send(command);
    }

    public void firmwareStatusUpdate(ChargingStationId chargingStationId, FirmwareStatus firmwareStatus) {

        UpdateFirmwareStatusCommand command = new UpdateFirmwareStatusCommand(chargingStationId, firmwareStatus);
        commandGateway.send(command);
    }

    public void statusNotification(ChargingStationId chargingStationId, ConnectorId connectorId, String errorCode, ComponentStatus status, String info, Date timeStamp, String vendorId, String vendorErrorCode) {
        //TODO: The attributes map can contain protocol specific key values, how to know what keys to expect on receiving end - Ingo Pak, 09 Jan 2014
        Map<String, String> attributes = new HashMap<>();
        attributes.put(ERROR_CODE_KEY, errorCode);
        attributes.put(INFO_KEY, info);
        attributes.put(VENDOR_ID_KEY, vendorId);
        attributes.put(VENDOR_ERROR_CODE_KEY, vendorErrorCode);

        StatusNotificationCommand command;

        if (connectorId.getNumberedId() == CHARGING_STATION_CONNECTOR_ID) {
            command = new ChargingStationStatusNotificationCommand(chargingStationId, status, timeStamp, attributes);
        } else {
            ChargingStationComponent component = ChargingStationComponent.CONNECTOR;
            command = new ComponentStatusNotificationCommand(chargingStationId, component, connectorId, status, timeStamp, attributes);
        }

        commandGateway.send(command);
    }

    /**
     * Generates a transaction identifier and starts a transaction by dispatching a StartTransactionCommand.
     *
     * @param chargingStationId      identifier of the charging station
     * @param connectorId            connector identifier on which the transaction is started
     * @param idTag                  the identifier which started the transaction
     * @param meterStart             meter value in Wh for the connector at start of the transaction
     * @param timestamp              date and time on which the transaction started
     * @param protocolIdentifier     identifier of the protocol that starts the transaction
     * @throws IllegalStateException when the charging station cannot be found, is not registered and configured, or the connectorId is unknown for this charging station
     * @return                       transaction identifier
     */
    public int startTransaction(ChargingStationId chargingStationId, ConnectorId connectorId, IdentifyingToken idTag, int meterStart, Date timestamp, String protocolIdentifier) {
        ChargingStation chargingStation = chargingStationRepository.findOne(chargingStationId.getId());
        if(chargingStation == null) {
            throw new IllegalStateException("Cannot start transaction for an unknown charging station.");
        }

        if(!chargingStation.isRegisteredAndConfigured()) {
            throw new IllegalStateException("Cannot start transaction for charging station that has not been registered/configured.");
        }

        if (connectorId.getNumberedId() > chargingStation.getNumberOfConnectors()) {
            throw new IllegalStateException("Cannot start transaction on a unknown connector.");
        }

        NumberedTransactionId transactionId = generateTransactionIdentifier(chargingStationId, protocolIdentifier);

        StartTransactionCommand command = new StartTransactionCommand(chargingStationId, transactionId, connectorId, idTag, meterStart, timestamp);
        commandGateway.send(command);

        return transactionId.getNumber();
    }

    public void stopTransaction(ChargingStationId chargingStationId, TransactionId transactionId, IdentifyingToken idTag, int meterValueStop, Date timeStamp){
        StopTransactionCommand command = new StopTransactionCommand(chargingStationId, transactionId, idTag, meterValueStop, timeStamp);
        commandGateway.send(command);
    }

    public void reservationStatusChanged(ChargingStationId chargingStationId, ReservationId reservationId, ReservationStatus newStatus) {
        commandGateway.send(new ReservationStatusChangedCommand(chargingStationId, reservationId, newStatus));
    }

    public void clearCacheStatusChanged(ChargingStationId chargingStationId, RequestStatus requestStatus) {
        commandGateway.send(new ClearCacheStatusChangedCommand(chargingStationId, requestStatus));
    }


    public void setCommandGateway(DomainCommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    public void setChargingStationRepository(ChargingStationRepository chargingStationRepository) {
        this.chargingStationRepository = chargingStationRepository;
    }

    public void setTransactionIdentifierRepository(TransactionIdentifierRepository transactionIdentifierRepository) {
        this.transactionIdentifierRepository = transactionIdentifierRepository;
    }

    public void setReservationIdentifierRepository(ReservationIdentifierRepository reservationIdentifierRepository) {
        this.reservationIdentifierRepository = reservationIdentifierRepository;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public String retrieveChargingStationAddress(ChargingStationId id) {
        ChargingStation chargingStation = chargingStationRepository.findOne(id.getId());

        return chargingStation != null? chargingStation.getIpAddress() : "";
    }

    /**
     * Generates a reservation identifier based on the charging station, the module (OCPP) and a auto-incremented number.
     *
     * @param chargingStationId charging station identifier to use when generating a reservation identifier.
     * @param protocolIdentifier identifier of the protocol, used when generating a reservation identifier.
     * @return reservation identifier based on the charging station, module and auto-incremented number.
     */
    public NumberedReservationId generateReservationIdentifier(ChargingStationId chargingStationId, String protocolIdentifier) {
        ReservationIdentifier reservationIdentifier = new ReservationIdentifier();

        reservationIdentifierRepository.saveAndFlush(reservationIdentifier);

        /* TODO JPA's identity generator creates longs, while OCPP and Motown supports ints. Where should we translate
         * between these and how should we handle error cases? - Mark van den Bergh, Januari 7th 2013
         */
        Long identifier = (Long) entityManagerFactory.getPersistenceUnitUtil().getIdentifier(reservationIdentifier);
        return new NumberedReservationId(chargingStationId, protocolIdentifier, identifier.intValue());
    }

    /**
     * Generates a transaction identifier based on the charging station, the module (OCPP) and a auto-incremented number.
     *
     * @param chargingStationId charging station identifier to use when generating a transaction identifier.
     * @param protocolIdentifier identifier of the protocol, used when generating a transaction identifier.
     * @return transaction identifier based on the charging station, module and auto-incremented number.
     */
    private NumberedTransactionId generateTransactionIdentifier(ChargingStationId chargingStationId, String protocolIdentifier) {
        TransactionIdentifier transactionIdentifier = new TransactionIdentifier();

        transactionIdentifierRepository.saveAndFlush(transactionIdentifier); // flush to make sure the generated id is populated

        /* TODO JPA's identity generator creates longs, while OCPP and Motown supports ints. Where should we translate
         * between these and how should we handle error cases? - Dennis Laumen, December 16th 2013
         */
        Long identifier = (Long) entityManagerFactory.getPersistenceUnitUtil().getIdentifier(transactionIdentifier);
        return new NumberedTransactionId(chargingStationId, protocolIdentifier, identifier.intValue());
    }
}
