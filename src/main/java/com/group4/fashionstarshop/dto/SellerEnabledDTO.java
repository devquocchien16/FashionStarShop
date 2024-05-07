package com.group4.fashionstarshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SellerEnabledDTO {
    private Long id;
    private String sellerName;
    private String email;
    private String phone;
    private boolean enabled;
}
