package com.shop.ecommerce.dtos;

import com.shop.ecommerce.model.Product;

import java.math.BigDecimal;
import java.util.UUID;

//record (deserialize)
/* immutable
 * auto generate private final field
 * constructor
 * getter
 * equal and hashcode
 * toString*/
public record ProductResponseDTO(UUID uuid,
                                 String name,
                                 String slug,
                                 String shortDescription,
                                 String featuredImageUrl,
                                 BigDecimal price,
                                 BigDecimal comparePrice,
                                 boolean isPriceHidden,
                                 Product.ProductStatus status,
                                 Boolean isFeatured,
                                 BigDecimal ratingAverage,
                                 Long ratingCount,
                                 String brandName,
                                 String categoryName) {
}
