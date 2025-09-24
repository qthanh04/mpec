package com.tavi.tavi_mrs.controller.hang_hoa.public_api;

import com.tavi.tavi_mrs.entities.hang_hoa.ThongSoChiTiet;
import com.tavi.tavi_mrs.entities.hang_hoa.ThongSoKiThuat;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.service.hang_hoa.ThongSoChiTietService;
import com.tavi.tavi_mrs.service.hang_hoa.ThongSoKiThuatService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/thong-so-chi-tiet")
public class ThongSoChiTietController {

    @Autowired
    ThongSoChiTietService thongSoChiTietService;

    @Autowired
    private ThongSoKiThuatService thongSoKiThuatService;

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findById(@RequestParam("id") int idThongSoChiTiet) {
        return thongSoChiTietService.findById(idThongSoChiTiet, false)
                .map(JsonResult :: found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/find-all")
    public ResponseEntity<JsonResult> findAllToPage(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                    @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ThongSoChiTiet> thongSoChiTietPage = thongSoChiTietService.findAllToPage(pageable);
        return Optional.ofNullable(thongSoChiTietPage)
                .map(thongSoChiTiets -> thongSoChiTiets.getTotalElements() != 0 ? JsonResult.found(PageJson.build(thongSoChiTiets)) : JsonResult.notFound("ThongSoChiTiet/Page"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @GetMapping("/find-by-thong-so-ki-thuat")
    public ResponseEntity<JsonResult> findByThongSoKiThuat(@RequestParam(value = "ten-thong-so-ki-thuat", required = false, defaultValue = "") String tenTSKT,
                                                           @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                           @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ThongSoChiTiet> thongSoChiTietPage = thongSoChiTietService.findByThongSoKiThuat(tenTSKT, pageable);
        return Optional.ofNullable(thongSoChiTietPage)
                .map(thongSoChiTiets -> thongSoChiTiets.getTotalElements() != 0 ? JsonResult.found(PageJson.build(thongSoChiTiets)) : JsonResult.notFound("ThongSoChiTiet/tenTSKT"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @GetMapping("/search")
    public ResponseEntity<JsonResult> search(@RequestParam(value = "ten-tsct", required = false) String tenTSCT,
                                             @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                             @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ThongSoChiTiet> thongSoChiTietPage = thongSoChiTietService.search(tenTSCT, pageable);
        return Optional.ofNullable(thongSoChiTietPage)
                .map(thongSoChiTiets -> thongSoChiTiets.getTotalElements() != 0 ? JsonResult.found(PageJson.build(thongSoChiTiets)) : JsonResult.notFound("ThongSoChiTiet/Search"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @PostMapping("/upload")
    @ApiOperation(value = "post thong so chi tiet", response = ThongSoChiTiet.class)
    public ResponseEntity<JsonResult> upload(@RequestBody ThongSoChiTiet thongSoChiTiet,
                                             @RequestParam("thong-so-ki-thuat-id") int thongSoKiThuatId) {
        return thongSoKiThuatService.findById(thongSoKiThuatId, false)
                .map(thongSoKiThuat -> {
                    thongSoChiTiet.setThongSoKiThuat(thongSoKiThuat);
                    thongSoChiTiet.setXoa(false);
                    return Optional.ofNullable(thongSoChiTietService.save(thongSoChiTiet))
                            .map(JsonResult::uploaded)
                            .orElse(JsonResult.serverError("ThongSoChiTiet"));
                }).orElse(JsonResult.serverError("ThongSoKiThuat"));
    }

    @PutMapping("/update")
    @ApiOperation(value = "put thong so chi tiet", response = ThongSoChiTiet.class)
    public ResponseEntity<JsonResult> update(@RequestBody ThongSoChiTiet thongSoChiTiet,
                                             @RequestParam("thong-so-chi-tiet-id") int thongSoChiTietId,
                                             @RequestParam("thong-so-ki-thuat-id") int thongSoKiThuatId) {
        return thongSoKiThuatService.findById(thongSoKiThuatId, false)
                .map(thongSoKiThuat -> {
                    thongSoChiTiet.setId(thongSoChiTietId);
                    thongSoChiTiet.setThongSoKiThuat(thongSoKiThuat);
                    thongSoChiTiet.setXoa(false);
                    return Optional.ofNullable(thongSoChiTietService.save(thongSoChiTiet))
                            .map(JsonResult::updated)
                            .orElse(JsonResult.serverError("ThongSoChiTiet"));
                }).orElse(JsonResult.serverError("ThongSoKiThuat"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<JsonResult> delete(@RequestParam("id") int id) {
        Optional<ThongSoChiTiet> thongSoChiTiet = thongSoChiTietService.findById(id, false);
        if (thongSoChiTiet.isPresent()) {
            Boolean aBoolean = thongSoChiTietService.delete(id);
            if (aBoolean) {
                return JsonResult.deleted();
            }
            return JsonResult.saveError("ThongSoKiThuat");
        }else {
            return JsonResult.idNotFound();
        }
    }

}
