package com.tavi.tavi_mrs.controller.so_quy;

import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.entities.so_quy.LoaiThu;
import com.tavi.tavi_mrs.service.so_quy.LoaiThuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/loai-thu")
public class LoaiThuController {

    @Autowired
    LoaiThuService loaiThuService;

    @GetMapping("/find-all")
    public ResponseEntity<JsonResult> findAllToPage(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                    @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<LoaiThu> loaiThuPage = loaiThuService.findAll(pageable);
        return Optional.ofNullable(loaiThuPage)
                .map(loaiThus -> loaiThus.getTotalElements() != 0 ? JsonResult.found(PageJson.build(loaiThus)) : JsonResult.notFound("LoaiThu"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findById(@RequestParam("id") int id) {
        return loaiThuService.findById(id, false)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/search")
    public ResponseEntity<JsonResult> search(@RequestParam(value = "ma-loai-thu", required = false, defaultValue = "") String maLoaiThu,
                                             @RequestParam(value = "ten-loai-thu", required = false, defaultValue = "") String tenLoaiThu,
                                             @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                             @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<LoaiThu> loaiThuPage;
        if ("".equals(maLoaiThu) && "".equals(tenLoaiThu)) {
            loaiThuPage = loaiThuService.findAll(pageable);
        } else {
            loaiThuPage = loaiThuService.findByTenLoaiThuAndMaLoaiThu(maLoaiThu, tenLoaiThu, pageable);
        }
        return Optional.ofNullable(loaiThuPage)
                .map(loaiThus -> loaiThus.getTotalElements() != 0 ? JsonResult.found(PageJson.build(loaiThus)) : JsonResult.notFound("Search/MaLoaiThu/TenLoaiThu"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @PostMapping("/upload")
    public ResponseEntity<JsonResult> save(@RequestBody LoaiThu loaiThu) {
        Boolean bool = loaiThuService.findByMaLoaiThu(loaiThu.getMaLoaiThu());
        if (!bool) {
            Boolean bool2 = loaiThuService.findByTenLoaiThu(loaiThu.getTenLoaiThu());
            if (!bool2) {
                loaiThu.setXoa(false);
                return loaiThuService.save(loaiThu)
                        .map(JsonResult::uploaded)
                        .orElse(JsonResult.saveError("LoaiThu"));
            } else {
                return JsonResult.existed("Tên Loại Thu");
            }
        } else {
            return JsonResult.existed("Mã Loại Thu");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<JsonResult> update(@RequestBody LoaiThu loaiThu) {
        Boolean boolMa = loaiThuService.findByMaLoaiThu(loaiThu.getMaLoaiThu());
        Boolean boolTen = loaiThuService.findByTenLoaiThu(loaiThu.getTenLoaiThu());
        return loaiThuService.findById(loaiThu.getId(), false)
                .map(loaiThu1 -> {
                    if (boolMa) {
                        return JsonResult.existed("Mã Loại Thu");
                    }
                    if (boolTen) {
                        return JsonResult.existed("Tên Loại Thu");
                    }
                    return loaiThuService.save(loaiThu)
                            .map(JsonResult::updated)
                            .orElse(JsonResult.saveError("LoaiThu"));
                })
                .orElse(JsonResult.parentNotFound("LoaiThu"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<JsonResult> delete(@RequestParam("id") int id) {
        Optional<LoaiThu> loaiThu = loaiThuService.findById(id, false);
        if (loaiThu.isPresent()) {
            Boolean aBoolean = loaiThuService.delete(id);
            if (aBoolean) {
                return JsonResult.deleted();
            }
            return JsonResult.saveError("LoaiThu");
        } else {
            return JsonResult.idNotFound();
        }
    }
}
