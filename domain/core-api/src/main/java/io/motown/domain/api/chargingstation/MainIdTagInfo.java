package io.motown.domain.api.chargingstation;

import java.util.Date;

public class MainIdTagInfo {

	
	public enum AuthenticationStatus {
        ACCEPTED,
        BLOCKED,
        EXPIRED,
        INVALID,
        CONCURRENT_TX,
        DELETED
    }
	
	private AuthenticationStatus status;

	private Date expiryDate;
	
	private String parentIdTag;

	public MainIdTagInfo()
	{
		//do nothing
	}
	public MainIdTagInfo(AuthenticationStatus status, Date expiryDate, String parentIdTag) {
		this.status = status;
		this.expiryDate = expiryDate;
		this.parentIdTag = parentIdTag;
	}
	
	public AuthenticationStatus getStatus() {
		return status;
	}

	public void setStatus(AuthenticationStatus status) {
		this.status = status;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getParentIdTag() {
		return parentIdTag;
	}

	public void setParentIdTag(String parentIdTag) {
		this.parentIdTag = parentIdTag;
	}
   
}
