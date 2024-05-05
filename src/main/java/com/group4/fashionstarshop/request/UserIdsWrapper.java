package com.group4.fashionstarshop.request;

import java.util.Date;
import java.util.List;

import com.group4.fashionstarshop.dto.UserEditDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserIdsWrapper {
	   private List<Long> Ids;

}
