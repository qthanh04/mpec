package com.tavi.tavi_mrs.service.so_quy;

import com.tavi.tavi_mrs.entities.so_quy.LoaiChi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LoaiChiService {

    Page<LoaiChi> findAll(Pageable pageable);

    Optional<LoaiChi> findById(int id, boolean xoa);

    Boolean findByTenLoaiChi(String tenLoaiChi);

    Boolean findByMaLoaiChi(String maLoaiChi);

    Page<LoaiChi> findByTenLoaiChiAndMaLoaiChi(String tenLoaiChi, String maLoaiChi, Pageable pageable);

    Optional<LoaiChi> save(LoaiChi loaiChi);

    Boolean delete(Integer id);

}
