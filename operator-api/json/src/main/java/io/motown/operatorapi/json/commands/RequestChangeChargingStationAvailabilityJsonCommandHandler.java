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
package io.motown.operatorapi.json.commands;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.motown.domain.api.chargingstation.*;
import io.motown.operatorapi.viewmodel.persistence.entities.ChargingStation;
import io.motown.operatorapi.viewmodel.persistence.repositories.ChargingStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
class RequestChangeChargingStationAvailabilityJsonCommandHandler implements JsonCommandHandler {

    private static final String COMMAND_NAME = "ChangeAvailability";

    private DomainCommandGateway commandGateway;

    private ChargingStationRepository repository;

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void handle(String chargingStationId, JsonObject commandObject) {
        try {
            ChargingStation chargingStation = repository.findOne(chargingStationId);
            if (chargingStation != null && chargingStation.isAccepted()) {
                String availability = commandObject.get("availability").getAsString();
                int connectorId = commandObject.get("connectorId").getAsInt();

                if("inoperative".equalsIgnoreCase(availability)) {
                    commandGateway.send(new RequestChangeChargingStationAvailabilityToInoperativeCommand(new ChargingStationId(chargingStationId), connectorId));
                } else {
                    commandGateway.send(new RequestChangeChargingStationAvailabilityToOperativeCommand(new ChargingStationId(chargingStationId), connectorId));
                }
            }
        } catch (ClassCastException ex) {
            throw new IllegalArgumentException("Configure command not able to parse the payload, is your json correctly formatted?");
        }
    }

    @Resource(name = "domainCommandGateway")
    public void setCommandGateway(DomainCommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Autowired
    public void setRepository(ChargingStationRepository repository) {
        this.repository = repository;
    }
}