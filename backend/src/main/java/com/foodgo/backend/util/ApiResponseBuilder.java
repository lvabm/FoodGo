package com.foodgo.backend.util;

import com.foodgo.backend.common.base.BaseResponse;
import com.foodgo.backend.common.dto.PageResponse;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
public final class ApiResponseBuilder {

  public static <T> BaseResponse<T> success(String message, T data) {
    return BaseResponse.<T>builder()
        .success(true)
        .message(message)
        .data(data)
        .timestamp(Instant.now())
        .build();
  }

  public static <T> PageResponse<T> success(String message, Page<T> pageData) {
    List<T> content = Collections.emptyList();
    int pageNumber = 0;
    int pageSize = 0;
    long totalElements = 0L;
    int totalPages = 0;

    if (Objects.nonNull(pageData)) {
      content = pageData.getContent() == null ? Collections.emptyList() : pageData.getContent();
      pageNumber = pageData.getNumber();
      pageSize = pageData.getSize();
      totalElements = pageData.getTotalElements();
      totalPages = pageData.getTotalPages();
    }

    return PageResponse.<T>builder()
        .success(true)
        .message(message)
        .data(content)
        .timestamp(Instant.now())
        .pageNumber(pageNumber)
        .pageSize(pageSize)
        .totalElements(totalElements)
        .totalPages(totalPages)
        .build();
  }
}
