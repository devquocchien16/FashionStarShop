package com.group4.fashionstarshop.service.implement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
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
import com.group4.fashionstarshop.dto.ImageConfirmDTO;
import com.group4.fashionstarshop.dto.ImageDTO;
import com.group4.fashionstarshop.dto.ProductConfirmDTO;
import com.group4.fashionstarshop.dto.ProductDTO;
import com.group4.fashionstarshop.dto.SellerDTO;
import com.group4.fashionstarshop.dto.SellerEnabledDTO;
import com.group4.fashionstarshop.dto.StoreDTO;
import com.group4.fashionstarshop.dto.StoreEnableDTO;
import com.group4.fashionstarshop.dto.StoreEnabledDTO;
import com.group4.fashionstarshop.dto.UserDTO;
import com.group4.fashionstarshop.dto.UserEnabledDTO;
import com.group4.fashionstarshop.dto.VariantDTO;
import com.group4.fashionstarshop.dto.VariantImageDTO;
import com.group4.fashionstarshop.enums.Role;
import com.group4.fashionstarshop.model.Admin;
import com.group4.fashionstarshop.model.Category;
import com.group4.fashionstarshop.model.Image;
import com.group4.fashionstarshop.model.Product;
import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.model.Variant;
import com.group4.fashionstarshop.repository.CategoryRepository;
import com.group4.fashionstarshop.repository.ImageRepository;
import com.group4.fashionstarshop.repository.ProductRepository;
import com.group4.fashionstarshop.repository.SellerRepository;
import com.group4.fashionstarshop.repository.UserRepository;
import com.group4.fashionstarshop.repository.VariantRepository;
import com.group4.fashionstarshop.model.Store;
import com.group4.fashionstarshop.payload.StoreResponse;
import com.group4.fashionstarshop.repository.AdminRepository;
import com.group4.fashionstarshop.repository.StoreRepository;
import com.group4.fashionstarshop.request.CategoryRequest;
import com.group4.fashionstarshop.request.ProductConfirmRequest;
import com.group4.fashionstarshop.request.StoreNameProcessRequest;
import com.group4.fashionstarshop.request.StoreRequest;
import com.group4.fashionstarshop.request.UserRequest;
import com.group4.fashionstarshop.service.AdminService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AdminServiceImpl implements AdminService {

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
	private SellerRepository sellerRepository;
	@Autowired
	private StoreRepository storeRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StoreConverter storeConverter;
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private VariantRepository variantRepository;
	@Override
	public String login(AdminDTO adminDto) {
		Admin admin = adminRepository.findByEmail(adminDto.getEmail());

		if (admin == null) {
			throw new UsernameNotFoundException("Admin not found");
		}
		if (!passwordEncoder.matches(adminDto.getPassword(), admin.getPassword())) {
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
		userRepository.saveAll(users);
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

	// process change store name
	@Override
	public StoreResponse processStoreRequest(StoreNameProcessRequest request, Long store_id) {
		Store store = storeRepository.findById(store_id).orElse(null);
		if ("OK".equals(request.getStatus())) {
			store.setName(store.getEdittingName());
			store.setEdittingName(null);
		} else {
			store.setAdminReply(request.getReason());
		}

		storeRepository.save(store);
		System.out.println(store.getName());
		StoreResponse response = new StoreResponse();
		response.setReason(request.getReason());
		response.setStoreDTO(storeConverter.entityToDTO(store));
		return response;
	}

	@Override
	public List<SellerEnabledDTO> listSellers() {
		List<Seller> userList = sellerRepository.findAll();
		List<SellerEnabledDTO> userDTOList = new ArrayList<>();

		for (Seller seller : userList) {
			SellerEnabledDTO userDTO = new SellerEnabledDTO();
			userDTO.setId(seller.getId());
			userDTO.setSellerName(seller.getSellerName());
			userDTO.setEmail(seller.getEmail());
			userDTO.setPhone(seller.getPhone());
			userDTO.setEnabled(seller.isEnabled());
			userDTOList.add(userDTO);
		}

		return userDTOList;
	}

	@Override
	public void unblockUsers(List<Long> ids) {
		List<User> users = userRepository.findAllByIdsIn(ids);
		for (User user : users) {
			user.setEnabled(true);
		}
		userRepository.saveAll(users);
	}

	@Override
	public void blockSellers(List<Long> ids) {
		List<Seller> sellers = sellerRepository.findAllByIdsIn(ids);
		for (Seller seller : sellers) {
			seller.setEnabled(false);
		}
		sellerRepository.saveAll(sellers);

	}

	@Override
	public void unblockSellers(List<Long> ids) {
		List<Seller> sellers = sellerRepository.findAllByIdsIn(ids);
		for (Seller seller : sellers) {
			seller.setEnabled(true);
		}
		sellerRepository.saveAll(sellers);

	}

	@Override
	public List<StoreEnableDTO> listStores() {
		List<Store> storeList = storeRepository.findAll();
		List<StoreEnableDTO> storeDTOList = new ArrayList<>();

		for (Store store : storeList) {
			StoreEnableDTO storeDTO = new StoreEnableDTO();
			storeDTO.setId(store.getId());
			storeDTO.setName(store.getName());
			storeDTO.setStatus(store.isStatus());

			// Tạo một đối tượng SellerDTO từ thông tin của Seller
			Seller seller = store.getSeller();
			SellerDTO sellerDTO = new SellerDTO();
			sellerDTO.setSellerName(seller.getSellerName());

			// Gán đối tượng SellerDTO cho storeDTO
			storeDTO.setSellerDTO(sellerDTO);

			storeDTOList.add(storeDTO);
		}

		return storeDTOList;
	}

	@Override
	public Category createCategory(CategoryRequest categoryRequest) {
		Category category = new Category();
		category.setName(categoryRequest.getName());
		categoryRepository.save(category);
		return category;
	}

	@Override
	public List<UserEnabledDTO> getUsersbyClientName(UserRequest userRequest) {
		String clientName = userRequest.getClientName();

		Optional<User> users = userRepository.findUsersByClientName(clientName);
		return users.stream().filter(User::isEnabled) // Assuming UserDTO has a method named isEnabled() to check if the
														// user is enabled
				.map(user -> {
					UserEnabledDTO userEnabledDTO = new UserEnabledDTO();
					// Assuming there are setters in UserEnabledDTO to set its properties
					userEnabledDTO.setId(user.getId());
					userEnabledDTO.setClientName(user.getClientName());
					// Set other properties as needed
					return userEnabledDTO;
				}).collect(Collectors.toList());
	}

	@Override
	public List<UserEnabledDTO> searchUsersByName(String clientName) {
		List<User> users = userRepository.findByClientNameContainingIgnoreCase(clientName);
		return users.stream().map(this::convertToDTO).collect(Collectors.toList());
	}
	private UserEnabledDTO convertToDTO(User user) {
        UserEnabledDTO userDTO = new UserEnabledDTO();
        userDTO.setId(user.getId());
        userDTO.setClientName(user.getClientName());
        userDTO.setEmail(user.getEmail());
        userDTO.setEnabled(user.isEnabled());
        userDTO.setPhone(user.getPhone());
        return userDTO;
    }

	@Override
	public List<UserEnabledDTO> searchUsersByNameOrEmail(String keyword) {
		List<User> users = userRepository.findByClientNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<SellerEnabledDTO> searchUsersBySellerNameOrEmail(String keyword) {
		List<Seller> users = sellerRepository.findBySellerNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
        return users.stream().map(this::convertSellerToDTO).collect(Collectors.toList());
	}
	private SellerEnabledDTO convertSellerToDTO(Seller seller) {
		SellerEnabledDTO userDTO = new SellerEnabledDTO();
        userDTO.setId(seller.getId());
        userDTO.setSellerName(seller.getSellerName());
        userDTO.setEmail(seller.getEmail());
        userDTO.setEnabled(seller.isEnabled());
        userDTO.setPhone(seller.getPhone());
        return userDTO;
    }
	public List<StoreDTO> findInactiveStores() {
	    List<Store> inactiveStores = storeRepository.findByStatus(false);
	    return storeConverter.entitiesToDTOs(inactiveStores);
	}
	@Override
	public StoreEnabledDTO confirmStoreRequest(StoreEnabledDTO storeRequest, Long store_id) {
	    Store store = storeRepository.findByStoreId(store_id).orElse(null);
	    System.out.println("Find id: " + store.getName() + "Name: "+ store.getName());
	    store.setStatus(true);
	    storeRepository.save(store);

	    System.out.println("Store Confirm: " + store.isStatus() + store.getName());
	    
	    StoreEnabledDTO result = new StoreEnabledDTO();
	    result.setName(store.getName());
	    result.setStatus(store.isStatus());
	    return result;	
	 }
	public List<ProductConfirmDTO> findProductInActive(){
	    List<Product> products = productRepository.findByStatus(false);
	    List<ProductConfirmDTO> productConfirmDTOs = new ArrayList<>();

	    for (Product product : products) {
	        ProductConfirmDTO productConfirmDTO = new ProductConfirmDTO();
	        productConfirmDTO.setId(product.getId());
	        productConfirmDTO.setTitle(product.getTitle());
	        productConfirmDTO.setDescription(product.getDescription());
	        productConfirmDTO.setMainPicture(product.getMainPicture());
	        productConfirmDTO.setCreateAt(product.getCreateAt());
	        productConfirmDTO.setStatus(product.isStatus());

	        StoreDTO storeDTO = new StoreDTO();
	        storeDTO.setName(product.getStore().getName());

	        productConfirmDTO.setStoreDTO(storeDTO);

	        productConfirmDTOs.add(productConfirmDTO);
	    }

	    return productConfirmDTOs;
		
	}
	public ProductConfirmRequest confirmProductRequest(ProductConfirmRequest productRequest, Long product_id) {
		Product product = productRepository.findById(product_id).orElse(null);
		product.setStatus(true);
		product.setAdminReply(productRequest.getAdminReply());
		
		productRepository.save(product);
		
		ProductConfirmRequest request = new ProductConfirmRequest();
		request.setTitle(product.getTitle());
		request.setMainPicture(product.getMainPicture());
		request.setDescription(product.getDescription());
		request.setCreateAt(product.getCreateAt());
		request.setStatus(product.isStatus());
		request.setAdminReply(product.getAdminReply());
		return request;
	}
	public List<VariantImageDTO> getAllVarriantsConfirm(){
	    // Lấy tất cả các biến thể từ cơ sở dữ liệu
        List<Variant> variants = variantRepository.findAll();
        
        // Chuyển đổi các biến thể thành đối tượng VariantDTO
        List<VariantImageDTO> variantDTOs = new ArrayList<>();
        for (Variant variant : variants) {
        	VariantImageDTO variantDTO = new VariantImageDTO();
            variantDTO.setId(variant.getId());
            variantDTO.setName(variant.getName());
            variantDTO.setPrice(variant.getPrice());
            variantDTO.setSkuCode(variant.getSkuCode());
            

            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(variant.getProduct().getId());
            productDTO.setTitle(variant.getProduct().getTitle());
            // Các thuộc tính khác của ProductDTO
            variantDTO.setProductDTO(productDTO);
            
            // Thêm thông tin về List<ImageDTO>
            List<ImageConfirmDTO> imageDTOList = new ArrayList<>();
            for (Image image : variant.getImages()) {
            	ImageConfirmDTO imageDTO = new ImageConfirmDTO();
                imageDTO.setId(image.getId());
                imageDTO.setImgPath(image.getImgPath());
                imageDTO.setStatus(image.isStatus());
                imageDTOList.add(imageDTO);
            }
            variantDTO.setImageDTOList(imageDTOList);
            
            variantDTOs.add(variantDTO);
        }
        
        return variantDTOs;
    
	}
	public List<ImageConfirmDTO> listImagesOfVariant(Long variant_id){
		List<Image> inActiveImages = imageRepository.findImagesByVariant_Id(variant_id);
	    List<ImageConfirmDTO> imageConfirmDTOs = new ArrayList<>();
	    
	    for (Image image : inActiveImages) {
	        ImageConfirmDTO imageConfirmDTO = new ImageConfirmDTO();
	        // Copy properties from Image to ImageConfirmDTO
	        imageConfirmDTO.setId(image.getId());
	        imageConfirmDTO.setImgPath(image.getImgPath());
	        imageConfirmDTO.setStatus(image.isStatus());
	        // Copy other properties as needed
	        
	        imageConfirmDTOs.add(imageConfirmDTO);
	    }
	    
	    return imageConfirmDTOs;
		
	}
	public void confirmImages(List<ImageConfirmDTO> imageConfirmDTOs) {
        for (ImageConfirmDTO imageConfirmDTO : imageConfirmDTOs) {
            if (imageConfirmDTO.isStatus()) { // Kiểm tra trạng thái true
                Image image = imageRepository.findById(imageConfirmDTO.getId()).orElse(null);
                if (image != null) {
                    image.setStatus(true); // Xác nhận hình ảnh
                    imageRepository.save(image);
                }
            }
        }
    }
	 public void confirmAllImagesOfVariant(Long variantId) {
	        List<Image> images = imageRepository.findImagesByVariant_Id(variantId);
	        for (Image image : images) {
	            image.setStatus(true);
	        }
	        imageRepository.saveAll(images);
	    }

	@Override
	public List<StoreDTO> searchStoreName(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}
}
