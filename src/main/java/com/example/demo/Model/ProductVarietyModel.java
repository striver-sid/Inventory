package com.example.demo.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="ProductVariety")
public class ProductVarietyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long varietyId;

    @NotBlank(message="variety name is required")
    @Column(nullable=false)
    private String varietyName;

    @Positive
    private Double price;

    @NotNull
    @Builder.Default
    private Boolean active=true;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isDefault = false;


    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch=FetchType.LAZY,optional=false)
    @JoinColumn(name="productId",nullable=false)
    private ProductModel product;

    @ManyToOne(fetch=FetchType.LAZY,optional=false )
    @JoinColumn(name="tenantId",nullable=false)
    private TenantModel tenant;
}
