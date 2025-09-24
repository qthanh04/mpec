package com.tavi.tavi_mrs.service_impl.hang_hoa;

import com.tavi.tavi_mrs.entities.hang_hoa.HangHoaThongSo;
import com.tavi.tavi_mrs.repository.hang_hoa.HangHoaThongSoRepo;
import com.tavi.tavi_mrs.service.hang_hoa.HangHoaThongSoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class HangHoaThongSoServiceImpl implements HangHoaThongSoService {

    private static final java.util.logging.Logger LOGGER = Logger.getLogger(NhomHangServiceImpl.class.getName());

    @Autowired
    HangHoaThongSoRepo hangHoaThongSoRepo;

    @Override
    public Optional<HangHoaThongSo> findById(int id, boolean xoa) {
        try {
            return hangHoaThongSoRepo.findById(id, false);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-hang-hoa-thong-so-by-id-error: " +ex);
            return Optional.empty();
        }
    }

    @Override
    public Page<HangHoaThongSo> findAllToPage(Pageable pageable) {
        try {
            return hangHoaThongSoRepo.findAllToPage(pageable);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-hang-hoa-thong-so-eror: " + ex);
            return null;
        }
    }

    @Override
    public Page<HangHoaThongSo> search(String tenTSKT, String tenTSCT, String tenHangHoa, Pageable pageable) {
        try {
            return hangHoaThongSoRepo.search(tenTSKT, tenTSCT, tenHangHoa, pageable);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "search-hang-hoa-thong-so-error: " + ex);
            return null;
        }
    }

    @Override
    public Optional<HangHoaThongSo> save(HangHoaThongSo hangHoaThongSo) {
        try {
            return Optional.ofNullable(hangHoaThongSoRepo.save(hangHoaThongSo));
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-hang-hoa-thong-so-error: " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Boolean delete(Integer id) {
        try {
            return hangHoaThongSoRepo.delete(id) > 0 ? true : false;
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "delete-hang-hoa-thong-so-error: " + ex);
            return false;
        }
    }
}
