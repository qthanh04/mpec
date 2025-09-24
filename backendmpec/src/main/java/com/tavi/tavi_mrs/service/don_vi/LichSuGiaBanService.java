package com.tavi.tavi_mrs.service.don_vi;

import com.tavi.tavi_mrs.entities.don_vi.LichSuGiaBan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Optional;

public interface LichSuGiaBanService {

    Page<LichSuGiaBan> findAll(Pageable pageable);

    Optional<LichSuGiaBan> findById(int id);

    Page<LichSuGiaBan> findByDonViHangHoaId(int donViHangHoaId, Pageable pageable);

    Optional<LichSuGiaBan> findByDonViHangHoaIdAndThoiGian(int donViHangHoaId, Date thoiGian);

    Optional<LichSuGiaBan> save(LichSuGiaBan lichSuGiaBan);

    Optional<LichSuGiaBan> findByDonViHangHoaAndThoiGianHienTai(int donViHangHoaId);

    Page<LichSuGiaBan> search(String text, Pageable pageable);

    Optional<LichSuGiaBan> findDistinctGiaBan(int hangHoaId);
}
