package com.foodgo.backend.common.base.mapper;

public interface CreatableMapper<Entity, CreateRequest> {

  Entity toEntity(CreateRequest createRequest);
}
