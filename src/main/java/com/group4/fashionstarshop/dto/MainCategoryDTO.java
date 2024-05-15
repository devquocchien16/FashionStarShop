package com.group4.fashionstarshop.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainCategoryDTO {
	private Long id;
    private String name;
    private List<ParentCategoryDTO> parentCategoryDTOList;
}
