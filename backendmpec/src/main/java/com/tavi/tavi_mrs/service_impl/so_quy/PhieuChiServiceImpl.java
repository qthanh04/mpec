package com.tavi.tavi_mrs.service_impl.so_quy;

import com.tavi.tavi_mrs.entities.hoa_don.HoaDon;
import com.tavi.tavi_mrs.entities.phieu_nhap_hang.PhieuNhapHang;
import com.tavi.tavi_mrs.entities.so_quy.PhieuChi;
import com.tavi.tavi_mrs.entities.so_quy.PhieuThu;
import com.tavi.tavi_mrs.repository.so_quy.PhieuChiRepo;
import com.tavi.tavi_mrs.service.so_quy.PhieuChiService;
import com.tavi.tavi_mrs.utils.PdfUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PhieuChiServiceImpl implements PhieuChiService {

    public static final java.util.logging.Logger LOGGER = Logger.getLogger(PhieuChiServiceImpl.class.getName());

    @Autowired
    PhieuChiRepo phieuChiRepo;

    @Autowired
    PdfUtils pdfUtils;

    @Override
    public Optional<PhieuChi> findByIdAndXoa(int id, boolean xoa) {
        try {
            return phieuChiRepo.findByIdAndXoa(id, false);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-by-id-error:" + ex);
            return Optional.empty();
        }
    }

    @Override
    public Page<PhieuChi> findAllToPage(Pageable pageable) {
        try {
            return phieuChiRepo.findAllToPage(pageable);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-to-page-error:" + ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean setTrangThai(int id, int trangThai) {
        try {
            return phieuChiRepo.setTrangThai(id, trangThai) > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.log(Level.SEVERE, "set-trang-thai-error:" + ex);
            return false;
        }
    }

    @Override
    public Page<PhieuChi> findByMaPhieuChiAndThoiGianAndTrangThai(Integer chiNhanhId, String maPhieuChi, String tenLoaiChi, String tenNhanVien, Date ngayDau, Date ngayCuoi, int trangThai, Pageable pageable) {
        try {
            return phieuChiRepo.findByMaPhieuChiAndThoiGianAndTrangThai(chiNhanhId, maPhieuChi, tenLoaiChi, tenNhanVien, ngayDau, ngayCuoi, trangThai, pageable);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-by-ma-phieu-chi-and-trang-thai-and-thoi-gian-error: " + ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<PhieuChi> save(PhieuChi phieuChi) {
        try {
            LocalDateTime now = LocalDateTime.now();
            Date out = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
            phieuChi.setThoiGian(out);
            phieuChi.setXoa(false);
            return Optional.ofNullable(phieuChiRepo.save(phieuChi));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-phieu-chi-error" + ex);
            return Optional.empty();
        }
    }

    @Override
    public Page<PhieuChi> findByChiNhanhAndText(int chinhanhId, String text, Pageable pageable) {
        try {
            return phieuChiRepo.findByChiNhanhAndText(chinhanhId, text, pageable);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "findByChiNhanhAndText error: " + ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean delete(Integer id) {
        try {
            return phieuChiRepo.delete(id) > 0 ? true : false;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "delete-phieu-chi-error: " + ex);
            return false;
        }
    }

    @Override
    public List<PhieuChi> findListPhieuChi(List<Integer> listPhieuChiId) {
        try {
            List<PhieuChi> phieuchi = new ArrayList<>();
            for (Integer id : listPhieuChiId) {
                Optional<PhieuChi> phieuChiOptional = phieuChiRepo.findByIdAndXoa(id,false);
                if (phieuChiOptional.isPresent()) {
                    phieuchi.add(phieuChiOptional.get());
                }
            }
            return phieuchi;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-list-hieu-thu-error : " + ex);
            return null;
        }
    }

    @Override
    public File createPhieuChiPhieuNhapHangPdf(PhieuChi phieuChi, PhieuNhapHang phieuNhapHang) {
        try {
            return pdfUtils.createPhieuChiPhieuNHapHangPDF(phieuChi, phieuNhapHang);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "createPhieuChiPhieuNhapHangPdf : " + ex);
            return null;
        }
    }

    @Override
    public File createPhieuChiPhieuTraKhachPdf(PhieuChi phieuChi, HoaDon hoaDon) {
        try {
            return pdfUtils.createPhieuChiPhieuTraKhachPDF(phieuChi, hoaDon);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "createPhieuChiPhieuTraKhachPdf : " + ex);
            return null;
        }
    }
}
