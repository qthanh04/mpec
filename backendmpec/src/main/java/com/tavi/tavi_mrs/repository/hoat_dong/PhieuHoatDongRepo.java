package com.tavi.tavi_mrs.repository.hoat_dong;

import com.tavi.tavi_mrs.entities.hoat_dong.PhieuHoatDong;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PhieuHoatDongRepo extends JpaRepository<PhieuHoatDong, Integer> {

    @Query(value = " select p from PhieuHoatDong p order by p.thoiGian desc ")
    Page<PhieuHoatDong> findAll(Pageable pageable);

    @Query(value = "select p from PhieuHoatDong p " +
            "left join p.hoaDon hd left join p.phieuThu pt " +
            "left join p.phieuNhapHang pnh left join p.phieuTraHangNhap pthn " +
            "left join p.phieuChi pc left join p.phieuTraKhach ptk " +
            "where (?2 is null or p.hoatDong.ten like concat('%',?2,'%') ) " +
            "and (?3 is null or p.thoiGian >= ?3) and (?4 is null or p.thoiGian <= ?4) " +
            "and (" +
            "hd.ma like concat('%',?1,'%') " +
            "or pt.maPhieu like concat('%',?1,'%') " +
            "or pnh.maPhieu like concat('%',?1,'%') " +
            "or pthn.maPhieu like concat('%',?1,'%') " +
            "or pc.maPhieu like concat('%',?1,'%') " +
            "or ptk.ma like concat('%',?1,'%')" +
            ") " +
            "order by p.thoiGian desc ")
    Page<PhieuHoatDong> findByMaPhieuAndTenHoatDongAndThoiGian(String maPhieu, String tenHoatDong, Date ngayDau, Date ngayCuoi, Pageable pageable);

//    @Query(value = "select p from PhieuHoatDong p " +
//            "where (?2 is null or p.hoatDong.ten like concat('%',?2,'%') ) " +
//            "and (?3 is null or p.thoiGian >= ?3) and (?4 is null or p.thoiGian <= ?4) " +
//            "and (p.hoaDon.ma is null or p.hoaDon.ma like concat('%',?1,'%') " +
//            "or p.phieuThu.maPhieu is null or p.phieuThu.maPhieu like concat('%',?1,'%') " +
//            "or p.phieuNhapHang.maPhieu is null or p.phieuNhapHang.maPhieu like concat('%',?1,'%') " +
//            "or p.phieuTraHangNhap.maPhieu is null or p.phieuTraHangNhap.maPhieu like concat('%',?1,'%') " +
//            "or p.phieuChi.maPhieu is null or p.phieuChi.maPhieu like concat('%',?1,'%') " +
//            "or p.phieuTraKhach.ma is null or p.phieuTraKhach.ma like concat('%',?1,'%')) " +
//            "order by p.thoiGian desc ")
//    Page<PhieuHoatDong> findByMaPhieuAndTenHoatDongAndThoiGian(String maPhieu, String tenHoatDong, Date ngayDau, Date ngayCuoi, Pageable pageable);
}
