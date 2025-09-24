package com.tavi.tavi_mrs.service_impl.so_quy;

import com.tavi.tavi_mrs.entities.ca_lam_viec.NguoiDungCaLamViec;
import com.tavi.tavi_mrs.entities.hoa_don.HoaDon;
import com.tavi.tavi_mrs.entities.phieu_tra_hang_nhap.PhieuTraHangNhap;
import com.tavi.tavi_mrs.entities.so_quy.PhieuThu;
import com.tavi.tavi_mrs.entities.so_quy.PhieuThuHoaDon;
import com.tavi.tavi_mrs.repository.so_quy.PhieuThuRepo;
import com.tavi.tavi_mrs.service.phieu_tra_hang_nhap.PhieuTraHangNhapService;
import com.tavi.tavi_mrs.service.so_quy.PhieuThuService;
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
public class PhieuThuServiceImpl implements PhieuThuService {

    public static final java.util.logging.Logger LOGGER = Logger.getLogger(PhieuChiServiceImpl.class.getName());

    @Autowired
    PhieuThuRepo phieuThuRepo;

    @Autowired
    private PdfUtils pdfUtils;

    @Override
    public Optional<PhieuThu> findByIdAndXoa(int id, boolean xoa) {
        try {
            return phieuThuRepo.findByIdAndXoa(id, false);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-by-id-error:" + ex);
            return Optional.empty();
        }
    }

    @Override
    public Page<PhieuThu> findAllToPage(Pageable pageable) {
        try {
            return phieuThuRepo.findAllToPage(pageable);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-to-page-error:" + ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean setTrangThai(int id, int trangThai) {
        try {
            return phieuThuRepo.setTrangThai(id, trangThai) > 0 ;
        }catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.log(Level.SEVERE, "set-trang-thai-error:" + ex);
            return false;
        }
    }

    @Override
    public Page<PhieuThu> findByMaPhieuThuAndThoiGianAndTrangThai(Integer chiNhanhId, String maPhieuThu, String tenLoaiThu, String tenNhanVien, Date ngayDau, Date ngayCuoi, int trangThai, Pageable pageable) {
        try {
            return phieuThuRepo.findByMaPhieuThuAndThoiGianAndTrangThai(chiNhanhId, maPhieuThu, tenLoaiThu, tenNhanVien, ngayDau, ngayCuoi, trangThai, pageable);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-by-ma-phieu-thu-and-trang-thai-and-thoi-gian-error: " + ex);
            ex.printStackTrace();
            return null;
        }
    }


    @Override
    public Optional<PhieuThu> save(PhieuThu phieuThu) {
        try {
            LocalDateTime now = LocalDateTime.now();
            Date out = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
            phieuThu.setThoiGian(out);
            phieuThu.setXoa(false);
            return Optional.ofNullable(phieuThuRepo.save(phieuThu));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-phieu-thu-error" + ex);
            return Optional.empty();
        }
    }

    @Override
    public Page<PhieuThu> findByChiNhanhAndText(int chinhanhId, String text, Pageable pageable) {
        try {
            return phieuThuRepo.findByChiNhanhAndText(chinhanhId, text, pageable);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "findByChiNhanhAndText error: " + ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean delete(Integer id) {
        try {
            return phieuThuRepo.delete(id) > 0 ? true : false;
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "delete-phieu-thu-error: " + ex);
            return false;
        }
    }

    @Override
    public List<PhieuThu> findListPhieuThu(List<Integer> listPhieuthuId) {
        try {
            List<PhieuThu> phieuthu = new ArrayList<>();
            for (Integer id : listPhieuthuId) {
                Optional<PhieuThu> phieuThuOptional = phieuThuRepo.findByIdAndXoa(id,false);
                if (phieuThuOptional.isPresent()) {
                    phieuthu.add(phieuThuOptional.get());
                }
            }
            return phieuthu;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-list-hieu-thu-error : " + ex);
            return null;
        }
    }

    @Override
    public File createPdf(PhieuThu phieuThu, HoaDon hoaDon) {
        try {
            return pdfUtils.createPhieuThuPDF(phieuThu, hoaDon);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "creatPdf: " + ex);
            return null;
        }
    }

    @Override
    public File createPhieuTraHangNhapPdf(PhieuThu phieuThu, PhieuTraHangNhap phieuTraHangNhap) {
        try {
            return pdfUtils.createPhieuThuPhieuTraHangNhapPDF(phieuThu, phieuTraHangNhap);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "createPhieuTraHangNhapPdf : " + ex);
            return null;
        }
    }
}
