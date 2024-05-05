package com.group4.fashionstarshop.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {
    private Long id;
    private String district;
    private String ward;
    private String city;
    private String street;
}
