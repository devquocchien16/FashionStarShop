package com.group4.fashionstarshop.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RatingDTO {
    private double percentage;
    private int count;
}