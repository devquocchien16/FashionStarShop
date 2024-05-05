package com.group4.fashionstarshop.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerRegisterDTO {
    private String sellerName;
    private String email;
    private String password;
    private String phone;
    private String birthDay;
    private String confirmPassword;
}
