package com.server.apple.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Payment {

    @Id
    @Column(name="payment_code")
    private int paymentCode;

    @ManyToOne
    @JoinColumn(name="cart_code")
    private Cart cart;

    @Column(name="total_price")
    private int totalPrice;

    @Column
    private String method;

    @Column
    private String status;





}

