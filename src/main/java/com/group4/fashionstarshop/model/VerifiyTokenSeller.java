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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "verify_seller")
public class VerifiyTokenSeller {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(targetEntity = Seller.class)
    @JoinColumn(nullable = false, name = "seller_id")
    private Seller seller;

    private Date expiryDate;
    
    public VerifiyTokenSeller(String token, User user) {
        super();
        this.token = token;
        this.seller = seller;
        this.expiryDate = this.getTokenExpirationTime();
    }

    public VerifiyTokenSeller(String token) {
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
