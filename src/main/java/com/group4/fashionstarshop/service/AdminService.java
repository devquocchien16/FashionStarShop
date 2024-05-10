package com.group4.fashionstarshop.service;


import java.util.List;

import com.group4.fashionstarshop.dto.AdminDTO;
import com.group4.fashionstarshop.dto.AdminRegister;
import com.group4.fashionstarshop.dto.CategoryDTO;
import com.group4.fashionstarshop.dto.UserDTO;
import com.group4.fashionstarshop.dto.UserEnabledDTO;
import com.group4.fashionstarshop.model.Admin;
import com.group4.fashionstarshop.model.Category;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.dto.StoreDTO;
import com.group4.fashionstarshop.payload.StoreResponse;
import com.group4.fashionstarshop.request.StoreNameProcessRequest;
import com.group4.fashionstarshop.request.StoreRequest;

public interface AdminService {
	String login(AdminDTO adminDto);
	Admin register(AdminRegister adminRegister);
	List<UserEnabledDTO> listUsers();
	public void blockUsers(List<Long> ids);
	Category createCategory(CategoryDTO categoryDTO);
	List<CategoryDTO> getCategories();
	void processStoreRequest(StoreNameProcessRequest request, Long store_id);
	AdminDTO finById(Long admin_id);
}
