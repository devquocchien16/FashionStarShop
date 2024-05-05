package com.group4.fashionstarshop.controller;

import com.group4.fashionstarshop.dto.SaveForLaterDTO;
import com.group4.fashionstarshop.model.Cart;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.request.SaveForLaterRequest;
import com.group4.fashionstarshop.service.CartService;
import com.group4.fashionstarshop.service.SaveForLaterService;
import com.group4.fashionstarshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/save-for-later")
public class SaveForLaterController {
    @Autowired
    private SaveForLaterService saveForLaterService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllSaveForLater(@PathVariable("id") Long userId){
        User user = userService.findById(userId);
        Cart cart = cartService.findCartByUserId(user);
        List<SaveForLaterDTO> saveForLaterDTOS = saveForLaterService.findSaveForLaterByCartId(cart.getId());
        return new ResponseEntity<>(saveForLaterDTOS, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSaveForLater(@PathVariable("id") Long saveForLaterId) {
        saveForLaterService.removeSaveForLater(saveForLaterId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addSaveForLater(@RequestBody SaveForLaterRequest saveForLaterRequest){
        SaveForLaterDTO saveForLaterDto = saveForLaterService.addSaveForLater(saveForLaterRequest);
        return new ResponseEntity<>(saveForLaterDto, HttpStatus.OK);
    }
}
