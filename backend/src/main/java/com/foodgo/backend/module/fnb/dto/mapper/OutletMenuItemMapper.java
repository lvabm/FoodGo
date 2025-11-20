package com.foodgo.backend.module.fnb.dto.mapper;

import com.foodgo.backend.module.fnb.dto.request.OutletMenuItemCreateRequest;
import com.foodgo.backend.module.fnb.dto.request.OutletMenuItemUpdateRequest;
import com.foodgo.backend.module.fnb.dto.response.OutletMenuItemResponse;
import com.foodgo.backend.module.fnb.entity.MenuItem;
import com.foodgo.backend.module.fnb.entity.OutletMenuItem;
import com.foodgo.backend.module.outlet.entity.Outlet;
import org.springframework.stereotype.Component;

@Component
public class OutletMenuItemMapper {
  public OutletMenuItem toEntity(OutletMenuItemCreateRequest dto, MenuItem menuItem, Outlet outlet) {
    if (dto == null) return null;
    OutletMenuItem entity = new OutletMenuItem();
    entity.setName(dto.getName());
    entity.setDescription(dto.getDescription());
    entity.setPrice(dto.getPrice());
    entity.setImageUrl(dto.getImageUrl());
    entity.setIsAvailable(dto.getIsAvailable());
    entity.setMenuItem(menuItem);
    entity.setOutlet(outlet);
    return entity;
  }

  public void updateEntityFromDto(OutletMenuItem entity, OutletMenuItemUpdateRequest dto) {
    if (dto == null) return;
    entity.setName(dto.getName());
    entity.setDescription(dto.getDescription());
    entity.setPrice(dto.getPrice());
    entity.setImageUrl(dto.getImageUrl());
    entity.setIsAvailable(dto.getIsAvailable());
  }

  public OutletMenuItemResponse toResponse(OutletMenuItem entity) {
    if (entity == null) return null;
    OutletMenuItemResponse res = new OutletMenuItemResponse();
    res.setId(entity.getId());
    res.setName(entity.getName());
    res.setDescription(entity.getDescription());
    res.setPrice(entity.getPrice());
    res.setImageUrl(entity.getImageUrl());
    res.setIsAvailable(entity.getIsAvailable());
    if (entity.getOutlet() != null) {
      res.setOutletId(entity.getOutlet().getId());
    }
    if (entity.getMenuItem() != null) {
      res.setMenuItemId(entity.getMenuItem().getId());
    }
    return res;
  }
}
