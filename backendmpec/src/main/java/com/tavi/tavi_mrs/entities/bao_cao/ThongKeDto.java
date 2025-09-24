package com.tavi.tavi_mrs.entities.bao_cao;

import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDung;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThongKeDto implements Serializable {

    private Integer id;

    private String ma;

    private String ten;

    private Double tongDoanhThu;

    public ThongKeDto(Integer id, Object ma, Object ten, Double tongDoanhThu) {
        this.id = id;
        this.ma = String.valueOf(ma);
        this.ten = String.valueOf(ten);
        this.tongDoanhThu = tongDoanhThu;
    }
}
