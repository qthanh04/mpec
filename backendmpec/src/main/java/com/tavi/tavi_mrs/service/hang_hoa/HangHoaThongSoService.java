package com.tavi.tavi_mrs.service.hang_hoa;

import com.tavi.tavi_mrs.entities.hang_hoa.HangHoaThongSo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface HangHoaThongSoService {

    Optional<HangHoaThongSo> findById(int id, boolean xoa);

    Page<HangHoaThongSo> findAllToPage(Pageable pageable);

    Page<HangHoaThongSo> search(String tenTSKT, String tenTSCT, String tenHangHoa, Pageable pageable);

    Optional<HangHoaThongSo> save(HangHoaThongSo hangHoaThongSo);

    Boolean delete(Integer id);

}
