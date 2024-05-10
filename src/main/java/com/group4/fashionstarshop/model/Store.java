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
    private String evidence;
    private String edittingName;
    private boolean status;
    private String adminReply;
    private boolean type;
    private String rejectedReason;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    @JsonBackReference(value = "store_seller")
    private Seller seller;

    @OneToMany(mappedBy = "store")
    @JsonManagedReference(value = "store_category")
    private List<StoreCategory> storeCategoryList;

    @OneToMany(mappedBy = "store")
    @JsonManagedReference(value = "store_product")
    private List<Product> productlist;

    @OneToMany(mappedBy = "store")
    @JsonManagedReference(value = "store_order")
    private List<Order> orderList;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @JsonBackReference(value = "store_address")
    private Address address;
}
