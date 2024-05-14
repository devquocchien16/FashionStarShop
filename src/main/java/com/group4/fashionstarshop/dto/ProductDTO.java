package com.group4.fashionstarshop.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import com.group4.fashionstarshop.model.Image;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ProductDTO {
    private Long id;
    private String title;
    private String description;
    private String mainPicture;
    private List<ImageDTO> imageList;
    private Boolean status;
    private Date createAt;
    private Date updatedAt;
    private StoreDTO store;
    private Long storeCategoryId; 
}
