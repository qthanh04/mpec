package com.tavi.tavi_mrs.payload.ca_lam_viec;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NguoiDungCaLamViecUpload {

    Integer caLamViecId;

    Integer nguoiDungId;

    String ghiChu;

    String ngayThang;
}
