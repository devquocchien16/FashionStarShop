package com.group4.fashionstarshop.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.group4.fashionstarshop.converter.StoreCategoryConverter;
import com.group4.fashionstarshop.converter.StoreConverter;
import com.group4.fashionstarshop.dto.StoreDTO;
import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.model.Store;
import com.group4.fashionstarshop.repository.SellerRepository;
import com.group4.fashionstarshop.repository.StoreRepository;
import com.group4.fashionstarshop.request.StoreRequest;
import com.group4.fashionstarshop.service.StoreService;

@Service
public class StoreServiceImpl implements StoreService {	
	@Autowired
    private StoreConverter storeConverter;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private StoreCategoryConverter storeCategoryConverter;
    @Override
    public StoreDTO findStore(Long id) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new RuntimeException("Store not found"));       
        return storeConverter.entityToDTO(store);
    }


    @Override
    public StoreDTO createStore(Long sellerId, StoreRequest request) {
        Seller seller = sellerRepository.findById(sellerId).orElseThrow(
                () -> new UsernameNotFoundException("seller not found"));
        Store store = new Store();
        store.setName(request.getName());
        store.setDescription(request.getDescription());
        store.setDealsImage("https://m.media-amazon.com/images/S/al-na-9d5791cf-3faf/1f6cbd86-1e6a-42d5-bd7c-e137bfef3fc6._CR0%2C0%2C3000%2C600_SX1920_.jpg");
        store.setHomeImage("https://congluan-cdn.congluan.vn/files/dieulinh/2020/07/16/st2-0904.jpg");
        store.setDealsSquareImage("https://i.ytimg.com/vi/YczCAwQ3wgs/maxresdefault.jpg");
        store.setInteractiveImage("https://png.pngtree.com/background/20210715/original/pngtree-black-friday-neon-lights-pink-background-banner-picture-image_1266832.jpg");
        store.setLogo("https://img.pikbest.com/origin/06/53/62/292pIkbEsTqDa.jpg!w700wp");
        store.setSeller(seller);
        storeRepository.save(store);
        StoreDTO newStoreDTO = storeConverter.entityToDTO(store);
        return newStoreDTO;
    }


	@Override
	public StoreDTO findStoresBySellerId(Long seller_id) {
		 Seller seller = sellerRepository.findById(seller_id).orElseThrow(
	                () -> new UsernameNotFoundException("seller not found"));
		 Store store = storeRepository.findBySeller(seller);
		 
		return storeConverter.entityToDTO(store);
	}


	@Override
	public StoreDTO updateStore(Long storeId, StoreRequest request) {
	    // Tìm kiếm cửa hàng cần cập nhật
	    Store store = storeRepository.findById(storeId)
	            .orElseThrow();
	    // Thực hiện cập nhật thông tin từ request
	    store.setEditingName(request.getName());
	    store.setDescription(request.getDescription());
	    store = storeRepository.save(store);

	    // Chuyển đổi và trả về đối tượng StoreDTO đã được cập nhật
	    return storeConverter.entityToDTO(store);
	}


	@Override
	public List<StoreDTO> findInactiveStores() {
	    List<Store> inactiveStores = storeRepository.findByStatus(false);
	    return storeConverter.entitiesToDTOs(inactiveStores);
	}


	@Override
	public List<StoreDTO> findStoreRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
