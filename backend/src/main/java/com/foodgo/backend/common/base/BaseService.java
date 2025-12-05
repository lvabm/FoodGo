package com.foodgo.backend.common.base;

import java.util.List;

public interface BaseService<Request, Response, ID> {

  Response create(Request request);

  Response update(ID id, Request request);

  Response getDetail(ID id);

  List<Response> getAll();

  Response softDelete(ID id);
}
