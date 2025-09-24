package com.tavi.tavi_mrs.service_impl.so_quy;

import com.tavi.tavi_mrs.entities.so_quy.PhieuChiNhapHangTraKhach;
import com.tavi.tavi_mrs.repository.so_quy.PhieuChiNhapHangTraKhachRepo;
import com.tavi.tavi_mrs.service.so_quy.PhieuChiNhapHangTraKhachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PhieuChiNhapHangTraKhachServiceImpl implements PhieuChiNhapHangTraKhachService {

    public static final java.util.logging.Logger LOGGER = Logger.getLogger(PhieuChiServiceImpl.class.getName());

    @Autowired
    PhieuChiNhapHangTraKhachRepo phieuChiNhapHangTraKhachRepo;

    @Override
    public Optional<PhieuChiNhapHangTraKhach> save(PhieuChiNhapHangTraKhach phieuChiNhapHangTraKhach) {
        try {
            return Optional.ofNullable(phieuChiNhapHangTraKhachRepo.save(phieuChiNhapHangTraKhach));
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-PhieuChiNhapHangTraKhach-error : " + ex);
            return Optional.empty();
        }
    }
}
