package com.group4.fashionstarshop.service.implement;

import com.group4.fashionstarshop.converter.*;
import com.group4.fashionstarshop.converter.impl.ProductConverterImpl;
import com.group4.fashionstarshop.dto.*;
import com.group4.fashionstarshop.model.*;
import com.group4.fashionstarshop.payload.ProductResponse;
import com.group4.fashionstarshop.repository.CategoryRepository;
import com.group4.fashionstarshop.repository.ImageRepository;
import com.group4.fashionstarshop.repository.ProductRepository;
import com.group4.fashionstarshop.repository.StoreCategoryRepository;
import com.group4.fashionstarshop.repository.StoreRepository;
import com.group4.fashionstarshop.request.ProductConfirmRequest;
import com.group4.fashionstarshop.request.ProductRequest;
import com.group4.fashionstarshop.service.ProductService;
import com.nimbusds.oauth2.sdk.Request;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductConverter productConverter;
	@Autowired
	private VariantConverter variantConverter;
	@Autowired
	private StoreConverter storeConverter;
	@Autowired
	private BulletConverter bulletConverter;
	@Autowired
	private OptionValueConverter optionValueConverter;
	@Autowired
	private OptionTableConverter optionTableConverter;
	@Autowired
	private StoreRepository storeRepository;
	@Autowired
	private StoreCategoryRepository storeCategoryRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductConverterImpl productConverterImpl;
	
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private ImageConverter imageConverter;

@Autowired
private StoreCategoryConverter storeCategoryConverter;
//	@Override
//	public List<ProductDTO> getAllProductDtosByStore(Long store_id) {
//		Store store = storeRepository.findById(store_id).orElse(null);
//		List<Product> products = productRepository.findAllByStore(store);
//		List<ProductDTO> productDTOs = productConverter.entitiesToDTOs(products);
//		return productDTOs;
//	}

	@Override
	public List<ProductDTO> getAllProductDtosByStoreCategory(Long storeCategory_id) {
		StoreCategory storeCategory = storeCategoryRepository.findById(storeCategory_id).orElseThrow();
		List<Product> productsByStoreCategory = productRepository.findAllByStoreCategory(storeCategory);
		List<ProductDTO> productDTOs = productConverter.entitiesToDTOs(productsByStoreCategory);
		return productDTOs;
	}


	@Override
	public StoreDTO getStoreByProductId(Long productId) {
		Product product = productRepository.findById(productId).orElse(new Product());
		Store store = product.getStore();
		StoreDTO storeDto = storeConverter.entityToDTO(store);
		return storeDto;
	}


	@Override
	public ProductDTO createProduct(ProductRequest productRequest, Long storeId) {
		 // Retrieve the Store entity
	    Store store = storeRepository.findById(storeId).orElseThrow(() -> new EntityNotFoundException("Store not found"));

	    // Retrieve the StoreCategory entity
	    StoreCategory storeCategory = storeCategoryRepository.findById(productRequest.getStoreCategoryId())
	            .orElseThrow(() -> new EntityNotFoundException("Store category not found"));

	    // Create a new Product entity
	    Product product = new Product();
	    product.setTitle(productRequest.getTitle());
	    product.setDescription(productRequest.getDescription());
	    product.setMainPicture(productRequest.getMainPicture());
	    product.setStatus(productRequest.isStatus());
	    product.setCreateAt(new Date());
	    product.setUpdatedAt(new Date());
	    product.setStore(store);
	    product.setStoreCategory(storeCategory);
	    productRepository.save(product);
	    
	    // Create Image entities for each image URL and associate them with the product
	    List<Image> imageList = new ArrayList<>();
	    for (String imageURL : productRequest.getImageList()) {
	        Image image = new Image();
	        image.setImgPath(imageURL);
	        image.setProduct(product);
	        imageList.add(image);
	        imageRepository.save(image);	        
	    }
	    product.setImageList(imageList);

	    // Save the product entity
	    Product newProduct = productRepository.save(product);

	    // Convert the saved Product entity to a ProductDTO
	    return productConverter.entityToDTO(newProduct);
	}

	@Override
	public Product updateProduct(ProductRequest productDto) {
		Product product = productConverter.dtoToEntity(productDto);
		return productRepository.save(product);
	}

	@Override
	public ProductDTO getProductDetails(Long productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OptionTableDTO> getProductOptions(Long productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductDTO updateProduct(Long productId, ProductRequest request) {
		Product product = productRepository.findById(productId).orElse(new Product());
		product.setTitle(request.getTitle());
		product.setDescription(request.getDescription());
		StoreCategory storeCategory = storeCategoryRepository.findById(request.getStoreCategoryId()).orElseThrow();
		product.setStoreCategory(storeCategory);
		
		productRepository.save(product);		
		return productConverter.entityToDTO(product);
	}



	 @Override
	    public ProductDTO getProductById(Long id) {
	        Product product = productRepository.findById(id).orElse(new Product()) ;
	        List<Image> images = product.getImageList();
	        List<ImageDTO> imageDTOList = imageConverter.entitiesToDTOs(images);
	       	        
	        ProductDTO productDTO = productConverterImpl.entityToDTO(product);	    
	        productDTO.setStoreCategoryId(product.getStoreCategory().getId());
	        productDTO.setImageList(imageDTOList);	        
	        return productDTO;
	    }

	    @Override
	    public List<ProductDTO> getAllProductDtos() {
	        List<Product> products = productRepository.findAll();
	        List<ProductDTO> productDTOS = productConverterImpl.entitiesToDTOs(products);
	        List<StoreDTO> storeDTOS = new ArrayList<>();
	        for(Product product: products){
	            Store store = product.getStore();
	            StoreDTO storeDto = storeConverter.entityToDTO(store);
	            storeDTOS.add(storeDto);
	        }
	        for(int i = 0; i < products.toArray().length; i++){
	            productDTOS.get(i).setStore(storeDTOS.get(i));
	        }
	        return productDTOS;
	    }

	    @Override
	    public List<ProductDTO> getAllProductDtosByStore(Long id) {
	        Store store = storeRepository.findById(id).orElse(null);
	        List<Product> products = productRepository.findAllByStore(store);
	        List<ProductDTO> productDTOS = productConverterImpl.entitiesToDTOs(products);
	        List<StoreDTO> storeDTOS = new ArrayList<>();
	        for(Product product: products){
	            Store productStore = product.getStore();
	            StoreDTO storeDto = storeConverter.entityToDTO(store);
	            storeDTOS.add(storeDto);
	        }
	        for(int i = 0; i < products.toArray().length; i++){
	            productDTOS.get(i).setStore(storeDTOS.get(i));
	        }
	        return productDTOS;
	    }

	    @Override
	    public List<ProductDTO> getAllProductDtosByStoreCategory(String categoryName) {
	        StoreCategory storeCategory = storeCategoryRepository.findByName(categoryName);
	        List<Product> products = productRepository.findAllByStoreCategory(storeCategory);	       
	        List<ProductDTO> productDTOS = productConverterImpl.entitiesToDTOs(products);
	        return productDTOS;
	    }

	    @Override
	    public List<ProductDTO> getAllProductDtosByStoreSubCategory(String categoryName) {
	        StoreCategory storeCategory = storeCategoryRepository.findByName(categoryName);
	        List<Product> products = productRepository.findAllByStoreCategory(storeCategory);
	        List<ProductDTO> productDTOS = productConverterImpl.entitiesToDTOs(products);
	        return productDTOS;
	    }

	    @Override
	    public List<VariantDTO> getVariantsByProductId(Long productId) {
	        Product product = productRepository.findById(productId).orElse(new Product());
	        List<Variant> variantList = product.getVariants();
	        return variantConverter.entitiesToDTOs(variantList);
	    }


	    @Override
	    public List<OptionTableDTO> getOptionsByProductId(Long productId) {
	        List<OptionTableDTO> optionTableDTOList = new ArrayList<>();
	        Product product = productRepository.findById(productId).orElse(new Product());
	        List<OptionTable> optionTableList = product.getOptionTables();
	        for(OptionTable optionTable : optionTableList){
	            List<OptionValue> optionValueList = optionTable.getOptionValues();
	            List<OptionValueDTO> optionValueDTOList = optionValueConverter.entitiesToDTOs(optionValueList);
	            OptionTableDTO optionTableDto = optionTableConverter.entityToDTO(optionTable);
	            optionTableDto.setOptionValueDTOList(optionValueDTOList);
	            optionTableDTOList.add(optionTableDto);
	        }
	        return optionTableDTOList;
	    }

	    @Override
	    public List<ProductDTO> getProductsByContaining(String text) {
	        List<Product> products = productRepository.findByTitleContaining(text);
	        return productConverterImpl.entitiesToDTOs(products);

	    }

	    @Override
	    public List<ProductDTO> getProductsOfStoreByContaining(Long id, String text) {
	        List<Product> products = productRepository.findByTitleContaining(text);
	        return productConverterImpl.entitiesToDTOs(products);
	    }

	    @Override
	    public Product createProduct(Long storeId,Long categoryId,Long storeCategoryId, ProductDTO productDto) {
	        Store store = storeRepository.findById(storeId).orElse(new Store());
	        Category category = categoryRepository.findById(categoryId).orElse(new Category());
	        StoreCategory storeCategory = storeCategoryRepository.findById(storeCategoryId).orElse(new StoreCategory());
	        Product product= productConverterImpl.dtoToEntity(productDto);
	        product.setStore(store);
	        product.setCategory(category);
	        product.setStoreCategory(storeCategory);
	        return productRepository.save(product);
	    }

	    @Override
	    public Product updateProduct(ProductDTO productDto) {
	        Product product = productConverterImpl.dtoToEntity(productDto);
	        return productRepository.save(product);

	    }

	    @Override
	    public void deleteProduct(Long productId) {
	        productRepository.deleteById(productId);
	    }


		@Override
		public List<ProductDTO> findProductRequest() {
			// TODO Auto-generated method stub
			return null;
		}
		public ProductConfirmRequest declineProductRequest(ProductConfirmRequest productRequest, Long productId) {
		    // Tìm sản phẩm trong cơ sở dữ liệu
		    Optional<Product> optionalProduct = productRepository.findById(productId);
		    
		    // Kiểm tra xem sản phẩm có tồn tại không
		    if (optionalProduct.isPresent()) {
		        Product product = optionalProduct.get();
		        
		        // Cập nhật trạng thái sản phẩm và adminReply
		        product.setStatus(false);
		        product.setAdminReply(productRequest.getAdminReply());
		        System.out.println(product.getAdminReply());
		        productRepository.save(product);
		        
		        // Tạo đối tượng ProductConfirmRequest và thiết lập các thuộc tính
		        ProductConfirmRequest request = new ProductConfirmRequest();
		        request.setStatus(product.isStatus());
		        request.setAdminReply(product.getAdminReply());
		        
		        return request;
		    } else {
		        // Trả về null nếu không tìm thấy sản phẩm
		        return null;
		    }
		}


	}