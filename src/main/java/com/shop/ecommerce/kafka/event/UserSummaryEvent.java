package com.shop.ecommerce.kafka.event;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

public class UserSummaryEvent implements Serializable {
    private Long userId;
    private UUID uuid;
    private String email;
    private String firstName;
    private String lastName;
    private String userName;
    private String role;
    private ZonedDateTime createdAt;

    public UserSummaryEvent(Long userId, String email, String role, ZonedDateTime createdAt) {
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
