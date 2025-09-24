package com.tavi.tavi_mrs.service_impl.hoa_don;

import com.tavi.tavi_mrs.entities.don_vi.LichSuGiaBan;
import com.tavi.tavi_mrs.entities.hoa_don.HoaDon;
import com.tavi.tavi_mrs.entities.hoa_don.HoaDonChiTiet;
import com.tavi.tavi_mrs.entities.json.HoaDonChiTietForm;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.repository.don_vi.LichSuGiaBanRepo;
import com.tavi.tavi_mrs.repository.hoa_don.HoaDonChiTietRepo;
import com.tavi.tavi_mrs.repository.hoa_don.HoaDonRepo;
import com.tavi.tavi_mrs.service.hoa_don.HoaDonChiTietService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class HoaDonChiTietServiceImpl implements HoaDonChiTietService {

    private static final Logger LOGGER = Logger.getLogger(HoaDonServiceImpl.class.getName());

    @Autowired
    private HoaDonChiTietRepo hoaDonChiTietRepo;

    @Autowired
    private HoaDonRepo hoaDonRepo;

    @Autowired
    private LichSuGiaBanRepo lichSuGiaBanRepo;

    @Override
    public List<HoaDonChiTiet> findAll() {
        try {
            return hoaDonChiTietRepo.findAll();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-hoa-don-chi-tiet-error : " + ex);
            return null;
        }
    }

    @Override
    public List<HoaDonChiTiet> findListByHoaDonId(int hoaDonId) {
        try {
            return hoaDonChiTietRepo.findListByIdHoaDon(hoaDonId);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-hoa-don-chi-tiet-error : " + ex);
            return null;
        }
    }

    @Override
    public Page<HoaDonChiTiet> findAllByIdHoaDon(int idHoaDon, Pageable pageable) {
        try {
            return hoaDonChiTietRepo.findAllByIdHoaDon(idHoaDon, pageable);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-hoa-don-chi-tiet-by-id-hoa-don-error : " + ex);
            return null;
        }
    }

    @Override
    public Optional<HoaDonChiTiet> findById(int id) {
        try {
            return hoaDonChiTietRepo.findById(id);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-hoa-don-chi-tiet-by-id : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public boolean save(HoaDonChiTiet hoaDonChiTiet) {
        try {
            Optional.ofNullable(hoaDonChiTietRepo.save(hoaDonChiTiet));
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save hoa don chi tiet error : " + ex);
            return false;
        }
    }

    @Override
    public ResponseEntity<JsonResult> saveByForm(HoaDonChiTietForm form) {
        try {
            List<HoaDonChiTiet> hoaDonChiTietList = form.getHoaDonChiTietList();
            HoaDon hoaDon = hoaDonRepo.findById(form.getHoaDonId(), false).get();
            for (int i = 0; i < hoaDonChiTietList.size(); i++) {
                HoaDonChiTiet hdct = hoaDonChiTietList.get(i);
                hdct.setHoaDon(hoaDon);
                LichSuGiaBan lichSuGiaBan = lichSuGiaBanRepo.findById(form.getLichSuGiaBanIdList().get(i)).get();
                hdct.setLichSuGiaBan(lichSuGiaBan);
                hdct.setXoa(false);
                try {
                    hoaDonChiTietRepo.save(hdct);
                } catch (Exception ex) {
                    return JsonResult.saveError("HoaDonChiTiet");
                }
            }
            return JsonResult.success("Them hoa don chi tiet thanh cong");
        } catch (Exception ex) {
            return JsonResult.parentNotFound("HoaDon");
        }
    }

    @Override
    public ResponseEntity<JsonResult> updateByForm(HoaDonChiTietForm form) {
        int hoaDonId = form.getHoaDonId();
        Pageable pageable = PageRequest.of(0, 1000);
        Page<HoaDonChiTiet> hoaDonChiTietPage = hoaDonChiTietRepo.findAllByIdHoaDon(hoaDonId, pageable);
        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietPage.getContent();
        hoaDonChiTiets.stream().forEach(hd -> {
            hoaDonChiTietRepo.deleted(hd.getId());
            System.out.println(hd.toString());
        });
        try {
            List<HoaDonChiTiet> hoaDonChiTietList = form.getHoaDonChiTietList();
            HoaDon hoaDon = hoaDonRepo.findById(form.getHoaDonId(), false).get();
            for (int i = 0; i < hoaDonChiTietList.size(); i++) {
                HoaDonChiTiet hdct = hoaDonChiTietList.get(i);
                hdct.setHoaDon(hoaDon);
                LichSuGiaBan lichSuGiaBan = lichSuGiaBanRepo.findById(form.getLichSuGiaBanIdList().get(i)).get();
                hdct.setLichSuGiaBan(lichSuGiaBan);
                hdct.setXoa(false);
                try {
                    hoaDonChiTietRepo.save(hdct);
                } catch (Exception ex) {
                    return JsonResult.saveError("HoaDonChiTiet");
                }
            }
            return JsonResult.success("Them hoa don chi tiet thanh cong");
        } catch (Exception ex) {
            return JsonResult.parentNotFound("HoaDon");
        }
    }


}
