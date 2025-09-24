package com.tavi.tavi_mrs.repository.thong_bao;


import com.tavi.tavi_mrs.entities.thong_bao.ThongBao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ThongBaoRepo extends JpaRepository<ThongBao, Integer> {

    @Query(value = "from ThongBao t where t.xoa = false and t.id = ?1")
    Optional<ThongBao> findByIdAndXoa(int id, boolean xoa);

    @Modifying
    @Transactional
    @Query("update ThongBao tb set tb.xoa = true where tb.id = ?1")
    int deleted(int id);

    @Query(value = "from ThongBao t where t.xoa = false ")
    Page<ThongBao> findAll(Pageable pageable);

    @Query("from ThongBao tb " +
            "where tb.xoa = false " +
            "and (?1 is null or tb.thoiGianGui >= ?1) " +
            "and (?2 is null or tb.thoiGianGui <= ?2) " +
            "and tb.tieuDe like concat('%',?3,'%') " +
            "order by tb.thoiGianGui desc ")
    Page<ThongBao> findByThoiGianGuiAndTieuDe(Date ngayDau, Date ngayCuoi, String tieuDe, Pageable pageable);

}
