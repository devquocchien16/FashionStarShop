package com.group4.fashionstarshop.dto;

import lombok.Data;

@Data
public class PasswordRequestUtil {
	 private String email;
	 private String oldPassword;
	 private String newPassword;
}
