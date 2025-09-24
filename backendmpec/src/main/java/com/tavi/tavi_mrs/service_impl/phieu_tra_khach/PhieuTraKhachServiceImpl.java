package com.tavi.tavi_mrs.service_impl.phieu_tra_khach;

import com.tavi.tavi_mrs.entities.phieu_tra_khach.PhieuTraKhach;
import com.tavi.tavi_mrs.entities.phieu_tra_khach.PhieuTraKhachChiTiet;
import com.tavi.tavi_mrs.repository.phieu_tra_khach.PhieuTraKhachRepo;
import com.tavi.tavi_mrs.service.phieu_tra_khach.PhieuTraKhachService;
import com.tavi.tavi_mrs.service_impl.phieu_tra_hang_nhap.PhieuTraHangNhapServiceImpl;
import com.tavi.tavi_mrs.utils.PdfUtils;
import com.tavi.tavi_mrs.utils.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PhieuTraKhachServiceImpl implements PhieuTraKhachService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhieuTraHangNhapServiceImpl.class);

    @Autowired
    private PhieuTraKhachRepo phieuTraKhachRepo;

    @Autowired
    private PdfUtils pdfUtils;

    @Override
    public Page<PhieuTraKhach> findAll(Pageable pageable) {
        try {
            return phieuTraKhachRepo.findAll(pageable);
        } catch (Exception ex) {
            LOGGER.error("find-all-phieu-tra-khach-error : " + ex);
            return null;
        }
    }

    @Override
    public Optional<PhieuTraKhach> findByIdAndXoa(int id, boolean xoa) {
        try {
            return phieuTraKhachRepo.findByIdAndXoa(id, xoa);
        } catch (Exception ex) {
            LOGGER.error("find-phieu-tra-khach-by-id-error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Page<PhieuTraKhach> findByNguoiDung(int nguoiDungId, Pageable pageable) {
        try {
            return phieuTraKhachRepo.findByNguoiDung(nguoiDungId, pageable);
        } catch (Exception ex) {
            LOGGER.error("find-phieu-tra-khach-by-nguoi-dung-error : " + ex);
            return null;
        }
    }

    @Override
    public Page<PhieuTraKhach> findByThoiGianAndTrangThai(Date ngayDau, Date ngayCuoi, int trangThai, Pageable pageable) {
        try {
            return phieuTraKhachRepo.findByThoiGianAndTrangThai(ngayDau, ngayCuoi, trangThai, pageable);
        } catch (Exception ex) {
            LOGGER.error("find-phieu-tra-khach-by-nguoi-dung-error : " + ex);
            return null;
        }
    }

    @Override
    public File createPDF(PhieuTraKhach phieuTraKhach, List<PhieuTraKhachChiTiet> phieuTraKhachChiTietList) {
        try {
            return pdfUtils.createPhieuTraKhachPDF(phieuTraKhach, phieuTraKhachChiTietList);
        } catch (Exception ex) {
            LOGGER.error("create phieu tra hang pdf error : " + ex);
            return null;
        }
    }

    @Override
    public Optional<PhieuTraKhach> findByMa(String ma) {
        try {
            return phieuTraKhachRepo.findByMa(ma);
        } catch (Exception ex) {
            LOGGER.error("find-phieu-tra-khach-by-ma-error : " + ex);
            return null;
        }
    }

    @Override
    public Boolean deleted(Integer id) {
        try {
            return phieuTraKhachRepo.deleted(id) > 0 ? true : false;
        } catch (Exception ex) {
            LOGGER.error("delete-phieu-tra-khach-error : " + ex);
            return false;
        }
    }

    @Override
    public Boolean setTrangThai(int id, int trangThai) {
        try {
            return phieuTraKhachRepo.setTrangThai(id, trangThai) > 0 ? true : false;
        } catch (Exception ex) {
            LOGGER.error("set-trang-thai-phieu-tra-khach-error : " + ex);
            return false;
        }
    }

    @Override
    public Optional<PhieuTraKhach> save(PhieuTraKhach phieuTraKhach) {
        try {
            phieuTraKhach.setXoa(false);
            String code = "";
            while (true) {
                code = "PTK-" + Random.randomCode();
                if (!phieuTraKhachRepo.findByMa(code).isPresent()) {
                    break;
                }
            }
            phieuTraKhach.setMa(code);
            return Optional.ofNullable(phieuTraKhachRepo.save(phieuTraKhach));
        } catch (Exception ex) {
            LOGGER.error("save-phieu-tra-khach-error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public boolean updateTienTraKhach(Double tienTraKhach, int phieuTraKhachId, Double conNo) {
        try {
            return phieuTraKhachRepo.updateTienTraKhach(tienTraKhach, phieuTraKhachId, conNo) > 0 ? true : false;
        }catch (Exception ex) {
            LOGGER.error("update-tien-khach-tra-eror");
            return false;
        }
    }
}
