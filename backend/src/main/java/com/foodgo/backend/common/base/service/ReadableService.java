package com.foodgo.backend.common.base.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public interface ReadableService<Id extends Serializable, FilterRequest, Response> {

  Response getDetail(Id id);

  List<Response> getAll();

  Page<Response> getPage(FilterRequest filterRequest, Pageable pageable);
}
