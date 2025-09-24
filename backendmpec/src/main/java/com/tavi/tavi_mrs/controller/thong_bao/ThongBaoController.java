package com.tavi.tavi_mrs.controller.thong_bao;

import com.tavi.tavi_mrs.entities.json.AnnounceForm;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.entities.thong_bao.ThongBao;
import com.tavi.tavi_mrs.service.nguoi_dung.NguoiDungService;
import com.tavi.tavi_mrs.service.thong_bao.ThongBaoService;
import com.tavi.tavi_mrs.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/thong-bao")
public class ThongBaoController {

    @Autowired
    private ThongBaoService thongBaoService;

    @Autowired
    private NguoiDungService nguoiDungService;

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findById(@RequestParam("id") int id) {
        return thongBaoService.findByIdAndXoa(id, false)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/find-all")
    public ResponseEntity<JsonResult> findAll(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                              @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ThongBao> thongBaoPage = thongBaoService.findAll(pageable);
        return Optional.ofNullable(thongBaoPage)
                .map(thongBaos -> thongBaos.getTotalElements() != 0 ? JsonResult.found(PageJson.build(thongBaos)) : JsonResult.notFound("ThongBao/Page"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/search")
    public ResponseEntity<JsonResult> search(@RequestParam(name = "ngay-dau", defaultValue = "", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayDau,
                                               @RequestParam(name = "ngay-cuoi", defaultValue = "", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayCuoi,
                                               @RequestParam(name = "tieu-de", defaultValue = "", required = false) String tieuDe,
                                               @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                               @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ThongBao> thongBaoPage;
        if (ngayDau == null && ngayCuoi == null && tieuDe.equals("")) {
            thongBaoPage = thongBaoService.findAll(pageable);
        } else {
            thongBaoPage = thongBaoService.findByThoiGianGuiAndTieuDe(DateTimeUtils.asDate(ngayDau), DateTimeUtils.asDate(ngayCuoi), tieuDe, pageable);
        }
        return Optional.ofNullable(thongBaoPage)
                .map(thongBaos -> thongBaos.getTotalElements() != 0 ? JsonResult.found(PageJson.build(thongBaos)) : JsonResult.notFound("ThongBao/ThoiGian/TieuDe"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @PostMapping("/upload")
    public ResponseEntity<JsonResult> upload(@RequestBody ThongBao thongBao) {
        thongBao.setXoa(false);
        LocalDateTime now = LocalDateTime.now();
        Date out = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        thongBao.setThoiGianGui(out);
        thongBao.setDaNhanPhanHoi(false);

        return Optional.ofNullable(thongBaoService.save(thongBao))
                .map(JsonResult::uploaded)
                .orElse(JsonResult.saveError("ThongBao"));
    }

//    @PostMapping("/send")
//    public ResponseEntity<JsonResult> sendAnnouncement(@RequestBody AnnounceForm form) throws MessagingException {
//        return thongBaoService.sendAnnouncement(form);
//    }
}
