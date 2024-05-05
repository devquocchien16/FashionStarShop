package com.group4.fashionstarshop.request;

import java.util.Date;

import lombok.Data;
@Data
public class UpdateSellerRequest {
	private String sellerName;
	private String phone;
	private Date birthDay;
	private String district;
	private String ward;
	private String city;
	private String street;
}
