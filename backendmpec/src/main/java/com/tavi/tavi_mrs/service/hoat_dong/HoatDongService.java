package com.tavi.tavi_mrs.service.hoat_dong;

import com.tavi.tavi_mrs.entities.don_vi.DonVi;
import com.tavi.tavi_mrs.entities.hoat_dong.HoatDong;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface HoatDongService {

    Page<HoatDong> findAll(Pageable pageable);

    Page<HoatDong> search(String ten, Pageable pageable);

    Optional<HoatDong> findById(int id);

    Optional<HoatDong> save(HoatDong hoatDong);

    Boolean deleted(int id);
}
