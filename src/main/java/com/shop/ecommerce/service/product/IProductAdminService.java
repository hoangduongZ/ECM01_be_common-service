package com.shop.ecommerce.service.product;

import com.shop.ecommerce.dtos.product.ProductResponseDTO;
import com.shop.ecommerce.dtos.product.request.ProductCreateRequestDTO;
import com.shop.ecommerce.dtos.product.request.ProductUpdateRequestDTO;

import java.util.UUID;

public interface IProductAdminService {
    ProductResponseDTO create(ProductCreateRequestDTO requestDTO);

    ProductResponseDTO update(UUID uuid, ProductUpdateRequestDTO productUpdateRequestDTO);

    boolean deleteProductById(UUID id);
}
