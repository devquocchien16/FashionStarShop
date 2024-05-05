package com.group4.fashionstarshop.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "order_item")
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	@JsonBackReference(value="order_orderItem")
	private Order shop_order;
	
	@ManyToOne
	@JoinColumn(name = "variant_id")
	@JsonBackReference(value = "variant_orderItem")
	private Variant variant;
	private int quantity;

	private double price;

}
