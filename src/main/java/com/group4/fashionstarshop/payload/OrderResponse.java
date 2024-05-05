package com.group4.fashionstarshop.payload;

import java.util.List;

import com.group4.fashionstarshop.dto.OrderDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
	List<OrderDTO> content;
}
