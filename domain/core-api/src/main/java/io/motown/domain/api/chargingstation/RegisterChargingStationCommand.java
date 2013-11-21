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
package io.motown.domain.api.chargingstation;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@code RegisterChargingStationCommand} is the command which is published when a charging station should be registered.
 */
public class RegisterChargingStationCommand {
    @TargetAggregateIdentifier
    private final ChargingStationId chargingStationId;

    /**
     * Creates a {@code RegisterChargingStationCommand} with an identifier.
     *
     * @param chargingStationId the identifier of the charging station.
     * @throws IllegalArgumentException if {@code chargingStationId} is {@code null}.
     */
    public RegisterChargingStationCommand(ChargingStationId chargingStationId) {
        checkArgument(chargingStationId != null);

        this.chargingStationId = chargingStationId;
    }

    /**
     * Gets the charging station identifier.
     *
     * @return the charging station identifier.
     */
    public ChargingStationId getChargingStationId() {
        return chargingStationId;
    }
}
