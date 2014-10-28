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

package io.motown.domain.api.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public  class Hashing256 implements HashAlgorithm {

    @Override
    public String getHex(String inputString) throws NoSuchAlgorithmException {
    	MessageDigest md = MessageDigest.getInstance("SHA-256");
    	md.update(inputString.getBytes());
    	byte[] digest = md.digest();
    	return convertByteToHex(digest);
    }

    @Override
    public String convertByteToHex(byte[] byteData) {
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < byteData.length; i++) {
    		sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
    	}
    	return sb.toString();
    }
}
