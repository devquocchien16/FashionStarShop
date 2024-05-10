package com.group4.fashionstarshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "password")
    private String password;
    @Column(name = "confirm_password")
    private String confirmPassword;
    @Column(name = "birthday")
    private LocalDate birthday;
    @Column(name = "gender")
    private boolean gender;
    @Column(name = "phone")
    private String phone;
    @Column(name = "enabled")
    private boolean enabled;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference(value = "user_address")
    private Set<Address> address;

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role;

    @OneToOne(mappedBy = "user")
    private Cart cart;

    @OneToMany(mappedBy = "user")
    @JsonBackReference(value = "user_order")
    private Set<Order> orders;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference(value = "user_review")
    private List<Review> reviewList;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference(value = "user_paymentMethod")
    private Set<PaymentMethod> paymentMethods;
}
