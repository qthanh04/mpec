package com.tavi.tavi_mrs.service.thong_bao;

import com.tavi.tavi_mrs.entities.thong_bao.ThongBaoNguoiNhan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Optional;

public interface ThongBaoNguoiNhanService {

    Page<ThongBaoNguoiNhan> findByThongBaoAndText(int thongBaoId, String text, Pageable pageable);

    Page<ThongBaoNguoiNhan> findByNguoiNhanAndText(int nguoiNhanId, String text, Pageable pageable);

    Page<ThongBaoNguoiNhan> findByTieuDeAndNguoiNhanAndThoiGian(String tieuDe, String nguoiNhan, Date ngayDau, Date ngayCuoi, Pageable pageable);

    Page<ThongBaoNguoiNhan> findAllToPage(Pageable pageable);

    Optional<ThongBaoNguoiNhan> save(ThongBaoNguoiNhan thongBaoNguoiNhan);

}
