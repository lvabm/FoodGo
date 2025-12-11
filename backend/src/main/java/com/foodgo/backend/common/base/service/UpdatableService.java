package com.foodgo.backend.common.base.service;

import java.io.Serializable;

public interface UpdatableService<Id extends Serializable, UpdateRequest, Response> {
  Response update(Id id, UpdateRequest updateRequest);
}
