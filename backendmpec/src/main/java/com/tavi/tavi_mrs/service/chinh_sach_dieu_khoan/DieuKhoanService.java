package com.tavi.tavi_mrs.service.chinh_sach_dieu_khoan;

import com.tavi.tavi_mrs.entities.chinh_sach_dieu_khoan.DieuKhoan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DieuKhoanService {

    Optional<DieuKhoan> findById(int id);

    List<DieuKhoan> findAll();

    Page<DieuKhoan> findAllToPage(Pageable pageable);

    Optional<DieuKhoan> save(DieuKhoan dieuKhoan);

    boolean delete(int id);
}