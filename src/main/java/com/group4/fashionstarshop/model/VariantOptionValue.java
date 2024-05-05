package com.group4.fashionstarshop.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "variant_optionvalue")
public class VariantOptionValue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "variant_id")
	private Variant variant;

	@ManyToOne
	@JoinColumn(name = "optionvalue_id")
	private OptionValue option_value;

}
