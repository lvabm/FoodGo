package com.foodgo.backend.common.base.service;

import java.io.Serializable;

public interface BaseService<
        Id extends Serializable, CreateRequest, UpdateRequest, FilterRequest, Response>
    extends CreatableService<CreateRequest, Response>,
        UpdatableService<Id, UpdateRequest, Response>,
        ReadableService<Id, FilterRequest, Response>,
        DeletableService<Id, Response> {

  boolean existsById(Id id);
}
