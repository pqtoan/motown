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

import io.motown.domain.api.security.IdentityContext;

import java.util.Objects;

import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@code ChangeAvailabilityToInoperativeRequestedEvent} is the event which is published when a charging station or one
 * of its components' availability should be changed to inoperative. Protocol add-ons should respond to this event or
 * one of its subclasses (if applicable) and request a charging station to change its availability.
 */
public abstract class ChangeAvailabilityToInoperativeRequestedEvent implements CommunicationWithChargingStationRequestedEvent {

    private final ChargingStationId chargingStationId;

    private final String protocol;

    private final IdentityContext identityContext;

    /**
     * Creates a {@code ChangeAvailabilityToInoperativeRequestedEvent}.
     *
     * @param chargingStationId the identifier of the charging station.
     * @param protocol          protocol identifier.
     * @param identityContext   identity context.
     * @throws NullPointerException     if {@code chargingStationId}, {@code protocol} or {@code identityContext} is {@code null}.
     * @throws IllegalArgumentException if {@code protocol} is empty.
     */
    protected ChangeAvailabilityToInoperativeRequestedEvent(ChargingStationId chargingStationId, String protocol, IdentityContext identityContext) {
        this.chargingStationId = checkNotNull(chargingStationId);
        checkNotNull(protocol);
        checkArgument(!protocol.isEmpty());
        this.protocol = protocol;
        this.identityContext = checkNotNull(identityContext);
    }

    /**
     * Gets the charging station's id.
     *
     * @return the charging station's id.
     */
    public ChargingStationId getChargingStationId() {
        return chargingStationId;
    }

    /**
     * Gets the charging station's protocol.
     *
     * @return the charging station's protocol.
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * Gets the identity context.
     *
     * @return the identity context.
     */
    public IdentityContext getIdentityContext() {
        return identityContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(chargingStationId, protocol, identityContext);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ChangeAvailabilityToInoperativeRequestedEvent other = (ChangeAvailabilityToInoperativeRequestedEvent) obj;
        return Objects.equals(this.chargingStationId, other.chargingStationId) && Objects.equals(this.protocol, other.protocol) && Objects.equals(this.identityContext, other.identityContext);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return toStringHelper(this)
                .add("chargingStationId", chargingStationId)
                .add("protocol", protocol)
                .add("identityContext", identityContext)
                .toString();
    }
}
