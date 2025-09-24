package com.tavi.tavi_mrs.controller.hang_hoa;

import com.tavi.tavi_mrs.entities.hang_hoa.ThongSoKiThuat;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.service.hang_hoa.NhomHangService;
import com.tavi.tavi_mrs.service.hang_hoa.ThongSoKiThuatService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("api/v1/admin/thong-so-ki-thuat")
public class ThongSoKiThuatController {

    @Autowired
    private ThongSoKiThuatService thongSoKiThuatService;

    @Autowired
    private NhomHangService nhomHangService;

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findById(@RequestParam("id") int idThongSoKiThuat) {
        return thongSoKiThuatService.findById(idThongSoKiThuat, false)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/find-all")
    public ResponseEntity<JsonResult> findAllToPage(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                    @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ThongSoKiThuat> thongSoKiThuatPage = thongSoKiThuatService.findAllToPage(pageable);
        return Optional.ofNullable(thongSoKiThuatPage)
                .map(thongSoKiThuats -> thongSoKiThuats.getTotalElements() != 0 ? JsonResult.found(PageJson.build(thongSoKiThuats)) : JsonResult.notFound("ThongSoKiThuat/Page"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/find-by-ten-nhom-hang")
    public ResponseEntity<JsonResult> findThongSoKiThuatByTenNhomHang(@RequestParam(value = "ten-nhom-hang", required = false, defaultValue = "") String tenNhomHang,
                                                                      @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                                      @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page -1, size);
        Page<ThongSoKiThuat> thongSoKiThuatPage = thongSoKiThuatService.findThongSoKiThuatByTenNhomHang(tenNhomHang, pageable);
        return Optional.ofNullable(thongSoKiThuatPage)
                .map(thongSoKiThuats -> thongSoKiThuats.getTotalElements() != 0 ? JsonResult.found(PageJson.build(thongSoKiThuats)) : JsonResult.notFound("ThongSoKiThuat/TenNhomHang"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/search")
    public ResponseEntity<JsonResult> search(@RequestParam(value = "ten-thong-so", required = false) String tenThongSo,
                                             @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                             @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ThongSoKiThuat> thongSoKiThuatPage = thongSoKiThuatService.search(tenThongSo, pageable);
        return Optional.ofNullable(thongSoKiThuatPage)
                .map(thongSoKiThuats -> thongSoKiThuats.getTotalElements() != 0 ? JsonResult.found(PageJson.build(thongSoKiThuats)) : JsonResult.notFound("ThongSoKiThuat/Search"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @PostMapping("/upload")
    @ApiOperation(value = "post thong so ki thuat", response = ThongSoKiThuat.class)
    public ResponseEntity<JsonResult> upload(@RequestBody ThongSoKiThuat thongSoKiThuat,
                                           @RequestParam("nhom-hang-id") int nhomHangId) {
        return nhomHangService.findById(nhomHangId, false)
                .map(nhomHang -> {
                    thongSoKiThuat.setNhomHang(nhomHang);
                    thongSoKiThuat.setXoa(false);
                    return Optional.ofNullable(thongSoKiThuatService.save(thongSoKiThuat))
                            .map(JsonResult::uploaded)
                            .orElse(JsonResult.serverError("ThongSoKiThuat"));
                }).orElse(JsonResult.serverError("NhomHang"));
    }

    @PutMapping("/update")
    @ApiOperation(value = "put thong so ki thuat", response = ThongSoKiThuat.class)
    public ResponseEntity<JsonResult> update(@RequestBody ThongSoKiThuat thongSoKiThuat,
                                           @RequestParam("thong-so-ki-thuat-id") int thongSoKiThuatId,
                                           @RequestParam("nhom-hang-id") int nhomHangId) {
        return nhomHangService.findById(nhomHangId, false)
                .map(nhomHang -> {
                    thongSoKiThuat.setId(thongSoKiThuatId);
                    thongSoKiThuat.setNhomHang(nhomHang);
                    thongSoKiThuat.setXoa(false);
                    return Optional.ofNullable(thongSoKiThuatService.save(thongSoKiThuat))
                            .map(JsonResult::updated)
                            .orElse(JsonResult.serverError("ThongSoKiThuat"));
                }).orElse(JsonResult.serverError("NhomHang"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<JsonResult> delete(@RequestParam("id") int id) {
        Optional<ThongSoKiThuat> thongSoKiThuat = thongSoKiThuatService.findById(id, false);
        if (thongSoKiThuat.isPresent()) {
            Boolean aBoolean = thongSoKiThuatService.delete(id);
            if (aBoolean) {
                return JsonResult.deleted();
            }
            return JsonResult.saveError("ThongSoKiThuat");
        }else {
            return JsonResult.idNotFound();
        }
    }
}
