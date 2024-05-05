package com.group4.fashionstarshop.service;

import com.group4.fashionstarshop.dto.SaveForLaterDTO;
import com.group4.fashionstarshop.request.SaveForLaterRequest;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SaveForLaterService {
    List<SaveForLaterDTO> findSaveForLaterByCartId (Long cartId);
    void removeSaveForLater(Long saveForLaterId);
    SaveForLaterDTO addSaveForLater(SaveForLaterRequest saveForLaterRequest);
}
