package com.tavi.tavi_mrs.service_impl.phieu_tra_hang_nhap;


import com.tavi.tavi_mrs.entities.hang_hoa.ChiNhanhHangHoa;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PhieuTraHangNhapForm;
import com.tavi.tavi_mrs.entities.phieu_tra_hang_nhap.PhieuTraHangNhap;
import com.tavi.tavi_mrs.entities.phieu_tra_hang_nhap.PhieuTraHangNhapChiTiet;
import com.tavi.tavi_mrs.repository.hang_hoa.ChiNhanhHangHoaRepo;
import com.tavi.tavi_mrs.repository.phieu_tra_hang_nhap.PhieuTraHangNhapChiTietRepo;
import com.tavi.tavi_mrs.repository.phieu_tra_hang_nhap.PhieuTraHangNhapRepo;
import com.tavi.tavi_mrs.service.phieu_tra_hang_nhap.PhieuTraHangNhapChiTietService;
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
public class PhieuTraHangNhapChiTietServiceImpl implements PhieuTraHangNhapChiTietService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhieuTraHangNhapChiTietServiceImpl.class);

    @Autowired
    private PhieuTraHangNhapChiTietRepo phieuTraHangNhapChiTietRepo;

    @Autowired
    private PhieuTraHangNhapRepo phieuTraHangNhapRepo;

    @Autowired
    private ChiNhanhHangHoaRepo chiNhanhHangHoaRepo;


    @Override
    public Page<PhieuTraHangNhapChiTiet> findAll(Pageable pageable) {
        try {
            return phieuTraHangNhapChiTietRepo.fingALl(pageable);
        } catch (Exception ex) {
            LOGGER.error("find-all-phieu-tra-nhap-hang-error : " + ex);
            return null;
        }
    }

    @Override
    public Page<PhieuTraHangNhapChiTiet> findAllByPhieuTraHangNhapId(int phieuTraHangNhapId, Pageable pageable) {
        try {
            return phieuTraHangNhapChiTietRepo.findAllByPhieuTraHangNhapId(phieuTraHangNhapId, pageable);
        } catch (Exception ex) {
            LOGGER.error("find-all-danh-sach-phieu-tra-hang-nhap-chi-tiet-by-phieu-tra-hang-nhap-id : " + ex);
            return null;
        }
    }

    @Override
    public List<PhieuTraHangNhapChiTiet> findListByPhieuTraHangNhapId(int id) {
        try {
            return phieuTraHangNhapChiTietRepo.findListByPhieuTraHangNhapId(id);
        } catch (Exception ex) {
            LOGGER.error("find-list-phieu-tra-nhap-hang-error : " + ex);
            return null;
        }
    }

    @Override
    public Optional<PhieuTraHangNhapChiTiet> findById(int id) {
        try {
            return phieuTraHangNhapChiTietRepo.findById(id);
        } catch (Exception ex) {
            LOGGER.error("find-phieu-tra-hang-nhap-chi-tiet-by-id : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<PhieuTraHangNhapChiTiet> save(PhieuTraHangNhapChiTiet phieuTraHangNhapChiTiet) {
        try {
            return Optional.ofNullable(phieuTraHangNhapChiTietRepo.save(phieuTraHangNhapChiTiet));
        } catch (Exception ex) {
            LOGGER.error("save-phieu-tra-hang-nhap-chi-tiet : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public ResponseEntity<JsonResult> saveByForm(PhieuTraHangNhapForm phieuTraHangNhapForm) {
        try {
            List<PhieuTraHangNhapChiTiet> phieuTraHangNhapChiTietList = phieuTraHangNhapForm.getPhieuTraHangNhapChiTietList();
            PhieuTraHangNhap phieuTraHangNhap = phieuTraHangNhapRepo.findByIdAndXoa(phieuTraHangNhapForm.getPhieuTraHangNhapId(), false).get();
            for (int i = 0; i < phieuTraHangNhapChiTietList.size(); i++) {
                PhieuTraHangNhapChiTiet phieuTraHangNhapChiTiet = phieuTraHangNhapChiTietList.get(i);
                phieuTraHangNhapChiTiet.setPhieuTraHangNhap(phieuTraHangNhap);
                try {
                    ChiNhanhHangHoa chiNhanhHangHoa = chiNhanhHangHoaRepo.findByChiNhanhIdAndHangHoaId(phieuTraHangNhapForm.getChiNhanhId()
                            , phieuTraHangNhapForm.getHangHoaIdList().get(i)).get();
                    phieuTraHangNhapChiTiet.setChiNhanhHangHoa(chiNhanhHangHoa);
                    try {
                        phieuTraHangNhapChiTietRepo.save(phieuTraHangNhapChiTiet);
                    } catch (Exception ex) {
                        return JsonResult.saveError("PhieuTraHangNhapChiTiet");
                    }
                } catch (Exception ex) {
                    return JsonResult.parentNotFound("ChiNhanhHangHoa");
                }
            }
            return JsonResult.success(phieuTraHangNhap);
        } catch (Exception ex) {
            return JsonResult.parentNotFound("PhieuTraHangNhap");
        }
    }

    @Override
    public Page<PhieuTraHangNhapChiTiet> findByChiNhanhAndText(int chiNhanhId, String text, Pageable pageable) {
        try {
            return phieuTraHangNhapChiTietRepo.findByChiNhanhAndText(chiNhanhId, text, pageable);
        } catch (Exception ex) {
            LOGGER.error("find-phieu-tra-hang-by-chi-nhanh-error : " + ex);
            return null;
        }

    }
}
