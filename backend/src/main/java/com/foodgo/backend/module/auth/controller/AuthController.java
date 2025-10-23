package com.foodgo.backend.module.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Operation(summary = "Đăng nhập")
    @PostMapping("/login")
    private void login(){

    }

    @Operation(summary = "Đăng xuất")
    @PostMapping("/logout")
    private void logout(){

    }

    @Operation(summary = "Đăng ký tài khoản")
    @PostMapping("register")
    private void register(){

    }

    @Operation(summary = "Gửi Yêu cầu Đặt lại Mật khẩu")
    @PostMapping("/forgot-password")
    private void forgotPassword(){

    }

    @Operation(summary = "Đặt lại mật khẩu bằng Link/Mã")
    @PatchMapping("/reset-password")
    private void resetPassword(){

    }

}
