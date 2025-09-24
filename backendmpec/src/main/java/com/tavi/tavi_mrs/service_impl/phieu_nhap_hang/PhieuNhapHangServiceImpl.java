package com.tavi.tavi_mrs.service_impl.phieu_nhap_hang;

import com.tavi.tavi_mrs.entities.nha_cung_cap.NhaCungCapDto;
import com.tavi.tavi_mrs.entities.phieu_nhap_hang.PhieuNhapHang;
import com.tavi.tavi_mrs.entities.phieu_nhap_hang.PhieuNhapHangChiTiet;
import com.tavi.tavi_mrs.repository.phieu_nhap_hang.PhieuNhapHangRepo;
import com.tavi.tavi_mrs.service.phieu_nhap_hang.PhieuNhapHangService;
import com.tavi.tavi_mrs.utils.PdfUtils;
import com.tavi.tavi_mrs.utils.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

@Service
public class PhieuNhapHangServiceImpl implements PhieuNhapHangService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhieuNhapHangServiceImpl.class);

    @Autowired
    private PdfUtils pdfUtils;

    @Autowired
    private PhieuNhapHangRepo phieuNhapHangRepo;

    @Override
    public Optional<PhieuNhapHang> findById(int id) {
        try {
            return phieuNhapHangRepo.findById(id);
        } catch (Exception ex) {
            LOGGER.error("find by id error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Page<PhieuNhapHang> findAll(Pageable pageable) {
        try {
            return phieuNhapHangRepo.findAll(pageable);
        } catch (Exception ex) {
            LOGGER.error("find all phieu nhap hang error : " + ex);
            return null;
        }
    }

    @Override
    public Optional<PhieuNhapHang> findByMaPhieu(String ma) {
        try {
            return phieuNhapHangRepo.findByMaPhieu(ma);
        } catch (Exception ex) {
            LOGGER.error("find phieu nhap hang by ma error : " + ex);
            return null;
        }
    }

    @Override
    public Optional<PhieuNhapHang> save(PhieuNhapHang phieuNhapHang) {
        try {
            phieuNhapHang.setXoa(false);
            String code = "";
            while (true) {
                code = "PN-" + Random.randomCode();
                if (!phieuNhapHangRepo.findByMaPhieu(code).isPresent()) {
                    break;
                }
            }
            phieuNhapHang.setMaPhieu(code);
            return Optional.ofNullable(phieuNhapHangRepo.save(phieuNhapHang));
        } catch (Exception ex) {
            LOGGER.error("save phieu nhap hang error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Page<PhieuNhapHang> findByIdNhaCungCap(int id, boolean xoa, Pageable pageable) {
        try {
            return phieuNhapHangRepo.findByIdNhaCungCap(id, xoa, pageable);
        }catch (Exception ex){
            LOGGER.error("findByIdNhaCungCap error :" + ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<PhieuNhapHang> findByNhaCungCapAndThoiGianAndMaPhieu(String maPhieu, Date ngayDau, Date ngayCuoi, int nhaCungCapId,int trangThai, Pageable pageable) {
        try {
            return phieuNhapHangRepo.findByNhaCungCapAndThoiGianAndMaPhieu(maPhieu, ngayDau, ngayCuoi, nhaCungCapId,trangThai, pageable);
        } catch (Exception ex) {
            LOGGER.error("findByNhaCungCapAndThoiGianAndMaPhieu error : " + ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateTienTraNhaCungCap(Double tienTraNhaCungCap, int phieuNhapHangId, Double conNo) {
        try {
            return phieuNhapHangRepo.updateTienTraNhaCungCap(tienTraNhaCungCap, phieuNhapHangId, conNo) > 0 ? true : false;
        }catch (Exception ex) {
            LOGGER.error("updateTienTraNhaCungCap error :" + ex);
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public File createPdf(PhieuNhapHang phieuNhapHang, List<PhieuNhapHangChiTiet> phieuNhapHangChiTietList) {
        try {
            return pdfUtils.createPhieuNhapPDF(phieuNhapHang, phieuNhapHangChiTietList);
        } catch (Exception e) {
            LOGGER.error("PDF-err : " + e);
            return null;
        }
    }

    @Override
    public List<PhieuNhapHang> findListHoaDon(List<Integer> listHoaDonId) {
        try {
            List<PhieuNhapHang> phieuNhapHangList = new ArrayList<>();
            for (Integer id : listHoaDonId) {
                Optional<PhieuNhapHang> phieuNhapHangOptional = phieuNhapHangRepo.findById(id);
                if (phieuNhapHangOptional.isPresent()) {
                    phieuNhapHangList.add(phieuNhapHangOptional.get());
                }
            }
            return phieuNhapHangList;
        } catch (Exception ex) {
            LOGGER.error("find-list-phieu-nhap-hang-error : " + ex);
            return null;
        }
    }

    @Override
    public Boolean setTrangThai(int id, Integer trangThai) {
        try {
            return phieuNhapHangRepo.setTrangThai(id, trangThai) > 0 ? true : false;
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.error("set trang thai error : " + ex);
            return false;
        }
    }

//    @Override
//    public Page<PhieuNhapHang> findByChiNhanhAndText(int chiNhanhId, String text, Pageable pageable) {
//        try{
//            return phieuNhapHangRepo.findByChiNhanhAndText(chiNhanhId,text, pageable);
//        }catch (Exception ex){
//            LOGGER.error("findByChiNhanhAndText error : " + ex);
//            return null;
//        }
//    }

    @Override
    public Boolean deleted(int id) {
        try {
            return phieuNhapHangRepo.deleted(id) > 0 ? true : false;
        } catch (Exception ex) {
            LOGGER.error("delete-phieu-nhap-hang-error : " + ex);
            return false;
        }
    }

    @Override
    public Page<NhaCungCapDto> topNhaCungCapTheoThang(Date ngayDau, Date ngayCuoi, Pageable pageable) {
        try {
            return phieuNhapHangRepo.topNhaCungCapTheoThang(ngayDau, ngayCuoi, pageable);
        } catch (Exception ex) {
            LOGGER.error("find-top-phieu-nhap-hang-error : " + ex);
            return null;
        }
    }

    @Override
    public Page<NhaCungCapDto> searchTopNhaCungCapTheoThang( Date ngayDau, Date ngayCuoi, String text, Pageable pageable) {
        try {
            return phieuNhapHangRepo.searchTopNhaCungCapTheoThang(ngayDau, ngayCuoi, text, pageable);
        } catch (Exception ex) {
            LOGGER.error("find-search-top-phieu-nhap-hang-error : " + ex);
            return null;
        }
    }
}
