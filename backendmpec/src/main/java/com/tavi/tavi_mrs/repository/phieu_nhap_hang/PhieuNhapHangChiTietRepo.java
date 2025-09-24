package com.tavi.tavi_mrs.repository.phieu_nhap_hang;

import com.tavi.tavi_mrs.entities.phieu_nhap_hang.PhieuNhapHangChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface PhieuNhapHangChiTietRepo extends JpaRepository<PhieuNhapHangChiTiet, Integer> {

    @Query("select p from PhieuNhapHangChiTiet p where p.xoa = false ")
    Optional<PhieuNhapHangChiTiet> findById(int id);

    @Query("select distinct p.phieuNhapHang from PhieuNhapHangChiTiet p where p.xoa = false and p.id = ?1 order by p.phieuNhapHang.thoiGian desc  ")
    Page<PhieuNhapHangChiTiet> findAll(Pageable pageable);

    @Query("select pt from PhieuNhapHangChiTiet pt " +
            "where pt.xoa = false and pt.phieuNhapHang.id = ?1 order by pt.phieuNhapHang.thoiGian desc  ")
    Page<PhieuNhapHangChiTiet> findByPhieuNhap(int phieuNhapId, Pageable pageable);

    @Query(value = "from PhieuNhapHangChiTiet p where p.xoa = false and p.phieuNhapHang.id = ?1 order by p.phieuNhapHang.thoiGian desc ")
    List<PhieuNhapHangChiTiet> findListByPhieuNhapId(int phieuNhapId);

    @Modifying
    @Transactional
    @Query(value = "update PhieuNhapHangChiTiet pn  set pn.xoa = true where pn.id = ?1")
    int deleted(Integer id);

    @Query(value = "select distinct pt.phieuNhapHang from PhieuNhapHangChiTiet pt " +
            "where pt.xoa = false " +
            "and pt.phieuNhapHang.xoa = false " +
            "and (0 = ?1 or pt.chiNhanhHangHoa.chiNhanh.id = ?1) " +
            "and " +
            "((?2 is null or pt.phieuNhapHang.nhaCungCap.ten like concat('%', upper(?2), '%')) " +
            "or (?2 is null or pt.phieuNhapHang.nguoiDung.hoVaTen like concat('%', upper(?2), '%'))) order by pt.phieuNhapHang.thoiGian desc ")
    Page<PhieuNhapHangChiTiet> findByChiNhanhAndText(int chiNhanhId, String text, Pageable pageable);

    @Query(nativeQuery = true, value = "select * from phieu_nhap_hang_chi_tiet as pn INNER JOIN " +
            " chi_nhanh_hang_hoa as cn on pn.chi_nhanh_hang_hoa_id = cn.id and " +
            " cn.chi_nhanh_id = :chiNhanhId and cn.hang_hoa_id = :hangHoaId " +
            " order by (select pnh.thoi_gian from phieu_nhap_hang pnh where pnh.id = pn.phieu_nhap_hang_id) desc limit 1")
    Optional<PhieuNhapHangChiTiet> timGiaNhapGanNhat(@Param("chiNhanhId") Integer chiNhanhId,
                                                     @Param("hangHoaId") Integer hangHoaId);
}

