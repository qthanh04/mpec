package com.tavi.tavi_mrs.service_impl.hang_hoa;

import com.tavi.tavi_mrs.entities.hang_hoa.ThongSoKiThuat;
import com.tavi.tavi_mrs.repository.hang_hoa.ThongSoKiThuatRepo;
import com.tavi.tavi_mrs.service.hang_hoa.ThongSoKiThuatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ThongSoKiThuatServiceImpl implements ThongSoKiThuatService {

    private static final java.util.logging.Logger LOGGER = Logger.getLogger(NhomHangServiceImpl.class.getName());

    @Autowired
    ThongSoKiThuatRepo thongSoKiThuatRepo;

    @Override
    public Optional<ThongSoKiThuat> findById(int id, boolean xoa) {
        try {
            return thongSoKiThuatRepo.findById(id, xoa);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-thong-so-ki-thuat-by-id-error: " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Page<ThongSoKiThuat> findThongSoKiThuatByTenNhomHang(String tenNhomHang, Pageable pageable) {
        try {
            return thongSoKiThuatRepo.findThongSoKiThuatByTenNhomHang(tenNhomHang, pageable);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-thong-so-ki-thuat-by-nhom-hang-erorr: " + ex);
            return null;
        }
    }

    @Override
    public Page<ThongSoKiThuat> findAllToPage(Pageable pageable) {
        try {
            return thongSoKiThuatRepo.findAllToPage(pageable);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-thong-so-ky-thuat-error: " + ex);
            return null;
        }
    }

    @Override
    public Optional<ThongSoKiThuat> save(ThongSoKiThuat thongSoKiThuat) {
        try {
            return Optional.ofNullable(thongSoKiThuatRepo.save(thongSoKiThuat));
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-thong-so-ky-thuat-error: " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Page<ThongSoKiThuat> search(String tenThongSo, Pageable pageable) {
        try {
            return thongSoKiThuatRepo.search(tenThongSo, pageable);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "search-thong-so-ky-thuat-error" + ex);
            return null;
        }
    }

    @Override
    public Boolean delete(Integer id) {
        try {
            return thongSoKiThuatRepo.delete(id) > 0 ? true : false;
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "delete-thong-so-ki-thuat-error: " + ex);
            return false;
        }
    }
}
