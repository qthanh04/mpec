package com.tavi.tavi_mrs.controller.hang_hoa;

import com.tavi.tavi_mrs.entities.hang_hoa.NhomHang;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.service.hang_hoa.NhomHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/nhom-hang")
public class NhomHangController {

    @Autowired
    private NhomHangService nhomHangService;

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findById(@RequestParam("id") int idThietBi) {
        return nhomHangService.findById(idThietBi, false)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @PostMapping("/upload")
    public ResponseEntity<JsonResult> save(@RequestBody NhomHang nhomHang) {
        Boolean bool = nhomHangService.findByMaNhomHang(nhomHang.getMaNhomHang());
        if (!bool) {
            Boolean bool2 = nhomHangService.findByTenNhomHang(nhomHang.getTenNhomHang());
            if (!bool2) {
                nhomHang.setXoa(false);
                return nhomHangService.save(nhomHang)
                        .map(JsonResult::uploaded)
                        .orElse(JsonResult.saveError("NhomHang"));
            } else {
                return JsonResult.existed("Tên Nhóm hàng");
            }
        } else {
            return JsonResult.existed("Mã nhóm hàng");
        }
    }

    @GetMapping("/find-all")
    ResponseEntity<JsonResult> findAllToPage(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                             @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<NhomHang> nhomHangPage = nhomHangService.findAllToPage(pageable);
        return Optional.ofNullable(nhomHangPage)
                .map(nhomHangs -> nhomHangs.getTotalElements() != 0 ? JsonResult.found(PageJson.build(nhomHangs)) : JsonResult.notFound("NhomHang/Page"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/check-ma-nhom-hang")
    public ResponseEntity<JsonResult> findByMaNhomHang(@RequestParam("ma-nhom-hang") String maNhomHang) {
        Boolean bool = nhomHangService.findByMaNhomHang(maNhomHang);
        if (!bool) {
            return JsonResult.valid("true"); // ma hang hoa hop le
        }
        return JsonResult.invalid();              // ma hang hoa da ton tai
    }

    @GetMapping("/search")
    ResponseEntity<JsonResult> search(@RequestParam(value = "ten-nhom-hang", required = false, defaultValue = "") String tenNhomHang,
                                      @RequestParam(value = "ma-nhom-hang", required = false, defaultValue = "") String maNhomHang,
                                      @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                      @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<NhomHang> nhomHangPage;
        if ("".equals(tenNhomHang) && "".equals(maNhomHang)) {
            nhomHangPage = nhomHangService.findAllToPage(pageable);
        } else {
            nhomHangPage = nhomHangService.findByTenNhomHangAndMaNhomHang(tenNhomHang, maNhomHang, pageable);
        }
        return Optional.ofNullable(nhomHangPage)
                .map(nhomHangs -> nhomHangs.getTotalElements() != 0 ? JsonResult.found(PageJson.build(nhomHangs)) : JsonResult.notFound("TenNhomHang/Search"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @PutMapping("/update")
    ResponseEntity<JsonResult> update(@RequestBody NhomHang nhomHang) {
        Boolean boolMa = nhomHangService.findByMaNhomHang(nhomHang.getMaNhomHang());
        Boolean boolTen = nhomHangService.findByTenNhomHang(nhomHang.getTenNhomHang());
        return nhomHangService.findByIdAndXoa(nhomHang.getId(), false).
                map(nhomHang1 -> {
                    if (boolMa) {
                        return JsonResult.existed("Mã nhóm hàng");
                    }
                    if (boolTen) {
                        return JsonResult.existed("Tên Nhóm hàng");
                    }
                    return nhomHangService.save(nhomHang)
                            .map(JsonResult::updated)
                            .orElse(JsonResult.saveError("NhomHang"));
                })
                .orElse(JsonResult.parentNotFound("NhomHang"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<JsonResult> delete(@RequestParam("id") int id) {
        Optional<NhomHang> nhomHang = nhomHangService.findByIdAndXoa(id, false);
        if (nhomHang.isPresent()) {
            Boolean bool = nhomHangService.deleted(id);
            if (bool) {
                return JsonResult.deleted();
            }
            return JsonResult.saveError("NhomHang");
        } else {
            return JsonResult.idNotFound();
        }
    }
}
