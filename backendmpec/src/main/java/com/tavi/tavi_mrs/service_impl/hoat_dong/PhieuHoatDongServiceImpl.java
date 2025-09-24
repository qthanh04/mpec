package com.tavi.tavi_mrs.service_impl.hoat_dong;

import com.tavi.tavi_mrs.entities.hoat_dong.PhieuHoatDong;
import com.tavi.tavi_mrs.payload.hoat_dong.PhieuHoatDongResponse;
import com.tavi.tavi_mrs.repository.hoat_dong.PhieuHoatDongRepo;
import com.tavi.tavi_mrs.service.hoat_dong.PhieuHoatDongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PhieuHoatDongServiceImpl implements PhieuHoatDongService {

    private static final Logger LOGGER = Logger.getLogger(PhieuHoatDongServiceImpl.class.getName());

    @Autowired
    private PhieuHoatDongRepo phieuHoatDongRepo;

    @Override
    public Page<PhieuHoatDong> findAll(Pageable pageable) {
        try {
            return phieuHoatDongRepo.findAll(pageable);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "findAll-phieu-hoat-dong-error : " + ex);
            return null;
        }
    }

    @Override
    public Optional<PhieuHoatDong> findByIdAndXoa(int id, boolean xoa) {
        return Optional.empty();
    }

    @Override
    public Page<PhieuHoatDong> findByMaPhieuAndTenHoatDongAndThoiGian(String maPhieu, String tenHoatDong, Date ngayDau, Date ngayCuoi, Pageable pageable) {
        try {
            return phieuHoatDongRepo.findByMaPhieuAndTenHoatDongAndThoiGian(maPhieu, tenHoatDong, ngayDau, ngayCuoi, pageable);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "findByMaPhieuAndTenHoatDongAndThoiGian error: " + ex);
            return null;
        }
    }


    @Override
    public Optional<PhieuHoatDong> save(PhieuHoatDong phieuHoatDong) {
        try {
            return Optional.ofNullable(phieuHoatDongRepo.save(phieuHoatDong));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-phieuHoatDong-error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Boolean deleted(int id) {
        return null;
    }
}
