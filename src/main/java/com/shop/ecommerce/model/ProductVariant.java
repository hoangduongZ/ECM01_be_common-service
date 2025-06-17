package com.shop.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "product_variants")
public class ProductVariant extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    private String sku;
    private String name;
    private BigDecimal price;
    @Column(name = "compare_price")
    private BigDecimal comparePrice;
    @Column(name = "cost_price")
    private BigDecimal costPrice;
    @Column(name = "inventory_quantity")
    private Integer inventoryQuantity;
    private BigDecimal weight;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "sort_order")
    private Integer sortOrder;
    @Column(name = "is_active")
    private Boolean isActive;
}
