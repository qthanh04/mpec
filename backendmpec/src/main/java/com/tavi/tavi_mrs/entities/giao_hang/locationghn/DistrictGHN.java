package com.tavi.tavi_mrs.entities.giao_hang.locationghn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistrictGHN {

    private int DistrictID;

    private int ProvinceID;

    private String DistrictName;

    private String Code;

    private int Type;

    private int SupportType;
}
