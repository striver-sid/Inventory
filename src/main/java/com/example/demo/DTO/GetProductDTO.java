package com.example.demo.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetProductDTO {
    @NotNull(message="Product id required")
    private Long productId;
}
