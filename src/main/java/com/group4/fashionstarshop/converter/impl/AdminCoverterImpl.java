package com.group4.fashionstarshop.converter.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.group4.fashionstarshop.converter.AdminConverter;
import com.group4.fashionstarshop.dto.AdminDTO;
import com.group4.fashionstarshop.model.Admin;
@Component
public class AdminCoverterImpl implements AdminConverter {

	@Override
	public AdminDTO convertEntityToDTO(Admin admin) {
		AdminDTO result = new AdminDTO();
        BeanUtils.copyProperties(admin, result);
        return result;
	}

}
