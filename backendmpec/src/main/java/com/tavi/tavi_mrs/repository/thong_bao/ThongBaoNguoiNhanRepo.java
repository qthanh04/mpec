package com.tavi.tavi_mrs.repository.thong_bao;

import com.tavi.tavi_mrs.entities.thong_bao.ThongBaoNguoiNhan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Repository
public interface ThongBaoNguoiNhanRepo extends JpaRepository<ThongBaoNguoiNhan, Integer> {

    @Query(value = "select t from ThongBaoNguoiNhan t " +
            "where t.xoa = false " +
            "and t.thongBao.xoa = false " +
            "and t.thongBao.id = ?1 " +
            "and t.thongBao.tieuDe like concat('%', ?2, '%') ")
    Page<ThongBaoNguoiNhan> findByThongBaoAndText(int thongBaoId, String text, Pageable pageable);


    @Query(value = "select t from ThongBaoNguoiNhan t " +
            "where t.xoa = false " +
            "and t.nguoiDung.xoa = false " +
            "and t.nguoiDung.id = ?1 " +
            "and t.nguoiDung.hoVaTen like concat('%', ?2, '%') ")
    Page<ThongBaoNguoiNhan> findByNguoiNhanAndText(int nguoiNhanId, String text, Pageable pageable);


    @Query(value = "select t from ThongBaoNguoiNhan t " +
            "where t.xoa = false and t.nguoiDung.xoa = false " +
            "and (?1 is null or t.thongBao.tieuDe like concat('%', ?1, '%')) " +
            "and (?2 is null or t.nguoiDung.hoVaTen like concat('%', ?2, '%')) " +
            "and (?3 is null or t.thongBao.thoiGianGui >= ?3) " +
            "and (?4 is null or t.thongBao.thoiGianGui <= ?4) ")
    Page<ThongBaoNguoiNhan> findByTieuDeAndNguoiNhanAndThoiGian(String tieuDe, String nguoiNhan, Date ngayDau, Date ngayCuoi, Pageable pageable);

    @Query(value = "select t from ThongBaoNguoiNhan t where t.xoa = false")
    Page<ThongBaoNguoiNhan> findAllToPage(Pageable pageable);
}
