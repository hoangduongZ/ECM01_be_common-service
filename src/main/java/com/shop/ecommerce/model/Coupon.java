package com.shop.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "coupons")
public class Coupon extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String name;
    private String description;
    private String type;
    private BigDecimal value;
    @Column(name = "minimum_amount")
    private BigDecimal minimumAmount;
    @Column(name = "maximum_discount")
    private BigDecimal maximumDiscount;
    @Column(name = "usage_limit")
    private Integer usageLimit;
    @Column(name = "usage_limit_per_customer")
    private Integer usageLimitPerCustomer;
    @Column(name = "used_count")
    private Integer usedCount;
    @Column(name = "is_active")
    private Boolean isActive;
}
