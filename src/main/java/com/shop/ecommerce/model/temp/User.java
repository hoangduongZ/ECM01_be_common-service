//package com.shop.ecommerce.model;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Index;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.Table;
//
//import java.time.LocalDate;
//import java.time.ZonedDateTime;
//import java.util.HashSet;
//import java.util.Set;
//import java.util.UUID;
//
//@Entity
//@Table(name = "users", indexes = {
//        @Index(name = "idx_users_email", columnList = "email"),
//        @Index(name = "idx_users_phone", columnList = "phone"),
//        @Index(name = "idx_users_status", columnList = "status")
//})
//public class User extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "uuid", nullable = false, unique = true, updatable = false)
//    private UUID uuid = UUID.randomUUID();
//
//    @Column(name = "email", nullable = false, unique = true)
//    private String email;
//
//    @Column(name = "phone", length = 20)
//    private String phone;
//
//    @Column(name = "password_hash", nullable = false)
//    private String passwordHash;
//
//    @Column(name = "first_name", nullable = false, length = 100)
//    private String firstName;
//
//    @Column(name = "last_name", nullable = false, length = 100)
//    private String lastName;
//
//    @Column(name = "avatar_url", length = 500)
//    private String avatarUrl;
//
//    @Column(name = "date_of_birth")
//    private LocalDate dateOfBirth;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "gender", length = 10)
//    private Gender gender;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "role_id", nullable = false)
//    private Role role;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "status", length = 20)
//    private UserStatus status = UserStatus.ACTIVE;
//
//    @Column(name = "email_verified_at")
//    private ZonedDateTime emailVerifiedAt;
//
//    @Column(name = "last_login_at")
//    private ZonedDateTime lastLoginAt;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<UserAddress> addresses = new HashSet<>();
//
//    // Enums
//    public enum Gender {
//        MALE, FEMALE, OTHER
//    }
//
//    public enum UserStatus {
//        ACTIVE, INACTIVE, SUSPENDED
//    }
//
//    // Constructors
//    public User() {}
//
//    public User(String email, String passwordHash, String firstName, String lastName) {
//        this.email = email;
//        this.passwordHash = passwordHash;
//        this.firstName = firstName;
//        this.lastName = lastName;
//    }
//
//    // Getters and Setters
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//    public UUID getUuid() { return uuid; }
//    public void setUuid(UUID uuid) { this.uuid = uuid; }
//    public String getEmail() { return email; }
//    public void setEmail(String email) { this.email = email; }
//    public String getPhone() { return phone; }
//    public void setPhone(String phone) { this.phone = phone; }
//    public String getPasswordHash() { return passwordHash; }
//    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
//    public String getFirstName() { return firstName; }
//    public void setFirstName(String firstName) { this.firstName = firstName; }
//    public String getLastName() { return lastName; }
//    public void setLastName(String lastName) { this.lastName = lastName; }
//    public String getAvatarUrl() { return avatarUrl; }
//    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
//    public LocalDate getDateOfBirth() { return dateOfBirth; }
//    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
//    public Gender getGender() { return gender; }
//    public void setGender(Gender gender) { this.gender = gender; }
//    public Role getRole() { return role; }
//    public void setRole(Role role) { this.role = role; }
//    public UserStatus getStatus() { return status; }
//    public void setStatus(UserStatus status) { this.status = status; }
//    public ZonedDateTime getEmailVerifiedAt() { return emailVerifiedAt; }
//    public void setEmailVerifiedAt(ZonedDateTime emailVerifiedAt) { this.emailVerifiedAt = emailVerifiedAt; }
//    public ZonedDateTime getLastLoginAt() { return lastLoginAt; }
//    public void setLastLoginAt(ZonedDateTime lastLoginAt) { this.lastLoginAt = lastLoginAt; }
//    public Set<UserAddress> getAddresses() { return addresses; }
//    public void setAddresses(Set<UserAddress> addresses) { this.addresses = addresses; }
//}
