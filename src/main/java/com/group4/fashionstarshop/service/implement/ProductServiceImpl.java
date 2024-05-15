package com.group4.fashionstarshop.service.implement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group4.fashionstarshop.converter.BulletConverter;
import com.group4.fashionstarshop.converter.CategoryConverter;
import com.group4.fashionstarshop.converter.ImageConverter;
import com.group4.fashionstarshop.converter.OptionTableConverter;
import com.group4.fashionstarshop.converter.OptionValueConverter;
import com.group4.fashionstarshop.converter.ProductConverter;
import com.group4.fashionstarshop.converter.StoreConverter;
import com.group4.fashionstarshop.converter.VariantConverter;
import com.group4.fashionstarshop.converter.impl.ProductConverterImpl;
import com.group4.fashionstarshop.dto.CategoryDTO;
import com.group4.fashionstarshop.dto.ImageDTO;
import com.group4.fashionstarshop.dto.OptionTableDTO;
import com.group4.fashionstarshop.dto.OptionValueDTO;
import com.group4.fashionstarshop.dto.ProductDTO;
import com.group4.fashionstarshop.dto.StoreDTO;
import com.group4.fashionstarshop.dto.VariantDTO;
import com.group4.fashionstarshop.model.Attribute;
import com.group4.fashionstarshop.model.Category;
import com.group4.fashionstarshop.model.Image;
import com.group4.fashionstarshop.model.OptionTable;
import com.group4.fashionstarshop.model.OptionValue;
import com.group4.fashionstarshop.model.Product;
import com.group4.fashionstarshop.model.Store;
import com.group4.fashionstarshop.model.Variant;
import com.group4.fashionstarshop.repository.AttributeRepository;
import com.group4.fashionstarshop.repository.CategoryRepository;
import com.group4.fashionstarshop.repository.ImageRepository;
import com.group4.fashionstarshop.repository.ProductRepository;
import com.group4.fashionstarshop.repository.StoreRepository;
import com.group4.fashionstarshop.request.AttributeRequest;
import com.group4.fashionstarshop.request.ProductRequest;
import com.group4.fashionstarshop.service.ProductService;

import jakarta.persistence.EntityNotFoundException;

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
	private CategoryConverter categoryConverter;

	@Autowired 
	private AttributeRepository attributeRepository;
//	@Override
//	public List<ProductDTO> getAllProductDtosByStore(Long store_id) {
//		Store store = storeRepository.findById(store_id).orElse(null);
//		List<Product> products = productRepository.findAllByStore(store);
//		List<ProductDTO> productDTOs = productConverter.entitiesToDTOs(products);
//		return productDTOs;
//	}




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

	    // Retrieve the Category entity
	    Category category = categoryRepository.findById(productRequest.getCategoryId())
	            .orElseThrow(() -> new EntityNotFoundException("Store category not found"));

	    
	    // Create a new Product entity
	    Product product = new Product();
	    product.setTitle(productRequest.getTitle());
	    product.setDescription(productRequest.getDescription());
	    product.setMainPicture(productRequest.getMainPicture());
	   // product.setStatus(false);
	    product.setCreateAt(new Date());
	    product.setUpdatedAt(new Date());
	    product.setStore(store);
	    product.setCategory(category);
	    //productRepository.save(product);	  

	    // Save the product entity
	    Product newProduct = productRepository.save(product);
	    
	    List<AttributeRequest> attributes = productRequest.getAttributes();
	    
	    
	    for (AttributeRequest attributeRequest : attributes) {
	    	Attribute attribute = new Attribute();
	    	attribute.setName(attributeRequest.getName());
	    	attribute.setValue(attributeRequest.getValue());
	    	attribute.setProduct(newProduct);
	    	attributeRepository.save(attribute);
		}
	    
	    ProductDTO productDTO = productConverter.entityToDTO(newProduct);
	    productDTO.setCategoryDTO(categoryConverter.entityToDTO(category));
	    // Convert the saved Product entity to a ProductDTO
	    return productDTO;
	}

	@Override
	public Product updateProduct(ProductRequest productDto) {
		Product product = productConverter.dtoToEntity(productDto);
		return productRepository.save(product);
	}
	
	@Override
	public ProductDTO sendRequestNeedCheck(Long productId) {
		Product product = productRepository.findById(productId).orElse(new Product());
		product.setNeedcheck(true);
		 productRepository.save(product);	  
		 return productConverter.entityToDTO(product);
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
		Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow();
		product.setCategory(category);		
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
	        StoreDTO storeDTO = storeConverter.entityToDTO(store);	 
	       // List<ProductDTO> productDTOs = productConverterImpl.entitiesToDTOs(products);
	        List<ProductDTO> productDTOs= new ArrayList<>();
	        for (Product product : products) {
				//CategoryDTO categoryDTO = categoryConverter.entityToDTO(product.getCategory());
				ProductDTO productDTO = productConverterImpl.entityToDTO(product);
				//productDTO.setCategoryDTO(categoryDTO);
				productDTO.setStore(storeDTO);
				productDTOs.add(productDTO);
			}	        
	       
	        return productDTOs;
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
	    public Product createProduct(Long storeId,Long categoryId,ProductDTO productDto) {
	        Store store = storeRepository.findById(storeId).orElse(new Store());
	        Category category = categoryRepository.findById(categoryId).orElse(new Category());	      
	        Product product= productConverterImpl.dtoToEntity(productDto);
	        product.setStore(store);
	        product.setCategory(category);       
	      
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