package com.tavi.tavi_mrs.controller.phieu_tra_khach;

import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.entities.json.PhieuTraKhachForm;
import com.tavi.tavi_mrs.entities.phieu_tra_khach.PhieuTraKhachChiTiet;
import com.tavi.tavi_mrs.service.hoa_don.HoaDonChiTietService;
import com.tavi.tavi_mrs.service.phieu_tra_khach.PhieuTraKhachChiTietService;
import com.tavi.tavi_mrs.service.phieu_tra_khach.PhieuTraKhachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/phieu-tra-khach-chi-tiet")
public class PhieuTraKhachChiTietController {

    @Autowired
    private PhieuTraKhachChiTietService phieuTraKhachChiTietService;

    @Autowired
    private PhieuTraKhachService phieuTraKhachService;

    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;

    @GetMapping("/search")
    ResponseEntity<JsonResult> search(
            @RequestParam(value = "chi-nhanh-id", required = false, defaultValue = "0") int chiNhanhId,
            @RequestParam(value = "text", defaultValue = "") String text,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PhieuTraKhachChiTiet> phieuTraKhachChiTietPage;
        if (chiNhanhId == 0 && text == "") {
            phieuTraKhachChiTietPage = phieuTraKhachChiTietService.findAllToPage(pageable);
        } else {
            phieuTraKhachChiTietPage = phieuTraKhachChiTietService.findByChiNhanhAndText(chiNhanhId, text, pageable);
        }
        return Optional.ofNullable(phieuTraKhachChiTietPage)
                .map(hangHoas -> hangHoas.getTotalElements() != 0 ? JsonResult.found(PageJson.build(hangHoas)) : JsonResult.notFound("PhieuTraKhachChiTiet"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/find-danh-sach-by-id")
    public ResponseEntity<JsonResult> findAllByPhieuTraKhachId(@RequestParam("phieu-tra-hang-nhap-id") int phieuKhachId,
                                                               @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                               @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PhieuTraKhachChiTiet> phieuTraKhachChiTietPage = phieuTraKhachChiTietService.findAllByPhieuTraKhachId(phieuKhachId, pageable);
        return Optional.ofNullable(phieuTraKhachChiTietPage)
                .map(phieuTraKhachChiTiets -> phieuTraKhachChiTiets.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phieuTraKhachChiTiets)) : JsonResult.notFound("PhieuTraHangNhapChiTiet"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findById(@RequestParam("id") int id) {
        return phieuTraKhachChiTietService.findById(id)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @PostMapping("/upload")
    public ResponseEntity<JsonResult> upload(@RequestParam("phieu-tra-khach-id") int phieuTraKhachId,
                                             @RequestParam("hoa-don-chi-tiet-id") int hoaDonChiTietId,
                                             @RequestBody PhieuTraKhachChiTiet phieuTraKhachChiTiet) {
        return phieuTraKhachService.findByIdAndXoa(phieuTraKhachId, false)
                .map(phieuTraKhach -> {
                    phieuTraKhachChiTiet.setPhieuTraKhach(phieuTraKhach);
                    return hoaDonChiTietService.findById(hoaDonChiTietId)
                            .map(hoaDonChiTiet -> {
                                phieuTraKhachChiTiet.setHoaDonChiTiet(hoaDonChiTiet);
                                phieuTraKhachChiTiet.setXoa(false);
                                return phieuTraKhachChiTietService.save(phieuTraKhachChiTiet)
                                        .map(JsonResult::uploaded)
                                        .orElse(JsonResult.saveError("PhieuTraKhachChiTiet"));
                            }).orElse(JsonResult.parentNotFound("HoaDonChiTiet"));
                }).orElse(JsonResult.parentNotFound("PhieuTraKhach"));

    }

    @PostMapping("/upload-by-form")
    public ResponseEntity<JsonResult> uploadByForm(@RequestBody PhieuTraKhachForm phieuTraKhachForm) {
        return phieuTraKhachChiTietService.uploadByForm(phieuTraKhachForm);
    }
}
