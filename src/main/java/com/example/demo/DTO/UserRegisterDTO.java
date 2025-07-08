package com.example.demo.DTO;

import com.example.demo.Model.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterDTO {
    @NotBlank(message="username is required")
    @Size(min=2,max=50,message="username length must be between 2 to 50 characters")
    private String userName;

    @Email(message="Invalid email")
    @NotBlank(message="email is required")
    @Size(max=100,message="Email characters must be atmost 100 characters")
    private String email;

    @NotBlank(message="Password is required")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least 1 uppercase, 1 lowercase, 1 digit, 1 special character, and be at least 8 characters long"
    )
    @Size(min=8,max=40,message="Password must be atleast 8 characters and atmost 40 characters")
    private String password;

    @NotBlank(message="Password is required")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least 1 uppercase, 1 lowercase, 1 digit, 1 special character, and be at least 8 characters long"
    )
    @Size(min=8,max=40,message="Password must be atleast 8 characters and atmost 40 characters")
    private String confirmPassword;

    @NotBlank(message="phone number is required")
    @Pattern(regexp="^[0-9]{10,15}$",message="Phone number must be 10 to 15  digits")
    private String phoneNo;


    @Enumerated(EnumType.STRING)
    private UserRole role;


    @Size(min=2,max=50,message="tenant length must be between 2 to 50 characters")
    private String tenantName;

}
