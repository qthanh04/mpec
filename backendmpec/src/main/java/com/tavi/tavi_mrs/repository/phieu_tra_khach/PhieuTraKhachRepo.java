package com.tavi.tavi_mrs.repository.phieu_tra_khach;

import com.tavi.tavi_mrs.entities.phieu_tra_khach.PhieuTraKhach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Repository
public interface PhieuTraKhachRepo extends JpaRepository<PhieuTraKhach, Integer> {

    @Query(value = "from PhieuTraKhach  pt " +
            "where pt.xoa = false and pt.trangThai = 1 " +
            "order by pt.thoiGian desc ")
    Page<PhieuTraKhach> findAll(Pageable pageable);

    @Query(value = "from PhieuTraKhach  pt " +
            "where pt.xoa = false " +
            "and pt.id = ?1 " +
            "and pt.trangThai = 1")
    Optional<PhieuTraKhach> findByIdAndXoa(int id, boolean xoa);

    @Query(value = "from PhieuTraKhach  pt " +
            "where pt.xoa = false " +
            "and pt.ma = ?1 ")
    Optional<PhieuTraKhach> findByMa(String ma);

    @Query(value = "from PhieuTraKhach  pt " +
            "where pt.xoa = false " +
            "and pt.nguoiDung.id = ?1 " +
            "order by pt.thoiGian desc ")
    Page<PhieuTraKhach> findByNguoiDung(int nguoiDungId, Pageable pageable);

    @Query(value = "from PhieuTraKhach  pt " +
            "where pt.xoa=false and " +
            "(?1 is null or pt.thoiGian >= ?1) and " +
            "(?2 is null or pt.thoiGian <= ?2) and " +
            "pt.trangThai = ?3 " +
            "order by pt.thoiGian desc")
    Page<PhieuTraKhach> findByThoiGianAndTrangThai(Date ngayDau, Date ngayCuoi, int trangThai, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "update PhieuTraKhach pt set pt.xoa = true where pt.id = ?1")
    int deleted(Integer id);

    @Transactional
    @Modifying
    @Query("update PhieuTraKhach pt set pt.trangThai = ?2 where pt.id = ?1")
    int setTrangThai(int id, int trangThai);

    @Transactional
    @Modifying
    @Query("update PhieuTraKhach p set p.tienDaTra=?1 , p.conNo = ?3 where p.id = ?2 ")
    int  updateTienTraKhach(Double tienTraKhach, int phieuTraKhachId, Double conNo);

}
