package com.example.demo.Repository;

import com.example.demo.Model.TenantModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantRepository extends JpaRepository<TenantModel,Long> {
    Optional<TenantModel> findByTenantNameIgnoreCase(String tenantName);
}
