package com.tavi.tavi_mrs.repository.bao_cao;

import com.tavi.tavi_mrs.entities.bao_cao.ExcelFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ExcelFileRepo extends JpaRepository<ExcelFile, Integer> {

    @Query(value = "from ExcelFile e where e.xoa = false order by e.thoiGianTao desc")
    Page<ExcelFile> findAll(Pageable pageable);

    @Query(value = "from ExcelFile e where e.xoa = false and e.id = ?1 ")
    Optional<ExcelFile> findById(Integer integer);

    @Query(value = "from ExcelFile e where e.xoa = false and e.tenLoai like concat('%', ?1 ,'%') order by e.thoiGianTao desc")
    Page<ExcelFile> findByTenLoai(String tenLoai, Pageable pageable);

    @Query(value = "from ExcelFile e where " +
            "(e.tenLoai is null or e.tenLoai like concat('%', ?1 ,'%') ) " +
            "and (?2 is null or e.maPhieu like concat('%', ?2 ,'%')) " +
            "and (?3 is null or e.thoiGianTao >= ?3) " +
            "and (?4 is null or e.thoiGianTao <=?4) " +
            "and e.xoa = ?5 " +
            "order by e.thoiGianTao desc")
    Page<ExcelFile> findByTenLoaiAndMaPhieuAndThoiGian(String tenLoai, String maPhieu, Date ngayDau, Date ngayCuoi, Boolean xoa, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "update ExcelFile e set e.xoa = true where e.id = ?1 ")
    int deleted(Integer id);


}
