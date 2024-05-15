package com.group4.fashionstarshop.sellercontroller;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.group4.fashionstarshop.dto.CategoryDTO;
import com.group4.fashionstarshop.dto.CommissionDTO;
import com.group4.fashionstarshop.dto.ImageConfirmDTO;
import com.group4.fashionstarshop.dto.OrderDTO;
import com.group4.fashionstarshop.dto.ProductConfirmDTO;
import com.group4.fashionstarshop.dto.ProductDTO;
import com.group4.fashionstarshop.dto.SellerEnabledDTO;
import com.group4.fashionstarshop.dto.StoreActiveDTO;
import com.group4.fashionstarshop.dto.UserEnabledDTO;
import com.group4.fashionstarshop.dto.VariantDTO;
import com.group4.fashionstarshop.dto.VariantImageDTO;
import com.group4.fashionstarshop.model.Admin;
import com.group4.fashionstarshop.model.Attribute;
import com.group4.fashionstarshop.model.Category;
import com.group4.fashionstarshop.model.Order;
import com.group4.fashionstarshop.model.RejectedReason;
import com.group4.fashionstarshop.model.Store;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.request.UserIdsWrapper;
import com.group4.fashionstarshop.request.UserRequest;
import com.group4.fashionstarshop.service.AdminService;
import com.group4.fashionstarshop.converter.AdminConverter;
import com.group4.fashionstarshop.dto.AdminDTO;
import com.group4.fashionstarshop.dto.AttributeDTO;
import com.group4.fashionstarshop.dto.StoreDTO;
import com.group4.fashionstarshop.dto.StoreEnableDTO;
import com.group4.fashionstarshop.dto.StoreEnabledDTO;
import com.group4.fashionstarshop.dto.StoreRegisterDTO;
import com.group4.fashionstarshop.dto.UserDTO;
import com.group4.fashionstarshop.payload.StoreResponse;
import com.group4.fashionstarshop.repository.AdminRepository;
import com.group4.fashionstarshop.repository.ReasonRepository;
import com.group4.fashionstarshop.repository.UserRepository;
import com.group4.fashionstarshop.request.AttributeRequest;
import com.group4.fashionstarshop.request.CategoryRequest;
import com.group4.fashionstarshop.request.ProductConfirmRequest;
import com.group4.fashionstarshop.request.StoreDeclinedRequest;
import com.group4.fashionstarshop.request.StoreNameProcessRequest;
import com.group4.fashionstarshop.request.StoreRequest;
import com.group4.fashionstarshop.service.AdminService;
import com.group4.fashionstarshop.service.AttributeService;
import com.group4.fashionstarshop.service.OrderService;
import com.group4.fashionstarshop.service.ProductService;
import com.group4.fashionstarshop.service.StoreService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.persistence.EntityNotFoundException;

@RestController
@CrossOrigin(origins = "http://localhost:3001")
@RequestMapping("api/admins")
public class AdminController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ReasonRepository reasonRepository;
	@Autowired
	private AdminConverter adminConverter;
	
	   @GetMapping("/{admin_id}")
	    public ResponseEntity<AdminDTO> getUser(@PathVariable("admin_id") Long adminId){
	        Admin admin = adminRepository.findById(adminId)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	        AdminDTO adminDTO = adminConverter.convertEntityToDTO(admin);
	        return ResponseEntity.ok(adminDTO);
	    }
	  @GetMapping("/users")
	    public ResponseEntity<List<UserEnabledDTO>> getUsers() {
	        List<UserEnabledDTO> userEnabled = adminService.listUsers();
	        return new ResponseEntity<>(userEnabled, HttpStatus.OK);
	    }
	  @GetMapping("/sellers")
	    public ResponseEntity<List<SellerEnabledDTO>> getSellers() {
	        List<SellerEnabledDTO> userEnabled = adminService.listSellers();
	        return new ResponseEntity<>(userEnabled, HttpStatus.OK);
	    }
//	  @GetMapping("/admins/stores")
//	    public ResponseEntity<List<StoreEnableDTO>> getStores() {
//	        List<StoreEnableDTO> userEnabled = adminService.listStores();
//	        return new ResponseEntity<>(userEnabled, HttpStatus.OK);
//	    }
	  	@PutMapping("/block/users")
	    public void blockUsers(@RequestParam List<Long> ids) {
	        adminService.blockUsers(ids);
	    }
		@PutMapping("/unblock/users")
	    public void unblockUsers(@RequestParam List<Long> ids) {
	        adminService.unblockUsers(ids);
	    }
		@PutMapping("/block/sellers")
	    public void blockSellers(@RequestParam List<Long> ids) {
	        adminService.blockSellers(ids);
	    }
		@PutMapping("/unblock/sellers")
	    public void unblockSellers(@RequestParam List<Long> ids) {
	        adminService.unblockSellers(ids);
	    }
	    @GetMapping("/categories")
	    public ResponseEntity<List<CategoryDTO>> getCategories(){
	    	List<CategoryDTO> categoryDTOs = adminService.getCategories();
	    	return new ResponseEntity<>(categoryDTOs, HttpStatus.OK);
	    }
	    
	    @PostMapping("/categories")
	    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequest categoryRequest) {
	        Category createdCategory = adminService.createCategory(categoryRequest);
	        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
	    }

    @GetMapping("/admins/users/search")
    public List<UserEnabledDTO> searchUsersByName(@RequestParam String keyword) {
        List<UserEnabledDTO> users = adminService.searchUsersByNameOrEmail(keyword);
        return users;
    }
    @GetMapping("/sellers/search")
    public List<SellerEnabledDTO> searchSellersByName(@RequestParam String keyword) {
        List<SellerEnabledDTO> users = adminService.searchUsersBySellerNameOrEmail(keyword);
        return users;
    }
    @GetMapping("/stores")
    public List<StoreRegisterDTO> findStoreStatusFalse(){
    	return adminService.findInactiveStores();
    }
    @GetMapping("/stores/active")
    public List<StoreRegisterDTO> findStoreStatusTrue(){
    	return adminService.findActiveStores();
    }
    @PostMapping("/stores/{store_id}/confirm")
    public ResponseEntity<StoreEnabledDTO> confirmStoreRequest(@RequestBody StoreEnabledDTO storeRequest, @PathVariable("store_id") Long storeId) {
        try {
            StoreEnabledDTO confirmedStore = adminService.confirmStoreRequest(storeRequest, storeId);
            return ResponseEntity.ok(confirmedStore);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
    @GetMapping("/products")
    public List<ProductConfirmDTO> findProductsStatusFalse(){
    	return adminService.findProductInActive();
    }
    @GetMapping("/products/active")
    public List<ProductConfirmDTO> findProductsStatusTrue(){
    	return adminService.findProductActive();
    }
    @PostMapping("/products/{product_id}/confirm")
    public ResponseEntity<ProductConfirmRequest> confirmProductRequest(@RequestBody ProductConfirmRequest request, @PathVariable("product_id") Long productId) {
        try {
            ProductConfirmRequest confirmedProduct = adminService.confirmProductRequest(request, productId);
            return ResponseEntity.ok(confirmedProduct);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/variants/{variant_id}/images")
    public List<ImageConfirmDTO> listImagesOfVariant(@PathVariable("variant_id") Long variantId){
        return adminService.listImagesOfVariant(variantId);
    }
    @PostMapping("/variants/{variant_id}/confirmAllImages")
    public void confirmAllImagesOfVariant(@PathVariable("variant_id") Long variantId) {
        adminService.confirmAllImagesOfVariant(variantId);
    }
    @GetMapping("/variants")
    public List<VariantImageDTO> getAllVariantsConfirm() {
        return adminService.getAllVarriantsConfirm();
    }
    @GetMapping("/users/count/enabled")
    public Long countEnabledUsers() {
        return userRepository.countByEnabled(true);
    }
    @GetMapping("/product/{product_id}/variants")
    public ResponseEntity<List<VariantImageDTO>> getVariantsByProductId(@PathVariable("product_id") Long productId) {
        List<VariantImageDTO> variants = adminService.getVariantsByProductId(productId);
        if (variants.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(variants, HttpStatus.OK);
        }
    }
    @GetMapping("/reasons")
    public List<RejectedReason> listReasons(){
    	return reasonRepository.findAll();
    }
    @GetMapping("/stores/{store_id}")
    public ResponseEntity<StoreRegisterDTO> getStoreRegisterById(@PathVariable Long store_id) {
        StoreRegisterDTO storeRegisterDTO = adminService.getStoreRegisterById(store_id);
        
        if (storeRegisterDTO != null) {
            return new ResponseEntity<>(storeRegisterDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/stores/order/{store_id}")
    public List<OrderDTO> getOrdersByStoreId(@PathVariable("store_id") Long storeId) {
        return adminService.getOrdersByStoreId(storeId);
    }
    @GetMapping("/calculate-commission")
    public Double calculateCommission(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate, 
                                             @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate, 
                                             @RequestParam("storeId") Long storeId) {
        Double commission = orderService.calculateCommission(startDate, endDate, storeId);
        System.out.println(commission);
        return commission;
    }
    @GetMapping("/calculate-revenue")
    public Double calculateStoreRevenue(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam("storeId") Long storeId) {
        return orderService.calculateStoreRevenue(startDate, endDate, storeId);
    }
    @GetMapping("/stores/orders")
    public List<OrderDTO> getOrdersByDateRangeAndStoreId(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam("storeId") Long storeId) {
        return orderService.findOrdersByCreatedAtBetweenAndStoreId(startDate, endDate, storeId);
    }
    @GetMapping("/stores/editing-name-active")
    public List<StoreActiveDTO> getListEditingNameActiveStore() {
        return adminService.getListEditingNameActiveStore();
    }
    @GetMapping("/stores/{storeId}/editing-name")
    public ResponseEntity<StoreActiveDTO> getActiveStoreById(@PathVariable Long storeId) {
        StoreActiveDTO storeDTO = adminService.findEditingNameActiveStoreById(storeId);
        if (storeDTO != null) {
            return new ResponseEntity<>(storeDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/products/{productId}/attributes")
    public ResponseEntity<List<Attribute>> getAttributesByProductId(@PathVariable Long productId) {
        List<Attribute> attributes = adminService.getAttributesByProductId(productId);
        if (attributes != null && !attributes.isEmpty()) {
            return new ResponseEntity<>(attributes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/products/need-edit")
    public ResponseEntity<List<ProductDTO>> getProductsNeedEdit() {
        List<ProductDTO> productDTOs = adminService.getListProducNeedEdit();
        
        if (!productDTOs.isEmpty()) {
            return new ResponseEntity<>(productDTOs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/products/{product_id}/need-edit/")
    public ResponseEntity<ProductDTO> getProductDetails(@PathVariable Long product_id) {
        ProductDTO productDTO = adminService.findDescActiveByProductId(product_id);
        
        if (productDTO != null) {
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
