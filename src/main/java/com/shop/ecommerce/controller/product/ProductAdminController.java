package com.shop.ecommerce.controller.product;

import com.shop.ecommerce.dtos.core.ApiResponseDTO;
import com.shop.ecommerce.dtos.product.ProductResponseDTO;
import com.shop.ecommerce.dtos.core.ApiErrorResponseDTO;
import com.shop.ecommerce.dtos.product.request.ProductCreateRequestDTO;
import com.shop.ecommerce.dtos.product.request.ProductUpdateRequestDTO;
import com.shop.ecommerce.service.product.IProductAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

/*
    CRUD product
* */
@RestController
@RequestMapping("${api.version}/admin/products")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Product-Admin", description = "Admin product CRUD APIs")
@SecurityRequirement(name = "bearerAuth")
public class ProductAdminController {
    private final IProductAdminService productAdminService;

    public ProductAdminController(IProductAdminService productAdminService) {
        this.productAdminService = productAdminService;
    }

    @Operation(summary = "Create a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created"),
            @ApiResponse(responseCode = "400", description = "Invalid data",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponseDTO.class)))
    })
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ProductCreateRequestDTO requestDTO) {
        ProductResponseDTO createdProduct = productAdminService.create(requestDTO);
        URI location = URI.create("/api/admin/products/" + createdProduct.uuid());
        return ResponseEntity.created(location).body(createdProduct);
    }

    @Operation(summary = "Update a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated"),
            @ApiResponse(responseCode = "400", description = "Invalid data",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponseDTO.class)))
    })
    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponseDTO<ProductResponseDTO>> update(@PathVariable String uuid, @RequestBody @Valid ProductUpdateRequestDTO requestDTO) {
        ProductResponseDTO productUpdated = productAdminService.update(UUID.fromString(uuid), requestDTO);
        return ResponseEntity.ok(new ApiResponseDTO<>(productUpdated));
    }

    @Operation(summary = "Delete a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted success"),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponseDTO.class)))
    })
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable String uuid) {
        Boolean status = productAdminService.deleteProductById(UUID.fromString(uuid));
        return ResponseEntity.ok(new ApiResponseDTO<>(java.util.Map.of("deleted", status)));
    }
}
