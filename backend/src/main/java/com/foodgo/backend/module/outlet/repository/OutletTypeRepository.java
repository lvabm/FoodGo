package com.foodgo.backend.module.outlet.repository;

import java.util.List;
import java.util.Optional;

import com.foodgo.backend.module.outlet.entity.OutletType;
import com.foodgo.backend.module.outlet.repository.projection.OutletTypeCountProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
;import org.springframework.stereotype.Repository;

@Repository
public interface OutletTypeRepository
    extends JpaRepository<OutletType, Integer>, JpaSpecificationExecutor<OutletType> {

  @Query(
      """
    SELECT ot.id AS id,
           ot.name AS name,
           ot.description AS description,
           COUNT(o.id) AS outletsCount
    FROM OutletType ot
    LEFT JOIN ot.outlets o
    GROUP BY ot.id, ot.name, ot.description
  """)
  List<OutletTypeCountProjection> findAllWithCountProjection();

  @Query(
      """
            SELECT ot.id AS id,
                   ot.name AS name,
                   ot.description AS description,
                   COUNT(o.id) AS outletsCount
            FROM OutletType ot
            LEFT JOIN ot.outlets o
            WHERE ot.id = :id
            GROUP BY ot.id, ot.name, ot.description
          """)
  Optional<OutletTypeCountProjection> findByIdWithCountProjection(@Param("id") Integer id);

  boolean existsByName(String name);
}
