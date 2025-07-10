package com.shop.ecommerce.service.product.impl;

import com.shop.ecommerce.dtos.product.ProductResponseDTO;
import com.shop.ecommerce.dtos.product.request.ProductCreateRequestDTO;
import com.shop.ecommerce.helper.SlugUtil;
import com.shop.ecommerce.model.Brand;
import com.shop.ecommerce.model.Category;
import com.shop.ecommerce.model.Product;
import com.shop.ecommerce.repository.BrandRepository;
import com.shop.ecommerce.repository.CategoryRepository;
import com.shop.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class ProductAdminServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private SlugUtil slugUtil;

    @InjectMocks
    private ProductAdminService productAdminService;

    @Test
    void createProduct_shouldGenerateSlugAndSaveProduct() {
        // Arrange
        ProductCreateRequestDTO requestDTO = new ProductCreateRequestDTO(
                "iPhone 15", null, "iPhone cao cấp", "Chip A17",
                "IPH15-BLK-128GB", 1L, 2L,
                new BigDecimal("1999.99"), new BigDecimal("2199.99"), new BigDecimal("1700.00"),
                true, 100, false, 10,
                new BigDecimal("0.5"), new BigDecimal("15"), new BigDecimal("7.5"), new BigDecimal("0.8"),
                "Mua iPhone 15 giá tốt", "iPhone 15 chính hãng bảo hành 12 tháng",
                "https://cdn.shop.com/products/iphone15.jpg",
                "https://cdn.shop.com/iphone15-1.jpg,https://cdn.shop.com/iphone15-2.jpg",
                Product.ProductStatus.ACTIVE, false, false
        );

        Brand brand = new Brand();
        brand.setId(1L);

        Category category = new Category();
        category.setId(2L);

        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(category));
        when(slugUtil.generateSlugWithUUID("iPhone 15")).thenReturn("iphone-15");
        when(productRepository.existsBySlug("iphone-15")).thenReturn(false);
        requestDTO.setSlug(slugUtil.generateSlugWithUUID("iPhone 15"));
        Product savedProduct = new Product();
        savedProduct.setId(99L);
        savedProduct.setName("iPhone 15");
        savedProduct.setSlug("iphone-15");

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // Act
        ProductResponseDTO result = productAdminService.create(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("iphone-15", result.slug());
        assertEquals("iPhone 15", result.name());

        verify(brandRepository).findById(1L);
        verify(categoryRepository).findById(2L);
        verify(slugUtil).generateSlugWithUUID("iPhone 15");
        verify(productRepository).existsBySlug("iphone-15");
        verify(productRepository).save(any(Product.class));
    }
}