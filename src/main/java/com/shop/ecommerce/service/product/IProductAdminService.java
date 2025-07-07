package com.shop.ecommerce.service.product;

import com.shop.ecommerce.dtos.ProductResponseDTO;
import com.shop.ecommerce.dtos.request.ProductCreateRequestDTO;

public interface IProductAdminService {
    public ProductResponseDTO create(ProductCreateRequestDTO requestDTO);
}
