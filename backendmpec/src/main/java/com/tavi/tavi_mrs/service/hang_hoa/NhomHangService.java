package com.tavi.tavi_mrs.service.hang_hoa;

import com.tavi.tavi_mrs.entities.hang_hoa.NhomHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface NhomHangService {

    List<NhomHang> findAll();

    Optional<NhomHang> findByIdAndXoa(int id, boolean xoa);

    Optional<NhomHang> save(NhomHang nhomHang);

    Optional<NhomHang> findById(int id, boolean xoa);

    Boolean findByMaNhomHang(String maNhomHang);

    Boolean findByTenNhomHang(String tenNhomHang);

    NhomHang findByTenNhomHangExcel(String tenNhomHang);

    Page<NhomHang> findAllToPage(Pageable pageable);

    Boolean deleted(Integer id);

    Page<NhomHang> findByTenNhomHangAndMaNhomHang(String tenNhomHang, String maNhomHang, Pageable pageable);

}
 