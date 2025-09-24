package com.tavi.tavi_mrs.controller.phong_ban;

import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.entities.phong_ban.PhongBan;
import com.tavi.tavi_mrs.service.phong_ban.PhongBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/phong-ban")
public class PhongBanController {

    @Autowired
    private PhongBanService phongBanService;

    @GetMapping("/find-all")
    public ResponseEntity<JsonResult> findAll() {
        return Optional.ofNullable(phongBanService.findAll())
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findByIdAndXoa(@RequestParam("id") int id) {
        return phongBanService.findByIdAndXoa(id, false)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/find-all-to-page")
    public ResponseEntity<JsonResult> findAllToPage(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                    @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PhongBan> phongBanPage = phongBanService.findAllToPage(pageable);
        return Optional.ofNullable(phongBanPage)
                .map(phongBans -> phongBans.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phongBans)) : JsonResult.notFound("PhongBan/Page"))
                .orElse(JsonResult.saveError("Internal Server Error"));
    }

    @GetMapping("/search")
    ResponseEntity<JsonResult> search(@RequestParam(value = "ten-phong-ban", required = false, defaultValue = "") String tenPhongBan,
                                      @RequestParam(value = "ma-phong-ban", required = false, defaultValue = "") String maPhongBan,
                                      @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                      @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PhongBan> phongBanPage;
        if ("".equals(tenPhongBan) && "".equals(maPhongBan)) {
            phongBanPage = phongBanService.findAllToPage(pageable);
        } else {
            phongBanPage = phongBanService.findByTenPhongBanAndMaPhongBan(tenPhongBan, maPhongBan, pageable);
        }
        return Optional.ofNullable(phongBanPage)
                .map(phongBans -> phongBans.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phongBans)) : JsonResult.notFound("TenPhongBan/Search"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/select-search")
    public ResponseEntity<JsonResult> selectSearch(@RequestParam(name = "text", required = false, defaultValue = "") String text,
                                                   @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                   @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return Optional.ofNullable(phongBanService.selectSearch(text, pageable))
                .map(JsonResult::found)
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/check-ma-phong-ban")
    public ResponseEntity<JsonResult> findByMaPhongBan(@RequestParam("ma-phong-ban") String maPhongBan) {
        Boolean bool = phongBanService.findByMaPhongBan(maPhongBan);
        if (!bool) {
            return JsonResult.valid("true"); // ma phong ban hop le
        }
        return JsonResult.invalid();              // ma phong ban da ton tai
    }

    @PostMapping("/upload")
    public ResponseEntity<JsonResult> save(@RequestBody PhongBan phongBan) {
        Boolean bool = phongBanService.findByMaPhongBan(phongBan.getMaPhongBan());
        if (!bool) {
            Boolean bool2 = phongBanService.findByTenPhongBan(phongBan.getTenPhongBan());
            if (!bool2) {
                phongBan.setXoa(false);
                return phongBanService.save(phongBan)
                        .map(JsonResult::uploaded)
                        .orElse(JsonResult.saveError("PhongBan"));
            } else {
                return JsonResult.existed("Tên Phòng Ban");
            }
        } else {
            return JsonResult.existed("Mã phòng ban");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<JsonResult> update(@RequestBody PhongBan phongBan) {
        Boolean boolMa = phongBanService.findByMaPhongBan(phongBan.getMaPhongBan());
        Boolean boolTen = phongBanService.findByTenPhongBan(phongBan.getTenPhongBan());
        return phongBanService.findByIdAndXoa(phongBan.getId(), false).
                map(phongBan1 -> {
                    if (boolMa) {
                        return JsonResult.existed("Mã phòng ban");
                    }
                    if (boolTen) {
                        return JsonResult.existed("Tên phòng ban");
                    }
                    return phongBanService.save(phongBan)
                            .map(JsonResult::updated)
                            .orElse(JsonResult.saveError("PhongBan"));
                })
                .orElse(JsonResult.parentNotFound("PhongBan"));
    }

        @DeleteMapping("/delete")
        public ResponseEntity<JsonResult> delete(@RequestParam("id") int id) {
            Optional<PhongBan> phongBan = phongBanService.findByIdAndXoa(id, false);
            if (phongBan.isPresent()) {
                Boolean bool = phongBanService.deleted(id);
                if (bool) {
                    return JsonResult.deleted();
                }
                return JsonResult.saveError("Phongban");
            } else {
                return JsonResult.idNotFound();
            }
        }
}
