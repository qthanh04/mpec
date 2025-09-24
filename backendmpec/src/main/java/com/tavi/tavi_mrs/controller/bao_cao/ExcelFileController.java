package com.tavi.tavi_mrs.controller.bao_cao;

import com.tavi.tavi_mrs.entities.bao_cao.BaoCao;
import com.tavi.tavi_mrs.entities.bao_cao.ExcelFile;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.service.bao_cao.ExcelFileService;
import com.tavi.tavi_mrs.utils.DateTimeUtils;
import net.bytebuddy.asm.Advice;
import org.apache.jasper.tagplugins.jstl.core.If;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/excel-file")
public class ExcelFileController {

    @Autowired
    private ExcelFileService excelFileService;

    @GetMapping("/find-all")
    public ResponseEntity<JsonResult> findAll(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                              @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ExcelFile> excelFilePage = excelFileService.findAll(pageable);
        return Optional.ofNullable(excelFilePage)
                .map(excelFiles -> excelFilePage.getTotalElements() != 0 ? JsonResult.found(PageJson.build(excelFilePage)) : JsonResult.notFound("ExcelFile/Page"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findByIdAndXoa(@RequestParam("id") int id) {
        return excelFileService.findById(id)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());

    }

    @PutMapping("/update")
    public ResponseEntity<JsonResult> save(ExcelFile excelFile) {
        return excelFileService.save(excelFile)
                .map(JsonResult::updated)
                .orElse(JsonResult.saveError("ExcelFile"));
    }

    @GetMapping("/find-by-ten-loai")
    public ResponseEntity<JsonResult> findByTenLoai(@RequestParam("ten-loai") String tenLoai,
                                                    @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                    @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ExcelFile> excelFilePage = excelFileService.findByTenLoai(tenLoai, pageable);
        return Optional.ofNullable(excelFilePage)
                .map(excelFiles -> excelFiles.getTotalElements() != 0 ? JsonResult.found(PageJson.build(excelFiles)) : JsonResult.notFound("ExcelFile/Page"))
                .orElse(JsonResult.serverError("TenLoai"));
    }

    @GetMapping("/search")
    public ResponseEntity<JsonResult> findByTenLoaiAndMaPhieuAndThoiGian(@RequestParam(value = "ten-loai", required = false, defaultValue = "") String tenLoai,
                                                                         @RequestParam(value = "ma-phieu", required = false, defaultValue = "") String maPhieu,
                                                                         @RequestParam(name = "ngay-dau", defaultValue = "1970-01-01T00:00:00+00:00", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime ngayDau,
                                                                         @RequestParam(name = "ngay-cuoi", defaultValue = "9999-12-31T00:00:00+00:00", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime ngayCuoi,
                                                                         @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                                         @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        System.out.println("from" + DateTimeUtils.asDate(ngayDau) + "to" + DateTimeUtils.asDate(ngayCuoi));
        System.out.println("from" + ngayDau + "to" + ngayCuoi);
        Page<ExcelFile> excelFilePage;
        if (tenLoai == "" && maPhieu == "" && ngayDau == null && ngayCuoi == null) {
            excelFilePage = excelFileService.findAll(pageable);
        } else {
            excelFilePage = excelFileService.findByTenLoaiAndMaPhieuAndThoiGian(tenLoai, maPhieu, DateTimeUtils.asDate(ngayDau), DateTimeUtils.asDate(ngayCuoi), false, pageable);
        }

        return Optional.ofNullable(excelFilePage)
                .map(excelFiles -> excelFiles.getTotalElements() != 0 ? JsonResult.found(PageJson.build(excelFiles)) : JsonResult.notFound("ExcelFile/ThoiGian/Search"))
                .orElse(JsonResult.serverError("TenLoai"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<JsonResult> deleted(@RequestParam("id") Integer id) {
        Optional<ExcelFile> excelFile = excelFileService.findById(id);
        if (excelFile.isPresent()) {
            Boolean bool = excelFileService.deleted(id);
            if (bool) {
                return JsonResult.deleted();
            }
            return JsonResult.saveError("ExcelFile");
        } else {
            return JsonResult.idNotFound();

        }
    }
}

