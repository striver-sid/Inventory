package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="Products")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotBlank(message = "product name is required")
    @Size(min = 3, max = 50, message = "product name must be atleast 3 characters and atmost 50 characters")
    @Column(unique = true, nullable = false)
    private String productName;

    @Column
    @Size(max = 500)
    private String description;

    @Positive
    private Double price;

    @Builder.Default
    private Boolean active = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "variety",cascade=CascadeType.ALL)
    @JsonIgnore
    private List<ProductVarietyModel> productVariety;

    @ManyToOne(fetch=FetchType.LAZY,optional=false )
    @JoinColumn(name="tenantId",nullable=false)
    private TenantModel tenant;


}
