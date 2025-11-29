package com.foodgo.backend.common.dto;

import com.foodgo.backend.common.base.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> extends BaseResponse<List<T>> {
  private int pageNumber;
  private int pageSize;
  private long totalElements;
  private int totalPages;
}
