package com.shop.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String slug;
    private String excerpt;
    private String content;
    private String featuredImage;
    private Integer authorId;
    private Integer categoryId;
    private String status;
    private String metaTitle;
    private String metaDescription;
    private Integer viewCount;
    private ZonedDateTime publishedAt;
}