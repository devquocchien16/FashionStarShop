package com.group4.fashionstarshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String mainPicture;
    private boolean status;
    private Date createAt;
    private Date updatedAt;   
    private String adminReply;
    @ManyToOne
    @JoinColumn (name = "category_id")
    @JsonBackReference(value = "product_category")
    private Category category;
    
    @ManyToOne
    @JoinColumn(name ="store_category_id")
    @JsonBackReference(value = "product_storeCategory")
    private StoreCategory storeCategory;

    @ManyToOne
    @JoinColumn(name="store_id")
    @JsonBackReference(value = "store_product")
    private Store store;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference(value = "product_optionTable")
    private List<OptionTable> optionTables;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference(value = "product_variant")
    private List<Variant> variants;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference(value = "product_attribute")
    private List<Attribute> attributes;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference(value = "product_bulletList")
    private List<Bullet> bulletList;

       
   


}
