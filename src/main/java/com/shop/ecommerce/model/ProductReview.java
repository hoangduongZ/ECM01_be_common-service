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

import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "product_reviews")
public class ProductReview extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "user_id")
    private Long user_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    private Byte rating;
    private String title;
    private String content;
    @Column(name = "helpful_count")
    private Integer helpfulCount;
    @Column(name = "admin_reply")
    private String adminReply;
    private ZonedDateTime adminRepliedAt;
    @Column(name = "is_verified")
    private Boolean isVerified;
    @Column(name = "is_approved")
    private Boolean isApproved;
}
