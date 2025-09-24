package com.tavi.tavi_mrs.payload.organization;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MRSThongTinDoanhNghiep {

    private Integer id;

    private String name;

    private String address;

    private String commune;

    private String district;

    private String province;
}
