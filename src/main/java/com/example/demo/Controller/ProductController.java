package com.example.demo.Controller;

import com.example.demo.DTO.DeleteProductDTO;
import com.example.demo.DTO.ProductDTO;
import com.example.demo.DTO.ProductResponseDTO;
import com.example.demo.DTO.UpdateProductDTO;
import com.example.demo.Model.ProductModel;
import com.example.demo.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class ProductController {

    @Autowired
    private ProductService productService;

    // ✅ Create product
    @PostMapping("/addProducts")
    public ResponseEntity<Object> addProducts(@RequestBody ProductDTO dto,
                                              @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);

        try {
            String response = productService.createProduct(dto, token);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Something went wrong");
        }
    }

    // ✅ Update product
    @PutMapping("/updateProduct")
    public ResponseEntity<Object> updateProduct(@RequestBody UpdateProductDTO dto,
                                                @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);

        try {
            String response = productService.updateProduct(dto, token);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Something went wrong");
        }
    }

    // ✅ Delete product
    @DeleteMapping("/deleteProduct")
    public ResponseEntity<Object> deleteProduct(@RequestBody DeleteProductDTO dto,
                                                @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);

        try {
            String response = productService.deleteProduct(dto, token);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Something went wrong");
        }
    }


    @GetMapping("/getProduct/{productId}")
    public ResponseEntity<Object> getProduct(@PathVariable Long productId,
                                             @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);

        try {
            ProductResponseDTO product = productService.getProduct(productId, token);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Something went wrong");
        }
    }

}
