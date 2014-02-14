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
package io.motown.vas.viewmodel;

import io.motown.domain.api.chargingstation.*;
import io.motown.vas.viewmodel.model.ChargingStation;
import io.motown.vas.viewmodel.persistence.repostories.ChargingStationRepository;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class VasEventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(io.motown.vas.viewmodel.VasEventHandler.class);

    @Autowired
    private ChargingStationRepository chargingStationRepository;

    @Autowired
    private ConfigurationConversionService configurationConversionService;

    //TODO: Determine which event handlers are necessary to keep the model state up to date - Ingo Pak, 22 Jan 2014

    @EventHandler
    public void handle(ChargingStationCreatedEvent event) {
        LOG.info("Handling ChargingStationCreatedEvent");

        String chargingStationId = event.getChargingStationId().getId();
        ChargingStation chargingStation = chargingStationRepository.findByChargingStationId(chargingStationId);

        if (chargingStation == null) {
            chargingStation = new ChargingStation(chargingStationId);
        }

        chargingStationRepository.save(chargingStation);
    }

    @EventHandler
    public void handle(ChargingStationAcceptedEvent event) {
        LOG.debug("ChargingStationAcceptedEvent for {} received!", event.getChargingStationId());

        ChargingStation chargingStation = getChargingStation(event.getChargingStationId());

        if (chargingStation != null) {
            chargingStation.setRegistered(true);
            chargingStationRepository.save(chargingStation);
        }
    }

    /**
     * Reads and interprets the configuration. This handler takes care of the following properties:
     * - configured
     * - chargeMode
     * - connectorTypes
     * - evses (all set to status UNKNOWN, ComponentStatusNotificationReceivedEvent handler will set the real state)
     *
     * @param event event containing the configuration information for the charging station.
     */
    @EventHandler
    public void handle(ChargingStationConfiguredEvent event) {
        LOG.info("ChargingStationConfiguredEvent");

        ChargingStation chargingStation = getChargingStation(event.getChargingStationId());

        if (chargingStation == null) {
            LOG.warn("Received a ChargingStationConfiguredEvent for unknown charging station. Creating the chargingStation.");
            chargingStation = new ChargingStation(event.getChargingStationId().getId());
        }

        Set<Evse> eventEvses = event.getEvses();

        chargingStation.setChargeMode(configurationConversionService.getChargeModeFromEvses(eventEvses));
        chargingStation.setConnectorTypes(configurationConversionService.getConnectorTypesFromEvses(eventEvses));
        chargingStation.setEvses(configurationConversionService.getEvsesFromEventEvses(eventEvses));
        chargingStation.setChargingCapabilities(configurationConversionService.getChargingCapabilitiesFromEvses(eventEvses));
        chargingStation.setConfigured(true);

        chargingStationRepository.save(chargingStation);
    }

    @EventHandler
    public void handle(ChargingStationPlacedEvent event) {
        LOG.info("ChargingStationPlacedEvent");

        updateLocationForChargingStation(event.getChargingStationId(), event.getCoordinates(), event.getAddress());
    }

    @EventHandler
    public void handle(ChargingStationLocationImprovedEvent event) {
        LOG.info("ChargingStationLocationImprovedEvent");

        updateLocationForChargingStation(event.getChargingStationId(), event.getCoordinates(), event.getAddress());
    }

    @EventHandler
    public void handle(ChargingStationMovedEvent event) {
        LOG.info("ChargingStationMovedEvent");

        updateLocationForChargingStation(event.getChargingStationId(), event.getCoordinates(), event.getAddress());
    }

    @EventHandler
    public void handle(ChargingStationOpeningTimesSetEvent event) {
        LOG.info("ChargingStationOpeningTimesSetEvent");

        ChargingStation chargingStation = getChargingStation(event.getChargingStationId());
        if (chargingStation != null) {
            // TODO implement - Mark Manders (2014-02-14)
        }
    }

    @EventHandler
    public void handle(ChargingStationOpeningTimesAddedEvent event) {
        LOG.info("ChargingStationOpeningTimesAddedEvent");

        ChargingStation chargingStation = getChargingStation(event.getChargingStationId());
        if (chargingStation != null) {
            // TODO implement - Mark Manders (2014-02-14)
        }
    }

    @EventHandler
    public void handle(ChargingStationMadeReservableEvent event) {
        LOG.info("ChargingStationMadeReservableEvent for {} received", event.getChargingStationId());

        updateReservableForChargingStation(event.getChargingStationId(), true);
    }

    @EventHandler
    public void handle(ChargingStationMadeNotReservableEvent event) {
        LOG.info("ChargingStationMadeNotReservableEvent for {} received", event.getChargingStationId());

        updateReservableForChargingStation(event.getChargingStationId(), false);
    }

    @EventHandler
    public void handle(ChargingStationStatusNotificationReceivedEvent event) {
        //TODO implement. - Mark van den Bergh, Februari 13th 2014
    }

    @EventHandler
    public void handle(ComponentStatusNotificationReceivedEvent event) {
        //TODO implement. - Mark van den Bergh, Februari 13th 2014
    }

    public void setChargingStationRepository(ChargingStationRepository chargingStationRepository) {
        this.chargingStationRepository = chargingStationRepository;
    }

    public void setConfigurationConversionService(ConfigurationConversionService configurationConversionService) {
        this.configurationConversionService = configurationConversionService;
    }

    /**
     * Tries to find a {@code ChargingStation} by the passed id, if not found an error will be logged and null will be
     * returned.
     *
     * @param chargingStationId    charging station identifier.
     * @return charging station if found, null otherwise.
     */
    private ChargingStation getChargingStation(ChargingStationId chargingStationId) {
        ChargingStation chargingStation = chargingStationRepository.findByChargingStationId(chargingStationId.getId());

        if (chargingStation == null) {
            LOG.error("Could not find charging station {}", chargingStationId);
        }

        return chargingStation;
    }

    /**
     * Updates the 'reservable' property of the charging station. If the charging station cannot be found in the
     * repository an error is logged.
     *
     * @param chargingStationId charging station identifier.
     * @param reservable true if the charging station is reservable, false otherwise.
     */
    private void updateReservableForChargingStation(ChargingStationId chargingStationId, boolean reservable) {
        ChargingStation chargingStation = getChargingStation(chargingStationId);

        if (chargingStation != null) {
            chargingStation.setReservable(reservable);
            chargingStationRepository.save(chargingStation);
        }
    }

    /**
     * Updates the location of the charging station to either new lat/long coordinates or a geographical address (or both).
     * If the charging station cannot be found in the repository an error is logged.
     *
     * @param chargingStationId charging station identifier.
     * @param coordinates the lat/long coordinates of the charging station.
     * @param address the geographical address of the charging station.
     */
    private void updateLocationForChargingStation(ChargingStationId chargingStationId, Coordinates coordinates, Address address) {
        ChargingStation chargingStation = getChargingStation(chargingStationId);

        if (chargingStation != null) {

            if (coordinates != null) {
                chargingStation.setLatitude(coordinates.getLatitude());
                chargingStation.setLongitude(coordinates.getLongitude());
            }

            if (address != null) {
                chargingStation.setAddress(address.getAddressline1());
                chargingStation.setCity(address.getCity());
                chargingStation.setCountry(address.getCountry());
                chargingStation.setPostalCode(address.getPostalCode());
                chargingStation.setRegion(address.getRegion());
            }

            chargingStationRepository.save(chargingStation);
        }
    }
}
