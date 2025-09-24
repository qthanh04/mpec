package com.tavi.tavi_mrs.controller.don_vi;

import com.tavi.tavi_mrs.entities.don_vi.DonViHangHoa;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.service.don_vi.DonViHangHoaService;
import com.tavi.tavi_mrs.service.don_vi.DonViService;
import com.tavi.tavi_mrs.service.hang_hoa.HangHoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/don-vi-hang-hoa")
public class DonViHangHoaController {

    @Autowired
    private DonViHangHoaService donViHangHoaService;

    @Autowired
    private HangHoaService hangHoaService;

    @Autowired
    private DonViService donViService;

    @GetMapping("/find-all")
    public ResponseEntity<JsonResult> findAll() {
        return Optional.ofNullable(donViHangHoaService.findAll())
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findAllByIdAndXoa(@RequestParam("id") int id) {
        return Optional.ofNullable(donViHangHoaService.findByIdAndXoa(id, false))
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/find-by-hang-hoa-id")
    public ResponseEntity<JsonResult> findByHangHoaId(@RequestParam("hangHoaId") int hangHoaId,
                                                      @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                                      @RequestParam(name = "size", required = false, defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<DonViHangHoa> donViHangHoaPage = donViHangHoaService.findByHangHoaId(hangHoaId, pageable);
        return Optional.ofNullable(donViHangHoaPage)
                .map(donViHangHoas -> donViHangHoas.getTotalElements() != 0 ? JsonResult.found(PageJson.build(donViHangHoas)) : JsonResult.notFound("DonViHangHoa"))
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/find-don-vi-co-ban-by-hang-hoa-id")
    public ResponseEntity<JsonResult> findByHangHoaId(@RequestParam("hangHoaId") int hangHoaId) {
        return donViHangHoaService.findDonViCoBanByHangHoaId(hangHoaId)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @PostMapping("/upload")
    public ResponseEntity<JsonResult> upload(@RequestParam("hang-hoa-id") int hangHoaId,
                                             @RequestParam("don-vi-id") int donViId,
                                             @RequestBody DonViHangHoa donViHangHoa) {
        System.out.println(donViHangHoa.toString());
        Boolean checkExsit = donViHangHoaService.findByTyLeAndDonViHangHoa(donViId, hangHoaId);
        if (checkExsit) {
            return JsonResult.existed("Don Vi Co Ban");
        } else {
            return hangHoaService.findById(hangHoaId, false)
                    .map(hangHoa -> {
                        donViHangHoa.setHangHoa(hangHoa);
                        return donViService.findByIdAndXoa(donViId, false)
                                .map(donVi -> {
                                    donViHangHoa.setDonVi(donVi);
                                    donViHangHoa.setXoa(false);
                                    return Optional.ofNullable(donViHangHoaService.save(donViHangHoa))
                                            .map(JsonResult::uploaded)
                                            .orElse(JsonResult.saveError("DonViHangHoa"));
                                }).orElse(JsonResult.parentNotFound("DonVI"));
                    }).orElse(JsonResult.parentNotFound("HangHoa"));
        }
    }
}
