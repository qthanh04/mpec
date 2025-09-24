package com.tavi.tavi_mrs.controller.bao_cao;

import com.tavi.tavi_mrs.entities.bao_cao.BaoCao;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.service.bao_cao.BaoCaoService;
import com.tavi.tavi_mrs.service.nguoi_dung.NguoiDungService;
import com.tavi.tavi_mrs.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/bao-cao")
public class BaoCaoController {

    @Autowired
    private BaoCaoService baoCaoService;

    @Autowired
    private NguoiDungService nguoiDungService;

    @GetMapping("/find-all")
    public ResponseEntity<JsonResult> findAll(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                              @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<BaoCao> baoCaoPage = baoCaoService.findAll(pageable);
        return Optional.ofNullable(baoCaoPage)
                .map(baoCaos -> baoCaos.getTotalElements() != 0 ? JsonResult.found(PageJson.build(baoCaos)) : JsonResult.notFound("BaoCao/Page"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findByIdAndXoa(@RequestParam("id") int id) {
        return baoCaoService.findByIdAndXoa(id, false)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/find-by-thong-bao")
    public ResponseEntity<JsonResult> findByThongBao(@RequestParam("thong-bao-id") int id,
                                                     @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                     @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<BaoCao> baoCaoPage = baoCaoService.findByThongBao(id, pageable);
        return Optional.ofNullable(baoCaoPage)
                .map(baoCaos -> baoCaos.getTotalElements() != 0 ? JsonResult.found(PageJson.build(baoCaos)) : JsonResult.notFound("BaoCao/Page"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }


    @GetMapping("/search")
    public ResponseEntity<JsonResult> search(@RequestParam(name = "tieu-de", defaultValue = "", required = false) String tieuDe,
                                             @RequestParam(name = "ngay-dau", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime ngayDau,
                                             @RequestParam(name = "ngay-cuoi", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime ngayCuoi,
                                             @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                             @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        System.out.println("from " + DateTimeUtils.asDate(ngayDau) + " to " + DateTimeUtils.asDate(ngayCuoi));
        System.out.println("from " + ngayDau + " to " + ngayCuoi);
        Page<BaoCao> baoCaoPage = baoCaoService.findByTieuDeAndThoiGian(tieuDe, DateTimeUtils.asDate(ngayDau), DateTimeUtils.asDate(ngayCuoi), false, pageable);
        return Optional.ofNullable(baoCaoPage)
                .map(baoCaos -> baoCaos.getTotalElements() != 0 ? JsonResult.found(PageJson.build(baoCaos)) : JsonResult.notFound("BaoCao/TieuDe/ThoiGian/Search"))
                .orElse(JsonResult.serverError("Internal Server error"));
    } 

    @DeleteMapping("/delete")
    public ResponseEntity<JsonResult> delete(@RequestParam("id") int id) {
        Optional<BaoCao> baoCao = baoCaoService.findByIdAndXoa(id, false);

        if (baoCao.isPresent()) {

            Boolean bool = baoCaoService.deleted(id);
            if (bool) {
                return JsonResult.deleted();
            }
            return JsonResult.saveError("BaoCao");
        } else {
            return JsonResult.idNotFound();

        }
    }

}