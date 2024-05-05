package com.group4.fashionstarshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imgPath;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    @JsonBackReference(value = "variant_image")
    private Variant variant;

    @ManyToOne
    @JoinColumn(name="store_category_id")
    @JsonBackReference(value = "storeCategory_image")
    private StoreCategory storeCategory;



}
