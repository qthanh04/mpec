package com.tavi.tavi_mrs.service.bao_cao;

import com.tavi.tavi_mrs.entities.bao_cao.ExcelFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Optional;

public interface ExcelFileService {

    Page<ExcelFile> findAll(Pageable pageable);

    Optional<ExcelFile> findById(int id);

    Optional<ExcelFile> save(ExcelFile excelFile);

    Page<ExcelFile> findByTenLoai(String tenLoai, Pageable pageable);

    Page<ExcelFile> findByTenLoaiAndMaPhieuAndThoiGian(String tenLoai, String maPhieu, Date ngayDau, Date ngayCuoi, boolean xoa, Pageable pageable);

    Boolean deleted(Integer id);

}
