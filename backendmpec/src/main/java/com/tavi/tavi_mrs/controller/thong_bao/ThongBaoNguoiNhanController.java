package com.tavi.tavi_mrs.controller.thong_bao;

import com.tavi.tavi_mrs.entities.hoa_don.HoaDon;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.entities.json.ThongBaoNguoiNhanForm;
import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDung;
import com.tavi.tavi_mrs.entities.thong_bao.ThongBao;
import com.tavi.tavi_mrs.entities.thong_bao.ThongBaoNguoiNhan;
import com.tavi.tavi_mrs.service.nguoi_dung.NguoiDungService;
import com.tavi.tavi_mrs.service.thong_bao.ThongBaoNguoiNhanService;
import com.tavi.tavi_mrs.service.thong_bao.ThongBaoService;
import com.tavi.tavi_mrs.utils.DateTimeUtils;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.crypto.spec.OAEPParameterSpec;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/api/v1/admin/thong-bao-nguoi-nhan")
public class ThongBaoNguoiNhanController {

    @Autowired
    private ThongBaoNguoiNhanService thongBaoNguoiNhanService;

    @Autowired
    private ThongBaoService thongBaoService;

    @Autowired
    private NguoiDungService nguoiDungService;

    @GetMapping("/find-by-nguoi-nhan")
    public ResponseEntity<JsonResult> findByNguoiNhan(@RequestParam(value = "nguoi-nhan-id") int nguoiNhanId,
                                                      @RequestParam(value = "text", defaultValue = "", required = false) String text,
                                                      @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                      @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ThongBaoNguoiNhan> thongBaoNguoiNhanPage = thongBaoNguoiNhanService.findByNguoiNhanAndText(nguoiNhanId, text, pageable);
        return Optional.ofNullable(thongBaoNguoiNhanPage)
                .map(thongBaoNguoiNhans -> thongBaoNguoiNhanPage.getTotalElements() != 0 ? JsonResult.found(PageJson.build(thongBaoNguoiNhanPage)) : JsonResult.notFound("thongBaoNguoiNhan/Page"))
                .orElse(JsonResult.serverError("Internal Server Error!"));
    }

    @GetMapping("/find-by-thong-bao")
    public ResponseEntity<JsonResult> findByThongBao(@RequestParam(value = "thong-bao-id") int thongBaoId,
                                                     @RequestParam(value = "text", defaultValue = "", required = false) String text,
                                                     @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                     @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ThongBaoNguoiNhan> thongBaoNguoiNhanPage = thongBaoNguoiNhanService.findByThongBaoAndText(thongBaoId, text, pageable);
        return Optional.ofNullable(thongBaoNguoiNhanPage)
                .map(thongBaoNguoiNhans -> thongBaoNguoiNhanPage.getTotalElements() != 0 ? JsonResult.found(PageJson.build(thongBaoNguoiNhanPage)) : JsonResult.notFound("thongBaoNguoiNhan/Page"))
                .orElse(JsonResult.serverError("Internal Server Error!"));
    }

    @GetMapping("/search")
    public ResponseEntity<JsonResult> search(@RequestParam(value = "tieu-de", defaultValue = "", required = false) String tieuDe,
                                             @RequestParam(value = "nguoi-nhan", defaultValue = "", required = false) String nguoiNhan,
                                             @RequestParam(value = "ngay-dau", defaultValue = "", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayDau,
                                             @RequestParam(value = "ngay-cuoi", defaultValue = "", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayCuoi,
                                             @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                             @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ThongBaoNguoiNhan> thongBaoNguoiNhanPage;
        if (tieuDe.equals("") && nguoiNhan.equals("") && ngayDau == null && ngayCuoi == null) {
            thongBaoNguoiNhanPage = thongBaoNguoiNhanService.findAllToPage(pageable);
        } else {
            thongBaoNguoiNhanPage = thongBaoNguoiNhanService.findByTieuDeAndNguoiNhanAndThoiGian(tieuDe, nguoiNhan, DateTimeUtils.asDate(ngayDau), DateTimeUtils.asDate(ngayCuoi), pageable);
        }
        return Optional.ofNullable(thongBaoNguoiNhanPage)
                .map(thongBaoNguoiNhans -> thongBaoNguoiNhans.getTotalElements() != 0 ? JsonResult.found(PageJson.build(thongBaoNguoiNhanPage)) : JsonResult.notFound("TieuDe/NguoiNhan"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @PostMapping("/upload")
    @ApiOperation(value = "post thong bao nguoi nhan", response = ThongBaoNguoiNhan.class)
    public ResponseEntity<JsonResult> post(@RequestBody ThongBaoNguoiNhanForm thongBaoNguoiNhanForm) {
        Optional<ThongBao> thongBao = thongBaoService.findByIdAndXoa(thongBaoNguoiNhanForm.getThongBaoId(), false);
        for (Integer id : thongBaoNguoiNhanForm.getNguoiDungId()) {
            ThongBaoNguoiNhan thongBaoNguoiNhan = new ThongBaoNguoiNhan();
            thongBaoNguoiNhan.setThongBao(thongBao.get());
            thongBaoNguoiNhan.setXoa(false);
            Optional<NguoiDung> nguoiDung = nguoiDungService.findById(id, false);
            thongBaoNguoiNhan.setNguoiDung(nguoiDung.get());
            thongBaoNguoiNhanService.save(thongBaoNguoiNhan);
        }
        return JsonResult.success("ThongBao");
    }

}
