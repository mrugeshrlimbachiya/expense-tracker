package com.expense_tracker.expense.service;


import com.expense_tracker.expense.config.auth.JwtUtil;
import com.expense_tracker.expense.dtos.AuthResponse;
import com.expense_tracker.expense.dtos.LoginRequest;
import com.expense_tracker.expense.dtos.RegisterRequest;
import com.expense_tracker.expense.entity.User;
import com.expense_tracker.expense.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    public void register(RegisterRequest req) {

        if (userRepository.findByUsername(req.username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(req.username)
                .password(passwordEncoder.encode(req.password))
                .email(req.email)
                .fullName(req.fullName)
                .build();

        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest req) {

        User user = userRepository.findByUsername(req.username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(req.password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getUserId().toString());

        return new AuthResponse(token);
    }
}