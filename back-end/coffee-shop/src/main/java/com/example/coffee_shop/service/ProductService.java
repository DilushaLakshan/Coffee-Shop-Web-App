package com.example.coffee_shop.service;

import com.example.coffee_shop.model.Product;
import com.example.coffee_shop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final String uploadDir = "uploads/";

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product){
        return productRepository.save(product);
    }
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    public Product getProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
    public Product updateProduct(Long id, Product updatedProduct){
        Optional<Product> existingProduct = productRepository.findById(id);
        if(existingProduct.isPresent()){
            Product product = existingProduct.get();
            product.setProductName(updatedProduct.getProductName());
            product.setPrice(updatedProduct.getPrice());
            product.setDescription(updatedProduct.getDescription());
//            product.setImgUrl(updatedProduct.getImgUrl());

            return productRepository.save(product);
        }
        return null;
    }
    public String uploadImage(MultipartFile file) throws IOException {
        Path filePath = Paths.get(uploadDir + file.getOriginalFilename());
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());
        return filePath.toString();
    }
}
