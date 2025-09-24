package com.tavi.tavi_mrs.repository.phieu_tra_hang_nhap;

import com.tavi.tavi_mrs.entities.phieu_tra_hang_nhap.PhieuTraHangNhapChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhieuTraHangNhapChiTietRepo extends JpaRepository<PhieuTraHangNhapChiTiet, Integer> {

    @Query(value = "select distinct pt.phieuTraHangNhap from PhieuTraHangNhapChiTiet pt " +
            "where pt.phieuTraHangNhap.xoa = false order by pt.phieuTraHangNhap.thoiGian desc ")
    Page<PhieuTraHangNhapChiTiet> fingALl(Pageable pageable);

    @Query(value = "from PhieuTraHangNhapChiTiet ptct " +
            "where ptct.phieuTraHangNhap.id = ?1 order by ptct.phieuTraHangNhap.thoiGian desc ")
    Page<PhieuTraHangNhapChiTiet> findAllByPhieuTraHangNhapId(int id, Pageable pageable);

    @Query(value = "from PhieuTraHangNhapChiTiet ptct " +
            "where ptct.phieuTraHangNhap.id = ?1 order by ptct.phieuTraHangNhap.thoiGian desc ")
    List<PhieuTraHangNhapChiTiet> findListByPhieuTraHangNhapId(int id);

    @Query(value = "from PhieuTraHangNhapChiTiet  ptct " +
            "where ptct.id = ?1")
    Optional<PhieuTraHangNhapChiTiet> findById(int id);

    @Query(value = "select distinct pt.phieuTraHangNhap from PhieuTraHangNhapChiTiet pt " +
            "where " +
            "(0 = ?1 or pt.chiNhanhHangHoa.chiNhanh.id = ?1) " +
            "and " +
            "((?2 is null or pt.phieuTraHangNhap.nhaCungCap.ten like concat('%', upper(?2), '%')) " +
            "or (?2 is null or pt.phieuTraHangNhap.nguoiDung.hoVaTen like concat('%', upper(?2), '%'))) order by pt.phieuTraHangNhap.thoiGian desc ")
    Page<PhieuTraHangNhapChiTiet> findByChiNhanhAndText(int chiNhanhId, String text, Pageable pageable);

}
