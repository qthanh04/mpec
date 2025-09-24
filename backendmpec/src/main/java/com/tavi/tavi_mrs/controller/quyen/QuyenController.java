package com.tavi.tavi_mrs.controller.quyen;

import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.entities.quyen.Quyen;
import com.tavi.tavi_mrs.service.quyen.QuyenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/quyen")
public class QuyenController {

    @Autowired
    private QuyenService quyenService;

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findById(@RequestParam("id") int id) {
        return quyenService.findById(id)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/find-all")
    public ResponseEntity<JsonResult> findAll(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                              @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Quyen> quyens = quyenService.findAllToPage(pageable);

        return Optional.ofNullable(quyens)
                .map(has -> has.getTotalElements() != 0 ? JsonResult.found(PageJson.build(has)) : JsonResult.notFound("Page"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @PostMapping("/upload")
    public ResponseEntity<JsonResult> upload(@RequestBody Quyen quyen) {
        return quyenService.save(quyen)
                .map(JsonResult::uploaded)
                .orElse(JsonResult.saveError("Quyen"));
    }

    @PutMapping("/update")
    public ResponseEntity<JsonResult> update(@RequestBody Quyen quyen) {
        return quyenService.save(quyen)
                .map(JsonResult::updated)
                .orElse(JsonResult.saveError("Quyen"));
    }
}
