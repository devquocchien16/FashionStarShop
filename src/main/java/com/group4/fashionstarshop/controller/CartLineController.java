package com.group4.fashionstarshop.controller;

import com.group4.fashionstarshop.dto.CartLineDTO;
import com.group4.fashionstarshop.model.Cart;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.request.CartLineRequest;
import com.group4.fashionstarshop.service.CartLineService;
import com.group4.fashionstarshop.service.CartService;
import com.group4.fashionstarshop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/cart-lines")
@AllArgsConstructor
public class CartLineController {
	@Autowired
    private CartLineService cartLineService;
	@Autowired
    private CartService cartService;
	@Autowired
    private UserService userService;


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCartLine(@PathVariable("id")Long cartLineId){
        cartLineService.removeCartLine(cartLineId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<CartLineDTO> addCartLine(@RequestBody CartLineRequest cartLineRequest){
        CartLineDTO cartLineDto = cartLineService.saveCartLine(cartLineRequest);
        return new ResponseEntity<>(cartLineDto ,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllCartLines(@PathVariable("id") Long userId){
        User user = userService.findById(userId);
        Cart cart = cartService.findCartByUserId(user);
        List<CartLineDTO> cartLineDTOS = cartLineService.findCartLinesByCartId(cart.getId());
        return new ResponseEntity<>(cartLineDTOS,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateCartLine(@RequestBody CartLineDTO cartLineDto) throws Exception {
        cartLineService.updateCartLine(cartLineDto, cartLineDto.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete-all/{id}")
    public ResponseEntity<?> deleteAllCartLine(@PathVariable("id") Long userId){
        User user = userService.findById(userId);
        Cart cart = cartService.findCartByUserId(user);
        cartLineService.removeAllCartLines(cart.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
