package com.tavi.tavi_mrs.service.hoat_dong;

import com.tavi.tavi_mrs.entities.hoat_dong.PhieuHoatDong;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Optional;

public interface PhieuHoatDongService {

    Page<PhieuHoatDong> findAll(Pageable pageable);

    Optional<PhieuHoatDong> findByIdAndXoa(int id, boolean xoa);

    Page<PhieuHoatDong> findByMaPhieuAndTenHoatDongAndThoiGian(String maPhieu, String tenHoatDong, Date ngayDau, Date ngayCuoi, Pageable pageable);

    Optional<PhieuHoatDong> save(PhieuHoatDong phieuHoatDong);

    Boolean deleted(int id);
}
