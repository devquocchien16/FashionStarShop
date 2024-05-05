package com.group4.fashionstarshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String district;

    private String ward;

    private String city;

    private String street;
    
    private boolean defaultAddress;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user_address")
    private User user;

    @ManyToOne
    @JoinColumn(name="seller_id")
    @JsonBackReference(value = "seller_address")
    private Seller seller;

    @OneToMany(mappedBy = "address")
    @JsonManagedReference(value = "address_order")
    private Set<Order> orders;
}
