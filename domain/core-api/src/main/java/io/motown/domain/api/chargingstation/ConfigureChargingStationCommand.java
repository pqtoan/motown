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

import com.google.common.collect.ImmutableSet;
import io.motown.domain.api.security.IdentityContext;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import java.util.Objects;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@code ConfigureChargingStationCommand} is the command which is published when a charging station should be
 * configured.
 */
public final class ConfigureChargingStationCommand {

    @TargetAggregateIdentifier
    private final ChargingStationId chargingStationId;

    private final Set<Evse> evses;

    private final IdentityContext identityContext;

    /**
     * Creates a {@code ConfigureChargingStationCommand} with an identifier.
     *
     * @param chargingStationId the identifier of the charging station.
     * @param evses             the evses with which the charging station should be configured.
     * @param identityContext   the identity context.
     * @throws NullPointerException if {@code chargingStationId} or {@code identityContext} is {@code null}.
     */
    public ConfigureChargingStationCommand(ChargingStationId chargingStationId, Set<Evse> evses, IdentityContext identityContext) {
        this.chargingStationId = checkNotNull(chargingStationId);
        this.evses = ImmutableSet.copyOf(checkNotNull(evses));
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
     * Gets the evses with which the charging station should be configured.
     *
     * @return an immutable {@link java.util.Set} of evses.
     */
    public Set<Evse> getEvses() {
        return evses;
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
        return Objects.hash(chargingStationId, evses, identityContext);
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
        final ConfigureChargingStationCommand other = (ConfigureChargingStationCommand) obj;
        return Objects.equals(this.chargingStationId, other.chargingStationId) && Objects.equals(this.evses, other.evses) && Objects.equals(this.identityContext, other.identityContext);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this.getClass())
                .add("chargingStationId", chargingStationId)
                .add("evses", evses)
                .add("identityContext", identityContext)
                .toString();
    }
}
