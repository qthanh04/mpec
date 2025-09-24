package com.tavi.tavi_mrs.controller.so_quy;


import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.phieu_nhap_hang.PhieuNhapHang;
import com.tavi.tavi_mrs.entities.phieu_tra_khach.PhieuTraKhach;
import com.tavi.tavi_mrs.entities.so_quy.PhieuChiNhapHangTraKhach;
import com.tavi.tavi_mrs.payload.nha_cung_cap.nhap_hang.TienTraNhaCungCap;
import com.tavi.tavi_mrs.payload.nha_cung_cap.nhap_hang.TienTraNhaCungCapList;
import com.tavi.tavi_mrs.payload.phieu_tra_khach.TienTraKhach;
import com.tavi.tavi_mrs.payload.phieu_tra_khach.TienTraKhachList;
import com.tavi.tavi_mrs.service.phieu_nhap_hang.PhieuNhapHangService;
import com.tavi.tavi_mrs.service.phieu_tra_khach.PhieuTraKhachService;
import com.tavi.tavi_mrs.service.so_quy.PhieuChiNhapHangTraKhachService;
import com.tavi.tavi_mrs.service.so_quy.PhieuChiService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/phieu-chi-nhap-hang-tra-khach")
public class PhieuChiNhapHangTraKhachController {

    @Autowired
    PhieuNhapHangService phieuNhapHangService;

    @Autowired
    PhieuTraKhachService phieuTraKhachService;

    @Autowired
    PhieuChiService phieuChiService;

    @Autowired
    PhieuChiNhapHangTraKhachService phieuChiNhapHangTraKhachService;

    //upload 1 phieu chi binh thuong khi tao 1 phieu nhap hang
    @PostMapping("/phieu-nhap-hang/upload")
    @ApiOperation(value = "upload phieu chi khi tao Phieu nhap hang", response = PhieuChiNhapHangTraKhach.class)
    public ResponseEntity<JsonResult> uploadPhieuChiKhiTaoPhieuNhapHang(@RequestParam("phieu-nhap-hang-id") int phieuNhapHangId,
                                                                        @RequestParam("phieu-chi-id") int phieuChiId,
                                                                        @RequestParam("tien-da-tra") Float tienDaTra) {
        PhieuChiNhapHangTraKhach phieuChiNhapHangTraKhach = new PhieuChiNhapHangTraKhach();
        return phieuNhapHangService.findById(phieuNhapHangId)
                .map(phieuNhapHang -> {
                    phieuChiNhapHangTraKhach.setPhieuNhapHang(phieuNhapHang);
                    return phieuChiService.findByIdAndXoa(phieuChiId, false)
                            .map(phieuChi -> {
                                phieuChiNhapHangTraKhach.setPhieuChi(phieuChi);
                                phieuChiNhapHangTraKhach.setTienDaTra(tienDaTra);
                                return phieuChiNhapHangTraKhachService.save(phieuChiNhapHangTraKhach)
                                        .map(JsonResult::uploaded)
                                        .orElse(JsonResult.saveError("PhieuChiNhapHangTraKhach"));
                            }).orElse(JsonResult.parentNotFound("PhieuChi"));
                }).orElse(JsonResult.parentNotFound("PhieuNhapHang"));
    }

    //upload 1 phieu chi binh thuong khi tao 1 phieu tra khach
    @PostMapping("/phieu-tra-khach/upload")
    @ApiOperation(value = "upload phieu chi khi tao Phieu tra khach", response = PhieuChiNhapHangTraKhach.class)
    public ResponseEntity<JsonResult> uploadPhieuChiKhiTaoPhieuTraKhach(@RequestParam("phieu-tra-khach-id") int phieuTraKhachId,
                                                                        @RequestParam("phieu-chi-id") int phieuChiId,
                                                                        @RequestParam("tien-da-tra") Float tienDaTra) {
        PhieuChiNhapHangTraKhach phieuChiNhapHangTraKhach = new PhieuChiNhapHangTraKhach();
        return phieuTraKhachService.findByIdAndXoa(phieuTraKhachId, false)
                .map(phieuTraKhach -> {
                    phieuChiNhapHangTraKhach.setPhieuTraKhach(phieuTraKhach);
                    return phieuChiService.findByIdAndXoa(phieuChiId, false)
                            .map(phieuChi -> {
                                phieuChiNhapHangTraKhach.setPhieuChi(phieuChi);
                                phieuChiNhapHangTraKhach.setTienDaTra(tienDaTra);
                                return phieuChiNhapHangTraKhachService.save(phieuChiNhapHangTraKhach)
                                        .map(JsonResult::uploaded)
                                        .orElse(JsonResult.saveError("PhieuChiNhapHangTraKhach"));
                            }).orElse(JsonResult.parentNotFound("PhieuChi"));
                }).orElse(JsonResult.parentNotFound("PhieuTraKhach"));
    }

    //o trang so quy va lap 1 phieu chi tra nha cung cap
    @PostMapping("/upload-phieu-chi-tra-nha-cung-cap")
    @ApiOperation(value = "upload Phieu Chi tra nha cung cap", response = PhieuChiNhapHangTraKhach.class)
    public ResponseEntity<JsonResult> uploadPhieuChiTraNhaCungCap(@RequestBody TienTraNhaCungCapList tienTraNhaCungCapList,
                                                                  @RequestParam("phieu-chi-id") int phieuChiId,
                                                                  @RequestParam("tong-tien-tra") Float tongTienTra) {
        List<TienTraNhaCungCap> tienTraNhaCungCaps = tienTraNhaCungCapList.getTienTraNhaCungCaps();
        try {
            for (int i = 0; i < tienTraNhaCungCaps.size(); i++) {
                PhieuChiNhapHangTraKhach phieuChiNhapHangTraKhach = new PhieuChiNhapHangTraKhach();
                Optional<PhieuNhapHang> phieuNhapHang = phieuNhapHangService.findById(tienTraNhaCungCaps.get(i).getPhieuNhapHangId());
                phieuNhapHangService.updateTienTraNhaCungCap(phieuNhapHang.get().getTienDaTra() + tienTraNhaCungCaps.get(i).getTienDaTra(), tienTraNhaCungCaps.get(i).getPhieuNhapHangId(),
                        phieuNhapHang.get().getTienPhaiTra() - (phieuNhapHang.get().getTienDaTra() + tienTraNhaCungCaps.get(i).getTienDaTra()));
                phieuChiNhapHangTraKhach.setPhieuNhapHang(phieuNhapHang.get());
                phieuChiNhapHangTraKhach.setPhieuChi(phieuChiService.findByIdAndXoa(phieuChiId, false).get());
                phieuChiNhapHangTraKhach.setTienDaTra(tongTienTra);
                phieuChiNhapHangTraKhachService.save(phieuChiNhapHangTraKhach)
                        .map(JsonResult::uploaded)
                        .orElse(JsonResult.saveError("PhieuChiNhapHangTraKhach"));
            }
            return JsonResult.uploaded("PhieuChi");
        } catch (Exception ex) {
            return JsonResult.saveError("PhieuChiNhapHangTraKhach");
        }
    }

    //o trang so quy va lap 1 phieu chi tra khach hang
    @PostMapping("/upload-phieu-chi-tra-khach-hang")
    @ApiOperation(value = "upload Phieu Chi tra khach hang", response = PhieuChiNhapHangTraKhach.class)
    public ResponseEntity<JsonResult> uploadPhieuChiTraKhachHang(@RequestBody TienTraKhachList tienTraKhachList,
                                                                 @RequestParam("phieu-chi-id") int phieuChiId,
                                                                 @RequestParam("tong-tien-tra") Float tongTienTra) {
        List<TienTraKhach> tienTraKhaches = tienTraKhachList.getTienTraKhachList();
        try {
            for (int i = 0; i < tienTraKhaches.size(); i++) {
                PhieuChiNhapHangTraKhach phieuChiNhapHangTraKhach = new PhieuChiNhapHangTraKhach();
                Optional<PhieuTraKhach> phieuTraKhach = phieuTraKhachService.findByIdAndXoa(tienTraKhaches.get(i).getPhieuTraKhachId(), false);
                phieuTraKhachService.updateTienTraKhach(phieuTraKhach.get().getTienDaTra() + tienTraKhaches.get(i).getTienDaTra(), tienTraKhaches.get(i).getPhieuTraKhachId(),
                        phieuTraKhach.get().getTienPhaiTra() - phieuTraKhach.get().getTienDaTra() + tienTraKhaches.get(i).getTienDaTra());
                phieuChiNhapHangTraKhach.setPhieuTraKhach(phieuTraKhach.get());
                phieuChiNhapHangTraKhach.setPhieuChi(phieuChiService.findByIdAndXoa(phieuChiId, false).get());
                phieuChiNhapHangTraKhach.setTienDaTra(tongTienTra);
                phieuChiNhapHangTraKhachService.save(phieuChiNhapHangTraKhach)
                        .map(JsonResult::uploaded)
                        .orElse(JsonResult.saveError("PhieuChiNhapHangTraKhach"));
            }
            return JsonResult.uploaded("PhieuChi");
        }catch (Exception ex) {
            return JsonResult.saveError("PhieuChiNhapHangTraKhach");
        }
    }

}
