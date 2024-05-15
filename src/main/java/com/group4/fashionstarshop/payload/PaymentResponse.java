package com.group4.fashionstarshop.payload;

import lombok.Data;

@Data
public class PaymentResponse {
	private String payment_url;

	  private String error_message;
	  
	  
	    public void setPayment_url(String payment_url) {
	        this.payment_url = payment_url;
	    }
	    public void setError_message(String error_message) {
	        this.error_message = error_message;
	    }
}
