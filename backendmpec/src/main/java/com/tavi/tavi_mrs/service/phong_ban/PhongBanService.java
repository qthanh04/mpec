package com.tavi.tavi_mrs.service.phong_ban;

import com.tavi.tavi_mrs.entities.dto.SelectResultListDto;
import com.tavi.tavi_mrs.entities.hang_hoa.NhomHang;
import com.tavi.tavi_mrs.entities.phong_ban.PhongBan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PhongBanService {

    List<PhongBan> findAll();

    Optional<PhongBan> findByIdAndXoa(int id, boolean xoa);

    Optional<PhongBan> save(PhongBan phongBan);

    Boolean findByMaPhongBan(String maPhongBan);

    Boolean findByTenPhongBan(String tenPhongBan);

    Page<PhongBan> findAllToPage(Pageable pageable);

    SelectResultListDto selectSearch(String text, Pageable pageable);

    Boolean deleted(Integer id);

    Page<PhongBan> findByTenPhongBanAndMaPhongBan(String tenPhongBan, String maPhongBan, Pageable pageable);
}
