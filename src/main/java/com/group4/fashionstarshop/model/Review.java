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
@Builder
@Getter
@Setter
@Table(name="REVIEW")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int star;
    private String title;
    private String content;
    private String image01;
    private String image02;
    private String image03;
    private String image04;
    private String image05;
    private String video;
    private boolean verified_shop;
    private boolean verified_admin;

    private Date create_at;
    private Date update_at;


    @ManyToOne
    @JoinColumn(name="customer_id")
    @JsonBackReference(value = "user_review")
    private User customer;

    @ManyToOne
    @JoinColumn(name ="variant_id")
    @JsonBackReference(value = "variant_review")
    private Variant variant;

    @OneToMany(mappedBy = "review")
    @JsonManagedReference(value = "comment_review")
    private List<Comment> commentList;

}
