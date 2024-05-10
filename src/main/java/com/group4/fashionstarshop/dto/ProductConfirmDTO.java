package com.group4.fashionstarshop.dto;

import java.util.Date;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductConfirmDTO {
    private Long id;

    private String title;
    private String description;
    private String mainPicture;
    private boolean status;
    private Date createAt;
    private Date updatedAt;   
    private String adminReply;
    private StoreDTO storeDTO;
}
