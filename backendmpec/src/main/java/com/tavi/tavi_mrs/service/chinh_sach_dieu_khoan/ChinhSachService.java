package com.tavi.tavi_mrs.service.chinh_sach_dieu_khoan;

import com.tavi.tavi_mrs.entities.chinh_sach_dieu_khoan.ChinhSach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ChinhSachService {

    boolean delete(Integer id);

    Optional<ChinhSach> save(ChinhSach chinhSach);

    Optional<ChinhSach> findById(int id);

    List<ChinhSach> findAll();

    Page<ChinhSach> findAllToPage(Pageable pageable);

}
