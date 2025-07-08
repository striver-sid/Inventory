package com.example.demo.Service;

import com.example.demo.Components.JwtUtil;
import com.example.demo.DTO.DeleteProductDTO;
import com.example.demo.DTO.ProductDTO;
import com.example.demo.DTO.ProductResponseDTO;
import com.example.demo.DTO.UpdateProductDTO;
import com.example.demo.Model.ProductModel;
import com.example.demo.Model.ProductVarietyModel;
import com.example.demo.Model.TenantModel;
import com.example.demo.Model.UserModel;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.ProductVarietyRepository;
import com.example.demo.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductVarietyRepository productVarietyRepository;

    public String createProduct(ProductDTO dto, String token) {
        // 1. Extract claims from the access token
        Claims claims = JwtUtil.extractAllClaims(token); // Or however you extract claims

        Long userId = Long.parseLong(claims.get("userId").toString());
        String role = claims.get("role").toString();

        // 2. Check if role is allowed
        if (!(role.equalsIgnoreCase("Staff") || role.equalsIgnoreCase("Admin"))) {
            throw new RuntimeException("Unauthorized: Only Staff or Admin can create products.");
        }

        // 3. Get the user and their tenant
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        TenantModel tenant = user.getTenant(); // Assuming UserModel has a tenant field

        // 4. Build ProductModel
        ProductModel product = ProductModel.builder()
                .productName(dto.getProductName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .active(dto.getActive() != null ? dto.getActive() : true)
                .tenant(tenant)
                .build();

        // 5. Save product
        ProductModel savedProduct = productRepository.save(product);

        // 6. Create and save default ProductVariety
        ProductVarietyModel defaultVariety = ProductVarietyModel.builder()
                .varietyName(savedProduct.getProductName())
                .price(savedProduct.getPrice())
                .isDefault(true)
                .active(savedProduct.getActive())
                .product(savedProduct)
                .tenant(tenant)
                .build();

        productVarietyRepository.save(defaultVariety);

        // 7. Return response DTO
        return "Product added successfully";
    }

    public String updateProduct(UpdateProductDTO dto, String token) {

        Claims claims = JwtUtil.extractAllClaims(token);
        String role = claims.get("role").toString();
        Long userId = Long.parseLong(claims.get("userId").toString());

        if (!(role.equalsIgnoreCase("Staff") || role.equalsIgnoreCase("Admin"))) {
            throw new RuntimeException("Unauthorized: Only Staff or Admin can update products.");
        }

        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        TenantModel tenant = user.getTenant(); // Assuming UserModel has a tenant field

        ProductModel existingProduct = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductModel updatedProduct = ProductModel.builder()
                .productId(existingProduct.getProductId()) // Retain the same ID
                .productName(dto.getProductName() != null ? dto.getProductName() : existingProduct.getProductName())
                .description(dto.getDescription() != null ? dto.getDescription() : existingProduct.getDescription())
                .price(dto.getPrice() != null ? dto.getPrice() : existingProduct.getPrice())
                .active(dto.getActive() != null ? dto.getActive() : true)
                .tenant(tenant)
                .build();

        productRepository.save(updatedProduct);
        return "Product updated successfully";
    }

    public String deleteProduct(DeleteProductDTO dto, String token) {
        Claims claims = JwtUtil.extractAllClaims(token);
        String role = claims.get("role").toString();
        Long userId = Long.parseLong(claims.get("userId").toString());

        if (!(role.equalsIgnoreCase("Staff") || role.equalsIgnoreCase("Admin"))) {
            throw new RuntimeException("Unauthorized: Only Staff or Admin can delete products.");
        }

        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProductModel product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // ✅ Check if product belongs to user's tenant
        if (!product.getTenant().getTenantId().equals(user.getTenant().getTenantId())) {
            throw new RuntimeException("Unauthorized: You can only delete products of your tenant.");
        }

        productRepository.deleteById(dto.getProductId());
        return "Product deleted successfully";
    }

    public ProductResponseDTO getProduct(Long productId, String token) {
        Claims claims = JwtUtil.extractAllClaims(token);
        String role = claims.get("role").toString();
        Long userId = Long.parseLong(claims.get("userId").toString());

        if (!(role.equalsIgnoreCase("Staff") || role.equalsIgnoreCase("Admin"))) {
            throw new RuntimeException("Unauthorized: Only Staff or Admin can view product details.");
        }

        ProductModel product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Convert to DTO
        return ProductResponseDTO.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .price(product.getPrice())
                .active(product.getActive())
                .build();
    }





}
