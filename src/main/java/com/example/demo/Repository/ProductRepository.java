package com.example.demo.Repository;

import com.example.demo.Model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductModel,Long> {


}
