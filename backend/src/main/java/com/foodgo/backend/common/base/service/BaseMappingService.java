package com.foodgo.backend.common.base.service;

import java.util.List;

public interface BaseMappingService<
    MainEntity, ExtendEntity, MappingEntity, CreateRequest, Response> {

  // Owner/Admin
  Response addFeature(Object mainEntityId, CreateRequest request);

  void removeFeature(Object mainEntityId, Integer extendEntityId);

  // Public read-only
  List<Response> listFeatures(Object mainEntityId);
}
