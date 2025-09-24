package com.tavi.tavi_mrs.service_impl.cong_ty;

import com.tavi.tavi_mrs.entities.cong_ty.TongCongTy;
import com.tavi.tavi_mrs.repository.cong_ty.TongCongTyRepo;
import com.tavi.tavi_mrs.service.cong_ty.TongCongTyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

@Service
public class TongCongTyServiceImpl implements TongCongTyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TongCongTyServiceImpl.class);

    @Autowired
    private TongCongTyRepo tongCongTyRepo;

    @Override
    public Optional<TongCongTy> save(TongCongTy tongCongTy) {
        try {
            return Optional.of(tongCongTyRepo.save(tongCongTy));
        } catch (Exception ex) {
            LOGGER.error("save-tongCongTy-error", ex);
            return null;
        }
    }

    @Override
    public Boolean deleted(Integer tongCongTy) {
        try {
            return tongCongTyRepo.deleted(tongCongTy) > 0 ? true : false;
        } catch (Exception ex) {
            LOGGER.error("deleted-tongCongTy-error", ex);
            return false;
        }
    }

    @Override
    public List<TongCongTy> findAll() {
        try {
            return tongCongTyRepo.findAllCongTy(null);
        } catch (Exception ex) {
            LOGGER.error("findAll-tongCongTy-error", ex);
            return null;
        }
    }

    @Override
    public Page<TongCongTy> findAll(Pageable pageable) {
        try {
            return tongCongTyRepo.findAllToPageCongTy(pageable);
        } catch (Exception ex) {
            LOGGER.error("find-all-tong-cong-ty-error: " + ex);
            return null;
        }
    }

    @Override
    public Optional<TongCongTy> findById(Integer tongCongTy) {
        try {
            return tongCongTyRepo.findByIdAndXoa(tongCongTy);
        } catch (Exception ex) {
            LOGGER.error("findById-tongCongTy", ex);
            return null;
        }

    }


}
