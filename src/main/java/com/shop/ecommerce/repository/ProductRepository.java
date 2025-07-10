package com.shop.ecommerce.repository;

import com.shop.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p " +
            "LEFT JOIN p.category c " + // Tham gia với Category để lọc theo slug
            "LEFT JOIN p.brand b " +    // Tham gia với Brand để lọc theo slug
            "WHERE p.status = 'active' " + // **Best Practice**: Luôn lọc sản phẩm active cho người dùng cuối
            // 1. Lọc theo search (trên nhiều trường: name, shortDescription, description, sku)
            "AND (:search IS NULL OR " +
            "     LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "     LOWER(p.shortDescription) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "     LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "     LOWER(p.sku) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            // 2. Lọc theo category slug
            "AND (:categorySlug IS NULL OR c.slug = :categorySlug) " +
            // 3. Lọc theo brand slug
            "AND (:brandSlug IS NULL OR b.slug = :brandSlug) " +
            // 4. Lọc theo khoảng giá
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            // 5. Lọc theo tình trạng còn hàng
            "AND (:inStock IS NULL OR " +
            "     (:inStock = TRUE AND p.trackInventory = TRUE AND p.inventoryQuantity > 0) OR " +
            "     (:inStock = FALSE AND (p.trackInventory = FALSE OR p.inventoryQuantity <= 0)))")
    Page<Product> filterProducts(
                                  @Param("search") String search,
                                  @Param("categorySlug") String categorySlug,
                                  @Param("brandSlug") String brandSlug,
                                  @Param("minPrice") BigDecimal minPrice,
                                  @Param("maxPrice") BigDecimal maxPrice,
                                  @Param("inStock") Boolean inStock,
                                  Pageable pageable
    );

    Optional<Product> findByUuid(UUID uuid);

    boolean existsBySlug(String slug);
}
