package com.foodgo.backend.module.notification.dto.mapper;

import com.foodgo.backend.module.notification.dto.response.NotificationResponse;
import com.foodgo.backend.module.notification.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
  
  @Mapping(source = "user.id", target = "userId")
  @Mapping(source = "createdAt", target = "createdAt")
  NotificationResponse toResponse(Notification notification);
}

