package com.tavi.tavi_mrs.service_impl.hang_hoa;

import com.tavi.tavi_mrs.entities.hang_hoa.ThongSoChiTiet;
import com.tavi.tavi_mrs.entities.hang_hoa.ThongSoKiThuat;
import com.tavi.tavi_mrs.repository.hang_hoa.ThongSoChiTietRepo;
import com.tavi.tavi_mrs.service.hang_hoa.ThongSoChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ThongSoChiTietImpl implements ThongSoChiTietService {

    private static final java.util.logging.Logger LOGGER = Logger.getLogger(NhomHangServiceImpl.class.getName());

    @Autowired
    ThongSoChiTietRepo thongSoChiTietRepo;

    @Override
    public Optional<ThongSoChiTiet> findById(int id, boolean xoa) {
        try {
            return thongSoChiTietRepo.findById(id, false);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-thong-so-chi-tiet-by-id-error: " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Page<ThongSoChiTiet> findAllToPage(Pageable pageable) {
        try {
            return thongSoChiTietRepo.findAllToPage(pageable);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-thong-so-chi-tiet-error: " +ex);
            return null;
        }
    }

    @Override
    public Page<ThongSoChiTiet> findByThongSoKiThuat(String tenThongSoKiThuat, Pageable pageable) {
        try {
            return thongSoChiTietRepo.findByThongSoKiThuat(tenThongSoKiThuat, pageable);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-by-thong-so-ki-thuat-error: " +ex);
            return null;
        }
    }

    @Override
    public Optional<ThongSoChiTiet> save(ThongSoChiTiet thongSoChiTiet) {
        try {
            return Optional.ofNullable(thongSoChiTietRepo.save(thongSoChiTiet));
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-thong-so-chi-tiet-error: " +ex);
            return Optional.empty();
        }
    }

    @Override
    public Page<ThongSoChiTiet> search(String tenThongSoChiTiet, Pageable pageable) {
        try {
            return thongSoChiTietRepo.search(tenThongSoChiTiet, pageable);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "search-thong-so-chi-tiet-error: " +ex);
            return null;
        }
    }

    @Override
    public Boolean delete(int id) {
        try {
            return thongSoChiTietRepo.delete(id) > 0 ? true : false;
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "delete-thong-so-chi-tiet-error : " + ex);
            return false;
        }
    }
}
