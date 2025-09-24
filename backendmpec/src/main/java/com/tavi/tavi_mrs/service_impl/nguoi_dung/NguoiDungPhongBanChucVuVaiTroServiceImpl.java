package com.tavi.tavi_mrs.service_impl.nguoi_dung;

import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDungPhongBanChucVuVaiTro;
import com.tavi.tavi_mrs.repository.nguoi_dung.NguoiDungPhongBanChucVuVaiTroRepo;
import com.tavi.tavi_mrs.service.nguoi_dung.NguoiDungPhongBanChucVuVaiTroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NguoiDungPhongBanChucVuVaiTroServiceImpl implements NguoiDungPhongBanChucVuVaiTroService {

    @Autowired
    private NguoiDungPhongBanChucVuVaiTroRepo nguoiDungPhongBanChucVuVaiTroRepo;

    private static final Logger LOGGER = LoggerFactory.getLogger(NguoiDungPhongBanChucVuVaiTroServiceImpl.class);

    @Override
    public Page<NguoiDungPhongBanChucVuVaiTro> findNguoiDungByPhongBanAndText(int chiNhanhId, String text, Pageable pageable) {
        try {
            return nguoiDungPhongBanChucVuVaiTroRepo.findNguoiDungByPhongBanAndText(chiNhanhId, text, pageable);
        } catch (Exception ex) {
            LOGGER.error("findNguoiDungByPhongBanAndText error", ex);
            ex.printStackTrace();
            return null;
        }
    }


    @Override
    public Optional<NguoiDungPhongBanChucVuVaiTro> save(NguoiDungPhongBanChucVuVaiTro nguoiDungPhongBanChucVuVaiTro) {
        try {
            return Optional.of(nguoiDungPhongBanChucVuVaiTroRepo.save(nguoiDungPhongBanChucVuVaiTro));
        } catch (Exception ex) {
            LOGGER.error("save NguoiDungPhongBanChucVuVaiTro error", ex);
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public int setPhongBanChucVuVaiTro(int chucVuId, int vaiTroId, int newPhongBanId, int nguoiDungId, int phongBanId) {
        try {
            return nguoiDungPhongBanChucVuVaiTroRepo.setPhongBanChucVuVaiTro(chucVuId, vaiTroId, newPhongBanId, nguoiDungId, phongBanId);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.error("setPhongBanChucVuVaiTro error {0}", ex);
            return 0;
        }
    }

    @Override
    public Page<NguoiDungPhongBanChucVuVaiTro> findNguoiDungFullInfor(int chiNhanhId, String text, Pageable pageable) {
        try {
            return nguoiDungPhongBanChucVuVaiTroRepo.findNguoiDungFullInfor(chiNhanhId, text, pageable);
        } catch (Exception ex) {
            LOGGER.error("findNguoiDungByPhongBanAndText error", ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<NguoiDungPhongBanChucVuVaiTro> findByTaiKhoan(String taiKhoan) {
        try {
            return nguoiDungPhongBanChucVuVaiTroRepo.findByTaiKhoan(taiKhoan);
        } catch (Exception ex) {
            LOGGER.error("findNguoiDungByPhongBanAndText error", ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<NguoiDungPhongBanChucVuVaiTro> findByNguoiDungId(int nguoiDungID) {
        try {
            return nguoiDungPhongBanChucVuVaiTroRepo.findByNguoiDungId(nguoiDungID);
        } catch (Exception ex) {
            LOGGER.error("findByNguoiDungId error", ex);
            return Optional.empty();
        }
    }
}
