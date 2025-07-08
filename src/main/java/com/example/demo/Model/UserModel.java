package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message="username is required")
    @Size(min=2,max=50,message="username length  must be between 2 to 50 characters")
    @Column(nullable=false)
    private String userName;

    @Email(message="Invalid email")
    @NotBlank(message="email is required")
    @Size(max=100,message="Email characters must be atmost 100 characters")
    @Column(unique=true,nullable = false)
    private String email;


    @NotBlank(message="Password is required")
    @Size(min=8,message="Password must be atleast 8 characters")
    @Column(nullable=false)
    private String hashedPassword;

    @NotBlank(message="phone number is required")
    @Pattern(regexp="^[0-9]{10,15}$",message="Phone number must be 10 to 15  digits")
    @Column(nullable=false)
    private String phoneNo;

    @NotNull(message="role is required")
    @Enumerated(EnumType.STRING)
    @Column
    private UserRole role;

    @Builder.Default
    private Boolean active=true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    //relationships
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenantId", nullable = true)
    private TenantModel tenant;




}
