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
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@code IncomingDataTransferCommand} is the command which is published when the charging station sends a custom data
 * message to the central system.
 */
public final class IncomingDataTransferCommand {

    @TargetAggregateIdentifier
    private final ChargingStationId chargingStationId;

    private String vendorId;

    private String messageId;

    private String data;

    /**
     * Creates a {@code IncomingDataTransferCommand} with an identifier.
     *
     * @param chargingStationId the identifier of the charging station.
     * @param vendorId the charging station vendor.
     * @param messageId optional additional identification field (empty if no value).
     * @param data the data to transfer (empty if no value).
     * @throws NullPointerException if {@code chargingStationId}, {@code vendorId}, {@code messageId}, {@code data} is
     *                              {@code null}.
     * @throws IllegalArgumentException if {@code vendorId} is empty.
     */
    public IncomingDataTransferCommand(ChargingStationId chargingStationId, String vendorId, String messageId, String data) {
        this.chargingStationId = checkNotNull(chargingStationId);
        checkNotNull(vendorId);
        checkArgument(!vendorId.isEmpty());
        this.vendorId = checkNotNull(vendorId);
        this.messageId = checkNotNull(messageId);
        this.data = checkNotNull(data);
    }

    /**
     * @return the charging station identifier.
     */
    public ChargingStationId getChargingStationId() {
        return chargingStationId;
    }

    /**
     * @return the vendor identifier.
     */
    public String getVendorId() {
        return vendorId;
    }

    /**
     * @return the additional message identifier.
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * @return the data.
     */
    public String getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IncomingDataTransferCommand that = (IncomingDataTransferCommand) o;

        if (!chargingStationId.equals(that.chargingStationId)) return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        if (messageId != null ? !messageId.equals(that.messageId) : that.messageId != null) return false;
        if (!vendorId.equals(that.vendorId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = chargingStationId.hashCode();
        result = 31 * result + vendorId.hashCode();
        result = 31 * result + (messageId != null ? messageId.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }
}
