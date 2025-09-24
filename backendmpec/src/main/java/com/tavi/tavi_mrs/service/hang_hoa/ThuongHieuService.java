package com.tavi.tavi_mrs.service.hang_hoa;

import com.tavi.tavi_mrs.entities.hang_hoa.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ThuongHieuService {

    Optional<ThuongHieu> findAll();

    Optional<ThuongHieu> findByIdAndXoa(int id, boolean xoa);

    Optional<ThuongHieu> save(ThuongHieu thuongHieu);

    Page<ThuongHieu> findAllToPage(Pageable pageable);

    Boolean deleted(Integer id);

    Boolean findByTenThuongHieu(String tenThuongHieu);

    Page<ThuongHieu> findByTenThuongHieu(String tenThuongHieu, Pageable pageable);

    ThuongHieu findByTenThuongHieuExcel(String tenThuongHieu);

}
