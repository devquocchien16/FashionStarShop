package com.group4.fashionstarshop.dto;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerDTO {
    private Long id;
    private String sellerName;
    private String email;
    private String password;
    private Double balance;
    private String phone;  
    private Date birthDay;
    private List<AddressDTO> addressDTOs;
}
