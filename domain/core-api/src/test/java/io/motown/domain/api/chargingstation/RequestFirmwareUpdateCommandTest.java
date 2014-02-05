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

import java.util.Date;
import java.util.HashMap;

import static io.motown.domain.api.chargingstation.CoreApiTestUtils.getChargingStationId;

public class RequestFirmwareUpdateCommandTest {

    @Test(expected = NullPointerException.class)
    public void nullPointerExceptionThrownWhenCreatingWithNullChargingStationId() {
        new RequestFirmwareUpdateCommand(null, "https://somewhere.nl", new Date(), new HashMap());
    }

    @Test(expected = NullPointerException.class)
    public void nullPointerExceptionThrownWhenCreatingWithNullDownloadLocation() {
        new RequestFirmwareUpdateCommand(getChargingStationId(), null, new Date(), new HashMap());
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalArgumentExceptionThrownWhenCreatingWithEmptyDownloadLocation() {
        new RequestFirmwareUpdateCommand(getChargingStationId(), "", new Date(), new HashMap());
    }

    @Test(expected = NullPointerException.class)
    public void nullPointerExceptionThrownWhenCreatingWithNullRetrieveDate() {
        new RequestFirmwareUpdateCommand(getChargingStationId(), "https://somewhere.nl", null, new HashMap());
    }

    @Test(expected = NullPointerException.class)
    public void nullPointerExceptionThrownWhenCreatingWithNullAttributes() {
        new RequestFirmwareUpdateCommand(getChargingStationId(), "https://somewhere.nl", new Date(), null);
    }
}
