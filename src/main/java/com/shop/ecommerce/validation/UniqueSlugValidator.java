package com.shop.ecommerce.validation;

import com.shop.ecommerce.repository.ProductRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class UniqueSlugValidator implements ConstraintValidator<UniqueSlug, String> {
    private final ProductRepository productRepository;

    public UniqueSlugValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()){
            return true;
        }
        return !productRepository.existsBySlug(value);
    }
}
