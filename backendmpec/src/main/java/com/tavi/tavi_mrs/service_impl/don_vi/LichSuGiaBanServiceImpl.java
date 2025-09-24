package com.tavi.tavi_mrs.service_impl.don_vi;

import com.tavi.tavi_mrs.entities.don_vi.LichSuGiaBan;
import com.tavi.tavi_mrs.repository.don_vi.LichSuGiaBanRepo;
import com.tavi.tavi_mrs.service.don_vi.LichSuGiaBanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class LichSuGiaBanServiceImpl implements LichSuGiaBanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LichSuGiaBanServiceImpl.class);

    @Autowired
    private LichSuGiaBanRepo lichSuGiaBanRepo;

    @Override
    public Page<LichSuGiaBan> findAll(Pageable pageable) {
        try {
            return lichSuGiaBanRepo.findAll(pageable);
        } catch (Exception ex) {
            LOGGER.error("find-all-lich-su-gia-ban-error : " + ex);
            return null;
        }
    }

    @Override
    public Optional<LichSuGiaBan> findById(int id) {
        try {
            return lichSuGiaBanRepo.findById(id);
        } catch (Exception ex) {
            LOGGER.error("find-by-id-error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Page<LichSuGiaBan> findByDonViHangHoaId(int donViHangHoaId, Pageable pageable) {
        try {
            return lichSuGiaBanRepo.findByDonViHangHoaId(donViHangHoaId, pageable);
        } catch (Exception ex) {
            LOGGER.error("find-lich-su-gia-ban-by-don-vi-hang-hoa-error : " + ex);
            return null;
        }
    }

    @Override
    public Optional<LichSuGiaBan> findByDonViHangHoaIdAndThoiGian(int donViHangHoaId, Date thoiGian) {
        try {
            return lichSuGiaBanRepo.findByDonViHangHoaIdAndThoiGian(donViHangHoaId, thoiGian);
        } catch (Exception ex) {
            LOGGER.error("find-lich-su-gia-ban-by-don-vi-hang-hoa-and-thoi-gian-error : " + ex);
            return null;
        }
    }

    @Override
    public Optional<LichSuGiaBan> save(LichSuGiaBan lichSuGiaBan) {
        try {
            return Optional.ofNullable(lichSuGiaBanRepo.save(lichSuGiaBan));
        } catch (Exception ex) {
            LOGGER.error("save-lich-su-gia-ban-error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<LichSuGiaBan> findByDonViHangHoaAndThoiGianHienTai(int donViHangHoaId) {
        try {
            return lichSuGiaBanRepo.findByDonViHangHoaAndThoiGianHienTai(donViHangHoaId);
        } catch (Exception ex) {
            LOGGER.error("find-gia-ban-hien-tai-error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Page<LichSuGiaBan> search(String text, Pageable pageable) {
        try {
            return lichSuGiaBanRepo.search(text, pageable);
        } catch (Exception ex) {
            LOGGER.error("search-lich-su-gia-ban-error : " + ex);
            return null;
        }
    }

    @Override
    public Optional<LichSuGiaBan> findDistinctGiaBan(int hangHoaId) {
        try {
            return lichSuGiaBanRepo.findDistinctGiaBan(hangHoaId);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
