package com.tavi.tavi_mrs.repository.phieu_nhap_hang;

import com.tavi.tavi_mrs.entities.khach_hang.KhachHangDto;
import com.tavi.tavi_mrs.entities.nha_cung_cap.NhaCungCapDto;
import com.tavi.tavi_mrs.entities.phieu_nhap_hang.PhieuNhapHang;
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
public interface PhieuNhapHangRepo extends JpaRepository<PhieuNhapHang, Integer> {

    @Query("select p from PhieuNhapHang p where p.xoa = false and p.id = ?1 ")
    Optional<PhieuNhapHang> findById(int id);

    @Query("select p from PhieuNhapHang p where p.xoa = false order by p.thoiGian desc ")
    Page<PhieuNhapHang> findAll(Pageable pageable);

    @Query(value = "from PhieuNhapHang p where p.xoa = false and p.maPhieu = ?1 ")
    Optional<PhieuNhapHang> findByMaPhieu(String ma);

    @Query(value = "from PhieuNhapHang p where p.xoa = false and (p.tienPhaiTra > p.tienDaTra) and p.nhaCungCap.id = ?1 order by p.thoiGian desc")
    Page<PhieuNhapHang> findByIdNhaCungCap(int id, boolean xoa, Pageable pageable);

    @Query(value = "from PhieuNhapHang p" +
            " where p.xoa = false " +
            "and (?1 is null or p.maPhieu like concat('%', ?1, '%')) " +
            "and (?2 is null or p.thoiGian >= ?2 ) and (?3 is null or p.thoiGian <= ?3) " +
            "and (0 = ?4 or p.nhaCungCap.id = ?4 )  " +
            "and (?5 = -1 or p.trangThai = ?5 ) " +
            "order by p.thoiGian desc ")
    Page<PhieuNhapHang> findByNhaCungCapAndThoiGianAndMaPhieu(String maPhieu, Date ngayDau, Date ngayCuoi, int nhaCungCapId,int trangThai, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update PhieuNhapHang p set p.tienDaTra=?1 , p.conNo = ?3 where p.id = ?2 ")
    int  updateTienTraNhaCungCap(Double tienTraNhaCungCap, int phieuNhapHangId, Double conNo);

    @Transactional
    @Modifying
    @Query("update PhieuNhapHang ph set ph.trangThai = ?2 where ph.id = ?1 ")
    int setTrangThai(int id, int trangThai);

    @Query(value = "update PhieuNhapHang p set p.xoa=true where p.id = ?1")
    int deleted(int id);

    @Query(nativeQuery = true)
    Page<NhaCungCapDto> topNhaCungCapTheoThang(Date ngayDau, Date ngayCuoi, Pageable pageable);

    @Query(nativeQuery = true)
    Page<NhaCungCapDto> searchTopNhaCungCapTheoThang( Date ngayDau, Date ngayCuoi, String text, Pageable pageable);
}