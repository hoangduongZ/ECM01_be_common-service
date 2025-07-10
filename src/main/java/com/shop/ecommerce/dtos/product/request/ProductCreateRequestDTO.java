package com.shop.ecommerce.dtos.product.request;

import com.shop.ecommerce.model.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ProductCreateRequestDTO {
    @Schema(description = "Tên sản phẩm", example = "iPhone 15", required = true)
    @NotBlank
    private String name;

    @Schema(description = "Slug sản phẩm (duy nhất, dạng URL)", example = "iphone-15", required = true)
    @NotBlank
    private String slug;

    @Schema(description = "Mô tả ngắn sản phẩm", example = "Bản cao cấp Apple 2023")
    private String shortDescription;

    @Schema(description = "Mô tả chi tiết sản phẩm", example = "Sản phẩm sử dụng chip A17 Bionic, camera 48MP...")
    private String description;

    @Schema(description = "Mã SKU nội bộ", example = "IPH15-BLACK-128GB")
    private String sku;

    @Schema(description = "ID thương hiệu", example = "1", required = true)
    @NotNull
    private Long brandId;

    @Schema(description = "ID danh mục sản phẩm", example = "3", required = true)
    @NotNull
    private Long categoryId;

    // Giá bán
    @Schema(description = "Giá bán sản phẩm", example = "1999.99")
    private BigDecimal price;

    @Schema(description = "Giá niêm yết (giá gạch)", example = "2199.99")
    private BigDecimal comparePrice;

    @Schema(description = "Giá vốn (cost)", example = "1700.00")
    private BigDecimal costPrice;

    // Tồn kho
    @Schema(description = "Có theo dõi tồn kho?", example = "true", defaultValue = "true")
    private Boolean trackInventory = true;

    @Schema(description = "Số lượng tồn kho", example = "100", defaultValue = "0")
    private Integer inventoryQuantity = 0;

    @Schema(description = "Cho phép đặt hàng khi hết hàng?", example = "false", defaultValue = "false")
    private Boolean allowBackorder = false;

    @Schema(description = "Ngưỡng cảnh báo tồn kho thấp", example = "10")
    private Integer lowStockThreshold = 10;

    // Trọng lượng & Kích thước
    @Schema(description = "Khối lượng (kg)", example = "0.5")
    private BigDecimal weight;

    @Schema(description = "Chiều dài (cm)", example = "15.0")
    private BigDecimal length;

    @Schema(description = "Chiều rộng (cm)", example = "7.5")
    private BigDecimal width;

    @Schema(description = "Chiều cao (cm)", example = "0.8")
    private BigDecimal height;

    // SEO
    @Schema(description = "Meta title SEO", example = "Mua iPhone 15 giá tốt")
    private String metaTitle;

    @Schema(description = "Meta description SEO", example = "Mua iPhone 15 chính hãng, bảo hành 12 tháng")
    private String metaDescription;

    @Schema(description = "Ảnh chính sản phẩm", example = "https://cdn.shop.com/products/iphone15.jpg")
    private String featuredImageUrl;

    @Schema(description = "Chuỗi URL ảnh gallery, phân cách bằng dấu phẩy", example = "url1.jpg,url2.jpg")
    private String galleryImages;

    @Schema(description = "Trạng thái sản phẩm", example = "DRAFT")
    private Product.ProductStatus status = Product.ProductStatus.DRAFT;

    @Schema(description = "Sản phẩm nổi bật?", example = "false", defaultValue = "false")
    private Boolean isFeatured = false;

    @Schema(description = "Ẩn giá sản phẩm?", example = "false", defaultValue = "false")
    private Boolean isPriceHidden = false;

    public ProductCreateRequestDTO(String name, String slug, String shortDescription, String description,
                                   String sku, Long brandId, Long categoryId, BigDecimal price,
                                   BigDecimal comparePrice, BigDecimal costPrice, Boolean trackInventory,
                                   Integer inventoryQuantity, Boolean allowBackorder, Integer lowStockThreshold,
                                   BigDecimal weight, BigDecimal length, BigDecimal width, BigDecimal height,
                                   String metaTitle, String metaDescription, String featuredImageUrl,
                                   String galleryImages, Product.ProductStatus status, Boolean isFeatured,
                                   Boolean isPriceHidden) {
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.description = description;
        this.sku = sku;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.price = price;
        this.comparePrice = comparePrice;
        this.costPrice = costPrice;
        this.trackInventory = trackInventory;
        this.inventoryQuantity = inventoryQuantity;
        this.allowBackorder = allowBackorder;
        this.lowStockThreshold = lowStockThreshold;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.metaTitle = metaTitle;
        this.metaDescription = metaDescription;
        this.featuredImageUrl = featuredImageUrl;
        this.galleryImages = galleryImages;
        this.status = status;
        this.isFeatured = isFeatured;
        this.isPriceHidden = isPriceHidden;
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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getComparePrice() {
        return comparePrice;
    }

    public void setComparePrice(BigDecimal comparePrice) {
        this.comparePrice = comparePrice;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public Boolean getTrackInventory() {
        return trackInventory;
    }

    public void setTrackInventory(Boolean trackInventory) {
        this.trackInventory = trackInventory;
    }

    public Integer getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(Integer inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }

    public Boolean getAllowBackorder() {
        return allowBackorder;
    }

    public void setAllowBackorder(Boolean allowBackorder) {
        this.allowBackorder = allowBackorder;
    }

    public Integer getLowStockThreshold() {
        return lowStockThreshold;
    }

    public void setLowStockThreshold(Integer lowStockThreshold) {
        this.lowStockThreshold = lowStockThreshold;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public String getFeaturedImageUrl() {
        return featuredImageUrl;
    }

    public void setFeaturedImageUrl(String featuredImageUrl) {
        this.featuredImageUrl = featuredImageUrl;
    }

    public String getGalleryImages() {
        return galleryImages;
    }

    public void setGalleryImages(String galleryImages) {
        this.galleryImages = galleryImages;
    }

    public Product.ProductStatus getStatus() {
        return status;
    }

    public void setStatus(Product.ProductStatus status) {
        this.status = status;
    }

    public Boolean getFeatured() {
        return isFeatured;
    }

    public void setFeatured(Boolean featured) {
        isFeatured = featured;
    }

    public Boolean getPriceHidden() {
        return isPriceHidden;
    }

    public void setPriceHidden(Boolean priceHidden) {
        isPriceHidden = priceHidden;
    }
}
