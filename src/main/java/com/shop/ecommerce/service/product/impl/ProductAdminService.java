package com.shop.ecommerce.service.product.impl;

import com.shop.ecommerce.dtos.ProductResponseDTO;
import com.shop.ecommerce.dtos.request.ProductCreateRequestDTO;
import com.shop.ecommerce.exception.DuplicateSlugException;
import com.shop.ecommerce.exception.NotFoundException;
import com.shop.ecommerce.helper.SlugUtil;
import com.shop.ecommerce.mapper.ProductMapper;
import com.shop.ecommerce.model.Brand;
import com.shop.ecommerce.model.Category;
import com.shop.ecommerce.model.Product;
import com.shop.ecommerce.repository.BrandRepository;
import com.shop.ecommerce.repository.CategoryRepository;
import com.shop.ecommerce.repository.ProductRepository;
import com.shop.ecommerce.service.product.IProductAdminService;
import org.springframework.stereotype.Service;

@Service
public class ProductAdminService implements IProductAdminService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductMapper productMapper;

    public ProductAdminService(ProductRepository productRepository, CategoryRepository categoryRepository, BrandRepository brandRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponseDTO create(ProductCreateRequestDTO requestDTO) {
        String slug= (requestDTO.getSlug() == null || requestDTO.getSlug().isBlank())?
                SlugUtil.generateSlugWithUUID(requestDTO.getName()):
                SlugUtil.generateSlugWithUUID(requestDTO.getSlug());

        if (productRepository.existsBySlug(slug)) {
            throw new DuplicateSlugException(slug);
        }

        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));
        Brand brand = null;
        if (requestDTO.getBrandId() != null){
            brand = brandRepository.findById(requestDTO.getBrandId())
                    .orElseThrow(()-> new NotFoundException("Brand not found!"));
        }

        Product product= productMapper.productRequestToEntity(requestDTO);
        product.setSlug(slug);
        product.setCategory(category);
        product.setBrand(brand);
        product.setStatus(Product.ProductStatus.DRAFT);

        productRepository.save(product);
        return productMapper.toProductResponse(product);
    }
}
