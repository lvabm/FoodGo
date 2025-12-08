package com.foodgo.backend.common.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> extends BaseResponse<List<T>> {
  private int pageNumber;
  private int pageSize;
  private long totalElements;
  private int totalPages;

  public PageResponse(
      int pageNumber,
      int pageSize,
      long totalElements,
      int totalPages,
      String message,
      List<T> data) {
    super(true, message, data, Instant.now());
    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
    this.totalElements = totalElements;
    this.totalPages = totalPages;
  }
}
