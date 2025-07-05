package com.shop.ecommerce.dtos.request;

import com.shop.ecommerce.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ProductCreateRequestDTO {
    @NotBlank
    private String name;

    private String slug;
    private String shortDescription;
    private String description;
    private String sku;

    @NotNull
    private Long brandId;

    @NotNull
    private Long categoryId;

    private BigDecimal price;
    private BigDecimal comparePrice;
    private BigDecimal costPrice;

    private Boolean trackInventory = true;
    private Integer inventoryQuantity = 0;
    private Boolean allowBackorder = false;
    private Integer lowStockThreshold = 10;

    private BigDecimal weight;
    private BigDecimal length;
    private BigDecimal width;
    private BigDecimal height;

    private String metaTitle;
    private String metaDescription;
    private String featuredImageUrl;
    private String galleryImages;

    private Product.ProductStatus status = Product.ProductStatus.draft;
    private Boolean isFeatured = false;
    private Boolean isPriceHidden = false;
}
