package com.group4.fashionstarshop.service;


import java.util.Date;
import java.util.List;

import com.group4.fashionstarshop.dto.AdminDTO;
import com.group4.fashionstarshop.dto.AdminRegister;
import com.group4.fashionstarshop.dto.CategoryDTO;
import com.group4.fashionstarshop.dto.ImageConfirmDTO;
import com.group4.fashionstarshop.dto.OrderDTO;
import com.group4.fashionstarshop.dto.ProductConfirmDTO;
import com.group4.fashionstarshop.dto.ProductDTO;
import com.group4.fashionstarshop.dto.SellerEnabledDTO;
import com.group4.fashionstarshop.dto.StoreActiveDTO;
import com.group4.fashionstarshop.dto.UserDTO;
import com.group4.fashionstarshop.dto.UserEnabledDTO;
import com.group4.fashionstarshop.dto.VariantDTO;
import com.group4.fashionstarshop.dto.VariantImageDTO;
import com.group4.fashionstarshop.model.Admin;
import com.group4.fashionstarshop.model.Attribute;
import com.group4.fashionstarshop.model.Category;
import com.group4.fashionstarshop.model.Store;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.dto.StoreDTO;
import com.group4.fashionstarshop.dto.StoreEnableDTO;
import com.group4.fashionstarshop.dto.StoreEnabledDTO;
import com.group4.fashionstarshop.dto.StoreRegisterDTO;
import com.group4.fashionstarshop.payload.StoreResponse;
import com.group4.fashionstarshop.request.CategoryRequest;
import com.group4.fashionstarshop.request.ProductConfirmRequest;
import com.group4.fashionstarshop.request.StoreDeclinedRequest;
import com.group4.fashionstarshop.request.StoreNameProcessRequest;
import com.group4.fashionstarshop.request.StoreRequest;
import com.group4.fashionstarshop.request.UserRequest;

public interface AdminService {
	String login(AdminDTO adminDto);
	Admin register(AdminRegister adminRegister);
	//Seller services
	List<StoreEnableDTO> listStores();
	List<SellerEnabledDTO> listSellers();
	public void blockSellers(List<Long> ids);
	void unblockSellers(List<Long> ids);
	//Category services
	Category createCategory(CategoryRequest categoryRequest);
	List<CategoryDTO> getCategories();
	//user Services
	void blockUsers(List<Long> ids);
	void unblockUsers(List<Long> ids);
	List<UserEnabledDTO> listUsers();
	List<UserEnabledDTO> getUsersbyClientName(UserRequest userRequest);
	List<UserEnabledDTO> searchUsersByName(String clientName);
	List<UserEnabledDTO> searchUsersByNameOrEmail(String keyword);
	//Store services
	StoreResponse processStoreRequest(StoreNameProcessRequest request, Long store_id);
	List<SellerEnabledDTO> searchUsersBySellerNameOrEmail(String keyword);
	List<StoreDTO> searchStoreName(String keyword);
	List<StoreRegisterDTO> findInactiveStores();
	List<StoreRegisterDTO> findActiveStores();
	StoreRegisterDTO getStoreRegisterById(Long store_id);
	//confirm and rejected store creation
	StoreEnabledDTO confirmStoreRequest(StoreEnabledDTO storeRequest, Long store_id);
	//confirm and rejected product creation
	List<ProductConfirmDTO> findProductInActive();
	List<ProductConfirmDTO> findProductActive();
	ProductConfirmRequest confirmProductRequest(ProductConfirmRequest productRequest, Long product_id);
	
	//Image confirm DTO
	List<ImageConfirmDTO> listImagesOfVariant(Long variant_id);
	void confirmImages(List<ImageConfirmDTO> imageConfirmDTOs);
	void confirmAllImagesOfVariant(Long variantId);
	List<VariantImageDTO> getAllVarriantsConfirm();
	List<VariantImageDTO> getVariantsByProductId(Long productId);
	//order service
	List<OrderDTO> getOrdersByStoreId(Long store_id);
	List<StoreActiveDTO> getListEditingNameActiveStore();
	StoreActiveDTO findEditingNameActiveStoreById(Long storeId);
	
	List<Attribute> getAttributesByProductId(Long product_id);
//	StoreActiveDTO confirmEditingNameAndLogo(StoreActiveDTO request, Long storeId);
	
	List<ProductDTO> getListProducNeedEdit();
	ProductDTO findDescActiveByProductId(Long product_id);
}
