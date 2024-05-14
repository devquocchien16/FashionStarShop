package com.group4.fashionstarshop.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;
import jakarta.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user_order")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "store_id")
    @JsonBackReference(value = "store_order")
    private Store store;    
   
    
    @Column(name = "order_date")
    private String orderDate;

    @ManyToOne
    @JoinColumn(name = "address_id")
    @JsonBackReference(value = "address_order")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "shipping_method_id")
    private ShippingMethod shippingMethod;

    @OneToMany(mappedBy = "shop_order")    
    @JsonManagedReference(value = "order_orderItem")
    private List<OrderItem> orderItemList;
    
    private String status;

    @Column(name = "order_total")
    private Double orderTotal;
    
    private Date createdAt;
    private Date canceledAt;
    private Date acceptedAt;
    private Date deliveringAt;
    private Date completedAt;
}
