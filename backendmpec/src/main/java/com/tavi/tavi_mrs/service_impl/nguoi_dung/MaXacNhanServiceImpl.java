package com.tavi.tavi_mrs.service_impl.nguoi_dung;

import com.tavi.tavi_mrs.entities.nguoi_dung.MaXacNhan;
import com.tavi.tavi_mrs.repository.nguoi_dung.MaXacNhanRepo;
import com.tavi.tavi_mrs.service.nguoi_dung.MaXacNhanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class MaXacNhanServiceImpl implements MaXacNhanService {

    @Autowired
    MaXacNhanRepo maXacNhanRepo;

    private static final Logger LOGGER = LoggerFactory.getLogger(MaXacNhanServiceImpl.class);

    @Override
    public Optional<MaXacNhan> save(MaXacNhan maXacNhan) {
        try {
            return Optional.ofNullable(maXacNhanRepo.save(maXacNhan));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<MaXacNhan> findByNguoiDungAndMaToken(int nguoiDungId, String maToken) {
        try {
            return maXacNhanRepo.findByNguoiDungAndMaToken(nguoiDungId, maToken);
        } catch (Exception ex) {
            LOGGER.error("find by id nguoi dung and ma error {0}", ex);
            return null;
        }
    }

    @Override
    public Optional<MaXacNhan> setTrangThai(int nguoiDungId, String token, int trangThai) {
        try {
            return maXacNhanRepo.setTrangThai(nguoiDungId, token, trangThai);
        } catch (Exception ex) {
            LOGGER.error("find by id nguoi dung and ma error {0}", ex);
            return null;
        }
    }

    @Override
    public String validateToken(int nguoiDungId, String maToken) {
        try {
            Optional<MaXacNhan> maXacNhanOptional = maXacNhanRepo.findByNguoiDungAndMaToken(nguoiDungId, maToken);
            if (!maXacNhanOptional.isPresent()) {
                return "Bad Request";
            }
            MaXacNhan maXacNhan = maXacNhanOptional.get();
            if (maXacNhan.getTrangThai() == 0) {
                return "Invalid Token";
            }
            Date now = new Date();
            if (maXacNhan.getThoiGianBatDau().after(now) || maXacNhan.getThoiGianKetThuc().before(now)) {
                return "Invalid Token";
            }
            return "Valid Token";
        } catch (Exception ex) {
            LOGGER.error("find by id nguoi dung and ma error {0}", ex);
            return "Server Internal Error";
        }
    }
}