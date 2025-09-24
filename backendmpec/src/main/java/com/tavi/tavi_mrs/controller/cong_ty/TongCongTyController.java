package com.tavi.tavi_mrs.controller.cong_ty;

import com.tavi.tavi_mrs.entities.cong_ty.TongCongTy;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.service.cong_ty.TongCongTyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/doanh-nghiep")
public class TongCongTyController {

    @Autowired
    private TongCongTyService tongCongTyService;

    @GetMapping("/find-all")
    public ResponseEntity<JsonResult> findAll(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                              @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<TongCongTy> tongCongTyPage = tongCongTyService.findAll(pageable);
        return Optional.ofNullable(tongCongTyPage)
                .map(tongCongTIES -> tongCongTyPage.getTotalElements() != 0 ? JsonResult.found(PageJson.build(tongCongTyPage)) : JsonResult.notFound("TongCongTy/Page"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @PostMapping("/upload")
    public ResponseEntity<JsonResult> themDoanhNghiep(@RequestBody TongCongTy tongCongTy) {
        tongCongTy.setXoa(false);
        return Optional.ofNullable(tongCongTyService.save(tongCongTy))
                .map(JsonResult::uploaded)
                .orElse(JsonResult.saveError("TongCongTy"));
    }

    @PutMapping("/update")
    @ApiOperation(value = "sua-thong-tin-doanh-nghiep", response = TongCongTy.class)
    public ResponseEntity<JsonResult> suaThongTin(@RequestBody TongCongTy tongCongTy) {
        return tongCongTyService.save(tongCongTy)
                .map(JsonResult::updated)
                .orElse(JsonResult.saveError("TongCongTy"));
    }

    @PutMapping("/update-image")
    public ResponseEntity<JsonResult> updateAnh() {
        return null;
    }


    @DeleteMapping("/delete")
    public ResponseEntity<JsonResult> xoa(@RequestParam("id") Integer tongCongTyId) {
        Optional<TongCongTy> tongCongTy = tongCongTyService.findById(tongCongTyId);
        if (tongCongTy.isPresent()) {

            Boolean bol = tongCongTyService.deleted(tongCongTyId);
            if (bol)
                return JsonResult.deleted();
            return JsonResult.saveError("TongCongTy");
        } else {
            return JsonResult.idNotFound();
        }
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> timBangId(@RequestParam(value = "id", required = false) Integer tongCongtyId) {
        return tongCongTyService.findById(tongCongtyId)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }
}
