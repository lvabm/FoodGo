package com.foodgo.backend.common.base;

import com.foodgo.backend.common.context.SuccessMessageContext;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@RequiredArgsConstructor
public abstract class BaseServiceImpl<E, Request, Response, ID>
    implements BaseService<Request, Response, ID> {

  protected final JpaRepository<E, ID> repository;

  protected abstract BaseMapper<E, Request, Response> mapper();

  protected abstract String getEntityName();

  protected abstract ID getId(E entity);

  @Override
  public Response create(Request request) {
    E entity = mapper().toEntity(request);
    E saved = repository.save(entity);

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.CREATE_SUCCESS, getEntityName(), getId(saved)));

    return mapper().toResponse(saved);
  }

  @Override
  public Response update(ID id, Request request) {
    E entity = findOrThrow(id);
    mapper().updateEntity(entity, request);
    E saved = repository.save(entity);

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.UPDATE_SUCCESS, getEntityName(), id));

    return mapper().toResponse(saved);
  }

  @Override
  public Response getDetail(ID id) {
    E entity = findOrThrow(id);

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.FETCH_DETAIL_SUCCESS, getEntityName(), id));

    return mapper().toResponse(entity);
  }

  @Override
  public List<Response> getAll() {
    List<E> list = repository.findAll();

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.FETCH_SUCCESS, getEntityName()));

    return list.stream().map(mapper()::toResponse).toList();
  }

  @Override
  public Response softDelete(ID id) {
    E entity = findOrThrow(id);

    if (entity instanceof BaseIntegerEntity<?> be) {
      be.setIsDeleted(true);
    } else if (entity instanceof BaseUUIDEntity be) {
      be.setIsDeleted(true);
    }

    repository.save(entity);

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.SOFT_DELETE_SUCCESS, getEntityName(), id));

    return mapper().toResponse(entity);
  }

  protected E findOrThrow(ID id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("ID: " + id + " not found"));
  }
}
