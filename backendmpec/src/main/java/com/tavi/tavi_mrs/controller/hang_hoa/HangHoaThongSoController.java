package com.tavi.tavi_mrs.controller.hang_hoa;

import com.tavi.tavi_mrs.entities.hang_hoa.HangHoaThongSo;
import com.tavi.tavi_mrs.entities.hang_hoa.ThongSoKiThuat;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.service.hang_hoa.HangHoaService;
import com.tavi.tavi_mrs.service.hang_hoa.HangHoaThongSoService;
import com.tavi.tavi_mrs.service.hang_hoa.ThongSoChiTietService;
import com.tavi.tavi_mrs.service.hang_hoa.ThongSoKiThuatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/hang-hoa-thong-so")
public class HangHoaThongSoController {

    @Autowired
    HangHoaThongSoService hangHoaThongSoService;

    @Autowired
    ThongSoChiTietService thongSoChiTietService;

    @Autowired
    ThongSoKiThuatService thongSoKiThuatService;

    @Autowired
    HangHoaService hangHoaService;

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findById(@RequestParam("id") int id) {
        return hangHoaThongSoService.findById(id, false)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/find-all")
    public ResponseEntity<JsonResult> findAll(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                              @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<HangHoaThongSo> hangHoaThongSoPage = hangHoaThongSoService.findAllToPage(pageable);
        return Optional.ofNullable(hangHoaThongSoPage)
                .map(hangHoaThongSos -> hangHoaThongSos.getTotalElements() != 0 ? JsonResult.found(PageJson.build(hangHoaThongSos)) : JsonResult.notFound("HangHoaThongSo"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @GetMapping("/search")
    public ResponseEntity<JsonResult> search(@RequestParam(name = "ten-thong-so-ki-thuat", defaultValue = "",required = false) String tenTSKT,
                                             @RequestParam(name = "ten-thong-so-chi-tiet", defaultValue = "", required = false) String tenTSCT,
                                             @RequestParam(name = "ten-hang-hoa", defaultValue = "", required = false) String tenHH,
                                             @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                             @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<HangHoaThongSo> hangHoaThongSoPage = hangHoaThongSoService.search(tenTSKT, tenTSCT, tenHH, pageable);
        return Optional.ofNullable(hangHoaThongSoPage)
                .map(hangHoaThongSos -> hangHoaThongSos.getTotalElements() != 0 ? JsonResult.found(PageJson.build(hangHoaThongSos)) : JsonResult.notFound("TenTSKT/TenTSCT/TenHH"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @PostMapping("/upload")
    public ResponseEntity<JsonResult> upload(@RequestBody HangHoaThongSo hangHoaThongSo,
                                             @RequestParam("thong-so-ki-thuat-id") int thongSoKiThuatId,
                                             @RequestParam("thong-so-chi-tiet-id") int thongSoChiTietId,
                                             @RequestParam("hang-hoa-id") int hangHoaId) {
        return thongSoKiThuatService.findById(thongSoKiThuatId, false)
                .map(thongSoKiThuat -> {
                    hangHoaThongSo.setThongSoKiThuat(thongSoKiThuat);
                    return thongSoChiTietService.findById(thongSoChiTietId, false)
                            .map(thongSoChiTiet -> {
                                hangHoaThongSo.setThongSoChiTiet(thongSoChiTiet);
                                return hangHoaService.findById(hangHoaId, false)
                                        .map(hangHoa -> {
                                            hangHoaThongSo.setHangHoa(hangHoa);
                                            hangHoaThongSo.setXoa(false);
                                            return Optional.ofNullable(hangHoaThongSoService.save(hangHoaThongSo))
                                                    .map(JsonResult :: uploaded)
                                                    .orElse(JsonResult.saveError("HangHoaThongSo"));
                                        }).orElse(JsonResult.serverError("HangHoa"));
                            }).orElse(JsonResult.serverError("ThongSoChiTiet"));
                }).orElse(JsonResult.serverError("ThongSoKiThuat"));
    }

    @PutMapping("/update")
    public ResponseEntity<JsonResult> update(@RequestBody HangHoaThongSo hangHoaThongSo,
                                             @RequestParam("thong-so-ki-thuat-id") int thongSoKiThuatId,
                                             @RequestParam("thong-so-chi-tiet-id") int thongSoChiTietId,
                                             @RequestParam("hang-hoa-id") int hangHoaId,
                                             @RequestParam("hang-hoa-thong-so-id") int hangHoaThongSoId) {
        return thongSoKiThuatService.findById(thongSoKiThuatId, false)
                .map(thongSoKiThuat -> {
                    hangHoaThongSo.setThongSoKiThuat(thongSoKiThuat);
                    return thongSoChiTietService.findById(thongSoChiTietId, false)
                            .map(thongSoChiTiet -> {
                                hangHoaThongSo.setThongSoChiTiet(thongSoChiTiet);
                                return hangHoaService.findById(hangHoaId, false)
                                        .map(hangHoa -> {
                                            hangHoaThongSo.setId(hangHoaThongSoId);
                                            hangHoaThongSo.setHangHoa(hangHoa);
                                            hangHoaThongSo.setXoa(false);
                                            return Optional.ofNullable(hangHoaThongSoService.save(hangHoaThongSo))
                                                    .map(JsonResult :: uploaded)
                                                    .orElse(JsonResult.saveError("HangHoaThongSo"));
                                        }).orElse(JsonResult.serverError("HangHoa"));
                            }).orElse(JsonResult.serverError("ThongSoChiTiet"));
                }).orElse(JsonResult.serverError("ThongSoKiThuat"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<JsonResult> delete(@RequestParam("id") int id) {
        Optional<HangHoaThongSo> hangHoaThongSo = hangHoaThongSoService.findById(id, false);
        if (hangHoaThongSo.isPresent()) {
            Boolean aBoolean = hangHoaThongSoService.delete(id);
            if (aBoolean) {
                return JsonResult.deleted();
            }
            return JsonResult.saveError("HangHoaThongSo");
        }else {
            return JsonResult.idNotFound();
        }
    }
}
