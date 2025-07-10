package com.shop.ecommerce.dtos.product.request;

import com.shop.ecommerce.model.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "DTO for updating a product")
public class ProductUpdateRequestDTO {
    @Schema(description = "Name of the product", example = "iPhone 15 Pro Max")
    @Size(min = 2, max = 500, message = "Name must be between 2 and 500 characters")
    private String name;

    @Schema(description = "Slug used for SEO-friendly URL", example = "iphone-15-pro-max")
    private String slug;

    @Schema(description = "Short description of the product", example = "Flagship phone 2025")
    @Size(max = 1000, message = "Short description cannot exceed 1000 characters")
    private String shortDescription;

    @Schema(description = "Detailed description of the product")
    private String description;

    @Schema(description = "Price of the product", example = "1999.99")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    private BigDecimal price;

    @Schema(description = "Inventory count", example = "100")
    @Min(value = 0, message = "Inventory cannot be negative")
    private Integer inventory;

    @Schema(description = "ID of the category this product belongs to", example = "1")
    private Long categoryId;

    @Schema(description = "ID of the brand of the product", example = "5")
    private Long brandId;

    @Schema(description = "Status of the product", example = "PUBLISHED")
    private Product.ProductStatus status;

    public ProductUpdateRequestDTO(String name, String slug, String shortDescription, String description,
                                   BigDecimal price, Integer inventory, Long categoryId, Long brandId,
                                   Product.ProductStatus status) {
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.description = description;
        this.price = price;
        this.inventory = inventory;
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Product.ProductStatus getStatus() {
        return status;
    }

    public void setStatus(Product.ProductStatus status) {
        this.status = status;
    }
}
