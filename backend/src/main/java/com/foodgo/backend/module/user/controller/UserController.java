package com.foodgo.backend.module.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Operation(summary = "Đổi Mật khẩu")
    @PatchMapping("/me/password")
    private void changePassword(){

    }

    @Operation(summary = "Cập nhật Hồ sơ cá nhân")
    @PutMapping("/me")
    private void updateProfile(){

    }

    @Operation(summary = "Tự thay đổi Trạng thái Tài khoản")
    @PatchMapping("/me/status")
    private void changeAccountStatus(){

    }

    @Operation(summary = "Xem Hồ sơ cá nhân")
    @GetMapping("/me")
    private void getMyProfile(){

    }

    @Operation(summary = "Xem Hồ sơ Người dùng khác")
    @GetMapping("/{userId}")
    private void getUserProfile(@PathVariable Integer userId){

    }






}
