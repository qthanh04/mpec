package com.tavi.tavi_mrs.service_impl.don_vi;

import com.tavi.tavi_mrs.entities.don_vi.DonVi;
import com.tavi.tavi_mrs.entities.hang_hoa.ThuongHieu;
import com.tavi.tavi_mrs.repository.don_vi.DonViRepo;
import com.tavi.tavi_mrs.service.don_vi.DonViService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonViServiceImpl implements DonViService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DonViServiceImpl.class);

    @Autowired
    private DonViRepo donViRepo;

    @Override
    public Page<DonVi> findAll(Pageable pageable) {
        try {
            return donViRepo.findAll(pageable);
        } catch (Exception ex) {
            LOGGER.error("find-all-don-vi-error : " + ex);
            return null;
        }
    }

    @Override
    public Page<DonVi> search(String tenDonVi, Pageable pageable) {
        try {
            return donViRepo.search(tenDonVi, pageable);
        } catch (Exception ex) {
            LOGGER.error("search-don-vi-error : " + ex);
            return null;
        }
    }

    @Override
    public Optional<DonVi> findByIdAndXoa(int id, boolean xoa) {
        try {
            return donViRepo.findByIdAndXoa(id, xoa);
        } catch (Exception ex) {
            LOGGER.error("find-don-vi-by-id-and-xoa : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public DonVi findById(int id) {
        try {
            return donViRepo.findById(id);
        } catch (Exception ex) {
            LOGGER.error("find-don-vi-by-id-and-xoa : " + ex);
            return null;
        }
    }

    @Override
    public Optional<DonVi> save(DonVi donVi) {
        try {
            return Optional.ofNullable(donViRepo.save(donVi));
        } catch (Exception ex) {
            LOGGER.error("save-don-vi-error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Boolean deleted(int id) {
        try {
            return donViRepo.deleted(id) > 0 ? true : false;
        } catch (Exception ex) {
            LOGGER.error("delete-don-vi-error : " + ex);
            return false;
        }
    }

    @Override
    public Boolean findByTenDonVi(String tenDonVi) {
        try {
            Optional<DonVi> donVi = donViRepo.findByTenDonVi(tenDonVi);
            if (donVi.get() != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public DonVi findByTenDonViExcel(String tenDonVi) {
        try {
            Optional<DonVi> donVi = donViRepo.findByTenDonVi(tenDonVi);
            if (donVi.get() != null) {
                return donVi.get();
            }
        } catch (Exception ex) {
            DonVi donVi= new DonVi();
            donVi.setTenDonVi(tenDonVi);
            donVi.setXoa(false);
            return donViRepo.save(donVi);
        }
        return null;
    }
}
