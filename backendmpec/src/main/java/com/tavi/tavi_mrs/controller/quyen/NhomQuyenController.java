package com.tavi.tavi_mrs.controller.quyen;

import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.entities.quyen.NhomQuyen;
import com.tavi.tavi_mrs.service.quyen.NhomQuyenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/nhom-quyen")
public class NhomQuyenController {

    @Autowired
    private NhomQuyenService nhomQuyenService;

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findById(@RequestParam("id") int id) {
        return nhomQuyenService.findById(id)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/find-all")
    public ResponseEntity<JsonResult> findAll(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                              @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<NhomQuyen> nhomQuyens = nhomQuyenService.findAllToPage(pageable);

        return Optional.ofNullable(nhomQuyens)
                .map(has -> has.getTotalElements() != 0 ? JsonResult.found(PageJson.build(has)) : JsonResult.notFound("Page"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @PostMapping("/upload")
    public ResponseEntity<JsonResult> upload(@RequestBody NhomQuyen nhomQuyen) {
        return nhomQuyenService.save(nhomQuyen)
                .map(JsonResult::uploaded)
                .orElse(JsonResult.saveError("NhomQuyen"));
    }

    @PutMapping("/update")
    public ResponseEntity<JsonResult> update(@RequestBody NhomQuyen nhomQuyen) {
        return nhomQuyenService.save(nhomQuyen)
                .map(JsonResult::updated)
                .orElse(JsonResult.saveError("NhomQuyen"));
    }
}
