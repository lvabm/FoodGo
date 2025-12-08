package com.foodgo.backend.common.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseController<CreateRequest, UpdateRequest, FilterRequest, Response, ID> {
  Response create(CreateRequest request);

  Response update(ID id, UpdateRequest request);

  Response detail(ID id);

  Page<Response> getPage(FilterRequest filter, Pageable pageable);

  Response delete(ID id);
}
