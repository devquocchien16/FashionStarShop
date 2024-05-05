package com.group4.fashionstarshop.service.implement;

import com.group4.fashionstarshop.converter.*;
import com.group4.fashionstarshop.dto.*;
import com.group4.fashionstarshop.model.*;
import com.group4.fashionstarshop.repository.*;
import com.group4.fashionstarshop.request.FindVariantRequest;
import com.group4.fashionstarshop.request.VariantRequest;
import com.group4.fashionstarshop.service.ReviewService;
import com.group4.fashionstarshop.service.VariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VariantServiceImpl implements VariantService {
	@Autowired
	private VariantConverter variantConverter;
	@Autowired
	private VariantRepository variantRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private OptionValueRepository optionValueRepository;
	@Autowired
	private VariantOptionValueRepository variantOptionValueRepository;
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private ReviewConverter reviewConverter;
	@Autowired
	private UserConverter userConverter;
	@Autowired
	private ProductConverter productConverter;
	@Autowired
	private OptionValueConverter optionValueConverter;
	@Autowired
	private ImageConverter imageConverter;
	@Autowired
	private VariantConverter variantConverterImpl;
	@Autowired
	private ImageConverter iImageConverter;
	@Autowired
	private ReviewService reviewService;
	@Override
	public VariantDTO updateVariant(VariantRequest variantRequest, Long variantId) {

		Variant variant = variantRepository.findById(variantId)
				.orElseThrow(() -> new EntityNotFoundException("Variant not found"));
		variant.setSkuCode(variantRequest.getSkuCode());
		variant.setStockQuantity(variantRequest.getStockQuantity());
		variant.setPrice(variantRequest.getPrice());
		variant.setSalePrice(variantRequest.getSalePrice());
		variant.setIsDeleted(variantRequest.getIsDeleted());

		Variant savedVariant = variantRepository.save(variant);

		return variantConverter.entityToDTO(savedVariant);
	}

	@Override
	public List<VariantDTO> createRawVariant(Long productId) {
		Product product = productRepository.findById(productId).orElseThrow();
		List<OptionTable> productOptions = product.getOptionTables();
		Map<OptionTable, List<OptionValue>> optionValuesMap = new HashMap<>();

		// Fetch all option values for each option in one query
		for (OptionTable productOption : productOptions) {
			List<OptionValue> optionValues = productOption.getOptionValues();
			optionValuesMap.put(productOption, optionValues);
		}
		List<Variant> variants = new ArrayList<>();
		createVariantCombinations(product, productOptions, optionValuesMap);
		variantRepository.saveAll(variants);
		variants = variantRepository.findByProduct(product);
		return variantConverter.entitiesToDTOs(variants);
	}

	private void createVariantCombinations(Product product, List<OptionTable> options,
			Map<OptionTable, List<OptionValue>> optionValuesMap) {
		List<List<OptionValue>> combinations = new ArrayList<>();
		generateCombinations(options, optionValuesMap, new ArrayList<>(), 0, combinations);

		for (List<OptionValue> combination : combinations) {
			Variant variant = new Variant();
			variant.setProduct(product);
			variantRepository.save(variant);
			for (OptionValue optionValue : combination) {
				VariantOptionValue variantOptionValue = new VariantOptionValue();
				variantOptionValue.setVariant(variant);
				variantOptionValue.setOption_value(optionValue);
				variantOptionValueRepository.save(variantOptionValue);
			}
		}
	}

	private void generateCombinations(List<OptionTable> options, Map<OptionTable, List<OptionValue>> optionValuesMap,
			List<OptionValue> currentCombination, int currentIndex, List<List<OptionValue>> combinations) {
		if (currentIndex == options.size()) {
			combinations.add(new ArrayList<>(currentCombination));
			return;
		}
		OptionTable option = options.get(currentIndex);
		List<OptionValue> values = optionValuesMap.get(option);
		for (OptionValue value : values) {
			currentCombination.add(value);
			generateCombinations(options, optionValuesMap, currentCombination, currentIndex + 1, combinations);
			currentCombination.remove(currentCombination.size() - 1);
		}
	}

	@Override
	public List<VariantDTO> getVariantsByProductId(Long product_id) {
		Product product = productRepository.findById(product_id)
				.orElseThrow(() -> new EntityNotFoundException("Product not found"));
		// Lấy danh sách các biến thể với trạng thái isDeleted khác false
		List<Variant> variants = variantRepository.findByProductAndIsDeletedNullOrIsDeletedFalse(product);

		List<VariantDTO> variantDTOs = variantConverter.entitiesToDTOs(variants);

		for (VariantDTO variantDTO : variantDTOs) {
			// Lấy danh sách các giá trị tùy chọn của biến thể
			List<OptionValueDTO> optionValues = getOptionValuesByVariantId(variantDTO.getId());
			// Gán danh sách giá trị tùy chọn vào biến thể tương ứng
			variantDTO.setOptionValueDTOList(optionValues);
			variantDTO.setProductDTO(productConverter.entityToDTO(product));
		}
		return variantDTOs;
	}

	public List<OptionValueDTO> getOptionValuesByVariantId(Long variantId) {
		Variant variant = variantRepository.findById(variantId)
				.orElseThrow(() -> new EntityNotFoundException("Variant not found with id: " + variantId));

		// Lấy danh sách các VariantOptionValue của biến thể
		List<VariantOptionValue> variantOptionValues = variantOptionValueRepository.findByVariant(variant);

		// Tạo danh sách để lưu trữ các OptionValueDTO
		List<OptionValueDTO> optionValueDTOs = new ArrayList<>();

		// Lặp qua từng VariantOptionValue và trích xuất OptionValue
		for (VariantOptionValue variantOptionValue : variantOptionValues) {
			OptionValue optionValue = variantOptionValue.getOption_value();

			// Chuyển đổi OptionValue thành OptionValueDTO và thêm vào danh sách
			optionValueDTOs.add(optionValueConverter.entityToDTO(optionValue));
		}

		return optionValueDTOs;
	}

	@Override
	public VariantDTO getVariantById(Long variantId) {
		Variant variant = variantRepository.findById(variantId)
				.orElseThrow(() -> new EntityNotFoundException("Variant not found with id: " + variantId));

		// Retrieve option values for the variant
		List<OptionValueDTO> optionValueDTOs = getOptionValuesByVariantId(variantId);
		List<Image> imageList = variant.getImages();
		List<ImageDTO> imageDTOList = imageConverter.entitiesToDTOs(imageList);
		List<ReviewDTO> reviewList = reviewService.getReviewsByVariantId(variant.getId());

		// Convert Variant to VariantDTO
		VariantDTO variantDTO = variantConverter.entityToDTO(variant);

		// Set option values in the DTO
		variantDTO.setOptionValueDTOList(optionValueDTOs);
		variantDTO.setImageDTOList(imageDTOList);
		variantDTO.setReviewDTOList(reviewList);
		//get and set productDTO
		Product product = variant.getProduct();
		variantDTO.setProductDTO(productConverter.entityToDTO(product));
		

		return variantDTO;
	}
	@Override
	public VariantDTO deleteVariant(Long variant_id) {
		Variant variant = variantRepository.findById(variant_id)
				.orElseThrow(() -> new EntityNotFoundException("Variant not found with id: " + variant_id));
		variant.setIsDeleted(true);
		variantRepository.save(variant);

		return variantConverter.entityToDTO(variant);

	}

	@Override
	public VariantDTO getVariantIdByProductIdAndOptionValueIds(Long productId, FindVariantRequest request) {
	    Product product = productRepository.findById(productId).orElse(null);
	    if (product == null) {
	        throw new EntityNotFoundException("Product not found with id: " + productId);
	    }
	    List<Variant> variants = variantRepository.findByProduct(product);
	    Long matchingVariantId = findMatchingVariantId(variants, request.getOptionValueIds());
	    VariantDTO variantDTO = new VariantDTO();
	    variantDTO.setId(matchingVariantId);
	    Variant variant = variantRepository.findById(matchingVariantId).orElse(null);
	    return variantConverter.entityToDTO(variant);
	}

	private Long findMatchingVariantId(List<Variant> variants, List<Long> optionValueIds) {
	    // Duyệt qua từng biến thể
	    for (Variant variant : variants) {
	        // Kiểm tra xem biến thể này có chứa tất cả các optionValueIds được yêu cầu hay không
	        if (containsAllOptionValues(variant, optionValueIds)) {
	            // Nếu có, trả về variant_id đầu tiên phù hợp
	            return variant.getId();
	        }
	    }
	    // Nếu không có biến thể phù hợp, trả về null hoặc ném một ngoại lệ tùy thuộc vào yêu cầu của bạn
	    return null;
	}

	private boolean containsAllOptionValues(Variant variant, List<Long> optionValueIds) {
	    // Lấy danh sách các variantOptionValues của biến thể
	    List<VariantOptionValue> variantOptionValues = variant.getVariantOptionValues();
	    // Tạo danh sách để lưu trữ các optionValueIds của biến thể
	    List<Long> variantOptionValueIds = new ArrayList<>();
	    for (VariantOptionValue variantOptionValue : variantOptionValues) {
	        variantOptionValueIds.add(variantOptionValue.getOption_value().getId());
	    }

	    // Kiểm tra xem danh sách optionValueIds của biến thể có chứa tất cả các optionValueIds được yêu cầu hay không
	    return variantOptionValueIds.containsAll(optionValueIds);
	}

	@Override
	public VariantDTO createVariant(VariantRequest variantRequest, Long productId) {
		Variant variant = new Variant();

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new EntityNotFoundException("Product not found"));

		variant.setSkuCode(variantRequest.getSkuCode());
		variant.setStockQuantity(variantRequest.getStockQuantity());
		variant.setWeight(variantRequest.getWeight());
		variant.setPrice(variantRequest.getPrice());
		variant.setSalePrice(variantRequest.getSalePrice());
		variant.setImg(variantRequest.getImg());
		variant.setName(variantRequest.getName());
		variant.setIsDeleted(variantRequest.getIsDeleted());
		variant.setProduct(product);

		Variant savedVariant = variantRepository.save(variant);

		if (variantRequest.getOptionValueIds() != null && !variantRequest.getOptionValueIds().isEmpty()) {
			List<OptionValue> optionValues = optionValueRepository.findAllById(variantRequest.getOptionValueIds());

			if (!optionValues.isEmpty()) {
				List<VariantOptionValue> variantOptionValues = optionValues.stream()
						.map(optionValue -> VariantOptionValue.builder().variant(savedVariant).option_value(optionValue)
								.build())
						.collect(Collectors.toList());

				variantOptionValueRepository.saveAll(variantOptionValues);
			} // updated to match the field name in VariantOptionValue
		}
		return variantConverter.entityToDTO(savedVariant);
	}

//	@Override
//	public List<VariantDTO> getVariantByProductId(Long product_id) {
//		List<VariantDTO> variantDtoList = new ArrayList<>();
//		List<Variant> variants = variantRepository.findByProduct_Id(product_id);
//		for (Variant variant : variants) {
//			List<OptionValue> optionValueList = variant.getOptionValues();
//			List<Image> imageList = variant.getImages();
//			List<ReviewDTO> reviewList = reviewService.getReviewsByVariantId(variant.getId());
//			List<ImageDTO> imageDTOList = imageConverter.entitiesToDTOs(imageList);
//			List<OptionValueDTO> optionValueDto = optionValueConverter.entitiesToDTOs(optionValueList);
//			VariantDTO variantDto = variantConverter.entityToDTO(variant);
//			variantDto.setOptionValueDTOList(optionValueDto);
//			variantDto.setImageDTOList(imageDTOList);
//			variantDto.setReviewDTOList(reviewList);
//			variantDtoList.add(variantDto);
//		}
//		return variantDtoList;
//	}
	
	@Override
	public List<VariantDTO> getVariantByProductId(Long product_id) {
		Product product = productRepository.findById(product_id)
				.orElseThrow(() -> new EntityNotFoundException("Product not found"));
		// Lấy danh sách các biến thể với trạng thái isDeleted khác false
		List<Variant> variants = variantRepository.findByProductAndIsDeletedNullOrIsDeletedFalse(product);

		List<VariantDTO> variantDTOs = variantConverter.entitiesToDTOs(variants);

		for (VariantDTO variantDTO : variantDTOs) {
			// Lấy danh sách các giá trị tùy chọn của biến thể
			List<OptionValueDTO> optionValues = getOptionValuesByVariantId(variantDTO.getId());
			// Gán danh sách giá trị tùy chọn vào biến thể tương ứng
			variantDTO.setOptionValueDTOList(optionValues);
			variantDTO.setProductDTO(productConverter.entityToDTO(product));
		}
		return variantDTOs;
	}

		@Override
		public VariantDTO getLowestPriceVariantByProductId(Long product_id) {
		List<Variant> variants = variantRepository.findByProduct_Id(product_id);
		Variant minVariant = variants.get(0);
		for (Variant variant : variants) {
			if (variant.getSalePrice() < minVariant.getSalePrice()) {
				minVariant = variant;
			}
		}
		Variant variant = variantRepository.findById(minVariant.getId()).orElse(null);		
		List<OptionValue> optionValue = optionValueRepository.findOptionValueById(product_id);
		List<Image> images = minVariant.getImages();
		
		return variantConverter.entityToDTO(variant);
		}
	@Override
	public Variant findById(Long id) {
		Variant variant = variantRepository.findById(id).orElse(null);
		return variant;
	}

//	@Override
//	public VariantDTO getLowestPriceVariantByProductId(Long product_id) {
//		List<Variant> variants = variantRepository.findByProduct_Id(product_id);
//		Variant minVariant = variants.get(0);
//		for (Variant variant : variants) {
//			if (variant.getSalePrice() < minVariant.getSalePrice()) {
//				minVariant = variant;
//			}
//		}
//		List<OptionValue> optionValueList = minVariant.VariantOptionValues();
//		List<Image> images = minVariant.getImages();
//		List<OptionValueDTO> optionValueDto = optionValueConverter.entitiesToDTOs(optionValueList);
//		List<ImageDTO> imageDtoList = iImageConverter.entitiesToDTOs(images);
//		VariantDTO variantDto = variantConverterImpl.entityToDTO(minVariant);
//		variantDto.setOptionValueDTOList(optionValueDto);
//		variantDto.setImageDTOList(imageDtoList);
//		return variantDto;
//	}

	@Override
	public VariantDTO getVariantByProductPriceMin(Long product_id) {
		Variant variant = variantRepository.findTopByProductIdOrderByPriceAsc(product_id);
		return variantConverterImpl.entityToDTO(variant);
	}

	@Override
	public Variant createVariant(VariantDTO variantDto, Long product_id) {
		Variant variant = variantConverterImpl.dtoToEntity(variantDto);
		Product product = productRepository.findById(product_id).orElse(null);
		variant.setProduct(product);
		return variantRepository.save(variant);
	}

	@Override
	public VariantDTO updateVariant(Long variantId, VariantDTO variantDto) {
		Variant variant = variantRepository.findById(variantId)
				.orElseThrow(() -> new EntityNotFoundException("variant not found"));
		variant.setName(variantDto.getName());
		variant.setSkuCode(variantDto.getSkuCode());
		variant.setStockQuantity(variantDto.getStockQuantity());
		variant.setWeight(variantDto.getWeight());
		variant.setPrice(variantDto.getPrice());
		variant.setSalePrice(variantDto.getSalePrice());
		Variant variant1 = variantRepository.save(variant);
		VariantDTO variantDTO = variantConverterImpl.entityToDTO(variant1);
		return variantDTO;
	}

	public Page<VariantDTO> getVariantsByContaining(String text, Pageable pageable) {
		Page<Variant> variantPage = variantRepository.findByNameContaining(text, pageable);
		return variantPage.map(variantConverterImpl::entityToDTO);
	}

	@Override
	public Page<VariantDTO> getVariantsByNameContainingAndPriceBetween(String text, double minPrice, double maxPrice,
			Pageable pageable) {
		Page<Variant> variantPage = variantRepository.findVariantsByNameContainingAndPriceBetween(text, minPrice,
				maxPrice, pageable);
		System.out.println("888888888888888888888" + variantPage.getSize());
		return variantPage.map(variantConverterImpl::entityToDTO);
	}

	@Override
	public Page<VariantDTO> getVariantsBySearchTextAndRating(String text, long star, Pageable pageable) {
		List<VariantDTO> variantList = new ArrayList<>();
		Page<Variant> variantPage = variantRepository.findByNameContaining(text, pageable);
		if (variantPage != null) {
			for (Variant variant : variantPage) {
				if (variantRepository.findAverageStarByReview(variant) != null) {
					long rating = Math.round(variantRepository.findAverageStarByReview(variant));
					System.out.println("******************************************" + rating);
					if (rating == star) {
						variantList.add(variantConverterImpl.entityToDTO(variant));
					}

				}
			}
		}

		return new PageImpl<>(variantList, pageable, variantPage.getTotalElements());
	}



	@Override
	public VariantDTO getVariantInfoById(Long variantId) {
		Variant variant = variantRepository.findById(variantId)
				.orElseThrow(() -> new EntityNotFoundException("Variant not found with id: " + variantId));

		// Retrieve option values for the variant
		List<OptionValueDTO> optionValueDTOs = getOptionValuesByVariantId(variantId);

		// Convert Variant to VariantDTO
		VariantDTO variantDTO = variantConverter.entityToDTO(variant);

		// Set option values in the DTO
		variantDTO.setOptionValueDTOList(optionValueDTOs);

		return variantDTO;

	}

	@Override
	public Page<VariantDTO> getNewestVariantsByText(String text, Pageable pageable) {
		Page<Variant> variantPage = variantRepository.findByNameContaining(text, pageable);
		List<Variant> variants = variantPage.getContent();
		List<Variant> sortedVariants = variants.stream().sorted((variant1, variant2) -> {
			Date createdAt1 = variantRepository.findCreatedAtByVariantId(variant1.getId());
			Date createdAt2 = variantRepository.findCreatedAtByVariantId(variant2.getId());
			return createdAt1.compareTo(createdAt2); // xep ngay theo giam dan
		}).collect(Collectors.toList());
		Page<Variant> sortedVariantPage = new PageImpl<>(sortedVariants, pageable, variantPage.getTotalElements());
		List<VariantDTO> sortedVariantDTOs = sortedVariants.stream()
				.map(variant -> variantConverterImpl.entityToDTO(variant)).collect(Collectors.toList());
		return new PageImpl<>(sortedVariantDTOs, pageable, sortedVariantPage.getTotalElements());

	}

//	@Override
//	public VariantDTO getVariantById(Long variantId) 
//	{Variant variant = variantRepository.findById(variantId). orElseThrow(
//			() -> new EntityNotFoundException("Variant not found with id: " + variantId));
//	// Retrieve option values for the variant
//	List<OptionValueDTO> optionValueDTOs = getOptionValuesByVariantId(variantId);
//	// Convert Variant to VariantDTO
//	VariantDTO variantDTO = variantConverter.entityToDTO(variant);
//	// Set option values in the DTO
//	variantDTO. setOptionValueDTOList(optionValueDTOs);
//	//get and set productDTO
//	Product product = variant.getProduct();variantDTO. setProductDTO(productConverter.entityToDTO(product));return variantDTO;
//	}


}
