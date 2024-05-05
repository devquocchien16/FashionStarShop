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
public class UserEnabledDTO {
    private Long id;
    private String clientName;
    private String email;
    private String phone;
    private boolean enabled;
}
