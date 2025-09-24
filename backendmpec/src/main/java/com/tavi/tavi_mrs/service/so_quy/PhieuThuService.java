package com.tavi.tavi_mrs.service.so_quy;

import com.tavi.tavi_mrs.entities.ca_lam_viec.NguoiDungCaLamViec;
import com.tavi.tavi_mrs.entities.hoa_don.HoaDon;
import com.tavi.tavi_mrs.entities.hoa_don.HoaDonChiTiet;
import com.tavi.tavi_mrs.entities.phieu_tra_hang_nhap.PhieuTraHangNhap;
import com.tavi.tavi_mrs.entities.so_quy.PhieuThu;
import com.tavi.tavi_mrs.entities.so_quy.PhieuThuHoaDon;
import com.tavi.tavi_mrs.service.phieu_tra_hang_nhap.PhieuTraHangNhapService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PhieuThuService {

    Optional<PhieuThu> findByIdAndXoa(int id, boolean xoa);

    Page<PhieuThu> findAllToPage(Pageable pageable);

    boolean setTrangThai(int id,int trangThai);

    Page<PhieuThu> findByMaPhieuThuAndThoiGianAndTrangThai(Integer chiNhanhId,String maPhieuThu, String tenLoaiThu, String tenNhanVien, Date ngayDau, Date ngayCuoi, int trangThai, Pageable pageable);

    Optional<PhieuThu> save(PhieuThu phieuThu);

    Page<PhieuThu> findByChiNhanhAndText(int chinhanhId, String text, Pageable pageable);

    Boolean delete(Integer id);

    List<PhieuThu> findListPhieuThu(List<Integer> listPhieuthuId);

    File createPdf(PhieuThu phieuThu, HoaDon hoaDon);

    File createPhieuTraHangNhapPdf(PhieuThu phieuThu, PhieuTraHangNhap phieuTraHangNhap);
}
