package com.tavi.tavi_mrs.service_impl.so_quy;

import com.tavi.tavi_mrs.entities.so_quy.PhieuThuHoaDon;
import com.tavi.tavi_mrs.repository.so_quy.PhieuThuHoaDonRepo;
import com.tavi.tavi_mrs.service.so_quy.PhieuThuHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PhieuThuHoaDonServiceImpl implements PhieuThuHoaDonService {

    public static final java.util.logging.Logger LOGGER = Logger.getLogger(PhieuChiServiceImpl.class.getName());

    @Autowired
    PhieuThuHoaDonRepo phieuThuHoaDonRepo;

    @Override
    public Optional<PhieuThuHoaDon> save(PhieuThuHoaDon phieuThuHoaDon) {
        try {
            return Optional.ofNullable(phieuThuHoaDonRepo.save(phieuThuHoaDon));
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-PhieuThuHoaDon-error");
            return Optional.empty();
        }
    }

    @Override
    public Optional<PhieuThuHoaDon> findById(int id) {
        try {
            return phieuThuHoaDonRepo.findById(id);
        }catch (Exception ex){
            LOGGER.log(Level.SEVERE, "find-by-id-eror");
            return Optional.empty();
        }
    }
}
