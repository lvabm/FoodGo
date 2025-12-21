package com.foodgo.backend.module.booking.dto.mapper;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.module.booking.dto.request.create.BookingCreateRequest;
import com.foodgo.backend.module.booking.dto.request.update.BookingUpdateRequest;
import com.foodgo.backend.module.booking.dto.response.BookingResponse;
import com.foodgo.backend.module.booking.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookingMapper
    extends BaseMapper<Booking, BookingCreateRequest, BookingUpdateRequest, BookingResponse> {

  @Override
  @Mapping(source = "outlet.id", target = "outletId")
  @Mapping(source = "outlet.name", target = "outletName")
  @Mapping(source = "outlet.address", target = "outletAddress")
  @Mapping(source = "user.id", target = "userId")
  @Mapping(source = "user.profile.fullName", target = "customerName")
  @Mapping(source = "user.phoneNumber", target = "customerPhone")
  @Mapping(target = "isReviewable", ignore = true) // Sẽ set logic ở Service
  BookingResponse toResponse(Booking entity);

  @Override
  @Mapping(target = "outlet", ignore = true) // Xử lý thủ công ở Service
  @Mapping(target = "user", ignore = true) // Xử lý thủ công ở Service
  @Mapping(target = "status", constant = "PENDING")
  Booking toEntity(BookingCreateRequest request);
}
