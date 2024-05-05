package com.group4.fashionstarshop.converter;


import com.group4.fashionstarshop.dto.SellerDTO;
import com.group4.fashionstarshop.dto.SellerLoginDTO;
import com.group4.fashionstarshop.model.Seller;

import java.util.List;

public interface SellerConverter {
    List<SellerDTO> entitiesToDTOs(List<Seller> element);
    SellerDTO entityToDTO(Seller element);
    Seller dtoToEntity(SellerDTO element);
    SellerDTO convertEntityToDTO(Seller seller);
}
