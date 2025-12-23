package com.foodgo.backend.module.location.dto.mapper;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.module.location.dto.request.create.CountryCreateRequest;
import com.foodgo.backend.module.location.dto.request.update.CountryUpdateRequest;
import com.foodgo.backend.module.location.dto.response.CountryResponse;
import com.foodgo.backend.module.location.entity.Country;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CountryMapper
    extends BaseMapper<Country, CountryCreateRequest, CountryUpdateRequest, CountryResponse> {}
