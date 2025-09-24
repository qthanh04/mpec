package com.tavi.tavi_mrs.service_impl.phieu_nhap_hang;

import com.tavi.tavi_mrs.entities.hang_hoa.ChiNhanhHangHoa;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PhieuNhapHangForm;
import com.tavi.tavi_mrs.entities.phieu_nhap_hang.PhieuNhapHang;
import com.tavi.tavi_mrs.entities.phieu_nhap_hang.PhieuNhapHangChiTiet;
import com.tavi.tavi_mrs.repository.hang_hoa.ChiNhanhHangHoaRepo;
import com.tavi.tavi_mrs.repository.phieu_nhap_hang.PhieuNhapHangChiTietRepo;
import com.tavi.tavi_mrs.repository.phieu_nhap_hang.PhieuNhapHangRepo;
import com.tavi.tavi_mrs.service.hang_hoa.ChiNhanhHangHoaService;
import com.tavi.tavi_mrs.service.phieu_nhap_hang.PhieuNhapHangChiTietService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhieuNhapHangChiTietServiceImpl implements PhieuNhapHangChiTietService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhieuNhapHangChiTietServiceImpl.class);

    @Autowired
    private PhieuNhapHangChiTietRepo phieuNhapHangChiTietRepo;

    @Autowired
    private PhieuNhapHangRepo phieuNhapHangRepo;

    @Autowired
    private ChiNhanhHangHoaRepo chiNhanhHangHoaRepo;

    @Autowired
    private ChiNhanhHangHoaService chiNhanhHangHoaService;

    @Override
    public Optional<PhieuNhapHangChiTiet> findById(int id) {
        try {
            return phieuNhapHangChiTietRepo.findById(id);
        } catch (Exception ex) {
            LOGGER.error("find by id error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Page<PhieuNhapHangChiTiet> fingAll(Pageable pageable) {
        try {
            return phieuNhapHangChiTietRepo.findAll(pageable);
        } catch (Exception ex) {
            LOGGER.error("find all phieu nhap hang chi tiet error : " + ex);
            return null;
        }
    }

    @Override
    public Optional<PhieuNhapHangChiTiet> save(PhieuNhapHangChiTiet phieuNhapHangChiTiet) {
        try {
            return Optional.ofNullable(phieuNhapHangChiTietRepo.save(phieuNhapHangChiTiet));
        } catch (Exception ex) {
            LOGGER.error("save phieu nhap hang chi tiet error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public List<PhieuNhapHangChiTiet> findListByPhieuNhapId(int phieuNhapId) {
        try {
            return phieuNhapHangChiTietRepo.findListByPhieuNhapId(phieuNhapId);
        } catch (Exception ex) {
            LOGGER.error("find list phieu nhap hang chi tiet by phieu nhap id error : " + ex);
            return null;
        }
    }

    @Override
    public Page<PhieuNhapHangChiTiet> findByChiNhanhAndText(int chiNhanhId, String text, Pageable pageable) {
        try {
            return phieuNhapHangChiTietRepo.findByChiNhanhAndText(chiNhanhId, text, pageable);
        } catch (Exception ex) {
            LOGGER.error("find danh sach phieu nhap theo chi nhanh error : " + ex);
            return null;
        }
    }

    @Override
    public Optional<PhieuNhapHangChiTiet> findGiaNhapGanNhat(int chiNhanhId, int hangHoaId) {
        try {
            return phieuNhapHangChiTietRepo.timGiaNhapGanNhat(chiNhanhId, hangHoaId);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    public Page<PhieuNhapHangChiTiet> findByPhieuNhap(int phieuNhapId, Pageable pageable) {
        try {
            return phieuNhapHangChiTietRepo.findByPhieuNhap(phieuNhapId, pageable);
        } catch (Exception ex) {
            LOGGER.error("find danh sach phieu nhap chi tiet theo phieu nhap error : " + ex);
            return null;
        }
    }

    @Override
    public ResponseEntity<JsonResult> saveByForm(PhieuNhapHangForm phieuNhapHangForm) {
        try {
            List<PhieuNhapHangChiTiet> phieuNhapHangChiTietList = phieuNhapHangForm.getPhieuNhapHangChiTietList();
            PhieuNhapHang phieuNhapHang = phieuNhapHangRepo.findById(phieuNhapHangForm.getPhieuNhapHangId()).get();
            for (int i = 0; i < phieuNhapHangChiTietList.size(); i++) {
                PhieuNhapHangChiTiet phieuNhapHangChiTiet = phieuNhapHangChiTietList.get(i);
                phieuNhapHangChiTiet.setPhieuNhapHang(phieuNhapHang);
                Optional<ChiNhanhHangHoa> chiNhanhHangHoaOptional;
                try {
                    chiNhanhHangHoaOptional = chiNhanhHangHoaRepo.findByChiNhanhIdAndHangHoaId(phieuNhapHangForm.getChiNhanhId()
                            , phieuNhapHangForm.getHangHoaIdList().get(i));
                } catch (Exception ex) {
                    chiNhanhHangHoaOptional = Optional.empty();
                }
                ChiNhanhHangHoa chiNhanhHangHoa = null;
                if (chiNhanhHangHoaOptional.isPresent()) {
                    chiNhanhHangHoa = chiNhanhHangHoaOptional.get();
                } else {
                    chiNhanhHangHoa = chiNhanhHangHoaService.save(phieuNhapHangForm.getChiNhanhId(), phieuNhapHangForm.getHangHoaIdList().get(i)).get();
                }
                phieuNhapHangChiTiet.setChiNhanhHangHoa(chiNhanhHangHoa);
                phieuNhapHangChiTiet.setXoa(false);
                try {
                    phieuNhapHangChiTietRepo.save(phieuNhapHangChiTiet);
                } catch (Exception ex) {
                    return JsonResult.saveError("PhieuNhapHangChiTiet");
                }

            }
            return JsonResult.success(phieuNhapHang);
        } catch (Exception ex) {
            return JsonResult.parentNotFound("PhieuNhapHang");
        }
    }

    @Override
    public ResponseEntity<JsonResult> updateByForm(PhieuNhapHangForm phieuNhapHangForm) {
        int phieuNhapHangId = phieuNhapHangForm.getPhieuNhapHangId();
        Pageable pageable = PageRequest.of(0, 1000);
        Page<PhieuNhapHangChiTiet> phieuNhapHangChiTietPage = phieuNhapHangChiTietRepo.findByPhieuNhap(phieuNhapHangId, pageable);
        List<PhieuNhapHangChiTiet> phieuNhapHangChiTiets = phieuNhapHangChiTietPage.getContent();
        phieuNhapHangChiTiets.stream().forEach(pn -> phieuNhapHangChiTietRepo.deleted(pn.getId()));
        try {
            List<PhieuNhapHangChiTiet> phieuNhapHangChiTietList = phieuNhapHangForm.getPhieuNhapHangChiTietList();
            PhieuNhapHang phieuNhapHang = phieuNhapHangRepo.findById(phieuNhapHangForm.getPhieuNhapHangId()).get();
            for (int i = 0; i < phieuNhapHangChiTietList.size(); i++) {
                PhieuNhapHangChiTiet phieuNhapHangChiTiet = phieuNhapHangChiTietList.get(i);
                phieuNhapHangChiTiet.setPhieuNhapHang(phieuNhapHang);
                try {
                    ChiNhanhHangHoa chiNhanhHangHoa = chiNhanhHangHoaRepo.findByChiNhanhIdAndHangHoaId(phieuNhapHangForm.getChiNhanhId()
                            , phieuNhapHangForm.getHangHoaIdList().get(i)).get();
                    phieuNhapHangChiTiet.setChiNhanhHangHoa(chiNhanhHangHoa);
                    try {
                        phieuNhapHangChiTiet.setXoa(false);
                        phieuNhapHangChiTietRepo.save(phieuNhapHangChiTiet);
                    } catch (Exception ex) {
                        return JsonResult.saveError("PhieuNhapHangChiTiet");
                    }
                } catch (Exception ex) {
                    return JsonResult.parentNotFound("ChiNhanhHangHoa");
                }
            }
            return JsonResult.success("Sua phieu nhap chi tiet thanh cong");
        } catch (Exception ex) {
            return JsonResult.parentNotFound("PhieuNhapHang");
        }
    }
}
