package com.group4.fashionstarshop.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RatingBreakdownDTO {
    private RatingDTO fiveStar;
    private RatingDTO fourStar;
    private RatingDTO threeStar;
    private RatingDTO twoStar;
    private RatingDTO oneStar;
}
