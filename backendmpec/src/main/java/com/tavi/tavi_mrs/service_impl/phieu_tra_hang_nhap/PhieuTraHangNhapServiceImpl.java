package com.tavi.tavi_mrs.service_impl.phieu_tra_hang_nhap;

import com.tavi.tavi_mrs.entities.phieu_tra_hang_nhap.PhieuTraHangNhap;
import com.tavi.tavi_mrs.entities.phieu_tra_hang_nhap.PhieuTraHangNhapChiTiet;
import com.tavi.tavi_mrs.repository.phieu_tra_hang_nhap.PhieuTraHangNhapRepo;
import com.tavi.tavi_mrs.service.phieu_tra_hang_nhap.PhieuTraHangNhapService;
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
public class PhieuTraHangNhapServiceImpl implements PhieuTraHangNhapService {

    private Logger LOGGER = LoggerFactory.getLogger(PhieuTraHangNhapServiceImpl.class);

    @Autowired
    private PdfUtils pdfUtils;

    @Autowired
    private PhieuTraHangNhapRepo phieuTraHangNhapRepo;


    @Override
    public Page<PhieuTraHangNhap> findAll(Pageable pageable) {
        try {
            return phieuTraHangNhapRepo.findAll(pageable);
        } catch (Exception ex) {
            LOGGER.error("find-all-phieu-tra-hang-nhap-error : " + ex);
            return null;
        }
    }

    @Override
    public Page<PhieuTraHangNhap> findByNhaCungCapAndThoiGianAndTrangThai(String tenNhaCungCap, Date ngayDau, Date ngayCuoi, int trangThai, Pageable pageable) {
        try {
            return phieuTraHangNhapRepo.findByNhaCungCapAndThoiGianAndTrangThai(tenNhaCungCap, ngayDau, ngayCuoi, trangThai, pageable);
        } catch (Exception ex) {
            LOGGER.error("find-by-nha-cung-cap-anh-thoi-gian-error : " + ex);
            return null;
        }
    }

    @Override
    public Page<PhieuTraHangNhap> findByChiNhanhAndText(int chiNhanhId, String text) {
        return null;
    }

    @Override
    public Optional<PhieuTraHangNhap> findByIdAndXoa(int id, boolean xoa) {
        try {
            return phieuTraHangNhapRepo.findByIdAndXoa(id, xoa);
        } catch (Exception ex) {
            LOGGER.error("find-phieu-tra-hang-nhap-error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Page<PhieuTraHangNhap> findByIdNhaCungCap(int id, boolean xoa, Pageable pageable) {
        try {
            return phieuTraHangNhapRepo.findByIdNhaCungCap(id, xoa, pageable);
        } catch (Exception ex) {
            LOGGER.error("find-by-id-nha-cung-cap-error : " + ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<PhieuTraHangNhap> findByMa(String ma) {
        try {
            return phieuTraHangNhapRepo.findByMaPhieu(ma);
        } catch (Exception ex) {
            LOGGER.error("find-phieu-tra-hang-nhap-by-ma-error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public File createPdf(PhieuTraHangNhap phieuTraHangNhap, List<PhieuTraHangNhapChiTiet> phieuTraHangNhapChiTietList) {
        try {
            return pdfUtils.createPhieuTraHangNhapPDF(phieuTraHangNhap, phieuTraHangNhapChiTietList);
        } catch (Exception ex) {
            LOGGER.error("create-pdf-phieu-tra-hang-nhap-err : " + ex);
            return null;
        }
    }

    @Override
    public Optional<PhieuTraHangNhap> save(PhieuTraHangNhap phieuTraHangNhap) {
        try {
            phieuTraHangNhap.setXoa(false);
            String code = "";
            while (true) {
                code = "PTK-" + Random.randomCode();
                if (!phieuTraHangNhapRepo.findByMaPhieu(code).isPresent()) {
                    break;
                }
            }
            phieuTraHangNhap.setMaPhieu(code);
            return Optional.ofNullable(phieuTraHangNhapRepo.save(phieuTraHangNhap));
        } catch (Exception ex) {
            LOGGER.error("save-phieu-tra-hang-nhap-error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Boolean deleted(Integer id) {
        try {
            return phieuTraHangNhapRepo.deleted(id) > 0 ? true : false;
        } catch (Exception ex) {
            LOGGER.error("delete-phieu-tra-hang-nhap-error : " + ex);
            return false;
        }
    }

    @Override
    public Boolean setTrangThai(int id, int trangThai) {
        try {
            return phieuTraHangNhapRepo.setTrangThai(id, trangThai) > 0 ? true : false;
        } catch (Exception ex) {
            LOGGER.error("thay-doi-trang-thai-error : " + ex);
            return false;
        }
    }

    @Override
    public boolean updateTienNhaCungCapTra(Double tienNhaCungCapTra, int phieuTraHangNhapId, Double conNo) {
        try {
            return phieuTraHangNhapRepo.updateTienNhaCungCapTra(tienNhaCungCapTra, phieuTraHangNhapId, conNo) > 0 ? true : false;
        }catch (Exception ex) {
            LOGGER.error("updateTienNhaCungCapTra-error: " + ex);
            return false;
        }
    }
}
