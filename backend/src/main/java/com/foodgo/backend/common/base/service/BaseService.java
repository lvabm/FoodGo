package com.foodgo.backend.common.base.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaseService<CreateRequest, UpdateRequest, FilterRequest, Response, Id> {

  Response create(CreateRequest createRequest);

  Response update(Id id, UpdateRequest updateRequest);

  Response getDetail(Id id);

  List<Response> getAll();

  Page<Response> getPage(FilterRequest filterRequest, Pageable pageable);

  Response softDelete(Id id);

  void hardDelete(Id id);

  boolean existsById(Id id);
}
