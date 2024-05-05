package com.group4.fashionstarshop.sellercontroller;

import com.group4.fashionstarshop.dto.ImageDTO;
import com.group4.fashionstarshop.model.Image;
import com.group4.fashionstarshop.model.Review;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.request.ImageRequest;
import com.group4.fashionstarshop.request.ReviewRequest;
import com.group4.fashionstarshop.payload.ImageCreateResponse;
import com.group4.fashionstarshop.repository.ReviewRepository;
import com.group4.fashionstarshop.repository.UserRepository;
import com.group4.fashionstarshop.service.ReviewService;
import com.group4.fashionstarshop.service.implement.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/image/{variant_id}")
public class ImageController {
    public final ImageServiceImpl imageService;
    public final ReviewService reviewService;



    @Autowired
    public ImageController(ImageServiceImpl imageService,ReviewService reviewService) {
        this.imageService = imageService;
        this.reviewService = reviewService;

    }
    @PostMapping("/create")
    public ResponseEntity<ImageCreateResponse> createImage(@RequestBody List<String> images, @PathVariable("variant_id") Long variant_id){
        ImageCreateResponse imageCreateResponse= new ImageCreateResponse();
        List<ImageDTO> imageDtoList = new ArrayList<>();
        for(String element : images){
            ImageDTO imageDto = ImageDTO.builder()
                    .imgPath(element)
                    .build();
            imageDtoList.add(imageDto);
        }
        ReviewRequest reviewRequest = new ReviewRequest(5,"High quality !! Must try!!","Design and Build Quality.The Galaxy S21 boasts a sleek and modern design. The build quality is excellent, with a glass front and back and a sturdy metal frame. It feels premium in hand and is surprisingly lightweight. The placement of buttons and ports is convenient, and the phone is water and dust resistant, which adds to its durability.");
        reviewService.save(reviewRequest,variant_id,1L);

        List<Image> image =imageService.createImage(imageDtoList,variant_id);
        if (image != null) {
            imageCreateResponse.setMessage("Image created successfully");
            return new ResponseEntity<>(imageCreateResponse, HttpStatus.CREATED);
        } else {
            imageCreateResponse.setMessage("Failed to create Image");
            return new ResponseEntity<>(imageCreateResponse,HttpStatus.BAD_REQUEST);
        }
    }
}
