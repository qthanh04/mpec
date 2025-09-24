package com.tavi.tavi_mrs.entities.json;

import com.tavi.tavi_mrs.entities.phieu_tra_hang_nhap.PhieuTraHangNhapChiTiet;
import com.tavi.tavi_mrs.entities.phieu_tra_khach.PhieuTraKhachChiTiet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhieuTraKhachForm implements Serializable {
    private Integer phieuTraKhachId;

    private List<Integer> hoaDonChiTietId;

    private List<PhieuTraKhachChiTiet> phieuTraKhachChiTietList;

}
