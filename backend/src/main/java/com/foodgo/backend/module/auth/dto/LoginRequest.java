package com.foodgo.backend.module.auth.dto;

public record LoginRequest(String email, String plainTextPassword) {}
