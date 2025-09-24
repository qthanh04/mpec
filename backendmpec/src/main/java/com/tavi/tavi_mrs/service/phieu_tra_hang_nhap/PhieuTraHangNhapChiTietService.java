package com.tavi.tavi_mrs.service.phieu_tra_hang_nhap;

import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PhieuTraHangNhapForm;
import com.tavi.tavi_mrs.entities.phieu_tra_hang_nhap.PhieuTraHangNhapChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PhieuTraHangNhapChiTietService {

    Page<PhieuTraHangNhapChiTiet> findAll(Pageable pageable);

    Page<PhieuTraHangNhapChiTiet> findAllByPhieuTraHangNhapId(int phieuTraHangNhapId, Pageable pageable);

    List<PhieuTraHangNhapChiTiet> findListByPhieuTraHangNhapId(int id);

    Optional<PhieuTraHangNhapChiTiet> findById(int id);

    Optional<PhieuTraHangNhapChiTiet> save(PhieuTraHangNhapChiTiet phieuTraHangNhapChiTiet);

    ResponseEntity<JsonResult> saveByForm(PhieuTraHangNhapForm phieuTraHangNhapForm);

    Page<PhieuTraHangNhapChiTiet> findByChiNhanhAndText(int chiNhanhId, String text, Pageable pageable);
}
