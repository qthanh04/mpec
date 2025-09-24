package com.tavi.tavi_mrs.controller.hoa_don;

import com.tavi.tavi_mrs.entities.hoa_don.HoaDonChiTiet;
import com.tavi.tavi_mrs.entities.json.HoaDonChiTietForm;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.service.don_vi.DonViHangHoaService;
import com.tavi.tavi_mrs.service.don_vi.LichSuGiaBanService;
import com.tavi.tavi_mrs.service.hoa_don.HoaDonChiTietService;
import com.tavi.tavi_mrs.service.hoa_don.HoaDonService;
import com.tavi.tavi_mrs.utils.PdfUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/hoa-don-chi-tiet")
public class HoaDonChiTietController {

    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private DonViHangHoaService donViHangHoaService;

    @Autowired
    private LichSuGiaBanService lichSuGiaBanService;

    @Autowired
    private PdfUtils pdfUtils;

    @GetMapping("/find-all")
    public ResponseEntity<JsonResult> findAll() {
        return Optional.ofNullable(hoaDonChiTietService.findAll())
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }


    @GetMapping("/find-all-by-id-hoa-don")
    public ResponseEntity<JsonResult> findAllByPhieuTraHangNhapId(@RequestParam("hoa-don-id") int hoaDonId,
                                                                  @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                                  @RequestParam(name = "size", defaultValue = "99999", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<HoaDonChiTiet> hoaDonChiTietPage = hoaDonChiTietService.findAllByIdHoaDon(hoaDonId, pageable);
        return Optional.ofNullable(hoaDonChiTietPage)
                .map(hoaDonChiTiets -> hoaDonChiTiets.getTotalElements() != 0 ? JsonResult.found(PageJson.build(hoaDonChiTiets)) : JsonResult.notFound("HoaDonChiTiet"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findById(@RequestParam("id") int id) {
        return hoaDonChiTietService.findById(id)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @PostMapping("/upload-by-form")
    public ResponseEntity<JsonResult> uploadByForm(@RequestBody HoaDonChiTietForm hoaDonChiTietForm) {
        return hoaDonChiTietService.saveByForm(hoaDonChiTietForm);
    }

    @PutMapping("/update-by-form")
    public ResponseEntity<JsonResult> updateByForm(@RequestBody HoaDonChiTietForm hoaDonChiTietForm) {
        return hoaDonChiTietService.updateByForm(hoaDonChiTietForm);
    }

//    @PostMapping("/upload")
//    @ApiOperation(value = "post phieu nhap hang chi tiet", response = HoaDonChiTiet.class)
//    public ResponseEntity<JsonResult> post(@RequestBody List<HoaDonChiTiet> hoaDonChiTietList,
//                                           @RequestParam("hoa-don-id") int hoaDonId) {
//        return (ResponseEntity<JsonResult>) hoaDonService.findById(hoaDonId,false)
//                .map(hoaDon -> {
//                    boolean result = true;
//                    for (HoaDonChiTiet hdct : hoaDonChiTietList) {
//                        hdct.setHoaDon(hoaDon);
//                        hdct.setXoa(false);
//                        if (!hoaDonChiTietService.save(hdct)) {
//                            result = false;
//                            break;
//                        }
//                    }
//                    if (result) {
//                        return ResponseEntity.ok(JsonResult.build("ok", "uploaded"));
//                    } else {
//                        return ResponseEntity.badRequest().build();
//                    }
//                }).orElse(JsonResult.parentNotFound("HoaDon"));
//    }

//    @PutMapping("/update")
//    @ApiOperation(value = "put phieu nhap hang chi tiet", response = HoaDonChiTiet.class)
//    public ResponseEntity<JsonResult> put(@RequestBody HoaDonChiTiet hoaDonChiTiet,
//                                           @RequestParam("hoa-don-id") int hoaDonId,
//                                           @RequestParam("don-vi-hang-hoa-id") int donViHangHoaId
//    ) {
//        return hoaDonService.findById( hoaDonId,false)
//                .map(hoaDon -> {
//                    hoaDonChiTiet.setHoaDon(hoaDon);
//                    return donViHangHoaService.findByIdAndXoa( donViHangHoaId, false)
//                            .map(donViHangHoa -> {
//                                hoaDonChiTiet.setDonViHangHoa(donViHangHoa);
//                                return hoaDonChiTietService.save(hoaDonChiTiet)
//                                        .map(JsonResult::updated)
//                                        .orElse(JsonResult.saveError("HoaDonChiTiet "));
//                            }).orElse(JsonResult.parentNotFound("DonViHangHoa"));
//                }).orElse(JsonResult.parentNotFound("HoaDon"));
//    }
}
