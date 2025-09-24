package com.tavi.tavi_mrs.controller.phieu_nhap_hang;

import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.entities.json.PhieuNhapHangForm;
import com.tavi.tavi_mrs.entities.phieu_nhap_hang.PhieuNhapHangChiTiet;
import com.tavi.tavi_mrs.service.hang_hoa.ChiNhanhHangHoaService;
import com.tavi.tavi_mrs.service.phieu_nhap_hang.PhieuNhapHangChiTietService;
import com.tavi.tavi_mrs.service.phieu_nhap_hang.PhieuNhapHangService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/phieu-nhap-hang-chi-tiet")
public class PhieuNhapHangChiTietController {

    @Autowired
    PhieuNhapHangService phieuNhapHangService;

    @Autowired
    ChiNhanhHangHoaService chiNhanhHangHoaService;

    @Autowired
    PhieuNhapHangChiTietService phieuNhapHangChiTietService;

    @PostMapping("/upload")
    @ApiOperation(value = "post phieu nhap hang chi tiet", response = PhieuNhapHangChiTiet.class)
    public ResponseEntity<JsonResult> post(@RequestBody PhieuNhapHangChiTiet phieuNhapHangChiTiet,
                                           @RequestParam("chi-nhanh-hang-hang-hoa-id") int chiNhanhHangHoaId,
                                           @RequestParam("phieu-nhap-hang-id") int phieuNhapHangId
    ) {
        return chiNhanhHangHoaService.findById(chiNhanhHangHoaId, false)
                .map(chiNhanhHangHoa -> {
                    phieuNhapHangChiTiet.setChiNhanhHangHoa(chiNhanhHangHoa);
                    chiNhanhHangHoaService.setTonKho(phieuNhapHangChiTiet.getSoLuong(), chiNhanhHangHoaId);
                    return phieuNhapHangService.findById(phieuNhapHangId)
                            .map(phieuNhapHang -> {
                                phieuNhapHangChiTiet.setPhieuNhapHang(phieuNhapHang);
                                phieuNhapHangChiTiet.setXoa(false);
                                return phieuNhapHangChiTietService.save(phieuNhapHangChiTiet)
                                        .map(JsonResult::uploaded)
                                        .orElse(JsonResult.saveError("PhieuNhapHangChiTiet"));
                            }).orElse(JsonResult.parentNotFound("PhieuNhapHang"));
                }).orElse(JsonResult.parentNotFound("HangHoa"));
    }

//    @PutMapping("/update")
//    @ApiOperation(value = "put phieu nhap hang chi tiet", response = PhieuNhapHangChiTiet.class)
//    public ResponseEntity<JsonResult> put(@RequestBody PhieuNhapHangChiTiet phieuNhapHangChiTiet,
//                                           @RequestParam("hang-hoa-id") int hangHoaId,
//                                           @RequestParam("phieu-nhap-hang-id") int phieuNhapHangId
//    ) {
//        return hangHoaService.findById( hangHoaId,false)
//                .map(hangHoa -> {
//                    phieuNhapHangChiTiet.setHangHoa(hangHoa);
//                    return phieuNhapHangService.findById( phieuNhapHangId)
//                            .map(phieuNhapHang -> {
//                                phieuNhapHangChiTiet.setPhieuNhapHang(phieuNhapHang);
//                                return phieuNhapHangChiTietService.save(phieuNhapHangChiTiet)
//                                        .map(JsonResult::updated)
//                                        .orElse(JsonResult.saveError(" PhieuNhapHangChiTiet"));
//                            }).orElse(JsonResult.parentNotFound("PhieuNhapHang"));
//                }).orElse(JsonResult.parentNotFound("HangHoa"));
//    }

    @GetMapping("/search-by-chi-nhanh-and-text")
    public ResponseEntity<JsonResult> findByChiNhanhAndText(@RequestParam(value = "chi-nhanh-id", defaultValue = "0", required = false) int chiNhanhId,
                                                            @RequestParam(value = "text", defaultValue = "", required = false) String text,
                                                            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                            @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PhieuNhapHangChiTiet> phieuNhapHangChiTietPage;
        if (chiNhanhId == 0 && text.equals("")) {
            phieuNhapHangChiTietPage = phieuNhapHangChiTietService.fingAll(pageable);
        } else {
            phieuNhapHangChiTietPage = phieuNhapHangChiTietService.findByChiNhanhAndText(chiNhanhId, text, pageable);
        }
        return Optional.ofNullable(phieuNhapHangChiTietPage)
                .map(phieuNhapHangChiTiets -> phieuNhapHangChiTiets.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phieuNhapHangChiTiets)) : JsonResult.notFound("PhieuNhapChiTiet"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/find-by-phieu-nhap")
    public ResponseEntity<JsonResult> findByPhieuNhap(@RequestParam("phieu-nhap-id") int phieuNhapId,
                                                      @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                      @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PhieuNhapHangChiTiet> phieuNhapHangChiTietPage = phieuNhapHangChiTietService.findByPhieuNhap(phieuNhapId, pageable);
        return Optional.ofNullable(phieuNhapHangChiTietPage)
                .map(phieuNhapHangChiTiets -> phieuNhapHangChiTiets.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phieuNhapHangChiTiets)) : JsonResult.notFound("PhieuNhapChiTiet"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @PostMapping("/upload-by-form")
    public ResponseEntity<JsonResult> uploadByForm(@RequestBody PhieuNhapHangForm phieuNhapHangForm) {
        return phieuNhapHangChiTietService.saveByForm(phieuNhapHangForm);
    }

    @PutMapping("/update-by-form")
    public ResponseEntity<JsonResult> updateByForm(@RequestBody PhieuNhapHangForm phieuNhapHangForm) {
        return phieuNhapHangChiTietService.updateByForm(phieuNhapHangForm);
    }

    @GetMapping("/tim-gia-nhap-gan-nhat")
    public ResponseEntity<JsonResult> findGiaNhapGanNhat(@RequestParam("chi-nhanh-id") int chiNhanhId,
                                                         @RequestParam("hang-hoa-id") int hangHoaId) {
        return phieuNhapHangChiTietService.findGiaNhapGanNhat(chiNhanhId, hangHoaId)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }
}
