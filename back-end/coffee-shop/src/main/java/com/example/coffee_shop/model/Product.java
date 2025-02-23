package com.example.coffee_shop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name") // Ensure it matches the DB column
    @NotBlank(message = "Product Name is required")
    private String productName;

    @Column(name = "price")
    @NotNull(message = "Price is required")
    @Min(value = 1, message = "Price must be greater than 0")
    private Double price; // Changed from double to Double

    @Column(name = "description")
    @NotBlank(message = "Description is required")
    private String description;

    @Column(name = "img_url")
    private String imgUrl;

    public Product() {
    }

    public Product(String productName, Double price, String description, String imgUrl) {
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.imgUrl = imgUrl;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImgUrl() { return imgUrl; }
    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }
}
