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
public class StoreEnableDTO {
    private Long id;
    private String name;
    private String edittingName;
    private boolean status;
    private String adminReply;
    private SellerDTO sellerDTO;
}
