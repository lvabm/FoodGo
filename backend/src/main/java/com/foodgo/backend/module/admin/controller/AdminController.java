package com.foodgo.backend.module.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/users/")
public class AdminController {

    // đang bị thiếu chức năng thêm mới người dùng

    @Operation(summary = "Cập nhật Thông tin Hồ sơ Người dùng")
    @PutMapping("{userId}/profile")
    private void updateUserProfile(@PathVariable Integer userId){

    }

    @Operation(summary = "Thay đổi Trạng thái Tài khoản Người dùng")
    @PutMapping("{userId}/status")
    private void updateUserStatus(@PathVariable Integer userId){

    }

    @Operation(summary = "Thay đổi Vai trò của Người dùng")
    @PutMapping("{userId}/roles")
    private void updateUserRoles(@PathVariable Integer userId){

    }

    @Operation(summary = "Xem Chi tiết Hồ sơ Người dùng")
    @GetMapping("{userId}")
    private void getUserById(@PathVariable Integer userId){

    }

    @Operation(summary = "Xóa mềm Tài khoản Người dùng")
    @DeleteMapping("{userId}")
    private void softDeleteUser(@PathVariable Integer userId){

    }

    @Operation(summary = "Xem Danh sách Người dùng, Tìm kiếm & Lọc Người dùng")
    @GetMapping()
    private void getAllUser(){

    }


    @Operation(summary = "Tính điểm Ranking")
    @PutMapping("/ranking")
    private void ranking(){

    }

}
