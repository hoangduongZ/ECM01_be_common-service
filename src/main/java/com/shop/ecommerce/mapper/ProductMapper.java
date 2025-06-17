package com.shop.ecommerce.mapper;

import com.shop.ecommerce.dtos.ProductResponseDTO;
import com.shop.ecommerce.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {
    public ProductResponseDTO toProductResponse(Product product){
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setSlug(product.getSlug());
        dto.setShortDescription(product.getShortDescription());
        dto.setFeaturedImageUrl(product.getFeaturedImageUrl());
        dto.setPrice(product.getPrice());
        dto.setComparePrice(product.getComparePrice());
        dto.setPriceHidden(product.isPriceHidden());
        dto.setStatus(product.getStatus());
        dto.setFeatured(product.getFeatured());
        dto.setRatingAverage(product.getRatingAverage());
        dto.setRatingCount(product.getRatingCount());

        if (product.getCategory() != null) {
            dto.setCategoryName(product.getCategory().getName());
        };
        if (product.getBrand() != null) {
            dto.setBrandName(product.getBrand().getName());
        }
        return dto;
    }
}
