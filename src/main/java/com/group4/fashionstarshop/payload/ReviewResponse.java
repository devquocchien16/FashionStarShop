package com.group4.fashionstarshop.payload;

import com.group4.fashionstarshop.dto.ReviewDTO;
import com.group4.fashionstarshop.dto.SummaryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private List<ReviewDTO> reviewDTOList;
    private SummaryDTO summaryDto;
}
