package com.foodgo.backend.module.auth.dto;

public record RegisterRequest(
    String fullName, String email, String plainTextPassword, String passwordConfirmation) {}
