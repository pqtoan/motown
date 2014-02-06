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

import org.junit.Test;

import java.util.ArrayList;

import static io.motown.domain.api.chargingstation.CoreApiTestUtils.getChargingStationId;
import static io.motown.domain.api.chargingstation.CoreApiTestUtils.getProtocol;


public class SendAuthorizationListEventTest {

    @Test(expected = NullPointerException.class)
    public void nullPointerExceptionThrownWhenCreatingEventWithChargingStationIdNull() {
        new SendAuthorizationListRequestedEvent(null, getProtocol(), new ArrayList<IdentifyingToken>(), 1, null, AuthorizationListUpdateType.FULL);
    }

    @Test(expected = NullPointerException.class)
    public void nullPointerExceptionThrownWhenCreatingEventWithProtocolNull() {
        new SendAuthorizationListRequestedEvent(getChargingStationId(), null, new ArrayList<IdentifyingToken>(), 1, null, AuthorizationListUpdateType.FULL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalArgumentExceptionThrownWhenCreatingEventWithProtocolEmpty() {
        new SendAuthorizationListRequestedEvent(getChargingStationId(), "", new ArrayList<IdentifyingToken>(), 1, null, AuthorizationListUpdateType.FULL);
    }

    @Test(expected = NullPointerException.class)
    public void nullPointerExceptionThrownWhenCreatingEventWithTokensNull() {
        new SendAuthorizationListRequestedEvent(getChargingStationId(), getProtocol(), null, 1, "", AuthorizationListUpdateType.FULL);
    }

    @Test(expected = NullPointerException.class)
    public void nullPointerExceptionThrownWhenCreatingEventWithHashNull() {
        new SendAuthorizationListRequestedEvent(getChargingStationId(), getProtocol(), new ArrayList<IdentifyingToken>(), 1, null, AuthorizationListUpdateType.FULL);
    }

    @Test(expected = NullPointerException.class)
    public void nullPointerExceptionThrownWhenCreatingEventWithUpdateTypeNull() {
        new SendAuthorizationListRequestedEvent(getChargingStationId(), getProtocol(), new ArrayList<IdentifyingToken>(), 1, null, null);
    }
}