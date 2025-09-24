package com.tavi.tavi_mrs.controller.chuc_vu_vai_tro;

import com.tavi.tavi_mrs.entities.chuc_vu_vai_tro.ChucVu;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.service.chuc_vu_vai_tro.ChucVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/chuc-vu")
public class ChucVuController {

    @Autowired
    private ChucVuService chucVuService;

    @GetMapping("/find-all")
    public ResponseEntity<JsonResult> findAll() {
        return Optional.ofNullable(chucVuService.findAll())
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findByIdAndXoa(@RequestParam("id") int id) {
        return chucVuService.findByIdAndXoa(id, false)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/find-all-to-page")
    public ResponseEntity<JsonResult> findAllToPage(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                    @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ChucVu> chucVuPage = chucVuService.findAllToPage(pageable);
        return Optional.ofNullable(chucVuPage)
                .map(chucVus -> chucVus.getTotalElements() != 0 ? JsonResult.found(PageJson.build(chucVus)) : JsonResult.notFound("ChucVu/Page"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/search")
    public ResponseEntity<JsonResult> search(@RequestParam(value = "ten-chuc-vu", required = false, defaultValue = "") String tenChucVu,
                                             @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                             @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ChucVu> chucVuPage;
        if ("".equals(tenChucVu)) {
            chucVuPage = chucVuService.findAllToPage(pageable);
        } else {
            chucVuPage = chucVuService.findByTenChucVu(tenChucVu, pageable);
        }
        return Optional.ofNullable(chucVuPage)
                .map(chucVus -> chucVus.getTotalElements() != 0 ? JsonResult.found(PageJson.build(chucVus)) : JsonResult.notFound("TenChucVu/Search"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }


    @GetMapping("/select-search")
    public ResponseEntity<JsonResult> selectSearch(@RequestParam(name = "text", required = false, defaultValue = "") String tenChucVu,
                                                   @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                   @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return Optional.ofNullable(chucVuService.selectSearch(tenChucVu, pageable))
                .map(JsonResult::found)
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @PostMapping("/upload")
    public ResponseEntity<JsonResult> upload(@RequestBody ChucVu chucVu) {
        Boolean bool = chucVuService.findByTenChucVuCheck(chucVu.getTenChucVu());
        if (!bool) {
            chucVu.setXoa(false);
            return chucVuService.save(chucVu)
                    .map(JsonResult::uploaded)
                    .orElse(JsonResult.saveError("ChucVu"));
        } else {
            return JsonResult.existed("Chức vụ ");
        }

    }

    @PutMapping("/update")
    public ResponseEntity<JsonResult> update(@RequestBody ChucVu chucVu) {
        Boolean bool = chucVuService.findByTenChucVuCheck(chucVu.getTenChucVu());
        return chucVuService.findByIdAndXoa(chucVu.getId(), false)
                .map(chucVu1 -> {
                    if (bool) {
                        return JsonResult.existed("Chức vụ");
                    }
                    return chucVuService.save(chucVu)
                            .map(JsonResult::updated)
                            .orElse(JsonResult.saveError("ChucVu"));

                })
                .orElse(JsonResult.parentNotFound("ChucVu"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<JsonResult> delete(@RequestParam("id") int id) {
        Optional<ChucVu> chucVu = chucVuService.findByIdAndXoa(id, false);

        if (chucVu.isPresent()) {

            Boolean bool = chucVuService.deleted(id);
            if (bool) {
                return JsonResult.deleted();
            }
            return JsonResult.saveError("ChucVu");
        } else {
            return JsonResult.idNotFound();

        }
    }
}
