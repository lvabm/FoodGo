package com.foodgo.backend.module.location.dto.mapper;

import com.foodgo.backend.common.base.mapper.ReadableMapper;
import com.foodgo.backend.module.location.dto.response.CountryResponse;
import com.foodgo.backend.module.location.entity.Country;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE // 🔑 Cấu hình quan trọng nhất!
    )
public interface CountryMapper extends ReadableMapper<Country, CountryResponse> {}
