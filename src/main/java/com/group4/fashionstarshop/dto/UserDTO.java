package com.group4.fashionstarshop.dto;

import java.util.Date;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String clientName;
    private String email;
    private String phone;
    private boolean gender;
    private Date birthday;
}
