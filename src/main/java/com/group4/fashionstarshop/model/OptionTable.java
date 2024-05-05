package com.group4.fashionstarshop.model;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "option_table")
public class OptionTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "optionTable")
    @JsonManagedReference(value = "optionTable_optionValue")
    private List<OptionValue> optionValues;

    @ManyToOne
    @JoinColumn(name="product_id")
    @JsonBackReference(value = "product_optionTable")
    private Product product;
}
