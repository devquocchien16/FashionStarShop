package com.group4.fashionstarshop;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.group4.fashionstarshop.dto.OrderDTO;
import com.group4.fashionstarshop.model.Order;

@SpringBootApplication
public class FashionStarShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(FashionStarShopApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		return modelMapper;

	}
}
