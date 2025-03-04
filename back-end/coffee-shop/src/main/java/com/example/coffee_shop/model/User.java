package com.example.coffee_shop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    @NotBlank(message = "Name is required")
    private String name;

    @Column(name = "email")
    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;

    @Column(name = "address")
    @NotBlank(message = "Address is required")
    private String address;

    @Column(name = "contact")
    @NotNull(message = "Contact number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Contact number must be exactly 10 digits")
    private String contact;

    @Column(name = "password")
    @NotBlank(message = "Password is required")
    private String password;

    @ManyToOne
    @JoinColumn(name = "user_type_id", nullable = false)
    private UserType userType;

    @Transient // This field is not persisted in the database
    private long userTypeId;

    public User() {
    }

    public User(String name, String email, String address, String contact, String password, long userTypeId) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.contact = contact;
        this.password = password;
        this.userTypeId = userTypeId;
    }

    // Getters and setters for all fields
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public long getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(long userTypeId) {
        this.userTypeId = userTypeId;
    }
}