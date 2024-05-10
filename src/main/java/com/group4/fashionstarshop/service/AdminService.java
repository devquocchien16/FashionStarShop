package com.group4.fashionstarshop.service;


import java.util.List;

import com.group4.fashionstarshop.dto.AdminDTO;
import com.group4.fashionstarshop.dto.AdminRegister;
import com.group4.fashionstarshop.dto.CategoryDTO;
import com.group4.fashionstarshop.dto.ImageConfirmDTO;
import com.group4.fashionstarshop.dto.ProductConfirmDTO;
import com.group4.fashionstarshop.dto.SellerEnabledDTO;
import com.group4.fashionstarshop.dto.UserDTO;
import com.group4.fashionstarshop.dto.UserEnabledDTO;
import com.group4.fashionstarshop.dto.VariantImageDTO;
import com.group4.fashionstarshop.model.Admin;
import com.group4.fashionstarshop.model.Category;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.dto.StoreDTO;
import com.group4.fashionstarshop.dto.StoreEnableDTO;
import com.group4.fashionstarshop.dto.StoreEnabledDTO;
import com.group4.fashionstarshop.payload.StoreResponse;
import com.group4.fashionstarshop.request.CategoryRequest;
import com.group4.fashionstarshop.request.ProductConfirmRequest;
import com.group4.fashionstarshop.request.StoreNameProcessRequest;
import com.group4.fashionstarshop.request.StoreRequest;
import com.group4.fashionstarshop.request.UserRequest;

public interface AdminService {
	String login(AdminDTO adminDto);
	Admin register(AdminRegister adminRegister);
	List<UserEnabledDTO> listUsers();
	List<StoreEnableDTO> listStores();
	List<SellerEnabledDTO> listSellers();
	void blockUsers(List<Long> ids);
	void unblockUsers(List<Long> ids);
	
	public void blockSellers(List<Long> ids);
	void unblockSellers(List<Long> ids);	
	Category createCategory(CategoryRequest categoryRequest);
	List<CategoryDTO> getCategories();
	List<UserEnabledDTO> getUsersbyClientName(UserRequest userRequest);
	List<UserEnabledDTO> searchUsersByName(String clientName);
	StoreResponse processStoreRequest(StoreNameProcessRequest request, Long store_id);
	List<UserEnabledDTO> searchUsersByNameOrEmail(String keyword);
	List<SellerEnabledDTO> searchUsersBySellerNameOrEmail(String keyword);
	List<StoreDTO> searchStoreName(String keyword);
	List<StoreDTO> findInactiveStores();
	StoreEnabledDTO confirmStoreRequest(StoreEnabledDTO storeRequest, Long store_id);
	
	List<ProductConfirmDTO> findProductInActive();
	ProductConfirmRequest confirmProductRequest(ProductConfirmRequest productRequest, Long product_id);
	List<ImageConfirmDTO> listImagesOfVariant(Long variant_id);
	void confirmImages(List<ImageConfirmDTO> imageConfirmDTOs);
	void confirmAllImagesOfVariant(Long variantId);
	List<VariantImageDTO> getAllVarriantsConfirm();
}
