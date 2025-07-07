package com.shop.ecommerce.controller.product;

import com.shop.ecommerce.dtos.ProductResponseDTO;
import com.shop.ecommerce.service.product.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
/*
* product public API
* */
@RestController
@RequestMapping("${api.version}/products")
@Tag(name = "Product-Public", description = "Product product APIs")
//@SecurityRequirement(name = "bearerAuth")
public class ProductController {
    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Paging product")
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(
            @RequestParam(required = false) String search, // Tìm kiếm theo tên/mô tả ngắn
            @RequestParam(required = false) String categorySlug, // Lọc theo slug danh mục
            @RequestParam(required = false) String brandSlug,    // Lọc theo slug thương hiệu
            @RequestParam(required = false) BigDecimal minPrice, // Lọc theo giá thấp nhất
            @RequestParam(required = false) BigDecimal maxPrice, // Lọc theo giá cao nhất
            @RequestParam(required = false) Boolean inStock,     // Lọc sản phẩm còn hàng
            @RequestParam(required = false) Boolean isFeatured,  // Lọc sản phẩm nổi bật
            @RequestParam(defaultValue = "0") int page,          // Số trang (bắt đầu từ 0)
            @RequestParam(defaultValue = "10") int size,         // Kích thước trang
            @RequestParam(defaultValue = "id,desc") String[] sort // Sắp xep
    ) {
        Page<ProductResponseDTO> productsPage = productService.getAllProducts(
                search, categorySlug, brandSlug, minPrice, maxPrice, inStock, isFeatured, page, size, sort
        );
        return ResponseEntity.ok(productsPage);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("test")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok("Test success!");
    }
}
