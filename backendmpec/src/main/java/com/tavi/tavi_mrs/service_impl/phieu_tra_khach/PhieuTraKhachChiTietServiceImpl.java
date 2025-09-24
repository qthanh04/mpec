package com.tavi.tavi_mrs.service_impl.phieu_tra_khach;

import com.tavi.tavi_mrs.entities.hoa_don.HoaDonChiTiet;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PhieuTraKhachForm;
import com.tavi.tavi_mrs.entities.phieu_tra_khach.PhieuTraKhach;
import com.tavi.tavi_mrs.entities.phieu_tra_khach.PhieuTraKhachChiTiet;
import com.tavi.tavi_mrs.repository.hoa_don.HoaDonChiTietRepo;
import com.tavi.tavi_mrs.repository.phieu_tra_khach.PhieuTraKhachChiTietRepo;
import com.tavi.tavi_mrs.repository.phieu_tra_khach.PhieuTraKhachRepo;
import com.tavi.tavi_mrs.service.phieu_tra_khach.PhieuTraKhachChiTietService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhieuTraKhachChiTietServiceImpl implements PhieuTraKhachChiTietService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhieuTraKhachChiTietServiceImpl.class);

    @Autowired
    private PhieuTraKhachChiTietRepo phieuTraKhachChiTietRepo;

    @Autowired
    private PhieuTraKhachRepo phieuTraKhachRepo;

    @Autowired
    private HoaDonChiTietRepo hoaDonChiTietRepo;

    @Override
    public Page<PhieuTraKhachChiTiet> findAllToPage(Pageable pageable) {
        try {
            return phieuTraKhachChiTietRepo.findAllToPage(pageable);
        } catch (Exception ex) {
            LOGGER.error("findAllToPage error", ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<PhieuTraKhachChiTiet> findAllByPhieuTraKhachId(int phieuTraKhachId, Pageable pageable) {
        try {
            return phieuTraKhachChiTietRepo.findAllByPhieuTraKhachId(phieuTraKhachId, pageable);
        } catch (Exception ex) {
            LOGGER.error("find-danh-sach-phieu-tra-khach-chi-tiet : " + ex);
            return null;
        }
    }

    @Override
    public List<PhieuTraKhachChiTiet> findListByPhieuTraKhachId(int id) {
        try {
            return phieuTraKhachChiTietRepo.findListByPhieuTraKhachId(id);
        } catch (Exception ex) {
            LOGGER.error("find-danh-sach-phieu-tra-khach-chi-tiet : " + ex);
            return null;
        }
    }

    @Override
    public Optional<PhieuTraKhachChiTiet> findById(int id) {
        try {
            return phieuTraKhachChiTietRepo.findById(id);
        } catch (Exception ex) {
            LOGGER.error("find-phieu-tra-khach-chi-tiet-by-id : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<PhieuTraKhachChiTiet> save(PhieuTraKhachChiTiet phieuTraKhachChiTiet) {
        try {
            return Optional.ofNullable(phieuTraKhachChiTietRepo.save(phieuTraKhachChiTiet));
        } catch (Exception ex) {
            LOGGER.error("save-phieu-tra-khach-error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Page<PhieuTraKhachChiTiet> findByChiNhanhAndText(int chiNhanhId, String text, Pageable pageable) {
        try {
            return phieuTraKhachChiTietRepo.findByChiNhanhAndText(chiNhanhId, text, pageable);
        } catch (Exception ex) {
            LOGGER.error("findByChiNhanhAndText error", ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseEntity<JsonResult> uploadByForm(PhieuTraKhachForm phieuTraKhachForm) {
        try {
            List<PhieuTraKhachChiTiet> phieuTraKhachChiTietList = phieuTraKhachForm.getPhieuTraKhachChiTietList();
            PhieuTraKhach phieuTraKhach = phieuTraKhachRepo.findByIdAndXoa(phieuTraKhachForm.getPhieuTraKhachId(), false).get();
            for (int i = 0; i < phieuTraKhachChiTietList.size(); i++) {
                PhieuTraKhachChiTiet phieuTraKhachChiTiet = phieuTraKhachChiTietList.get(i);
                phieuTraKhachChiTiet.setPhieuTraKhach(phieuTraKhach);
                try {
                    HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepo.findById(phieuTraKhachForm.getHoaDonChiTietId().get(i)).get();
                    phieuTraKhachChiTiet.setHoaDonChiTiet(hoaDonChiTiet);
                    phieuTraKhachChiTiet.setXoa(false);
                    try {
                        phieuTraKhachChiTietRepo.save(phieuTraKhachChiTiet);
                    } catch (Exception ex) {
                        System.err.println(ex);
                        return JsonResult.saveError("PhieuTraKhachChiTiet");
                    }
                } catch (Exception ex) {
                    JsonResult.parentNotFound("HoaDonChiTiet");
                }
            }
            return JsonResult.success(phieuTraKhach);
        } catch (Exception ex) {
            return JsonResult.parentNotFound("PhieuTraKhach");
        }
    }
}
