package com.shop.ecommerce.mapper;

import com.shop.ecommerce.dtos.ProductResponseDTO;
import com.shop.ecommerce.dtos.request.ProductCreateRequestDTO;
import com.shop.ecommerce.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {
    public ProductResponseDTO toProductResponse(Product product){
        ProductResponseDTO dto = new ProductResponseDTO(
                product.getUuid(), product.getName(),
                product.getSlug(),product.getShortDescription(),
                product.getFeaturedImageUrl(), product.getPrice(),
                product.getComparePrice(), product.isPriceHidden(),
                product.getStatus(), product.getFeatured(),
                product.getRatingAverage(), product.getRatingCount(),
                product.getBrand().getName(), product.getCategory().getName()
        );
        return dto;
    }

    public Product productRequestToEntity(ProductCreateRequestDTO dto){
        Product product = new Product();
        product.setName(dto.getName());
        product.setShortDescription(dto.getShortDescription());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setComparePrice(dto.getComparePrice());
        return product;
    }
}
