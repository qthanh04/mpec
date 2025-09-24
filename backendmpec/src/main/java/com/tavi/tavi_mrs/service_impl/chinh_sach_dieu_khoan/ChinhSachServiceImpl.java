package com.tavi.tavi_mrs.service_impl.chinh_sach_dieu_khoan;

import com.tavi.tavi_mrs.entities.chinh_sach_dieu_khoan.ChinhSach;
import com.tavi.tavi_mrs.entities.chinh_sach_dieu_khoan.DieuKhoan;
import com.tavi.tavi_mrs.repository.chinh_sach_dieu_khoan.ChinhSachRepo;
import com.tavi.tavi_mrs.service.chinh_sach_dieu_khoan.ChinhSachService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChinhSachServiceImpl implements ChinhSachService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChinhSachServiceImpl.class);

    @Autowired
    private ChinhSachRepo chinhSachRepo;

    @Override
    public boolean delete(Integer id) {
        try {
            return chinhSachRepo.delete(id) > 0;
        } catch (Exception ex) {
            LOGGER.error("delete-ChinhSach-error" + ex);
            ex.printStackTrace();
            return false;
        }
    }


    @Override
    public Optional<ChinhSach> findById(int id) {
        try {
            return chinhSachRepo.findById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.error("find-by-id-error :" + ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<ChinhSach> save(ChinhSach chinhSach) {
        try {
            return Optional.of(chinhSachRepo.save(chinhSach));
        } catch (Exception ex) {
            LOGGER.error("save chinh sach error", ex);
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<ChinhSach> findAll() {
        try {
            return chinhSachRepo.findAll();
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.error("findALLChinhSach error", ex);
            return null;
        }
    }

    @Override
    public Page<ChinhSach> findAllToPage(Pageable pageable) {
        try {
            return chinhSachRepo.findAllToPage(pageable);
        }catch (Exception ex) {
            LOGGER.error("find-all-chinh-sach-error: " + ex);
            return null;
        }
    }
}
