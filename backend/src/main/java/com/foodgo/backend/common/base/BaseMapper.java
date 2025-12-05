package com.foodgo.backend.common.base;

import org.mapstruct.MappingTarget;

public interface BaseMapper<E, Request, Response> {

  E toEntity(Request request);

  Response toResponse(E entity);

  void updateEntity(@MappingTarget E entity, Request request);
}
