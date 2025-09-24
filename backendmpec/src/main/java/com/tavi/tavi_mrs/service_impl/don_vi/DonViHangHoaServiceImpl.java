package com.tavi.tavi_mrs.service_impl.don_vi;

import com.tavi.tavi_mrs.entities.don_vi.DonVi;
import com.tavi.tavi_mrs.entities.don_vi.DonViHangHoa;
import com.tavi.tavi_mrs.repository.don_vi.DonViHangHoaRepo;
import com.tavi.tavi_mrs.service.don_vi.DonViHangHoaService;
import com.tavi.tavi_mrs.service_impl.ca_lam_viec.CaLamViecServiceImpl;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DonViHangHoaServiceImpl implements DonViHangHoaService {

    private static final Logger LOGGER = Logger.getLogger(DonViHangHoaServiceImpl.class.getName());

    @Autowired
    private DonViHangHoaRepo donViHangHoaRepo;

    @Override
    public List<DonViHangHoa> findAll() {
        try {
            return donViHangHoaRepo.findAll();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-don-vi-hang-hoa-error : " + ex);
            return null;
        }
    }

    @Override
    public Optional<DonViHangHoa> findByIdAndXoa(int id, boolean xoa) {
        try {
            return donViHangHoaRepo.findByIdAndXoa(id, xoa);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-don-vi-hang-hoa-by-id-error :" + ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<DonViHangHoa> findDonViCoBanByHangHoaId(int hangHoaId) {
        Optional donviHangHoaOptional = null;
        try {
            donviHangHoaOptional = donViHangHoaRepo.findDonViCoBanByHangHoaId(hangHoaId);
        } catch (Exception ex) {
            donviHangHoaOptional = donViHangHoaRepo.findDistinctByHangHoaId(hangHoaId);
        }
        return donviHangHoaOptional;
    }

    @Override
    public Optional<DonViHangHoa> save(DonViHangHoa donViHangHoa) {
        try {
            return Optional.ofNullable(donViHangHoaRepo.save(donViHangHoa));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-don-vi-hang-hoa-error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Page<DonViHangHoa> findByHangHoaId(int id, Pageable pageable) {
        try {
            return donViHangHoaRepo.findByHangHoaId(id, pageable);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-hang-hoa-by-id-error :" + ex);
            return null;
        }
    }

    @Override
    public Optional<DonViHangHoa> findByTyLeAndDonViAndHangHoa(int donViId, int hangHoaId) {
        try {
            return donViHangHoaRepo.findByTyLeAndDonViAndHangHoa(donViId, hangHoaId);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-ty-le-and-don-vi-hang-hoa-error :" + ex);
            return Optional.empty();
        }
    }

    @Override
    public Boolean findByTyLeAndDonViHangHoa(int donViId, int hangHoaId) {
        try {
            Optional<DonViHangHoa> donViHangHoaOptional = donViHangHoaRepo.findByTyLeAndDonViAndHangHoa(donViId,hangHoaId);
            if (donViHangHoaOptional.get() != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }
}
