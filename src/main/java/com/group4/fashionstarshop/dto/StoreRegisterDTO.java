package com.group4.fashionstarshop.dto;

import java.util.List;

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
public class StoreRegisterDTO {
	 private Long id;
	 private String name;
	 private String logo;
	 private String evidence;
	 private String edittingName;
	 private boolean status;
	 private String adminReply;
	 private boolean type;
	 private String rejectedReason;
	 private SellerDTO sellerDTO;
}
