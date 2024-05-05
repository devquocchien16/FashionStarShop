package com.group4.fashionstarshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import jakarta.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "STORE_CATEGORY")
public class StoreCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name="store_id")
    @JsonBackReference(value = "store_category")
    private Store store;
    
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;     

    
    @OneToMany(mappedBy = "storeCategory")
    @JsonManagedReference(value = "product_storeCategory")
    private List<Product> productList;

    @OneToMany(mappedBy ="storeCategory")
    @JsonManagedReference(value = "storeCategory_image")
    private List<Image> imageList;
}
