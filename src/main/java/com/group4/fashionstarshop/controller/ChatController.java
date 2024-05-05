package com.group4.fashionstarshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.group4.fashionstarshop.dto.CreateChatRequest;
import com.group4.fashionstarshop.model.ChatRoom;
import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.service.ChatRoomService;
import com.group4.fashionstarshop.service.SellerService;
import com.group4.fashionstarshop.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class ChatController {

	@Autowired
	private ChatRoomService chatRoomService;
	@Autowired
	private UserService userService;
	
	@Autowired
	private SellerService sellerService;
	
	@PostMapping("/api/chats")
	public ChatRoom createChat(@RequestHeader("Authorization") String jwt,@RequestBody CreateChatRequest req) {
		User user = userService.findUserByJwt(jwt);
		Seller seller = sellerService.findById(req.getId());
		ChatRoom chat = chatRoomService.createChat(seller, user);
		return chat;
	}
	
	@GetMapping("/api/chats")
	public List<ChatRoom> findChat(@RequestHeader("Authorization") String jwt) {
		Seller seller = sellerService.findUserByJwt(jwt);
		List<ChatRoom> chats = chatRoomService.findSellerChat(seller.getId());
		return chats;
	}

}
