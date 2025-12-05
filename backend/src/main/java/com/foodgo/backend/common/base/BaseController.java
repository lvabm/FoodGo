package com.foodgo.backend.common.base;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
public abstract class BaseController<Request, Response, ID> {

  protected final BaseService<Request, Response, ID> service;

  @PostMapping
  public Response create(@Valid @RequestBody Request request) {
    return service.create(request);
  }

  @PutMapping("/{id}")
  public Response update(@PathVariable ID id, @Valid @RequestBody Request request) {
    return service.update(id, request);
  }

  @GetMapping("/{id}")
  public Response detail(@PathVariable ID id) {
    return service.getDetail(id);
  }

  @GetMapping
  public List<Response> getAll() {
    return service.getAll();
  }

  @DeleteMapping("/{id}")
  public Response delete(@PathVariable ID id) {
    return service.softDelete(id);
  }
}
