package com.tavi.tavi_mrs.service_impl.quyen;

import com.tavi.tavi_mrs.entities.quyen.Quyen;
import com.tavi.tavi_mrs.repository.quyen.QuyenRepo;
import com.tavi.tavi_mrs.service.quyen.QuyenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuyenServiceImpl implements QuyenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuyenServiceImpl.class);

    @Autowired
    private QuyenRepo quyenRepo;

    @Override
    public Optional<Quyen> findById(int id) {
        try {
            return quyenRepo.findById(id);
        } catch (Exception ex) {
            LOGGER.error("find-quyen-by-id-error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Page<Quyen> findAllToPage(Pageable pageable) {
        try {
            return quyenRepo.findAllToPage(pageable);
        } catch (Exception ex) {
            LOGGER.error("find-all-quyen-error : " + ex);
            return null;
        }
    }

    @Override
    public Optional<Quyen> save(Quyen quyen) {
        try {
            return Optional.ofNullable(quyenRepo.save(quyen));
        } catch (Exception ex) {
            LOGGER.error("save-quyen-error : " + ex);
            return Optional.empty();
        }
    }
}
