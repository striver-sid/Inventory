package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Tenants")
@Data//Generate getters,setters,tostring,equals and hashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenantModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long tenantId;

    @NotBlank(message="tenant name is required")
    @Size(min=2,max=50,message="tenant name length must be between 2 to 50 characters")
    @Column(unique=true,nullable=false)
    private String tenantName;

    @Email(message="Invalid email")
    @NotBlank(message="Email is required")
    @Size(max=100,message="Email characters must be atmost 100 charatcers")
    @Column(unique=true,nullable=false)
    private String email;

    @NotBlank(message="Phone Number is required")
    @Pattern(regexp="^[0-9]{10,15}$",message="Phone number must be 10 to 15")
    @Column(nullable=false)
    private String phoneNo;



    @Builder.Default
    private Boolean active=true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserModel> users = new ArrayList<>();

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy="tenant",cascade=CascadeType.ALL,orphanRemoval = true)
    private List<ProductModel> products=new ArrayList<>();

}
