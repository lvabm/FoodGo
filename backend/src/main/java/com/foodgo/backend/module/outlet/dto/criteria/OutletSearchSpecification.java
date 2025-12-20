package com.foodgo.backend.module.outlet.dto.criteria;

import com.foodgo.backend.module.outlet.dto.request.filter.OutletFilterRequest;
import com.foodgo.backend.module.outlet.entity.Outlet;
import com.foodgo.backend.module.outlet.entity.OutletFeatureMapping;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public record OutletSearchSpecification(OutletFilterRequest request)
    implements Specification<Outlet> {

  @Override
  public Predicate toPredicate(Root<Outlet> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    List<Predicate> predicates = new ArrayList<>();

    // 1. Lọc theo Tên (tương đối)
    request
        .optionalName()
        .ifPresent(
            name ->
                predicates.add(
                    builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%")));

    // 2. Lọc theo Price Range (Chính xác)
    request
        .optionalPriceRange()
        .ifPresent(priceRange -> predicates.add(builder.equal(root.get("priceRange"), priceRange)));

    // 3. Lọc theo District ID (Many-to-One Join)
    request
        .optionalDistrictId()
        .ifPresent(
            districtId -> {
              Join<Object, Object> districtJoin = root.join("district", JoinType.INNER);
              predicates.add(builder.equal(districtJoin.get("id"), districtId));
            });

    // 4. Lọc theo Outlet Type ID (Many-to-One Join)
    request
        .optionalOutletTypeId()
        .ifPresent(
            typeId -> {
              Join<Object, Object> typeJoin = root.join("type", JoinType.INNER);
              predicates.add(builder.equal(typeJoin.get("id"), typeId));
            });

    // 5. Lọc theo Feature IDs (Many-to-Many Join)
    request
        .optionalFeatureIds()
        .ifPresent(
            featureIds -> {
              // Sử dụng Join qua bảng mapping OutletHasFeature (giả định tên trường là
              // "outletFeatureMappings")
              Join<Outlet, OutletFeatureMapping> featureMappingJoin =
                  root.join("outletFeatureMappings", JoinType.INNER);
              Join<OutletFeatureMapping, Object> featureJoin =
                  featureMappingJoin.join("feature", JoinType.INNER);

              // Lọc theo danh sách IDs
              predicates.add(featureJoin.get("id").in(featureIds));

              // Đảm bảo không bị trùng lặp kết quả khi join Many-to-Many
              query.distinct(true);
            });

    // Thêm điều kiện mặc định: isActive = true và isDeleted = false (từ BaseUUIDEntity)
    predicates.add(builder.equal(root.get("isActive"), true));
    predicates.add(builder.equal(root.get("isDeleted"), false));

    return builder.and(predicates.toArray(new Predicate[0]));
  }
}
