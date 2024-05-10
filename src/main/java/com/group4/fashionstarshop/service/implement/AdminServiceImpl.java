package com.group4.fashionstarshop.service.implement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.group4.fashionstarshop.configuration.security.JwtTokenUtil;
import com.group4.fashionstarshop.configuration.security.JwtUserDetailsService;
import com.group4.fashionstarshop.converter.StoreConverter;
import com.group4.fashionstarshop.dto.AdminDTO;
import com.group4.fashionstarshop.dto.AdminRegister;
import com.group4.fashionstarshop.dto.CategoryDTO;
import com.group4.fashionstarshop.dto.UserDTO;
import com.group4.fashionstarshop.dto.UserEnabledDTO;
import com.group4.fashionstarshop.enums.Role;
import com.group4.fashionstarshop.model.Admin;
import com.group4.fashionstarshop.model.Category;
import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.repository.CategoryRepository;
import com.group4.fashionstarshop.repository.UserRepository;
import com.group4.fashionstarshop.model.Store;
import com.group4.fashionstarshop.payload.StoreResponse;
import com.group4.fashionstarshop.repository.AdminRepository;
import com.group4.fashionstarshop.repository.StoreRepository;
import com.group4.fashionstarshop.request.StoreNameProcessRequest;
import com.group4.fashionstarshop.request.StoreRequest;
import com.group4.fashionstarshop.service.AdminService;
@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    
    @Autowired
    private StoreRepository storeRepository;
    
    @Autowired
    private StoreConverter storeConverter;
    
	@Override
	public String login(AdminDTO adminDto) {
	    Admin admin = adminRepository.findByEmail(adminDto.getEmail());

        if(admin == null){
            throw new UsernameNotFoundException("Admin not found");
        }
        if(!passwordEncoder.matches(adminDto.getPassword(), admin.getPassword())){
            throw new AuthenticationServiceException("Wrong password");
        }
        return jwtTokenUtil.generateAdminToken(admin);
	}
	@Override
	public Admin register(AdminRegister adminRegister) {
		 jwtUserDetailsService.loadAdminByEmail(adminRegister.getEmail());
 	    String password = adminRegister.getPassword();
 	    String confirmPassword = adminRegister.getConfirmPassword(); // Added line to retrieve confirm password
 	    if (!password.equals(confirmPassword)) {
 	        throw new IllegalArgumentException("Password and confirm password do not match");
 	    }

 	    Admin admin = new Admin();
 	    admin.setName(adminRegister.getName());
 	    admin.setEmail(adminRegister.getEmail());
 	    admin.setPassword(passwordEncoder.encode(password));
 	    admin.setRole("ROLE_".concat(Role.ADMIN.toString()));
 	    adminRepository.save(admin);
 	    return admin;
	}
	@Override
	public List<UserEnabledDTO> listUsers() {
		 	List<User> userList = userRepository.findAll();
		    List<UserEnabledDTO> userDTOList = new ArrayList<>();

		    for (User user : userList) {
		        UserEnabledDTO userDTO = new UserEnabledDTO();
		        userDTO.setId(user.getId());
		        userDTO.setClientName(user.getClientName());
		        userDTO.setEmail(user.getEmail());
		        userDTO.setPhone(user.getPhone());
		        userDTO.setEnabled(user.isEnabled());
		        userDTOList.add(userDTO);
		    }

		    return userDTOList;
	}
	 public void blockUsers(List<Long> ids) {
	        List<User> users = userRepository.findAllByIdsIn(ids);
	        for (User user : users) {
	            user.setEnabled(false);
	        }
	        userRepository.saveAll(users); // Lưu tất cả các thay đổi trong một lần
	 }
	@Override
	public Category createCategory(CategoryDTO categoryDTO) {
	       Category category = new Category();
	        category.setName(categoryDTO.getName());
	        categoryRepository.save(category);
	        return category;
	}
	@Override
	public List<CategoryDTO> getCategories() {
		List<Category> categories = categoryRepository.findAll();
	    List<CategoryDTO> dtoList = new ArrayList<>();

	    for (Category category : categories) {
	        CategoryDTO categoryDTO = new CategoryDTO();
	        categoryDTO.setId(category.getId());
	        categoryDTO.setName(category.getName());
	        dtoList.add(categoryDTO);
	    }
	    return dtoList;
	}
	//process change store name
	@Override
	public void processStoreRequest(StoreNameProcessRequest request, Long store_id) {
		Store store = storeRepository.findById(store_id).orElse(null);
		if("APPROVE".equals(request.getStatus())) {
			store.setName(store.getEditingName());
			store.setStatus(true);
		}
		else {
			store.setAdminReply(request.getReason());
			store.setEditingName(null);
		}		
	}
	@Override
	public AdminDTO finById(Long admin_id) {
		Admin admin = adminRepository.findById(admin_id).orElseThrow(null);
		AdminDTO adminDTO = new AdminDTO();
		adminDTO.setEmail(admin.getEmail());
		adminDTO.setPassword(admin.getPassword());
		return adminDTO;
	}

}
