package com.foodgo.backend.module.fnb.dto.mapper;

import com.foodgo.backend.module.fnb.dto.request.OutletHasFnbCreateRequest;
import com.foodgo.backend.module.fnb.dto.request.OutletHasFnbUpdateRequest;
import com.foodgo.backend.module.fnb.dto.response.OutletHasFnbResponse;
import com.foodgo.backend.module.fnb.entity.Fnb;
import com.foodgo.backend.module.fnb.entity.OutletHasFnb;
import com.foodgo.backend.module.outlet.entity.Outlet;
import org.springframework.stereotype.Component;

@Component
public class OutletHasFnbMapper {

  public OutletHasFnb toEntity(OutletHasFnbCreateRequest dto, Fnb fnb, Outlet outlet) {
    if (dto == null) return null;

    OutletHasFnb entity = new OutletHasFnb();
    entity.setPrice(dto.getPrice());
    entity.setFnb(fnb);
    entity.setOutlet(outlet);

    return entity;
  }

  // Convert UpdateRequest → Update Entity
  public void updateEntityFromDto(OutletHasFnb entity, OutletHasFnbUpdateRequest dto) {
    if (entity == null || dto == null) return;

    entity.setPrice(dto.getPrice());
  }

  // Convert Entity → Response
  public OutletHasFnbResponse toResponse(OutletHasFnb entity) {
    if (entity == null) return null;

    OutletHasFnbResponse res = new OutletHasFnbResponse();
    res.setId(entity.getId());
    res.setPrice(entity.getPrice());

    if (entity.getFnb() != null) {
      res.setFnbId(entity.getFnb().getId());
    }

    if (entity.getOutlet() != null) {
      res.setOutletId(entity.getOutlet().getId());
    }

    return res;
  }
}
