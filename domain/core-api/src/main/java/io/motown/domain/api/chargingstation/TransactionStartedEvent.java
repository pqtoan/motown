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

import java.util.Date;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@code TransactionStartedEvent} is the event which is published when a transaction has started.
 */
public final class TransactionStartedEvent {

    private final ChargingStationId chargingStationId;

    private final TransactionId transactionId;

    private final int connectorId;

    private final IdentifyingToken identifyingToken;

    private final int meterStart;

    private final Date timestamp;

    /**
     * Creates a {@code TransactionStartedEvent}.
     *
     * In contrast to most of the other classes and methods in the Core API the {@code transactionId} and
     * {@code identifyingToken} are possibly mutable. Some default, immutable implementations of these interfaces are
     * provided but the mutability of these parameters can't be guaranteed.
     *
     * @param chargingStationId the charging station's identifier.
     * @param transactionId     the transaction's identifier.
     * @param connectorId       the connector's identifier or position.
     * @param identifyingToken  the token which started the transaction.
     * @param meterStart        meter value in Wh for the connector when the transaction started.
     * @param timestamp         the time at which the transaction started.
     * @throws NullPointerException if {@code chargingStationId}, {@code transactionId}, {@code identifyingToken} or
     * {@code timestamp} is {@code null}.
     * @throws IllegalArgumentException if {@code connectorId} is negative.
     */
    public TransactionStartedEvent(ChargingStationId chargingStationId, TransactionId transactionId, int connectorId, IdentifyingToken identifyingToken, int meterStart, Date timestamp) {
        this.chargingStationId = checkNotNull(chargingStationId);
        this.transactionId = checkNotNull(transactionId);
        checkArgument(connectorId > 0);
        this.connectorId = connectorId;
        this.identifyingToken = checkNotNull(identifyingToken);
        this.meterStart = meterStart;
        this.timestamp = new Date(checkNotNull(timestamp).getTime());
    }

    /**
     * Gets the charging station's identifier.
     *
     * @return the charging station's identifier.
     */
    public ChargingStationId getChargingStationId() {
        return chargingStationId;
    }

    /**
     * Gets the transaction's identifier.
     *
     * @return the transaction's identifier
     */
    public TransactionId getTransactionId() {
        return transactionId;
    }

    /**
     * Gets the connector's identifier or position.
     *
     * @return the connector's identifier or position.
     */
    public int getConnectorId() {
        return connectorId;
    }

    /**
     * Gets the token which started the transaction.
     *
     * @return the token.
     */
    public IdentifyingToken getIdentifyingToken() {
        return identifyingToken;
    }

    /**
     * Gets the meter value when the transaction started.
     *
     * @return the meter value when the transaction started.
     */
    public int getMeterStart() {
        return meterStart;
    }

    /**
     * Gets the time at which the transaction started.
     *
     * @return the time at which the transaction started.
     */
    public Date getTimestamp() {
        return timestamp;
    }
}
