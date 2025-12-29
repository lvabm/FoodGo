package com.foodgo.backend.module.outlet.dto.mapper;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.module.outlet.dto.response.OutletTypeResponse;
import com.foodgo.backend.module.outlet.entity.OutletType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE // 🔑 Cấu hình quan trọng nhất!
    )
public interface OutletTypeMapper
    extends BaseMapper<OutletType, Object, Object, OutletTypeResponse> {}
