package com.example.demo.DTO;




import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductDTO {

    @NotBlank(message="product Id required")
    private Long productId;

    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 50, message = "Product name must be at least 3 characters and at most 50 characters")
    private String productName;

    @Size(max = 500, message = "Description must be at most 500 characters")
    private String description;

    @Positive(message = "Price must be a positive number")
    private Double price;

    private Long tenantId;

    private Boolean active=true;
}

