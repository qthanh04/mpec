package com.tavi.tavi_mrs.service_impl.so_quy;

import com.tavi.tavi_mrs.entities.so_quy.LoaiChi;
import com.tavi.tavi_mrs.repository.so_quy.LoaiChiRepo;
import com.tavi.tavi_mrs.service.so_quy.LoaiChiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class LoaiChiServiceImpl implements LoaiChiService {

    private static final java.util.logging.Logger LOGGER = Logger.getLogger(LoaiChiServiceImpl.class.getName());

    @Autowired
    LoaiChiRepo loaiChiRepo;

    @Override
    public Page<LoaiChi> findAll(Pageable pageable) {
        try {
            return loaiChiRepo.findAll(pageable);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-loai-chi-error :" + ex);
            return null;
        }
    }

    @Override
    public Optional<LoaiChi> findById(int id, boolean xoa) {
        try {
            return loaiChiRepo.findById(id, false);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-by-id-loai-chi-error: " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Boolean findByTenLoaiChi(String tenLoaiChi) {
        try {
            Optional<LoaiChi> loaiChi = loaiChiRepo.findByTenLoaiChi(tenLoaiChi);
            if (loaiChi.get() != null) {
                return true;
            }else {
                return false;
            }
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-by-ten-loai-chi-error" + ex);
            return false;
        }
    }

    @Override
    public Boolean findByMaLoaiChi(String maLoaiChi) {
        try {
            Optional<LoaiChi> loaiChi = loaiChiRepo.findByMaLoaiChi(maLoaiChi);
            if (loaiChi.get() != null) {
                return true;
            }else {
                return false;
            }
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-by-ma-loai-chi-error: " + ex);
            return false;
        }
    }

    @Override
    public Page<LoaiChi> findByTenLoaiChiAndMaLoaiChi(String tenLoaiChi, String maLoaiChi, Pageable pageable) {
        try {
            return loaiChiRepo.findByTenLoaiChiAndMaLoaiChi(tenLoaiChi, maLoaiChi, pageable);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-by-ten-loai-chi-and-ma-loai-chi-error: " + ex);
            return null;
        }
    }


    @Override
    public Optional<LoaiChi> save(LoaiChi loaiChi) {
        try {
            return Optional.ofNullable(loaiChiRepo.save(loaiChi));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-loai-chi-error: " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Boolean delete(Integer id) {
        try {
            return loaiChiRepo.delete(id) > 0 ? true : false;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "delete-loai-chi-error: " + ex);
            return false;
        }
    }
}
