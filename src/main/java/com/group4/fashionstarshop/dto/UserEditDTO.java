package com.group4.fashionstarshop.dto;

import java.util.Date;

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
public class UserEditDTO {
	
    private String clientName;
    private String phone;
    private boolean gender;
    private Date birthday;
}
