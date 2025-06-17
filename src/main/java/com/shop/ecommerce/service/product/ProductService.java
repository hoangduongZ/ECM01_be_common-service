package com.shop.ecommerce.service.product;

import com.shop.ecommerce.dtos.ProductResponseDTO;
import com.shop.ecommerce.mapper.ProductMapper;
import com.shop.ecommerce.model.Product;
import com.shop.ecommerce.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Page<ProductResponseDTO> getAllProducts(String search, String categorySlug,
                                                   String brandSlug, BigDecimal minPrice,
                                                   BigDecimal maxPrice, Boolean inStock,
                                                   Boolean isFeatured, int page, int size, String[] sort) {
        Sort sorting = buildSort(sort);
        Pageable pageable = PageRequest.of(page, size, sorting);
        Page<Product> products = productRepository.filterProducts(search,categorySlug,brandSlug,
                minPrice, maxPrice, inStock,pageable);
        return products.map(productMapper::toProductResponse);
    }

    private Sort buildSort(String[] sort) {
        List<Sort.Order> orders = new ArrayList<>();
        for (String sortParam : sort) {
            String[] _sort = sortParam.split(",");
            String property = _sort[0];
            Sort.Direction direction = _sort.length > 1 ? Sort.Direction.fromString(_sort[1]) : Sort.Direction.ASC;
            orders.add(new Sort.Order(direction, property));
        }
        return Sort.by(orders);
    }
}
