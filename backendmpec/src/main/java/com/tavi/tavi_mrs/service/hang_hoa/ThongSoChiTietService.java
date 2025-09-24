package com.tavi.tavi_mrs.service.hang_hoa;

import com.tavi.tavi_mrs.entities.hang_hoa.ThongSoChiTiet;
import com.tavi.tavi_mrs.entities.hang_hoa.ThongSoKiThuat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ThongSoChiTietService {

    Optional<ThongSoChiTiet> findById(int id, boolean xoa);

    Page<ThongSoChiTiet> findAllToPage(Pageable pageable);

    Page<ThongSoChiTiet> findByThongSoKiThuat( String tenThongSoKiThuat, Pageable pageable);

    Optional<ThongSoChiTiet> save(ThongSoChiTiet thongSoChiTiet);

    Page<ThongSoChiTiet> search(String tenThongSoChiTiet, Pageable pageable);

    Boolean delete(int id);
}
