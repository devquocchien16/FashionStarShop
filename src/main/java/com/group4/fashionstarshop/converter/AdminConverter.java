package com.group4.fashionstarshop.converter;

import com.group4.fashionstarshop.dto.AdminDTO;
import com.group4.fashionstarshop.model.Admin;

public interface AdminConverter {
	AdminDTO convertEntityToDTO(Admin admin);
}
