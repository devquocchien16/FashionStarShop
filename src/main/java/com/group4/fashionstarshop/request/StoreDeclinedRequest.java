package com.group4.fashionstarshop.request;

import lombok.Data;

public class StoreDeclinedRequest {
	private String rejectedReason;
	public String getRejectedReason() {
		return rejectedReason;
	}
	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}
	
}
