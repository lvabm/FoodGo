package com.foodgo.backend.module.search.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/search")
public class SearchController {
    @Operation(summary = "Tìm kiếm")
    @GetMapping()
    private void searchOutlets(){

    }
}
