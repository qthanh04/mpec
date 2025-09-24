package com.tavi.tavi_mrs.repository.so_quy;

import com.tavi.tavi_mrs.entities.so_quy.PhieuChi;
import com.tavi.tavi_mrs.entities.so_quy.PhieuThu;
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
public interface PhieuChiRepo extends JpaRepository<PhieuChi, Integer> {

    @Query(value = "from  PhieuChi pc where pc.xoa = false and pc.id = ?1")
    Optional<PhieuChi> findByIdAndXoa(int id, boolean xoa);

    @Query(value = "from PhieuChi pc where pc.xoa = false order by pc.thoiGian desc")
    Page<PhieuChi> findAllToPage(Pageable pageable);

    @Query(value = "from PhieuChi  pc " +
            "where pc.xoa = false " +
            "and (0=?1 or pc.chiNhanh.id = ?1) " +
            "and (?2 is null or pc.maPhieu like concat('%',?2,'%')) " +
            "and (?3 is null or pc.loaiChi.tenLoaiChi like concat('%',?3,'%')) " +
            "and (?4 is null or pc.nguoiDung.hoVaTen like concat('%',?4,'%')) " +
            "and (?5 is null or pc.thoiGian >= ?5) and (?6 is null or pc.thoiGian <= ?6) " +
            "and (?7 = -1 or pc.trangThai = ?7 ) " +
            "order by pc.thoiGian desc ")
    Page<PhieuChi> findByMaPhieuChiAndThoiGianAndTrangThai(Integer chiNhanhId, String maPhieuChi, String tenLoaiChi,
                                                           String tenNhanVien, Date ngayDau,
                                                           Date ngayCuoi, int trangThai, Pageable pageable);

    @Query(value = "select p from PhieuChi p " +
            "where p.xoa = false " +
            "and (0 = ?1 or p.chiNhanh.id = ?1) " +
            "and ( " +
            "(p.nguoiDung.hoVaTen is not null and upper(p.nguoiDung.hoVaTen) like concat('%', upper(?2) ,'%') )" +
            "or (p.loaiChi.maLoaiChi is not null and upper(p.loaiChi.maLoaiChi) like concat('%', upper(?2) ,'%') )" +
            ") " +
            "order by p.thoiGian desc")
    Page<PhieuChi> findByChiNhanhAndText(int chinhanhId, String text, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update PhieuChi p set p.trangThai = ?2 where p.id = ?1")
    int setTrangThai(int id, int trangThai);

    @Transactional
    @Modifying
    @Query("update PhieuChi p set p.xoa = true where p.id = ?1")
    int delete(Integer id);


}
