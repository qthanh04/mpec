package com.tavi.tavi_mrs.service.phieu_tra_hang_nhap;

import com.tavi.tavi_mrs.entities.phieu_tra_hang_nhap.PhieuTraHangNhap;
import com.tavi.tavi_mrs.entities.phieu_tra_hang_nhap.PhieuTraHangNhapChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PhieuTraHangNhapService {

    Page<PhieuTraHangNhap> findAll(Pageable pageable);

    Page<PhieuTraHangNhap> findByNhaCungCapAndThoiGianAndTrangThai(String tenNhaCungCap, Date ngayDau, Date ngayCuoi, int trangThai, Pageable pageable);

    Page<PhieuTraHangNhap> findByChiNhanhAndText(int chiNhanhId, String text);

    Optional<PhieuTraHangNhap> findByIdAndXoa(int id, boolean xoa);

    Page<PhieuTraHangNhap> findByIdNhaCungCap(int id, boolean xoa, Pageable pageable);

    Optional<PhieuTraHangNhap> findByMa(String ma);

    File createPdf(PhieuTraHangNhap phieuTraHangNhap, List<PhieuTraHangNhapChiTiet> phieuTraHangNhapChiTietList);

    Optional<PhieuTraHangNhap> save(PhieuTraHangNhap phieuTraHangNhap);

    Boolean deleted(Integer id);

    Boolean setTrangThai(int id, int trangThai);

    boolean updateTienNhaCungCapTra(Double tienNhaCungCapTra, int phieuTraHangNhapId,Double conNo);

}
