package com.expense_tracker.expense.controller;

//import com.expense_tracker.expense.config.authService.AuthService;
import com.expense_tracker.expense.dtos.AuthResponse;
import com.expense_tracker.expense.dtos.LoginRequest;
import com.expense_tracker.expense.dtos.RegisterRequest;
import com.expense_tracker.expense.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {


    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest req) {
        authService.register(req);
        return "User Registered";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {
        return authService.login(req);
    }
}
