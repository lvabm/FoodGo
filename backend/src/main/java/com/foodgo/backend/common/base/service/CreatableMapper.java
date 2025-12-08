package com.foodgo.backend.common.base.service;

public interface CreatableMapper<Entity, CreateRequest> {

  Entity toEntity(CreateRequest createRequest);
}
