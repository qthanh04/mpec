package com.tavi.tavi_mrs.repository.so_quy;

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
public interface PhieuThuRepo extends JpaRepository<PhieuThu, Integer> {

    @Query(value = "from  PhieuThu p where p.xoa = false and p.id = ?1")
    Optional<PhieuThu> findByIdAndXoa(int id, boolean xoa);

    @Query(value = "from PhieuThu p where p.xoa = false order by p.thoiGian desc")
    Page<PhieuThu> findAllToPage(Pageable pageable);

    @Query(value = "from PhieuThu  p " +
            "where p.xoa = false " +
            "and (0=?1 or p.chiNhanh.id = ?1) " +
            "and (?2 is null or p.loaiThu.maLoaiThu like concat('%',?2,'%')) "+
            "and (?3 is null or p.loaiThu.tenLoaiThu like concat('%',?3,'%')) " +
            "and (?4 is null or p.nguoiDung.hoVaTen like concat('%',?4,'%')) " +
            "and (?5 is null or p.thoiGian >= ?5) and (?6 is null or p.thoiGian <= ?6) " +
            "and (?7 = -1 or p.trangThai = ?7 ) " +
            "order by p.thoiGian desc")
    Page<PhieuThu> findByMaPhieuThuAndThoiGianAndTrangThai(Integer chiNhanhId, String maPhieuThu,
                                                           String tenLoaiThu, String tenNhanVien, Date ngayDau,
                                                           Date ngayCuoi, int trangThai, Pageable pageable);

    @Query(value = "select p from PhieuThu p " +
            "where p.xoa = false " +
            "and (0 = ?1 or p.chiNhanh.id = ?1) " +
            "and ( " +
            "(p.nguoiDung.hoVaTen is not null and upper(p.nguoiDung.hoVaTen) like concat('%', upper(?2) ,'%') )" +
            "or (p.loaiThu.maLoaiThu is not null and upper(p.loaiThu.maLoaiThu) like concat('%', upper(?2) ,'%') )" +
            ") " +
            "order by p.thoiGian desc")
    Page<PhieuThu> findByChiNhanhAndText(int chinhanhId, String text, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update PhieuThu p set p.trangThai = ?2 where p.id = ?1")
    int setTrangThai(int id, int trangThai);

    @Transactional
    @Modifying
    @Query("update PhieuThu p set p.xoa = true where p.id = ?1")
    int delete(Integer id);
}
