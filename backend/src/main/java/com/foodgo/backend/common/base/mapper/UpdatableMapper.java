package com.foodgo.backend.common.base.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface UpdatableMapper<Entity, UpdateRequest> {
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(UpdateRequest updateRequest, @MappingTarget Entity entity);
}
