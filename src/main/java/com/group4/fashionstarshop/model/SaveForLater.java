package com.group4.fashionstarshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import jakarta.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "SAVE_FOR_LATER")
public class SaveForLater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private int quanity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonBackReference(value = "cart_saveForLater")
    private Cart cart;

    @OneToOne
    @JoinColumn(name = "variant_id")
    private Variant variant;
}
