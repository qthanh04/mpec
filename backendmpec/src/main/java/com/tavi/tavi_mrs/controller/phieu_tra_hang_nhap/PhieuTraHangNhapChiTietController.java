package com.tavi.tavi_mrs.controller.phieu_tra_hang_nhap;

import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.entities.json.PhieuTraHangNhapForm;
import com.tavi.tavi_mrs.entities.phieu_nhap_hang.PhieuNhapHangChiTiet;
import com.tavi.tavi_mrs.entities.phieu_tra_hang_nhap.PhieuTraHangNhapChiTiet;
import com.tavi.tavi_mrs.service.hang_hoa.ChiNhanhHangHoaService;
import com.tavi.tavi_mrs.service.phieu_tra_hang_nhap.PhieuTraHangNhapChiTietService;
import com.tavi.tavi_mrs.service.phieu_tra_hang_nhap.PhieuTraHangNhapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/phieu-tra-hang-nhap-chi-tiet")
public class PhieuTraHangNhapChiTietController {

    @Autowired
    private PhieuTraHangNhapChiTietService phieuTraHangNhapChiTietService;

    @Autowired
    private PhieuTraHangNhapService phieuTraHangNhapService;

    @Autowired
    private ChiNhanhHangHoaService chiNhanhHangHoaService;

    @GetMapping("/find-danh-sach-by-id")
    public ResponseEntity<JsonResult> findAllByPhieuTraHangNhapId(@RequestParam("phieu-tra-hang-nhap-id") int phieuTraHangNhapId,
                                                                  @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                                  @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PhieuTraHangNhapChiTiet> phieuTraHangNhapChiTietPage = phieuTraHangNhapChiTietService.findAllByPhieuTraHangNhapId(phieuTraHangNhapId, pageable);
        return Optional.ofNullable(phieuTraHangNhapChiTietPage)
                .map(phieuTraHangNhapChiTiets -> phieuTraHangNhapChiTiets.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phieuTraHangNhapChiTiets)) : JsonResult.notFound("PhieuTraHangNhapChiTiet"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findById(@RequestParam("id") int id) {
        return phieuTraHangNhapChiTietService.findById(id)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/search-by-chi-nhanh-and-text")
    public ResponseEntity<JsonResult> findByChiNhanhAndText(@RequestParam(value = "chi-nhanh-id", defaultValue = "0", required = false) int chiNhanhId,
                                                            @RequestParam(value = "text", defaultValue = "", required = false) String text,
                                                            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                            @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PhieuTraHangNhapChiTiet> phieuNhapHangChiTietPage;
        if (chiNhanhId == 0 && text.equals("")) {
            phieuNhapHangChiTietPage = phieuTraHangNhapChiTietService.findAll(pageable);
        } else {
            phieuNhapHangChiTietPage = phieuTraHangNhapChiTietService.findByChiNhanhAndText(chiNhanhId, text, pageable);
        }
        return Optional.ofNullable(phieuNhapHangChiTietPage)
                .map(phieuNhapHangChiTiets -> phieuNhapHangChiTiets.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phieuNhapHangChiTiets)) : JsonResult.notFound("PhieuNhapChiTiet"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @PostMapping("/upload")
    public ResponseEntity<JsonResult> upload(@RequestParam("phieu-tra-hang-nhap-id") int phieuTraHangNhapId,
                                             @RequestParam("hang-hoa-id") int hangHoaId,
                                             @RequestParam("chi-nhanh-id") int chiNhanhId,
                                             @RequestBody PhieuTraHangNhapChiTiet phieuTraHangNhapChiTiet) {
        return phieuTraHangNhapService.findByIdAndXoa(phieuTraHangNhapId, false)
                .map(phieuTraHangNhap -> {
                    phieuTraHangNhapChiTiet.setPhieuTraHangNhap(phieuTraHangNhap);
                    return chiNhanhHangHoaService.findByChiNhanhIdAndHangHoaId(chiNhanhId, hangHoaId)
                            .map(chiNhanhHangHoa -> {
                                phieuTraHangNhapChiTiet.setChiNhanhHangHoa(chiNhanhHangHoa);
                                return phieuTraHangNhapChiTietService.save(phieuTraHangNhapChiTiet)
                                        .map(JsonResult::uploaded)
                                        .orElse(JsonResult.saveError("PhieuTraHangNhapChiTiet"));
                            }).orElse(JsonResult.parentNotFound("PhieuTraHangNhap"));
                }).orElse(JsonResult.parentNotFound("PhieuTraHangNhap"));
    }

    @PostMapping("/upload-by-form")
    public ResponseEntity<JsonResult> uploadByForm(@RequestBody PhieuTraHangNhapForm phieuTraHangNhapForm) {
        return phieuTraHangNhapChiTietService.saveByForm(phieuTraHangNhapForm);
    }

}
