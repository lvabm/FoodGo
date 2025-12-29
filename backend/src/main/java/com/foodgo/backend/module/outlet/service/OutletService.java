package com.foodgo.backend.module.outlet.service;

import com.foodgo.backend.common.base.service.BaseService;
import com.foodgo.backend.module.outlet.dto.request.filter.OutletFilterRequest;
import com.foodgo.backend.module.outlet.dto.request.create.OutletCreateRequest;
import com.foodgo.backend.module.outlet.dto.request.update.OutletUpdateRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletResponse;

import java.util.List;
import java.util.UUID;

// Kế thừa BaseService cho Full CRUD
public interface OutletService
    extends BaseService<
        UUID, OutletCreateRequest, OutletUpdateRequest, OutletFilterRequest, OutletResponse> {

  List<OutletResponse> getOwnerOutlets();

  /**
   * Tìm outlets gần nhất từ một điểm
   * @param latitude Vĩ độ
   * @param longitude Kinh độ
   * @param radiusKm Bán kính tìm kiếm (kilometers)
   * @param maxResults Số kết quả tối đa
   * @return Danh sách outlets với thông tin khoảng cách
   */
  List<com.foodgo.backend.module.outlet.dto.response.OutletWithDistanceResponse> findNearbyOutlets(
      java.math.BigDecimal latitude,
      java.math.BigDecimal longitude,
      Double radiusKm,
      Integer maxResults);

  /**
   * Lấy gợi ý tìm kiếm
   */
  List<com.foodgo.backend.module.outlet.controller.SearchController.SearchSuggestionResponse> getSearchSuggestions(
      String query, Integer limit);

  /**
   * Lấy kết quả autocomplete
   */
  com.foodgo.backend.module.outlet.controller.SearchController.AutocompleteResponse getAutocompleteResults(
      String query, Integer limit);

  /**
   * Lấy outlets mới nhất (sắp xếp theo ID desc - UUID được tạo theo thời gian)
   */
  List<OutletResponse> getNewestOutlets(Integer limit);

  /**
   * Lấy outlets đang có khuyến mãi (có active advertisements)
   */
  List<OutletResponse> getPromotedOutlets(Integer limit);
}
