package com.example.demo.Controller;

import com.example.demo.Components.JwtUtil;
import com.example.demo.DTO.AccessRefreshDTO;
import com.example.demo.DTO.LoginResponseDTO;
import com.example.demo.DTO.LoginUserDTO;
import com.example.demo.DTO.UserRegisterDTO;
import com.example.demo.Model.UserModel;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.persistence.Access;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/inventory")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid UserRegisterDTO dto){
        String dbResponse=userService.registerUser(dto);

        if(dbResponse.equals("Tenant not found")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dbResponse);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(dbResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody @Valid LoginUserDTO dto){
        try {
            LoginResponseDTO tokenResponse = userService.loginUser(dto);
            return ResponseEntity.ok(tokenResponse);
        }catch(RuntimeException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    @PostMapping("/refresh/auth")
    public ResponseEntity<?> refreshAccessToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Refresh token missing or invalid. Please login again.");
        }

        String refreshToken = authHeader.substring(7); // Remove "Bearer "

        try {
            //  Validate refresh token
            if (!JwtUtil.isTokenValid(refreshToken)) {
                return ResponseEntity.status(401).body("Invalid or expired refresh token. Please login again.");
            }

            //  Extract userId from subject
            Claims claims = JwtUtil.extractAllClaims(refreshToken);
            String subject = claims.getSubject(); // Stored userId as subject
            Long userId = Long.parseLong(subject);

            //  Fetch user using userId
            UserModel user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            //  Set claims for new access token
            Map<String, Object> accessClaims = new HashMap<>();
            accessClaims.put("userId", user.getUserId());
            accessClaims.put("email", user.getEmail());
            accessClaims.put("role", user.getRole());

            //  Generate new access token (15 min validity for example)
            String newAccessToken = JwtUtil.generateAccessToken(accessClaims, String.valueOf(userId));

            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while refreshing token. Please login again.");
        }
    }

}
