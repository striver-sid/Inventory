package com.example.demo.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="DeviceTokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceTokenModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long deviceId;

    @NotBlank(message="device name required")
    @Size(min=2,max=50,message="device name must be atleast 2 or atmost 50 characters")
    @Column(nullable=false)
    private String deviceName;

    @Column(nullable=false)
    private String refreshToken;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder.Default
    private Boolean revoked=false;

    //refresh token
    @ManyToOne
    @JoinColumn(name="userId",nullable=false)
    private UserModel user;

}
