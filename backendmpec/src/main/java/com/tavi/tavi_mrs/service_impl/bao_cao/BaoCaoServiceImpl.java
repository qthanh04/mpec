package com.tavi.tavi_mrs.service_impl.bao_cao;

import com.tavi.tavi_mrs.entities.bao_cao.BaoCao;
import com.tavi.tavi_mrs.repository.bao_cao.BaoCaoRepo;
import com.tavi.tavi_mrs.service.bao_cao.BaoCaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class BaoCaoServiceImpl implements BaoCaoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaoCaoServiceImpl.class);

    @Autowired
    private BaoCaoRepo baoCaoRepo;

    @Override
    public Page<BaoCao> findAll(Pageable pageable) {
        try {
            return baoCaoRepo.findAll(pageable);
        } catch (Exception ex) {
            LOGGER.error("find-all-bao-cao-error : " + ex);
            return null;
        }
    }

    @Override
    public Optional<BaoCao> findByIdAndXoa(int id, boolean xoa) {
        try {
            return baoCaoRepo.findByIdAndXoa(id, xoa);
        } catch (Exception ex) {
            LOGGER.error("find-bao-cao-by-id-error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Page<BaoCao> findByThongBao(int idThongBao, Pageable pageable) {
        try {
            return baoCaoRepo.findByThongBao(idThongBao, pageable);
        } catch (Exception ex) {
            LOGGER.error("findby-thong-bao-error : " + ex);
            return null;
        }
    }

    @Override
    public Page<BaoCao> findByTieuDeAndThoiGian(String tieuDe, Date ngayDau, Date ngayCuoi, boolean xoa, Pageable pageable) {
        try {
            return baoCaoRepo.findByTieuDeAndThoiGian(tieuDe, ngayDau, ngayCuoi, xoa, pageable);
        } catch (Exception ex) {
            LOGGER.error("find-bao-vao-by-tieu-de-and-thoi-gian-error: " + ex);
            return null;
        }
    }

    @Override
    public Boolean deleted(Integer id) {
        try {
            return baoCaoRepo.deleted(id) > 0 ? true : false;
        } catch (Exception ex) {
            LOGGER.error("delete-bao-cao-error : " + ex);
            return false;
        }
    }
}
