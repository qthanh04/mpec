package com.tavi.tavi_mrs.repository.phieu_tra_khach;

import com.tavi.tavi_mrs.entities.phieu_tra_khach.PhieuTraKhachChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhieuTraKhachChiTietRepo extends JpaRepository<PhieuTraKhachChiTiet, Integer> {

    @Query(value = "from PhieuTraKhachChiTiet pk where pk.xoa=false and " +
            "pk.id in " +
            "(select max(pt.id) from PhieuTraKhachChiTiet pt group by pt.phieuTraKhach) " +
            "order by pk.phieuTraKhach.thoiGian desc ")
    Page<PhieuTraKhachChiTiet> findAllToPage(Pageable pageable);

    @Query(value = "from PhieuTraKhachChiTiet ptk " +
            "where ptk.xoa = false and ptk.phieuTraKhach.id = ?1 order by ptk.phieuTraKhach.thoiGian desc")
    Page<PhieuTraKhachChiTiet> findAllByPhieuTraKhachId(int id, Pageable pageable);

    @Query(value = "from PhieuTraKhachChiTiet ptk " +
            "where ptk.xoa = false and ptk.phieuTraKhach.id = ?1 order by ptk.phieuTraKhach.thoiGian desc")
    List<PhieuTraKhachChiTiet> findListByPhieuTraKhachId(int id);

    @Query(value = "from PhieuTraKhachChiTiet ptk " +
            "where ptk.xoa=false and ptk.id = ?1")
    Optional<PhieuTraKhachChiTiet> findById(int id);

    @Query(value = "from PhieuTraKhachChiTiet p where " +
            "p.id in (select max(pt.id) from PhieuTraKhachChiTiet pt group by pt.phieuTraKhach)" +
            "and p.xoa = false " +
            "and (0 = ?1 or p.hoaDonChiTiet.hoaDon.chiNhanh.id = ?1) " +
            "and ( " +
            "(p.phieuTraKhach.ma is not null and upper(p.phieuTraKhach.ma) like concat('%', upper(?2) ,'%'))" +
            "or (p.phieuTraKhach.nguoiDung.hoVaTen is not null and upper(p.phieuTraKhach.nguoiDung.hoVaTen) like concat('%', upper(?2) ,'%') )" +
            "or (p.hoaDonChiTiet.hoaDon.khachHang.tenKhachHang is not null and upper(p.hoaDonChiTiet.hoaDon.khachHang.tenKhachHang) like concat('%', upper(?2) ,'%') )" +
            ") order by p.phieuTraKhach.thoiGian desc")
    Page<PhieuTraKhachChiTiet> findByChiNhanhAndText(int chiNhanhId, String text, Pageable pageable);

}
