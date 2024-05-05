package com.group4.fashionstarshop.service.implement;

import com.group4.fashionstarshop.converter.CartLineConverter;
import com.group4.fashionstarshop.dto.CartLineDTO;
import com.group4.fashionstarshop.model.Cart;
import com.group4.fashionstarshop.model.CartLine;
import com.group4.fashionstarshop.model.Variant;
import com.group4.fashionstarshop.request.CartLineRequest;
import com.group4.fashionstarshop.repository.CartLineRepository;
import com.group4.fashionstarshop.repository.CartRepository;
import com.group4.fashionstarshop.repository.VariantRepository;
import com.group4.fashionstarshop.service.CartLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CartLineServiceImpl implements CartLineService {
    private final CartLineRepository cartLineRepository;
    private final CartLineConverter cartLineConverter;
    private final CartRepository cartRepository;
    private final VariantRepository variantRepository;

    @Autowired
    public CartLineServiceImpl(CartLineRepository cartLineRepository, CartLineConverter cartLineConverter, CartRepository cartRepository, VariantRepository variantRepository) {
        this.cartLineRepository = cartLineRepository;
        this.cartLineConverter = cartLineConverter;
        this.cartRepository = cartRepository;
        this.variantRepository = variantRepository;
    }

    @Override
    public void updateCartLine(CartLineDTO cartLineDto, Long id) throws Exception {
        CartLine cartLine = cartLineRepository.findById(id).orElse(null);
        cartLine.setQuantity(cartLineDto.getQuantity());
        cartLineRepository.save(cartLine);
    }

    @Override
    public void removeCartLine(Long id) {
       CartLine cartLine = cartLineRepository.findById(id).orElse(null);
        cartLineRepository.deleteCartLineById(cartLine.getId());
    }

    @Override
    public List<CartLineDTO> findCartLinesByCartId(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        List<CartLine> cartLines = cartLineRepository.findCartLineByCart(cart);
        List<CartLineDTO> cartLineDTOS = new ArrayList<>();
        for (CartLine cartLine : cartLines) {
			CartLineDTO cartLineDTO =  cartLineConverter.convertEntityToDto(cartLine);
			cartLineDTOS.add(cartLineDTO);
		}
        return cartLineDTOS;
    }

    @Override
    public CartLineDTO saveCartLine(CartLineRequest cartLineRequest) {
        Variant variant = variantRepository.findById(cartLineRequest.getVariantId()).orElse(null);
        Cart cart = cartRepository.findById(cartLineRequest.getCartId()).orElse(null);
        CartLine cartLine = cartLineRepository.findCartLineByVariant(variant);
        if (cartLine != null) {
            int newQuantity = cartLine.getQuantity() + cartLineRequest.getQuantity();
            cartLine.setQuantity(newQuantity);
            cartLineRepository.save(cartLine);

            CartLineDTO cartLineDto = cartLineConverter.convertEntityToDto(cartLine);
            return cartLineDto;
        }
        int quantity = cartLineRequest.getQuantity();
        CartLine newCartLine = new CartLine();
        newCartLine.setCart(cart);
        newCartLine.setVariant(variant);
        newCartLine.setQuantity(quantity);
        cartLineRepository.save(newCartLine);

        CartLineDTO cartLineDto = cartLineConverter.convertEntityToDto(newCartLine);
        return cartLineDto;
    }

    @Override
    public void removeAllCartLines(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        cartLineRepository.deleteAllByCart(cart);
    }
}
