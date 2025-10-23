package com.foodgo.backend.module.location.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationController {

    @Operation(summary = "Hiển thị bản đồ/quốc lộ địa chỉ")
    @GetMapping("/outlets/{outletId}")
    private void getOutletLocation(@PathVariable Integer outletId){

    }
}
