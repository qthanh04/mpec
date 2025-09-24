package com.tavi.tavi_mrs.repository.phieu_tra_hang_nhap;

import com.tavi.tavi_mrs.entities.phieu_nhap_hang.PhieuNhapHang;
import com.tavi.tavi_mrs.entities.phieu_tra_hang_nhap.PhieuTraHangNhap;
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
public interface PhieuTraHangNhapRepo extends JpaRepository<PhieuTraHangNhap, Integer> {

    @Query(value = "from PhieuTraHangNhap  pt " +
            "where pt.xoa = false and pt.trangThai = 1 order by pt.thoiGian desc ")
    Page<PhieuTraHangNhap> findAll(Pageable pageable);

    @Query(value = "from PhieuTraHangNhap pt " +
            "where pt.id = ?1 and " +
            "pt.xoa = ?2 and pt.trangThai = 1")
    Optional<PhieuTraHangNhap> findByIdAndXoa(int id, boolean xoa);

    @Query("from PhieuTraHangNhap p where p.xoa = false and p.nhaCungCap.id = ?1 and p.tongTien > p.tienDaTra order by p.thoiGian desc ")
    Page<PhieuTraHangNhap> findByIdNhaCungCap(int id, Boolean xoa, Pageable pageable);

    @Query(value = "from PhieuTraHangNhap pt " +
            "where pt.xoa = false and pt.maPhieu = ?1 ")
    Optional<PhieuTraHangNhap> findByMaPhieu(String ma);

    @Query(value = "from PhieuTraHangNhap pt " +
            "where pt.xoa = false " +
            "and (?1 is null or pt.nhaCungCap.ten like concat('%', ?1, '%')) " +
            "and (?2 is null or pt.thoiGian >= ?2) and (?3 is null or pt.thoiGian <= ?3) " +
            "and pt.trangThai = ?4 " +
            "order by pt.thoiGian desc ")
    Page<PhieuTraHangNhap> findByNhaCungCapAndThoiGianAndTrangThai(String tenNhaCungCap, Date ngayDau, Date ngayCuoi, int trangThai, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "update PhieuTraHangNhap pt set pt.xoa = true where pt.id = ?1")
    int deleted(Integer id);

    @Transactional
    @Modifying
    @Query("update PhieuTraHangNhap pt set pt.trangThai = ?2 where pt.id = ?1")
    int setTrangThai(int id, int trangThai);

    @Transactional
    @Modifying
    @Query("update PhieuTraHangNhap p set p.tienDaTra=?1 , p.conNo = ?3 where p.id = ?2 ")
    int  updateTienNhaCungCapTra(Double tienNhaCungCapTra, int phieuTraHangNhapId, Double conNo);
}
