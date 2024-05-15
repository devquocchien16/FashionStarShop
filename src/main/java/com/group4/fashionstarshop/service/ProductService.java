package com.group4.fashionstarshop.service;

import com.group4.fashionstarshop.dto.OptionTableDTO;
import com.group4.fashionstarshop.dto.ProductDTO;
import com.group4.fashionstarshop.dto.StoreDTO;
import com.group4.fashionstarshop.dto.VariantDTO;
import com.group4.fashionstarshop.model.Product;
import com.group4.fashionstarshop.request.AddProductRequest;
import com.group4.fashionstarshop.request.ProductRequest;

import java.util.List;

import org.springframework.data.domain.Page;


public interface ProductService {
	
	  public List<ProductDTO> getAllProductDtosByStore(Long id);
	    List<ProductDTO> getAllProductDtosByStoreCategory(String categoryName);
	    List<ProductDTO> getAllProductDtosByStoreSubCategory(String categoryName);
	    ProductDTO getProductById(Long id);
	    List<ProductDTO> getAllProductDtos();
	    List<VariantDTO> getVariantsByProductId(Long productId);
	    List<ProductDTO> getProductsByContaining(String text);	    

	    List<ProductDTO> getProductsOfStoreByContaining(Long id, String text);
	    Product updateProduct(ProductDTO productDto);
	    void deleteProduct(Long productId);
		ProductDTO createProduct(ProductRequest productRequest, Long storeId);
		List<OptionTableDTO> getOptionsByProductId(Long productId);
		StoreDTO getStoreByProductId(Long productId);
		List<ProductDTO> getAllProductDtosByStoreCategory(Long storeCategory_id);
		ProductDTO getProductDetails(Long productId);
		List<OptionTableDTO> getProductOptions(Long productId);
		ProductDTO updateProduct(Long productId, ProductRequest request);
		Product updateProduct(ProductRequest productDto);
		//for admin
		public List<ProductDTO> findProductRequest();
		Product createProduct(Long storeId, Long categoryId, ProductDTO productDto);
		//seller send request for admin after create product
		ProductDTO sendRequestNeedCheck(Long productId);
		


//     Page<ProductDTO> getAllProduct(String category, String store, Integer minPrice, Integer maxPrice, Integer pageNumber, Integer pageSize);
}
