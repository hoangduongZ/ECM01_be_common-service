package com.shop.ecommerce.repository;

import com.shop.ecommerce.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
