package com.foodgo.backend.module.sharing.dto.mapper;

import com.foodgo.backend.module.sharing.dto.request.SharingListCreateRequest;
import com.foodgo.backend.module.sharing.dto.request.SharingListUpdateRequest;
import com.foodgo.backend.module.sharing.dto.response.SharingListResponse;
import com.foodgo.backend.module.sharing.entity.SharingList;
import com.foodgo.backend.module.sharing.entity.SharingListMember;
import com.foodgo.backend.module.user.entity.UserAccount;
import org.springframework.stereotype.Component;

@Component
public class SharingListMapper {

  // Chuyển từ CreateRequest → Entity
  public SharingListMember toEntity(
      SharingListCreateRequest dto, UserAccount userAccount, SharingList sharingList) {
    SharingListMember entity = new SharingListMember();
    entity.setUserAccount(userAccount);
    entity.setSharingList(sharingList);
    return entity;
  }

  // Cập nhật từ UpdateRequest → Entity
  public void updateEntityFromDto(
      SharingListMember entity,
      SharingListUpdateRequest dto,
      UserAccount userAccount,
      SharingList sharingList) {
    entity.setUserAccount(userAccount);
    entity.setSharingList(sharingList);
  }

  // Chuyển từ Entity → Response
  public SharingListResponse toResponse(SharingListMember entity) {
    SharingListResponse response = new SharingListResponse();
    response.setId(entity.getId());
    response.setUserId(entity.getUserAccount().getId());
    response.setGroupId(entity.getSharingList().getId());
    return response;
  }
}
