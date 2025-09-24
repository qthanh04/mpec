package com.tavi.tavi_mrs.service.so_quy;


import com.tavi.tavi_mrs.entities.so_quy.LoaiThu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LoaiThuService {

    Page<LoaiThu> findAll(Pageable pageable);

    Optional<LoaiThu> findById(int id, boolean xoa);

    Boolean findByTenLoaiThu(String tenLoaiThu);

    Boolean findByMaLoaiThu(String maLoaiThu);

    Page<LoaiThu> findByTenLoaiThuAndMaLoaiThu(String tenLoaiThu, String maLoaiThu, Pageable pageable);

    Optional<LoaiThu> save(LoaiThu loaiThu);

    Boolean delete(Integer id);
}
