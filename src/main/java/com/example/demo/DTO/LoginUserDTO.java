package com.example.demo.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;



@Data
public class LoginUserDTO {

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
}

