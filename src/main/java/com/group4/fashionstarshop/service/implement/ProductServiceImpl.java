package com.group4.fashionstarshop.service.implement;

import com.group4.fashionstarshop.converter.*;
import com.group4.fashionstarshop.converter.impl.ProductConverterImpl;
import com.group4.fashionstarshop.dto.*;
import com.group4.fashionstarshop.model.*;
import com.group4.fashionstarshop.repository.CategoryRepository;
import com.group4.fashionstarshop.repository.ProductRepository;
import com.group4.fashionstarshop.repository.StoreCategoryRepository;
import com.group4.fashionstarshop.repository.StoreRepository;
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
import java.util.List;

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
		Store store = storeRepository.findById(storeId).orElse(new Store());
		StoreCategory storeCategory = storeCategoryRepository.findById(productRequest.getStoreCategoryId())
				.orElse(new StoreCategory());
		Product product = new Product();
		product = productConverter.dtoToEntity(productRequest);
		product.setStore(store);
		product.setMainPicture(productRequest.getMainPicture());
		product.setStoreCategory(storeCategory);
		Product newProduct = productRepository.save(product);
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
	        List<Bullet> bullets = product.getBulletList();
	        List<BulletDTO> bulletDTOList = bulletConverter.entitiesToDTOs(bullets);
	        ProductDTO productDto = productConverterImpl.entityToDTO(product);


	        return productDto;
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


	}