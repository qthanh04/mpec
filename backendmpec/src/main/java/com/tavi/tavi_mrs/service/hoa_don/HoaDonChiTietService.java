package com.tavi.tavi_mrs.service.hoa_don;

import com.tavi.tavi_mrs.entities.hoa_don.HoaDonChiTiet;
import com.tavi.tavi_mrs.entities.json.HoaDonChiTietForm;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.Optional;

public interface HoaDonChiTietService {

    List<HoaDonChiTiet> findAll();

    List<HoaDonChiTiet> findListByHoaDonId(int hoaDonId);

    Page<HoaDonChiTiet> findAllByIdHoaDon(int idHoaDon, Pageable pageable);

    Optional<HoaDonChiTiet> findById(int id);

    boolean save(HoaDonChiTiet hoaDonChiTiet);

    ResponseEntity<JsonResult> saveByForm(HoaDonChiTietForm form);

    ResponseEntity<JsonResult> updateByForm(HoaDonChiTietForm form);
}
