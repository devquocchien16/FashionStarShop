package com.group4.fashionstarshop.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VariantCreateResponse {
    String message;
    Long variant_id;
}
