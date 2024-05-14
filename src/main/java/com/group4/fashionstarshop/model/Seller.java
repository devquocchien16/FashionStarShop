package com.group4.fashionstarshop.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name ="SELLER")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seller_name")
    private String sellerName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
    @Column(name = "confirm_password")
    private String confirmPassword;
    @Column(name = "balance")
    private Double balance;
    @Column(name = "phone")
    private String phone;

    private boolean enabled;
    @Column(name = "role")
    private String role;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;    
      
    @OneToMany(mappedBy = "seller")
    @JsonManagedReference(value = "address_seller")
    private List<Address> addresses;

    @OneToOne(mappedBy = "seller", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "store_seller")
    private Store store;
    
    @OneToMany(mappedBy = "seller")
    @JsonManagedReference(value = "comment_seller")
    private List<Comment> storeComments;


}
