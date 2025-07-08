package com.example.demo.Repository;

import com.example.demo.Model.TenantModel;
import com.example.demo.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel,Long> {
    public Optional<UserModel> findByEmail(String email);
}
