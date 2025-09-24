package com.tavi.tavi_mrs.service_impl.quyen;

import com.tavi.tavi_mrs.entities.quyen.NhomQuyen;
import com.tavi.tavi_mrs.repository.hang_hoa.NhomHangRepo;
import com.tavi.tavi_mrs.repository.quyen.NhomQuyenRepo;
import com.tavi.tavi_mrs.service.quyen.NhomQuyenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NhomQuyenServiceImpl implements NhomQuyenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NhomQuyenServiceImpl.class);

    @Autowired
    private NhomQuyenRepo nhomQuyenRepo;

    @Override
    public Optional<NhomQuyen> findById(int id) {
        try {
            return nhomQuyenRepo.findById(id);
        } catch (Exception ex) {
            LOGGER.error("find-nhom-quyen-by-id : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Page<NhomQuyen> findAllToPage(Pageable pageable) {
        try {
            return nhomQuyenRepo.findAllToPage(pageable);
        } catch (Exception ex) {
            LOGGER.error("find-all-nhom-quyen-error : " + ex);
            return null;
        }
    }

    @Override
    public Optional<NhomQuyen> save(NhomQuyen nhomQuyen) {
        try {
            return Optional.ofNullable(nhomQuyenRepo.save(nhomQuyen));
        } catch (Exception ex) {
            LOGGER.error("save-nhom-quyen-error : " + ex);
            return Optional.empty();
        }
    }
}
