package com.foodgo.backend.module.booking.dto;

import com.foodgo.backend.common.constant.BookingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookingStatusUpdate {

    @NotNull(message = "Trạng thái booking không được để trống")
    private BookingStatus status;
}
