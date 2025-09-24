package com.tavi.tavi_mrs.service.hang_hoa;

import com.tavi.tavi_mrs.entities.hang_hoa.ThongSoKiThuat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ThongSoKiThuatService {

    Optional<ThongSoKiThuat> findById(int id, boolean xoa);

    Page<ThongSoKiThuat> findThongSoKiThuatByTenNhomHang(String tenNhomhang, Pageable pageable);

    Page<ThongSoKiThuat> findAllToPage(Pageable pageable);

    Optional<ThongSoKiThuat> save(ThongSoKiThuat thongSoKiThuat);

    Page<ThongSoKiThuat> search(String tenThongSo, Pageable pageable);

    Boolean delete(Integer id);


}
