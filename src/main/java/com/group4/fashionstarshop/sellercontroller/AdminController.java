package com.group4.fashionstarshop.sellercontroller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.group4.fashionstarshop.dto.ProductDTO;
import com.group4.fashionstarshop.dto.SellerEnabledDTO;
import com.group4.fashionstarshop.dto.UserEnabledDTO;
import com.group4.fashionstarshop.model.Admin;
import com.group4.fashionstarshop.model.Category;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.request.UserIdsWrapper;
import com.group4.fashionstarshop.request.UserRequest;
import com.group4.fashionstarshop.service.AdminService;
import com.group4.fashionstarshop.converter.AdminConverter;
import com.group4.fashionstarshop.dto.AdminDTO;
import com.group4.fashionstarshop.dto.AttributeDTO;
import com.group4.fashionstarshop.dto.StoreDTO;
import com.group4.fashionstarshop.dto.StoreEnableDTO;
import com.group4.fashionstarshop.dto.UserDTO;
import com.group4.fashionstarshop.payload.StoreResponse;
import com.group4.fashionstarshop.repository.AdminRepository;
import com.group4.fashionstarshop.request.AttributeRequest;
import com.group4.fashionstarshop.request.CategoryRequest;
import com.group4.fashionstarshop.request.StoreNameProcessRequest;
import com.group4.fashionstarshop.request.StoreRequest;
import com.group4.fashionstarshop.service.AdminService;
import com.group4.fashionstarshop.service.AttributeService;
import com.group4.fashionstarshop.service.ProductService;
import com.group4.fashionstarshop.service.StoreService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@CrossOrigin(origins = "http://localhost:3001")
@RequestMapping("api")
public class AdminController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private AdminConverter adminConverter;
	   @GetMapping("/admins/{admin_id}")
	    public ResponseEntity<AdminDTO> getUser(@PathVariable("admin_id") Long adminId){
	        Admin admin = adminRepository.findById(adminId)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	        AdminDTO adminDTO = adminConverter.convertEntityToDTO(admin);
	        return ResponseEntity.ok(adminDTO);
	    }
	  @GetMapping("/admins/users")
	    public ResponseEntity<List<UserEnabledDTO>> getUsers() {
	        List<UserEnabledDTO> userEnabled = adminService.listUsers();
	        return new ResponseEntity<>(userEnabled, HttpStatus.OK);
	    }
	  @GetMapping("/admins/sellers")
	    public ResponseEntity<List<SellerEnabledDTO>> getSellers() {
	        List<SellerEnabledDTO> userEnabled = adminService.listSellers();
	        return new ResponseEntity<>(userEnabled, HttpStatus.OK);
	    }
	  @GetMapping("/admins/stores")
	    public ResponseEntity<List<StoreEnableDTO>> getStores() {
	        List<StoreEnableDTO> userEnabled = adminService.listStores();
	        return new ResponseEntity<>(userEnabled, HttpStatus.OK);
	    }
	  	@PutMapping("/admins/block/users")
	    public void blockUsers(@RequestParam List<Long> ids) {
	        adminService.blockUsers(ids);
	    }
		@PutMapping("/admins/unblock/users")
	    public void unblockUsers(@RequestParam List<Long> ids) {
	        adminService.unblockUsers(ids);
	    }
		@PutMapping("/admins/block/sellers")
	    public void blockSellers(@RequestParam List<Long> ids) {
	        adminService.blockSellers(ids);
	    }
		@PutMapping("/admins/unblock/sellers")
	    public void unblockSellers(@RequestParam List<Long> ids) {
	        adminService.unblockSellers(ids);
	    }
	    @GetMapping("/admins/categories")
	    public ResponseEntity<List<CategoryDTO>> getCategories(){
	    	List<CategoryDTO> categoryDTOs = adminService.getCategories();
	    	return new ResponseEntity<>(categoryDTOs, HttpStatus.OK);
	    }
	    
	    @PostMapping("/admins/categories")
	    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequest categoryRequest) {
	        Category createdCategory = adminService.createCategory(categoryRequest);
	        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
	    }
	    

    @GetMapping("/admins/users/search")
    public List<UserEnabledDTO> searchUsersByName(@RequestParam String keyword) {
        List<UserEnabledDTO> users = adminService.searchUsersByNameOrEmail(keyword);
        return users;
    }
    @GetMapping("/admins/sellers/search")
    public List<SellerEnabledDTO> searchSellersByName(@RequestParam String keyword) {
        List<SellerEnabledDTO> users = adminService.searchUsersBySellerNameOrEmail(keyword);
        return users;
    }
   
}
