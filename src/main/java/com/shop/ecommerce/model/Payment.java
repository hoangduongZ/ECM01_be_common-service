package com.shop.ecommerce.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
public class Payment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private UUID uuid = UUID.randomUUID();
    private Integer orderId;
    private String paymentMethod;
    private BigDecimal amount;
    private String currency;
    private String status;
    private String transactionId;
    private String gatewayResponse;
    private ZonedDateTime paidAt;
}
