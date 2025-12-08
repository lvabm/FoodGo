package com.foodgo.backend.common.base.service;

import org.mapstruct.MappingTarget;

public interface UpdatableMapper<Entity, UpdateRequest> {
  void updateEntity(UpdateRequest updateRequest, @MappingTarget Entity entity);
}
