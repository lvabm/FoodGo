package com.foodgo.backend.common.base.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Base response wrapper cho tất cả API success responses.
 * 
 * <p>Format chuẩn cho single object responses:
 * <pre>{@code
 * {
 *   "success": true,
 *   "message": "Thông báo thành công",
 *   "data": { ... },
 *   "timestamp": "2024-01-01T00:00:00Z"
 * }
 * }</pre>
 * 
 * @param <T> Type của data object
 * @author FoodGo Team
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
  
  /** 
   * Trạng thái thành công. Luôn là {@code true} cho success responses.
   * @default true
   */
  @Builder.Default
  private boolean success = true;
  
  /** 
   * Thông báo thành công (tiếng Việt).
   * @default "Thao tác thành công."
   */
  private String message;
  
  /** 
   * Dữ liệu trả về (DTO object).
   */
  private T data;
  
  /** 
   * Thời gian response được tạo (ISO 8601 format).
   * @default Current timestamp
   */
  @Builder.Default 
  private Instant timestamp = Instant.now();
}
