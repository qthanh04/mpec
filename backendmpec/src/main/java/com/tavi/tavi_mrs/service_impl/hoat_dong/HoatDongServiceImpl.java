package com.tavi.tavi_mrs.service_impl.hoat_dong;

import com.tavi.tavi_mrs.entities.hoat_dong.HoatDong;
import com.tavi.tavi_mrs.repository.hoat_dong.HoatDongRepo;
import com.tavi.tavi_mrs.service.hoat_dong.HoatDongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class HoatDongServiceImpl implements HoatDongService {

    private static final Logger LOGGER = Logger.getLogger(HoatDongServiceImpl.class.getName());

    @Autowired
    private HoatDongRepo hoatDongRepo;

    @Override
    public Page<HoatDong> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Page<HoatDong> search(String ten, Pageable pageable) {
        return null;
    }

    @Override
    public Optional<HoatDong> findById(int id) {
        try {
            return hoatDongRepo.findById(id);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-hoat-dong-by-id-error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<HoatDong> save(HoatDong hoatDong) {
        return Optional.empty();
    }

    @Override
    public Boolean deleted(int id) {
        return null;
    }
}
