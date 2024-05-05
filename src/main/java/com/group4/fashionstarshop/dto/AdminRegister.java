package com.group4.fashionstarshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminRegister {
	private String name;
	private String email;
	private String password;
	private String confirmPassword;
	private String role;
}
