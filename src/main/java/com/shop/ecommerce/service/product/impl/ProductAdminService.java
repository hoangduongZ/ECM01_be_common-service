package com.shop.ecommerce.service.product.impl;

import com.shop.ecommerce.dtos.product.ProductResponseDTO;
import com.shop.ecommerce.dtos.product.request.ProductCreateRequestDTO;
import com.shop.ecommerce.dtos.product.request.ProductUpdateRequestDTO;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProductAdminService implements IProductAdminService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductMapper productMapper;
    private final SlugUtil slugUtil;

    public ProductAdminService(ProductRepository productRepository, CategoryRepository categoryRepository, BrandRepository brandRepository, ProductMapper productMapper, SlugUtil slugUtil) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.productMapper = productMapper;
        this.slugUtil = slugUtil;
    }

    @Override
    @Transactional
    public ProductResponseDTO create(ProductCreateRequestDTO requestDTO) {
        String slug= (requestDTO.getSlug() == null || requestDTO.getSlug().isBlank())?
                slugUtil.generateSlugWithUUID(requestDTO.getName()):
                slugUtil.generateSlugWithUUID(requestDTO.getSlug());

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

    @Override
    public ProductResponseDTO update(UUID id, ProductUpdateRequestDTO requestDTO) {
        Product product= productRepository.findByUuid(id).orElseThrow(()->
                new NotFoundException("Product not found with id "+ id));
        if (requestDTO.getSlug() != null && requestDTO.getSlug().isBlank()){
            String newSlug = slugUtil.generateSlugWithUUID(requestDTO.getSlug());
            if (!newSlug.equals(product.getSlug()) && productRepository.existsBySlug(newSlug)){
                throw new DuplicateSlugException(newSlug);
            }
            product.setSlug(newSlug);
        }

        if (requestDTO.getName() != null){
            product.setName(requestDTO.getName());
        }

        if (requestDTO.getShortDescription() != null){
            product.setShortDescription(requestDTO.getShortDescription());
        }

        if (requestDTO.getPrice() != null){
            product.setPrice(requestDTO.getPrice());
        }

        if (requestDTO.getInventory() != null){
            product.setInventoryQuantity(requestDTO.getInventory());
        }

        if (requestDTO.getCategoryId() != null){
            Category category = categoryRepository.findById(requestDTO.getCategoryId())
                    .orElseThrow(()-> new NotFoundException("Category not found with id: "
                            + requestDTO.getCategoryId()));
            product.setCategory(category);
        }

        if (requestDTO.getBrandId() != null){
            Brand brand= brandRepository.findById(requestDTO.getBrandId())
                    .orElseThrow(()-> new NotFoundException("Brand not found with id: "
                            + requestDTO.getBrandId()));
            product.setBrand(brand);
        }

        productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

    @Override
    public boolean deleteProductById(UUID uuid) {
        Product product = productRepository.findByUuid(uuid).orElseThrow(
                ()-> new NotFoundException("Product not found with uuid: "+ uuid)
        );
        if (product.getUuid() != null){
            productRepository.deleteById(product.getId());
            return true;
        }
        return false;
    }
}
