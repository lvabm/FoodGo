package com.foodgo.backend.common.base.mapper;

import java.util.List;

public interface ReadableMapper<Entity, Response> {

  Response toResponse(Entity entity);

  List<Response> toResponseList(List<Entity> entities);
}
