package com.group4.fashionstarshop.dto;

import com.group4.fashionstarshop.model.StoreCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreCategoryDTO {
	private Long id;
	private String name;
	private CategoryDTO categoryDTO; // DTO for parent category
	
}
