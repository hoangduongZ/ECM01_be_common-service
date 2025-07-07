package com.shop.ecommerce.dtos.core;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

public class ApiErrorResponseDTO {
    @Schema(description = "Mã lỗi", example = "BAD_REQUEST")
    private String code;
    @Schema(description = "Thông báo lỗi", example = "Invalid product name")
    private String message;
    private String path;
    private List<FieldErrorDetail> errors;
    private LocalDateTime timestamp = LocalDateTime.now();

    public ApiErrorResponseDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiErrorResponseDTO(String code, String message, List<FieldErrorDetail> errors, String path) {
        this.code = code;
        this.message = message;
        this.errors = errors;
        this.path = path;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<FieldErrorDetail> getErrors() {
        return errors;
    }

    public void setErrors(List<FieldErrorDetail> errors) {
        this.errors = errors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
