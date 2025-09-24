package com.tavi.tavi_mrs.service.bao_cao;

import com.tavi.tavi_mrs.entities.bao_cao.BaoCao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Optional;

public interface BaoCaoService {

    Page<BaoCao> findAll(Pageable pageable);

    Optional<BaoCao> findByIdAndXoa(int id, boolean xoa);

    Page<BaoCao> findByThongBao(int idThongBao, Pageable pageable);

    Page<BaoCao> findByTieuDeAndThoiGian(String tieuDe, Date ngayDau, Date ngayCuoi, boolean xoa, Pageable pageable);

    Boolean deleted(Integer id);
}
