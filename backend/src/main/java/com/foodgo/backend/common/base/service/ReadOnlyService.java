package com.foodgo.backend.common.base.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReadOnlyService<Response, Id, Filter> {

  Response getDetail(Id id);

  List<Response> getAll();

  Page<Response> getPage(Filter filter, Pageable pageable);
}
