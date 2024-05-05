package com.group4.fashionstarshop.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequest {
	private Long id;
	private Long userId;
	private String district;
	private String ward;
	private String city;
	private String street;
	private boolean defaultAddress;
}
