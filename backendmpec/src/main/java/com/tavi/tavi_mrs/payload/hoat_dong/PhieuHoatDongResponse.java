package com.tavi.tavi_mrs.payload.hoat_dong;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhieuHoatDongResponse {

    int nguoiDungId;

    String tenNguoiDung;

    int phieuId;

    String maPhieu;

    Double giaTri;

    Date thoiGian;

    String hoatDong;
}
