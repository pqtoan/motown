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
package io.motown.ocpp.viewmodel;

import io.motown.domain.api.chargingstation.*;
import org.axonframework.common.annotation.MetaData;

public interface OcppRequestHandler {

    void handle(ConfigurationItemsRequestedEvent event);

    void handle(StopTransactionRequestedEvent event, @MetaData(CorrelationToken.KEY) CorrelationToken correlationToken);

    void handle(SoftResetChargingStationRequestedEvent event, @MetaData(CorrelationToken.KEY) CorrelationToken correlationToken);

    void handle(HardResetChargingStationRequestedEvent event, @MetaData(CorrelationToken.KEY) CorrelationToken correlationToken);

    void handle(StartTransactionRequestedEvent event, @MetaData(CorrelationToken.KEY) CorrelationToken correlationToken);

    void handle(UnlockEvseRequestedEvent event, @MetaData(CorrelationToken.KEY) CorrelationToken correlationToken);

    void handle(ChangeChargingStationAvailabilityToInoperativeRequestedEvent event, @MetaData(CorrelationToken.KEY) CorrelationToken correlationToken);

    void handle(ChangeChargingStationAvailabilityToOperativeRequestedEvent event, @MetaData(CorrelationToken.KEY) CorrelationToken correlationToken);

    void handle(ChangeComponentAvailabilityToInoperativeRequestedEvent event, @MetaData(CorrelationToken.KEY) CorrelationToken correlationToken);

    void handle(ChangeComponentAvailabilityToOperativeRequestedEvent event, @MetaData(CorrelationToken.KEY) CorrelationToken correlationToken);

    void handle(DataTransferRequestedEvent event, @MetaData(CorrelationToken.KEY) CorrelationToken correlationToken);

    void handle(ChangeConfigurationItemRequestedEvent event, @MetaData(CorrelationToken.KEY) CorrelationToken correlationToken);

    void handle(DiagnosticsRequestedEvent event, @MetaData(CorrelationToken.KEY) CorrelationToken correlationToken);

    void handle(ClearCacheRequestedEvent event, @MetaData(CorrelationToken.KEY) CorrelationToken correlationToken);

    void handle(FirmwareUpdateRequestedEvent event);

    void handle(AuthorizationListVersionRequestedEvent event, @MetaData(CorrelationToken.KEY) CorrelationToken correlationToken);

    void handle(SendAuthorizationListRequestedEvent event, @MetaData(CorrelationToken.KEY) CorrelationToken correlationToken);

    void handle(ReserveNowRequestedEvent event, @MetaData(CorrelationToken.KEY) CorrelationToken correlationToken);

    void handle(CancelReservationRequestedEvent event, @MetaData(CorrelationToken.KEY) CorrelationToken correlationToken);
}
