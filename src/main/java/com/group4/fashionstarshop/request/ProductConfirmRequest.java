package com.group4.fashionstarshop.request;

import java.util.Date;

import com.group4.fashionstarshop.dto.ProductConfirmDTO;
import com.group4.fashionstarshop.dto.StoreDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductConfirmRequest {
    private String title;
    private String description;
    private String mainPicture;
    private boolean status;
    private Date createAt;
    private Date updatedAt;   
    private String adminReply;
    private StoreDTO storeDTO;
}
