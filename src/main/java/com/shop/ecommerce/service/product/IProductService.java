package com.shop.ecommerce.service.product;

import com.shop.ecommerce.dtos.product.ProductResponseDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface IProductService {
    Page<ProductResponseDTO> getAllProducts(String search, String categorySlug, String brandSlug, BigDecimal minPrice, BigDecimal maxPrice, Boolean inStock, Boolean isFeatured, int page, int size, String[] sort);
}
