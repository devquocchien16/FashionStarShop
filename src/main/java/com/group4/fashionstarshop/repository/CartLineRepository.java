package com.group4.fashionstarshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group4.fashionstarshop.model.Cart;
import com.group4.fashionstarshop.model.CartLine;
import com.group4.fashionstarshop.model.Variant;

import java.util.List;

@Repository
public interface CartLineRepository extends JpaRepository<CartLine, Long> {
    List<CartLine> findCartLineByCart(Cart cart);

    void deleteAllByCart(Cart cart);

    void deleteCartLineById(Long cartLineId);

    CartLine findCartLineByVariant (Variant variant);

    List<CartLine> findByCart_Id(Long cartId);
}
