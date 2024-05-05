package com.group4.fashionstarshop.request;

import java.util.List;

import org.hibernate.bytecode.internal.bytebuddy.PrivateAccessorException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VariantRequest {   
    private String skuCode;
    private int stockQuantity;
    private double price;
    private String name;
    private double weight;
    private double salePrice;
    private String img;   
    private Boolean isDeleted;
    private Long productId;
    private List<Long> optionValueIds;
}
