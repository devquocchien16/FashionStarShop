package com.group4.fashionstarshop.request;

import java.util.List;

import lombok.Data;


@Data
public class FindVariantRequest {
 private List<Long> optionValueIds;
}
