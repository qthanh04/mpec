package com.tavi.tavi_mrs.service_impl.so_quy;

import com.tavi.tavi_mrs.entities.so_quy.LoaiThu;
import com.tavi.tavi_mrs.repository.so_quy.LoaiThuRepo;
import com.tavi.tavi_mrs.service.so_quy.LoaiThuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class LoaiThuServiceImpl implements LoaiThuService {

    public static final java.util.logging.Logger LOGGER = Logger.getLogger(LoaiThuServiceImpl.class.getName());

    @Autowired
    LoaiThuRepo loaiThuRepo;

    @Override
    public Page<LoaiThu> findAll(Pageable pageable) {
        try {
            return loaiThuRepo.findAll(pageable);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-error: " + ex);
            return null;
        }
    }

    @Override
    public Optional<LoaiThu> findById(int id, boolean xoa) {
        try {
            return loaiThuRepo.findById(id, false);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-by-id-error: " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Boolean findByTenLoaiThu(String tenLoaiThu) {
        try {
            Optional<LoaiThu> loaiThu = loaiThuRepo.findByTenLoaiThu(tenLoaiThu);
            if (loaiThu.get() != null) {
                return true;
            }else {
                return false;
            }
        }catch (Exception ex){
            LOGGER.log(Level.SEVERE, "find-by-ten-loai-thu-error: " + ex);
            return false;
        }
    }

    @Override
    public Boolean findByMaLoaiThu(String maLoaiThu) {
        try {
            Optional<LoaiThu> loaiThu = loaiThuRepo.findByMaLoaiThu(maLoaiThu);
            if (loaiThu.get() != null) {
                return true;
            }else {
                return false;
            }
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-by-ma-loai-thu-error: " + ex);
            return false;
        }
    }

    @Override
    public Page<LoaiThu> findByTenLoaiThuAndMaLoaiThu(String tenLoaiThu, String maLoaiThu, Pageable pageable) {
        try {
            return loaiThuRepo.findByTenLoaiThuAndMaLoaiThu(tenLoaiThu, maLoaiThu, pageable);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-by-ten-loai-thu-an-ma-loai-thu-error: " + ex);
            return null;
        }
    }

    @Override
    public Optional<LoaiThu> save(LoaiThu loaiThu) {
        try {
            return Optional.ofNullable(loaiThuRepo.save(loaiThu));
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-loai-thu-error: " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Boolean delete(Integer id) {
        try {
            return loaiThuRepo.delete(id) > 0 ? true : false;
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "delete-loai-thu-error: " + ex);
            return false;
        }
    }
}
