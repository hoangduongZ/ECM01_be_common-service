package com.shop.ecommerce.dtos.core;

public class ApiResponseDTO<T> {
    private T data;
    private String message;
    private int status;

    public ApiResponseDTO(T data) {
        this.data = data;
        this.message = "Success";
        this.status = 200;
    }

    public ApiResponseDTO(T data, String message, int status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }
}
