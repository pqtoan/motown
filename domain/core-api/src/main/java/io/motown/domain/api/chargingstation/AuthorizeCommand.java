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
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import java.util.Objects;

import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@code AuthorizeCommand} is the command which is published when a charging station wants to authorize the
 * identification that wants to start a transaction.
 */
public final class AuthorizeCommand {

    @TargetAggregateIdentifier
    private final ChargingStationId chargingStationId;

    private final String identifyingToken;

    private final IdentityContext identityContext;

    /**
     * Creates a {@code AuthorizeCommand}.
     *
     * @param chargingStationId the identifier of the charging station.
     * @param identifyingToken  the identifier that needs to be authorized.
     * @param identityContext the identity context.
     * @throws NullPointerException if {@code chargingStationId}, {@code identifyingToken} or {@code identityContext} is {@code null}.
     */
    public AuthorizeCommand(ChargingStationId chargingStationId, String identifyingToken, IdentityContext identityContext) {
        this.chargingStationId = checkNotNull(chargingStationId);
        this.identifyingToken = checkNotNull(identifyingToken);
        this.identityContext = checkNotNull(identityContext);
    }

    /**
     * Gets the charging station identifier.
     *
     * @return the charging station identifier.
     */
    public ChargingStationId getChargingStationId() {
        return this.chargingStationId;
    }

    /**
     * Gets the identifier that needs to be authorized.
     *
     * @return the identifier that needs to be authorized.
     */
    public String getIdentifyingToken() {
        return identifyingToken;
    }

    /**
     * Gets the identity context.
     *
     * @return the identity context.
     */
    public IdentityContext getIdentityContext() {
        return identityContext;
    }

    @Override
    public int hashCode() {
        return Objects.hash(chargingStationId, identifyingToken, identityContext);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final AuthorizeCommand other = (AuthorizeCommand) obj;
        return Objects.equals(this.chargingStationId, other.chargingStationId) && Objects.equals(this.identifyingToken, other.identifyingToken) && Objects.equals(this.identityContext, other.identityContext);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return toStringHelper(this)
                .add("chargingStationId", chargingStationId)
                .add("identifyingToken", identifyingToken)
                .add("identityContext", identityContext)
                .toString();
    }
}
