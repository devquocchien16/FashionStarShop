package com.group4.fashionstarshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "variant")
public class Variant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skuCode;
    private int stockQuantity;
    private double weight;
    private double price;
    private double salePrice;
    private String img;
    private Boolean isDeleted;    
    
    private boolean status;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference(value = "product_variant")
    private Product product;

    @OneToMany(mappedBy = "variant")
    @JsonManagedReference(value = "variant_image")
    private List<Image> images;
    
    @OneToMany(mappedBy = "variant", cascade = CascadeType.REMOVE)
    private List<VariantOptionValue> variantOptionValues;
    
    private String name;

    @OneToOne(mappedBy = "variant")
    private CartLine cartLine;

    @OneToOne(mappedBy = "variant")
    private SaveForLater saveForLater;

    @OneToMany(mappedBy = "variant")
    @JsonManagedReference(value = "variant_review")
    private List<Review> reviews;
   

    @OneToMany(mappedBy = "variant")
    @JsonManagedReference(value = "variant_orderItem")
    private List<OrderItem> orderItemList;
    
    private String variantPicture;
}

