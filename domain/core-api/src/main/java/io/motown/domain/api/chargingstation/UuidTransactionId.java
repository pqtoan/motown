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

import java.util.Objects;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A UUID based charging transaction identifier.
 */
public final class UuidTransactionId implements TransactionId {

    private final UUID uuid;

    /**
     * Creates a {@code UuidTransactionId} with a random UUID.
     */
    public UuidTransactionId() {
        uuid = UUID.randomUUID();
    }

    /**
     * Creates a {@code UuidTransactionId} with a given string representing a UUID.
     * <p/>
     * The {@code String} representation of the UUID should follow the description in the {@link UUID#toString} method.
     *
     * @param uuid the {@link UUID} identifying the transaction.
     * @throws NullPointerException if the given UUID is null.
     */
    public UuidTransactionId(String uuid) {
        this.uuid = UUID.fromString(checkNotNull(uuid));
    }

    /**
     * Creates a {@code UuidTransactionId} with a given UUID.
     *
     * @param uuid the {@link UUID} identifying the transaction.
     * @throws NullPointerException if the given UUID is null.
     */
    public UuidTransactionId(UUID uuid) {
        this.uuid = checkNotNull(uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return uuid.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final UuidTransactionId other = (UuidTransactionId) obj;
        return Objects.equals(this.uuid, other.uuid);
    }
}
