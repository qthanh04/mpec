package com.tavi.tavi_mrs.controller.so_quy;

import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.entities.so_quy.LoaiChi;
import com.tavi.tavi_mrs.service.so_quy.LoaiChiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/loai-chi")
public class LoaiChiController {

    @Autowired
    LoaiChiService loaiChiService;

    @GetMapping("/find-all")
    public ResponseEntity<JsonResult> findAll(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                              @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<LoaiChi> loaiChiPage = loaiChiService.findAll(pageable);
        return Optional.ofNullable(loaiChiPage)
                .map(loaiChis -> loaiChis.getTotalElements() != 0 ? JsonResult.found(PageJson.build(loaiChis)) : JsonResult.notFound("LoaiChi/Page"))
                .orElse(JsonResult.serverError("Internal Server Error!"));
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findByIdAndXoa(@RequestParam("id") int id) {
        return loaiChiService.findById(id, false)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("search")
    public ResponseEntity<JsonResult> search(@RequestParam(value = "ten-loai-chi", required = false, defaultValue = "") String tenLoaiChi,
                                             @RequestParam(value = "ma-loai-chi", required = false, defaultValue = "") String maLoaiChi,
                                             @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                             @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<LoaiChi> loaiChiPage;
        if ("".equals(tenLoaiChi) && "".equals(maLoaiChi)) {
            loaiChiPage = loaiChiService.findAll(pageable);
        }else {
            loaiChiPage = loaiChiService.findByTenLoaiChiAndMaLoaiChi(tenLoaiChi, maLoaiChi, pageable);
        }
        return Optional.ofNullable(loaiChiPage)
                .map(loaiChis -> loaiChis.getTotalElements() != 0 ? JsonResult.found(PageJson.build(loaiChis)) : JsonResult.notFound("TenLoaiChi/Page"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @PutMapping("/upload")
    public ResponseEntity<JsonResult> save(@RequestBody LoaiChi loaiChi) {
        Boolean bool = loaiChiService.findByMaLoaiChi(loaiChi.getMaLoaiChi());
        if (!bool) {
            Boolean bool2 = loaiChiService.findByTenLoaiChi(loaiChi.getTenLoaiChi());
            if ((!bool2)) {
                loaiChi.setXoa(false);
                return loaiChiService.save(loaiChi)
                        .map(JsonResult :: uploaded)
                        .orElse(JsonResult.saveError("LoaiChi"));
            }else {
                return JsonResult.existed("Tên Loại Chi");
            }
        }else {
            return JsonResult.existed("Mã Loại Chi");
        }
    }

    @PostMapping("/update")
    public ResponseEntity<JsonResult> update(@RequestBody LoaiChi loaiChi) {
        Boolean boolMa = loaiChiService.findByMaLoaiChi(loaiChi.getMaLoaiChi());
        Boolean boolTen = loaiChiService.findByTenLoaiChi(loaiChi.getTenLoaiChi());
        return loaiChiService.findById(loaiChi.getId(), false)
                .map(loaiChi1 -> {
                    if (boolMa) {
                        return JsonResult.existed("Mã Loại Chi");
                    }
                    if (boolTen) {
                        return JsonResult.existed("Tên Loại Chi");
                    }
                    return loaiChiService.save(loaiChi)
                            .map(JsonResult::updated)
                            .orElse(JsonResult.saveError("LoaiChi"));
                })
                .orElse(JsonResult.parentNotFound("LoaiChi"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<JsonResult> delete(@RequestParam("id") int id) {
        Optional<LoaiChi> loaiChi = loaiChiService.findById(id, false);
        if (loaiChi.isPresent()) {
            Boolean bool = loaiChiService.delete(id);
            if (bool) {
                return JsonResult.deleted();
            }
            return JsonResult.saveError("LoaiChi");
        } else {
            return JsonResult.idNotFound();
        }
    }
}
