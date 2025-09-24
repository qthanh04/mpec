package com.tavi.tavi_mrs.service_impl.thong_bao;

import com.tavi.tavi_mrs.entities.thong_bao.ThongBaoNguoiNhan;
import com.tavi.tavi_mrs.repository.thong_bao.ThongBaoNguoiNhanRepo;
import com.tavi.tavi_mrs.service.thong_bao.ThongBaoNguoiNhanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ThongBaoNguoiNhanServiceImpl implements ThongBaoNguoiNhanService {

    @Autowired
    private ThongBaoNguoiNhanRepo thongBaoNguoiNhanRepo;

    private static final Logger LOGGER = LoggerFactory.getLogger(ThongBaoNguoiNhanServiceImpl.class);

    @Override
    public Page<ThongBaoNguoiNhan> findByThongBaoAndText(int thongBaoId, String text, Pageable pageable) {
        try {
            return thongBaoNguoiNhanRepo.findByThongBaoAndText(thongBaoId, text, pageable);
        } catch (Exception ex) {
            LOGGER.error("findByThongBaoAndText-error: ", ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<ThongBaoNguoiNhan> findByNguoiNhanAndText(int nguoiNhanId, String text, Pageable pageable) {
        try {
            return thongBaoNguoiNhanRepo.findByNguoiNhanAndText(nguoiNhanId, text, pageable);
        } catch (Exception ex) {
            LOGGER.error("findByNguoiNhanAndText-error", ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<ThongBaoNguoiNhan> findByTieuDeAndNguoiNhanAndThoiGian(String tieuDe, String nguoiNhan, Date ngayDau, Date ngayCuoi, Pageable pageable) {
        try {
            return thongBaoNguoiNhanRepo.findByTieuDeAndNguoiNhanAndThoiGian(tieuDe, nguoiNhan, ngayDau, ngayCuoi, pageable);
        }catch (Exception ex) {
            LOGGER.error("findByTieuDeAndNguoiNhanAndThoiGian-erorr", ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<ThongBaoNguoiNhan> findAllToPage(Pageable pageable) {
        try {
            return thongBaoNguoiNhanRepo.findAllToPage(pageable);
        }catch (Exception ex) {
            LOGGER.error("findAllToPage-erorr", ex);
            ex.printStackTrace();
            return null;
        }
    }


    @Override
    public Optional<ThongBaoNguoiNhan> save(ThongBaoNguoiNhan thongBaoNguoiNhan) {
        try {
            return Optional.of(thongBaoNguoiNhanRepo.save(thongBaoNguoiNhan));
        } catch (Exception ex) {
            LOGGER.error("save ThongBaoNguoiNhan error", ex);
            ex.printStackTrace();
            return null;
        }
    }
}
