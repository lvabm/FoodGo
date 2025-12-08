package com.foodgo.backend.module.outlet.dto;

import java.io.Serializable;

public record OutletTypeResponse(Integer id, String name, String description, Integer outletsCount)
    implements Serializable {}
