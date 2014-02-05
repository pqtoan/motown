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


public class SendAuthorizationListCommandTest {

    @Test(expected = NullPointerException.class)
    public void nullPointerExceptionThrownWhenCreatingCommandWithChargingStationIdNull() {
        new SendAuthorizationListCommand(null, new ArrayList<IdentifyingToken>(), 1, "", AuthorizationListUpdateType.FULL);
    }

    @Test(expected = NullPointerException.class)
    public void nullPointerExceptionThrownWhenCreatingCommandWithTokensNull() {
        new SendAuthorizationListCommand(getChargingStationId(), null, 1, "", AuthorizationListUpdateType.FULL);
    }

    @Test(expected = NullPointerException.class)
    public void nullPointerExceptionThrownWhenCreatingCommandWithHashNull() {
        new SendAuthorizationListCommand(getChargingStationId(), new ArrayList<IdentifyingToken>(), 1, null, AuthorizationListUpdateType.FULL);
    }

    @Test(expected = NullPointerException.class)
    public void nullPointerExceptionThrownWhenCreatingCommandWithUpdateTypeNull() {
        new SendAuthorizationListCommand(getChargingStationId(), new ArrayList<IdentifyingToken>(), 1, "", null);
    }
}
