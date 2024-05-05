package com.group4.fashionstarshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "payment_method")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigInteger cartNumber;

    private String nameOnCard;

    private String expirationDate;

    private Boolean defaultPayment;

    @OneToMany(mappedBy = "paymentMethod")
    private Set<Order> shopOrderSet;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable=false)
    @JsonBackReference(value = "user_paymentMethod")
    private User user;
}
