package com.foodgo.backend.module.location_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import com.foodgo.backend.module.outlet_d.entity.Outlet;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "district")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class District extends BaseEntity<Integer> {
    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    //1. QUAN HỆ ONE - TO - MANY: District <--> Outlet
    // Outlet sở hữu quan hệ (fk_district_id_outlet)
    @OneToMany(mappedBy = "district", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Outlet> outlets;

    //2. QUAN HỆ MANY - TO - ONE: Province <--> District
    // Province sở hữu quan hệ (fk_province_id_district)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "province_id", nullable = false)
    private Province province;


}
