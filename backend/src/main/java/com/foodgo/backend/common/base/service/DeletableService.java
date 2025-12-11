package com.foodgo.backend.common.base.service;

import java.io.Serializable;

public interface DeletableService<Id extends Serializable, Response> {
  Response softDelete(Id id);

  void hardDelete(Id id);
}
