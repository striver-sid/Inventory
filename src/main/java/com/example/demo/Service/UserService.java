package com.example.demo.Service;


import com.example.demo.Components.JwtUtil;
import com.example.demo.DTO.LoginResponseDTO;
import com.example.demo.DTO.LoginUserDTO;
import com.example.demo.DTO.UserRegisterDTO;
import com.example.demo.Model.TenantModel;
import com.example.demo.Model.UserModel;
import com.example.demo.Model.UserRole;
import com.example.demo.Repository.TenantRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TenantRepository tenantRepository;


    public String registerUser(UserRegisterDTO dto){

        TenantModel tenant = null;

        if (dto.getRole() != UserRole.CUSTOMER) {
            Optional<TenantModel> optionalTenant = tenantRepository.findByTenantNameIgnoreCase(dto.getTenantName());

            if (optionalTenant.isEmpty()) {
                return "Tenant not found";
            }
            tenant = optionalTenant.get();
        }

        UserModel user = UserModel.builder()
                .userName(dto.getUserName())
                .email(dto.getEmail())
                .hashedPassword(passwordEncoder.encode(dto.getPassword()))
                .phoneNo(dto.getPhoneNo())
                .role(dto.getRole())
                .tenant(tenant)
                .build();

        // 3. Save user
        userRepository.save(user);
        return "User registered successfully";
    }

    public LoginResponseDTO loginUser(LoginUserDTO dto) {
        Optional<UserModel> userOpt = userRepository.findByEmail(dto.getEmail());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        UserModel user = userOpt.get();
        if (!passwordEncoder.matches(dto.getPassword(), user.getHashedPassword())) {
            throw new RuntimeException("Invalid  password");
        }
        //set the claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());

        //generate tokens
        String accessToken;
        String refreshToken;
        try{
            accessToken = JwtUtil.generateAccessToken(claims, user.getUserId().toString());
            refreshToken = JwtUtil.generateRefreshToken(user.getUserId().toString());
        }catch(Exception ex){
            throw new RuntimeException("Cant generate token");
        }
        //set the response

        return new LoginResponseDTO(accessToken,refreshToken);
    }
}
