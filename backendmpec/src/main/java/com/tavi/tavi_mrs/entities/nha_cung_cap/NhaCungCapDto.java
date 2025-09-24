package com.tavi.tavi_mrs.entities.nha_cung_cap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NhaCungCapDto {
    private Integer id;

    private String ten;

    private Double tongTien;

    private Double conNo;

    public NhaCungCapDto(Integer id, Object ten, Double tongTien, Double conNo) {
        this.id = id;
        this.ten = String.valueOf(ten);
        this.tongTien = tongTien;
        this.conNo = conNo;
    }
}
