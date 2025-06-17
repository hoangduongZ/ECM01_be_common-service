package com.shop.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private UUID uuid = UUID.randomUUID();
    @Column(name = "order_number")
    private String orderNumber;
    @Column(name = "user_id")
    private Long user_id;
    @Column(name = "customer_email")
    private String customerEmail;
    @Column(name = "customer_phone")
    private String customerPhone;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "shipping_address")
    private String shippingAddress;
    @Column(name = "billing_address")
    private String billingAddress;

    @Enumerated(EnumType.STRING)
    private Status status;
    private BigDecimal subtotal;
    @Column(name = "tax_amount")
    private BigDecimal taxAmount;
    @Column(name = "shipping_fee")
    private BigDecimal shippingFee;
    @Column(name = "discount_amount")
    private BigDecimal discountAmount;
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;
    @Column(name = "coupon_code")
    private String couponCode;
    @Column(name = "shipping_method")
    private String shippingMethod;
    @Column(name = "tracking_number")
    private String trackingNumber;
    @Column(name = "shipped_at")
    private ZonedDateTime shippedAt;
    @Column(name = "delivered_at")
    private ZonedDateTime deliveredAt;
    private String notes;
    @Column(name = "admin_notes")
    private String adminNotes;

    public static enum Status{
        pending, confirmed, processing, shipped, delivered, cancelled, refunded
    }
}