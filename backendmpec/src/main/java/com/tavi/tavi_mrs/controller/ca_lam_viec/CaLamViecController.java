package com.tavi.tavi_mrs.controller.ca_lam_viec;

import com.tavi.tavi_mrs.entities.ca_lam_viec.CaLamViec;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.service.ca_lam_viec.CaLamViecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/ca-lam-viec")
public class CaLamViecController {

    @Autowired
    private CaLamViecService caLamViecService;

    @GetMapping("/find-all")
    public ResponseEntity<JsonResult> findAllToPage(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                    @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<CaLamViec> caLamViecPage = caLamViecService.findAll(pageable);
        return Optional.ofNullable(caLamViecPage)
                .map(caLamViecs -> caLamViecPage.getTotalElements() != 0 ? JsonResult.found(PageJson.build(caLamViecs)) : JsonResult.notFound("CaLamViec/Page"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/search")
    public ResponseEntity<JsonResult> search(@RequestParam(value = "text", defaultValue = "") String text,
                                             @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                             @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<CaLamViec> caLamViecPage;
        if (text.equals("")) {
            caLamViecPage = caLamViecService.findAll(pageable);
        } else {
            caLamViecPage = caLamViecService.searchByName(text, pageable);
        }
        return Optional.ofNullable(caLamViecPage)
                .map(caLamViecs -> caLamViecs.getTotalElements() != 0 ? JsonResult.found(PageJson.build(caLamViecs)) : JsonResult.notFound("Ca Lam Viec"))
                .orElse(JsonResult.serverError("Internal Serever Error"));
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findByIdAndXoa(@RequestParam("id") int id) {
        return caLamViecService.findByIdAndXoa(id, false)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @PostMapping("/upload")
    public ResponseEntity<JsonResult> upload(@RequestBody CaLamViec caLamViec) {
        caLamViec.setXoa(false);
        return caLamViecService.save(caLamViec)
                .map(JsonResult::uploaded)
                .orElse(JsonResult.saveError("CaLamViec"));
    }

    @PutMapping("/update")
    public ResponseEntity<JsonResult> update(@RequestBody CaLamViec caLamViec) {
        return caLamViecService.save(caLamViec)
                .map(JsonResult::updated)
                .orElse(JsonResult.saveError("CaLamViec"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<JsonResult> delete(@RequestParam("id") int id) {
        Optional<CaLamViec> caLamViec = caLamViecService.findByIdAndXoa(id, false);
        if (caLamViec.isPresent()) {
            Boolean bool = caLamViecService.deleted(id);
            if (bool) {
                return JsonResult.deleted();
            }
            return JsonResult.saveError("CaLamViec");
        } else {
            return JsonResult.idNotFound();
        }
    }
}
