package com.example.demo.Repository;

import com.example.demo.Model.ProductVarietyModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVarietyRepository extends JpaRepository<ProductVarietyModel,Long> {
}
