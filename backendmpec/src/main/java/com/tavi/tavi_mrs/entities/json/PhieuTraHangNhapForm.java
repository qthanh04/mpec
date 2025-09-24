package com.tavi.tavi_mrs.entities.json;

import com.tavi.tavi_mrs.entities.hoa_don.HoaDonChiTiet;
import com.tavi.tavi_mrs.entities.phieu_nhap_hang.PhieuNhapHangChiTiet;
import com.tavi.tavi_mrs.entities.phieu_tra_hang_nhap.PhieuTraHangNhapChiTiet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhieuTraHangNhapForm implements Serializable {

    private Integer phieuTraHangNhapId;

    private Integer chiNhanhId;

    private List<Integer> hangHoaIdList;

    private List<PhieuTraHangNhapChiTiet> phieuTraHangNhapChiTietList;
}
