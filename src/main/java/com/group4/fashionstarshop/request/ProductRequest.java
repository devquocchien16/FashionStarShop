package com.group4.fashionstarshop.request;

import java.util.List;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
	private String title;
	private String description;
	private String mainPicture;
	private List<String> imageList;
	private String status;
	private Long categoryId;
	private Long storeCategoryId;
	private Long storeId;
}
