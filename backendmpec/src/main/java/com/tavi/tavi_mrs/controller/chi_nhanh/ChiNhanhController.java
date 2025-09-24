package com.tavi.tavi_mrs.controller.chi_nhanh;

import com.tavi.tavi_mrs.entities.chi_nhanh.ChiNhanh;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.service.chi_nhanh.ChiNhanhService;
import com.tavi.tavi_mrs.service.cong_ty.TongCongTyService;
import com.tavi.tavi_mrs.utils.Random;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/chi-nhanh")
@ApiOperation(value = "API of chi nhanh")
public class    ChiNhanhController {

    @Autowired
    private ChiNhanhService chiNhanhService;

    @Autowired
    private TongCongTyService tongCongTyService;

    @GetMapping("/find-by-id")
    @ApiOperation(value = "find by id", response = ChiNhanh.class)
    public ResponseEntity<JsonResult> findById(@RequestParam("id") int id) {
        return chiNhanhService.findByIdAndXoa(id, false)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/find-all")
    public ResponseEntity<JsonResult> findAllToPage(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                    @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1 , size);
        Page<ChiNhanh> chiNhanhPage = chiNhanhService.findAllToPage(pageable);
        return Optional.ofNullable(chiNhanhPage)
                .map(chiNhanhs -> chiNhanhPage.getTotalElements() != 0 ? JsonResult.found(PageJson.build(chiNhanhPage)) : JsonResult.notFound("ChiNhanh/Page"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/tong-cty")
    @ApiOperation(value = "find by id tong cong ty", response = ChiNhanh.class, responseContainer = "List")
    public ResponseEntity<JsonResult> findByTongCty(@RequestParam(value = "tong-cty-id", defaultValue = "1", required = false) Integer idTongCty) {
        return Optional.ofNullable(chiNhanhService.findByTongCty(idTongCty))
                .map(rsList -> !rsList.isEmpty() ? JsonResult.found(rsList) : JsonResult.notFound("ChiNhanh"))
                .orElse(JsonResult.serverError("TongCongTy"));
    }

    @PostMapping("/upload")
    public ResponseEntity<JsonResult> themChiNhanh(@RequestBody ChiNhanh chiNhanh,
                                                   @RequestParam(value = "cong-ty-id", required = false) Integer congTyId) {
        String maCN = "CN" + chiNhanh.getDiaChi().substring(0, 1) + " - " + Random.randomCode();
        return tongCongTyService.findById(congTyId)
                .map(tongCongTy -> {
                    chiNhanh.setXoa(false);
                    chiNhanh.setTongCongTy(tongCongTy);
                    chiNhanh.setMaChiNhanh(maCN);
                    return chiNhanhService.save(chiNhanh)
                            .map(JsonResult::uploaded)
                            .orElse(JsonResult.saveError("ChiNhanh"));
                })
                .orElse(JsonResult.notFound("TongCongTy"));
    }

    @PutMapping("/update")
    public ResponseEntity<JsonResult> suaThongTin(@RequestBody ChiNhanh chiNhanh,
                                                  @RequestParam(value = "cong-ty-id") Integer idTongCongTy
    ) {
        return tongCongTyService.findById(idTongCongTy)
                .map(tongCongTy -> {
                    chiNhanh.setXoa(false);
                    chiNhanh.setTongCongTy(tongCongTy);
                    chiNhanh.setMaChiNhanh(chiNhanhService.findByIdAndXoa(chiNhanh.getId(), false).get().getMaChiNhanh());
                    return chiNhanhService.save(chiNhanh)
                            .map(JsonResult::updated)
                            .orElse(JsonResult.saveError("ChiNhanh"));
                })
                .orElse(JsonResult.notFound("TongCongTy"));
    }


    @DeleteMapping("/delete")
    public ResponseEntity<JsonResult> xoa(@RequestParam("id") Integer chiNhanhId) {
        Optional<ChiNhanh> chiNhanh = chiNhanhService.findByIdAndXoa(chiNhanhId, false);
        if (chiNhanh.isPresent()) {

            Boolean bol = chiNhanhService.deleted(chiNhanhId);
            if (bol) {
                return JsonResult.deleted();
            }
            return JsonResult.saveError("ChiNhanh");
        } else {

            return JsonResult.idNotFound();

        }
    }

    @GetMapping("/find-by-companyId")
    public ResponseEntity<JsonResult> timTheoDoanhNghiep(@RequestParam(value = "cong-ty-id", required = false) Integer doanhNghiepId,
                                                         @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                         @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ChiNhanh> chiNhanhPage = chiNhanhService.search(doanhNghiepId, pageable);
        return Optional.ofNullable(chiNhanhPage)
                .map(chiNhanhs -> chiNhanhs.getTotalElements() != 0 ? JsonResult.found(PageJson.build(chiNhanhs)) : JsonResult.notFound("chiNhanh"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/search")
    public ResponseEntity<JsonResult> selectSearch(@RequestParam(name = "text", required = false, defaultValue = "") String text) {
        return Optional.ofNullable(chiNhanhService.selectSearch(text))
                .map(JsonResult::found)
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/select-search")
    public ResponseEntity<JsonResult> selectSearch(@RequestParam(name = "text", required = false, defaultValue = "") String text,
                                                   @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                   @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ChiNhanh> chiNhanhPage = chiNhanhService.selectSearch(text, pageable);
        return Optional.ofNullable(chiNhanhPage)
                .map(chiNhanhs -> chiNhanhs.getTotalElements() != 0 ? JsonResult.found(PageJson.build(chiNhanhs)) : JsonResult.notFound("chiNhanh"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/select2-search")
    public ResponseEntity<JsonResult> select2Search(@RequestParam(name = "text", required = false, defaultValue = "") String text,
                                                   @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                   @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return Optional.ofNullable(chiNhanhService.select2Search(text, pageable))
                .map(JsonResult::found)
                .orElse(JsonResult.serverError("Internal Server error"));
    }
}
