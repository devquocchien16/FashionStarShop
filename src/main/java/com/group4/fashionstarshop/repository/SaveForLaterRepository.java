package com.group4.fashionstarshop.repository;

import com.group4.fashionstarshop.model.Cart;
import com.group4.fashionstarshop.model.SaveForLater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaveForLaterRepository extends JpaRepository<SaveForLater, Long> {
    List<SaveForLater> findSaveForLaterByCart(Cart cart);
}
