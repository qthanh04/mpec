package com.tavi.tavi_mrs.entities.khach_hang;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KhachHangDto implements Serializable {

    private Integer id;

    private String ma;

    private String ten;

    private Double tongTienMua;

    private Double conNo;

    public KhachHangDto(Integer id, Object ma, Object ten, Double tongTienMua, Double conNo) {
        this.id = id;
        this.ma = String.valueOf(ma);
        this.ten = String.valueOf(ten);
        this.tongTienMua = tongTienMua;
        this.conNo = conNo;
    }
}
