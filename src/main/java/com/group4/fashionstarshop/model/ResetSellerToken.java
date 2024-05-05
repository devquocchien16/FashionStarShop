package com.group4.fashionstarshop.model;

import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "token_seller")
public class ResetSellerToken {
	private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    private Date expiryDate;
    
    public ResetSellerToken(String token, Seller seller) {
        super();
        this.token = token;
        this.seller = seller;
        this.expiryDate = this.getTokenExpirationTime();
    }

    public ResetSellerToken(String token) {
        super();
        this.token = token;
        this.expiryDate = this.getTokenExpirationTime();
    }
    public Date getTokenExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, EXPIRATION);
        return new Date(calendar.getTime().getTime());
    }
}
