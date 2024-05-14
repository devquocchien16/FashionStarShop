package com.group4.fashionstarshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Fetch;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="store")

public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String logo;   
    private String editingName;
    private Boolean status;   
    private boolean type;       
  
   
    private String tax_num;
    private String certificate_image; 
    private String identity_type;
    private String identity_num;
    private String identity_image_1;
    private String identity_image_2;    
  
    
    private String description;
    
    private String adminReply;
  
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    @JsonBackReference(value = "store_seller")
    private Seller seller;  

    @OneToMany(mappedBy = "store")
    @JsonManagedReference(value = "store_product")
    private List<Product> productlist;

    @OneToMany(mappedBy = "store")
    @JsonManagedReference(value = "store_order")
    private List<Order> orderList;
}
