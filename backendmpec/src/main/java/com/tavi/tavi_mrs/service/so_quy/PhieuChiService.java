package com.tavi.tavi_mrs.service.so_quy;

import com.tavi.tavi_mrs.entities.hoa_don.HoaDon;
import com.tavi.tavi_mrs.entities.phieu_nhap_hang.PhieuNhapHang;
import com.tavi.tavi_mrs.entities.phieu_tra_hang_nhap.PhieuTraHangNhap;
import com.tavi.tavi_mrs.entities.so_quy.PhieuChi;
import com.tavi.tavi_mrs.entities.so_quy.PhieuThu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PhieuChiService {

    Optional<PhieuChi> findByIdAndXoa(int id, boolean xoa);

    Page<PhieuChi> findAllToPage(Pageable pageable);

    boolean setTrangThai(int id,int trangThai);

    Page<PhieuChi> findByMaPhieuChiAndThoiGianAndTrangThai(Integer chiNhanhId,String maPhieuChi, String tenLoaiChi,
                                                           String tenNhanVien, Date ngayDau, Date ngayCuoi,
                                                           int trangThai, Pageable pageable);
    Optional<PhieuChi> save(PhieuChi phieuChi);

    Page<PhieuChi> findByChiNhanhAndText(int chinhanhId, String text, Pageable pageable);

    Boolean delete(Integer id);

    List<PhieuChi> findListPhieuChi(List<Integer> listPhieuChiId);

    File createPhieuChiPhieuNhapHangPdf(PhieuChi phieuChi, PhieuNhapHang phieuNhapHang);

    File createPhieuChiPhieuTraKhachPdf(PhieuChi phieuChi, HoaDon hoaDon);

}
