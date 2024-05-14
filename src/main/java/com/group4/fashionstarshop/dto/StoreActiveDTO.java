package com.group4.fashionstarshop.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.group4.fashionstarshop.model.Seller;

import lombok.Data;

@Data
public class StoreActiveDTO {
    private Long id;
    private String name;
    private String logo;
    private String evidence;
    private String editingName;
    private boolean status;
    private String adminReply;
    private boolean type;
    private String rejectedReason;
    private String description;
    private SellerDTO sellerDTO;
}
