package com.foodgo.backend.common.dto;

import com.foodgo.backend.common.base.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> extends BaseResponse<T> {
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
}
