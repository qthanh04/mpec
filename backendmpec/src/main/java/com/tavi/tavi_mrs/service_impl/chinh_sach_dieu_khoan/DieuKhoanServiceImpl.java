package com.tavi.tavi_mrs.service_impl.chinh_sach_dieu_khoan;

import com.tavi.tavi_mrs.entities.chinh_sach_dieu_khoan.DieuKhoan;
import com.tavi.tavi_mrs.repository.chinh_sach_dieu_khoan.DieuKhoanRepo;
import com.tavi.tavi_mrs.service.chinh_sach_dieu_khoan.DieuKhoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DieuKhoanServiceImpl implements DieuKhoanService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DieuKhoanServiceImpl.class);

    @Autowired
    private DieuKhoanRepo dieuKhoanRepo;

    @Override
    public Optional<DieuKhoan> findById(int id) {
        try {
            return dieuKhoanRepo.findById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.error("find by id error {0}", ex);
            return Optional.empty();
        }
    }

    @Override
    public List<DieuKhoan> findAll() {
        try {
            return dieuKhoanRepo.findAll();
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.error("find all error {0}", ex);
            return null;
        }
    }

    @Override
    public Page<DieuKhoan> findAllToPage(Pageable pageable) {
        try {
            return dieuKhoanRepo.findAll(pageable);
        }catch (Exception ex) {
            LOGGER.error("find-all-dieu-khoan-error: " + ex);
            return null;
        }
    }

    @Override
    public Optional<DieuKhoan> save(DieuKhoan dieuKhoan) {
        try {
            return Optional.of(dieuKhoanRepo.save(dieuKhoan));
        } catch (Exception ex) {
            LOGGER.error("save dieu khoan error", ex);
            ex.printStackTrace();
            return Optional.empty();
        }
    }



    @Override
    public boolean delete(int id) {
        try {
            return dieuKhoanRepo.delete( id) > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.error("delete xe error {0}", ex);
            return false;
        }
    }
}