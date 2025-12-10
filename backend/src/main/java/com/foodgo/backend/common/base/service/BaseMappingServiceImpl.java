package com.foodgo.backend.common.base.service;

import java.util.List;

import com.foodgo.backend.common.context.SuccessMessageContext;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class BaseMappingServiceImpl<
        MainEntity, ExtendEntity, MappingEntity, CreateRequest, Response>
    implements BaseMappingService<
        MainEntity, ExtendEntity, MappingEntity, CreateRequest, Response> {

  // --- Abstract methods to override in child service ---
  protected abstract MainEntity findMainEntityAndEnsurePermission(Object mainEntityId);

  protected abstract ExtendEntity findExtendEntityOrThrow(Integer extendEntityId);

  protected abstract boolean existsMapping(Object mainEntityId, Integer extendEntityId);

  protected abstract MappingEntity createMappingEntity(CreateRequest request);

  protected abstract void setEntities(
      MappingEntity mapping, MainEntity mainEntity, ExtendEntity extendEntity);

  protected abstract MappingEntity saveMapping(MappingEntity mapping);

  protected abstract Response toResponse(MappingEntity mapping);

  protected abstract MappingEntity findMapping(Object mainEntityId, Integer extendEntityId);

  protected abstract void deleteMapping(MappingEntity mapping);

  protected abstract String getEntityName();

  protected abstract Object getId(MappingEntity mapping);

  protected abstract Integer getExtendEntityId(CreateRequest request);

  protected abstract List<MappingEntity> findAllMappingsByMainEntityId(Object mainEntityId);

  // --- Common logic ---
  @Override
  public Response addFeature(Object mainEntityId, CreateRequest request) {
    MainEntity mainEntity = findMainEntityAndEnsurePermission(mainEntityId);
    ExtendEntity extendEntity = findExtendEntityOrThrow(getExtendEntityId(request));

    if (existsMapping(mainEntityId, getExtendEntityId(request))) {
      throw new IllegalArgumentException(
          String.format("%s %s đã có mapping này.", getEntityName(), mainEntityId));
    }

    MappingEntity mapping = createMappingEntity(request);
    setEntities(mapping, mainEntity, extendEntity);

    MappingEntity saved = saveMapping(mapping);

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.CREATE_SUCCESS, getEntityName(), getId(saved)));

    return toResponse(saved);
  }

  @Override
  public void removeFeature(Object mainEntityId, Integer extendEntityId) {
    findMainEntityAndEnsurePermission(mainEntityId);

    MappingEntity mapping = findMapping(mainEntityId, extendEntityId);
    deleteMapping(mapping);

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.HARD_DELETE_SUCCESS, getEntityName(), getId(mapping)));
  }

  @Override
  @Transactional(readOnly = true)
  public List<Response> listFeatures(Object mainEntityId) {
    return findAllMappingsByMainEntityId(mainEntityId).stream().map(this::toResponse).toList();
  }
}
