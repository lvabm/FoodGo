package com.foodgo.backend.common.base.service;

public interface CreatableService<CreateRequest, Response> {
  Response create(CreateRequest createRequest);
}
