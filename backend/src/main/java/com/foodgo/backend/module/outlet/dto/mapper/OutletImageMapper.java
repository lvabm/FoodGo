package com.foodgo.backend.module.outlet.dto.mapper;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.module.outlet.dto.response.OutletImageResponse;
import com.foodgo.backend.module.outlet.entity.OutletImage;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OutletImageMapper
    extends BaseMapper<OutletImage, Object, Object, OutletImageResponse> {}
