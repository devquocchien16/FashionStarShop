package com.group4.fashionstarshop.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.group4.fashionstarshop.model.Variant;

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
public class ImageConfirmDTO {
    private Long id;
    private String imgPath;
    private boolean status;
    private VariantDTO variantDTO;
}
