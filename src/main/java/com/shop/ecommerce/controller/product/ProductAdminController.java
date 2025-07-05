package com.shop.ecommerce.controller.product;

import com.shop.ecommerce.service.product.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    CRUD product
* */
@RestController
@RequestMapping("${api.version}/admin/products")
@PreAuthorize("hasRole('ADMIN')")
public class ProductAdminController {
    private final IProductService productService;

    public ProductAdminController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> create() {

        return null;
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> update(@PathVariable String uuid) {
        return null;
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable String uuid) {
        return null;
    }
}
