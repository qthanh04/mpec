package com.tavi.tavi_mrs.service.phieu_nhap_hang;

import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PhieuNhapHangForm;
import com.tavi.tavi_mrs.entities.json.PhieuTraHangNhapForm;
import com.tavi.tavi_mrs.entities.phieu_nhap_hang.PhieuNhapHangChiTiet;
import com.tavi.tavi_mrs.entities.phieu_tra_hang_nhap.PhieuTraHangNhapChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PhieuNhapHangChiTietService {

    Optional<PhieuNhapHangChiTiet> findById(int id);

    Page<PhieuNhapHangChiTiet> fingAll(Pageable pageable);

    Optional<PhieuNhapHangChiTiet> save(PhieuNhapHangChiTiet phieuNhapHangChiTiet);

    List<PhieuNhapHangChiTiet> findListByPhieuNhapId(int phieuNhapId);

    Page<PhieuNhapHangChiTiet> findByChiNhanhAndText(int chiNhanhId, String text, Pageable pageable);

    Optional<PhieuNhapHangChiTiet> findGiaNhapGanNhat(int chiNhanhId, int hangHoaId);

    Page<PhieuNhapHangChiTiet> findByPhieuNhap(int phieuNhapId, Pageable pageable);

    ResponseEntity<JsonResult> saveByForm(PhieuNhapHangForm phieuNhapHangForm);

    ResponseEntity<JsonResult> updateByForm(PhieuNhapHangForm phieuNhapHangForm);
}
