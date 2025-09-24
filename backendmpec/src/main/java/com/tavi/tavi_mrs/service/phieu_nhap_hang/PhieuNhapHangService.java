package com.tavi.tavi_mrs.service.phieu_nhap_hang;

import com.tavi.tavi_mrs.entities.khach_hang.KhachHangDto;
import com.tavi.tavi_mrs.entities.nha_cung_cap.NhaCungCapDto;
import com.tavi.tavi_mrs.entities.phieu_nhap_hang.PhieuNhapHang;
import com.tavi.tavi_mrs.entities.phieu_nhap_hang.PhieuNhapHangChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PhieuNhapHangService {

    Optional<PhieuNhapHang> findById(int id);

    Page<PhieuNhapHang> findAll(Pageable pageable);

    Optional<PhieuNhapHang> findByMaPhieu(String ma);

    Optional<PhieuNhapHang> save(PhieuNhapHang phieuNhapHang);

    Page<PhieuNhapHang> findByIdNhaCungCap(int id, boolean xoa, Pageable pageable);

    Page<PhieuNhapHang> findByNhaCungCapAndThoiGianAndMaPhieu(String maPhieu, Date ngayDau, Date ngayCuoi, int nhaCungCapId,int trangThai, Pageable pageable);

//    Page<PhieuNhapHang> findByChiNhanhAndText(int chiNhanhId, String text, Pageable pageable);

    boolean updateTienTraNhaCungCap(Double tienTraNhaCungCap, int phieuNhapHangId,Double conNo);

    File createPdf(PhieuNhapHang phieuNhapHang, List<PhieuNhapHangChiTiet> phieuNhapHangChiTietList);

    List<PhieuNhapHang> findListHoaDon(List<Integer> listHoaDonId);

    Boolean setTrangThai(int id, Integer trangThai);

    Boolean deleted(int id);

    Page<NhaCungCapDto> topNhaCungCapTheoThang(Date ngayDau, Date ngayCuoi, Pageable pageable);

    Page<NhaCungCapDto> searchTopNhaCungCapTheoThang(Date ngayDau, Date ngayCuoi,String text, Pageable pageable);
}
