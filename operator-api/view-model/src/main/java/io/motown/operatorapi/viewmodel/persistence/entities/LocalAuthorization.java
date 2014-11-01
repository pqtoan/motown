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
package io.motown.operatorapi.viewmodel.persistence.entities;

import io.motown.domain.api.chargingstation.MainIdTagInfo.AuthenticationStatus;

import javax.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class LocalAuthorization {

    private String token;
    
    private String parentToken;

    private AuthenticationStatus authenticationStatus;

    public LocalAuthorization() {
    }

    public LocalAuthorization(String token,String parentToken, AuthenticationStatus authenticationStatus) {
        this.token = token;
        this.parentToken = parentToken;
        this.authenticationStatus = authenticationStatus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public String getParentToken() {
        return parentToken;
    }

    public void setParentToken(String Token) {
        this.parentToken = parentToken;
    }

    public AuthenticationStatus getAuthenticationStatus() {
        return authenticationStatus;
    }

   

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final LocalAuthorization other = (LocalAuthorization) obj;
        return Objects.equals(this.token, other.token);
    }

	public void setAuthenticationStatus(AuthenticationStatus status) {
		// TODO Auto-generated method stub
		this.authenticationStatus = authenticationStatus;
		
	}

	
}
