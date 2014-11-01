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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@code StartTransactionRequestedEvent} is the event which is published when a request has been made to remote start a
 * transaction. Protocol add-ons should respond to this event (if applicable) and request a charging station to start a
 * transaction.
 */
public final class StartTransactionRequestedEvent implements CommunicationWithChargingStationRequestedEvent {

    private final ChargingStationId chargingStationId;

    private final String protocol;

    private final String identifyingToken;

    private final EvseId evseId;

    private final IdentityContext identityContext;

    /**
     * Creates a {@code StartTransactionRequestedEvent} with an identifier, a protocol and a evse identifier.
     *
     * @param chargingStationId the charging station's identifier.
     * @param protocol          the protocol identifier.
     * @param identifyingToken  the token that should start the transaction.
     * @param evseId            the identifier of the evse.
     * @param identityContext   identity context.
     * @throws NullPointerException if {@code chargingStationId}, {@code protocol}, {@code identifyingToken}, {@code evseId}
     *                          or {@code identityContext} is {@code null}.
     */
    public StartTransactionRequestedEvent(ChargingStationId chargingStationId, String protocol, String identifyingToken, EvseId evseId, IdentityContext identityContext) {
        this.chargingStationId = checkNotNull(chargingStationId);
        checkNotNull(protocol);
        checkArgument(!protocol.isEmpty());
        this.protocol = protocol;
        this.identifyingToken = checkNotNull(identifyingToken);
        this.evseId = checkNotNull(evseId);
        this.identityContext = checkNotNull(identityContext);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChargingStationId getChargingStationId() {
        return chargingStationId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProtocol() {
        return protocol;
    }

    /**
     * Gets the token which should start the transaction.
     *
     * @return the token.
     */
    public String getIdentifyingToken() {
        return identifyingToken;
    }

    /**
     * Gets the evse identifier.
     *
     * @return the evse identifier.
     */
    public EvseId getEvseId() {
        return evseId;
    }

    /**
     * Gets the identity context.
     *
     * @return the identity context.
     */
    public IdentityContext getIdentityContext() {
        return identityContext;
    }
}
