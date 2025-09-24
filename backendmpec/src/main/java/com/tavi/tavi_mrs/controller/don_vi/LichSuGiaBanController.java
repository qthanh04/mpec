package com.tavi.tavi_mrs.controller.don_vi;

import com.tavi.tavi_mrs.entities.don_vi.DonViHangHoa;
import com.tavi.tavi_mrs.entities.don_vi.LichSuGiaBan;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.entities.json.ThietLapGiaJson;
import com.tavi.tavi_mrs.entities.phieu_nhap_hang.PhieuNhapHangChiTiet;
import com.tavi.tavi_mrs.service.don_vi.DonViHangHoaService;
import com.tavi.tavi_mrs.service.don_vi.LichSuGiaBanService;
import com.tavi.tavi_mrs.service.phieu_nhap_hang.PhieuNhapHangChiTietService;
import com.tavi.tavi_mrs.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/lich-su-gia-ban")
public class LichSuGiaBanController {

    @Autowired
    private PhieuNhapHangChiTietService phieuNhapHangChiTietService;

    @Autowired
    private LichSuGiaBanService lichSuGiaBanService;

    @Autowired
    private DonViHangHoaService donViHangHoaService;

    @GetMapping("/find-all")
    public ResponseEntity<JsonResult> findAll(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                              @RequestParam(name = "size", defaultValue = "99999", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<LichSuGiaBan> lichSuGiaBanPage = lichSuGiaBanService.findAll(pageable);
        return Optional.ofNullable(lichSuGiaBanPage)
                .map(lichSuGiaBans -> lichSuGiaBans.getTotalElements() != 0 ? JsonResult.found(PageJson.build(lichSuGiaBans)) : JsonResult.notFound("LichSuGiaBan"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @GetMapping("find-by-id")
    public ResponseEntity<JsonResult> findById(@RequestParam("id") int id) {
        return lichSuGiaBanService.findById(id)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("find-distinct-by-hang-hoa-id")
    public ResponseEntity<JsonResult> findDistinceByHangHoaId(@RequestParam("hangHoaId") int hangHoaId) {
        return lichSuGiaBanService.findDistinctGiaBan(hangHoaId)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("find-by-don-vi-hang-hoa-id")
    public ResponseEntity<JsonResult> findByDonViHangHoaId(@RequestParam("donViHangHoaId") int donViHangHoaId,
                                                           @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                           @RequestParam(name = "size", defaultValue = "5", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<LichSuGiaBan> lichSuGiaBanPage = lichSuGiaBanService.findByDonViHangHoaId(donViHangHoaId, pageable);
        return Optional.ofNullable(lichSuGiaBanPage)
                .map(lichSuGiaBans -> lichSuGiaBans.getTotalElements() != 0 ? JsonResult.found(PageJson.build(lichSuGiaBans)) : JsonResult.notFound("LichSuGiaBan"))
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("find-by-don-vi-hang-hoa-id-and-thoi-gian")
    public ResponseEntity<JsonResult> findByDonViHangHoaIdAndThoiGian(@RequestParam("don-vi-hang-hoa-id") int donViHangHoaId,
                                                                      @RequestParam("thoi-gian") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date thoiGian) {
        System.out.println(thoiGian);
        return lichSuGiaBanService.findByDonViHangHoaIdAndThoiGian(donViHangHoaId, thoiGian)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/find-gia-ban-hien-tai")
    public ResponseEntity<JsonResult> findGiaBanHienTai(@RequestParam("don-vi-hang-hoa-id") int donViHangHoaId) {
        return lichSuGiaBanService.findByDonViHangHoaAndThoiGianHienTai(donViHangHoaId)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @PostMapping("/upload")
    public ResponseEntity<JsonResult> upload(@RequestParam("don-vi-hang-hoa-id") int donViHangHoaId,
                                             @RequestBody LichSuGiaBan lichSuGiaBan) {
        return donViHangHoaService.findByIdAndXoa(donViHangHoaId, false)
                .map(donViHangHoa -> {
                    lichSuGiaBan.setDonViHangHoa(donViHangHoa);
                    LocalDateTime dateTime = LocalDateTime.now();
                    Date thoiGian = DateTimeUtils.asDate(dateTime);
                    Optional<LichSuGiaBan> giaBanCuOptional = lichSuGiaBanService.findByDonViHangHoaAndThoiGianHienTai(donViHangHoaId);
                    if (!giaBanCuOptional.equals(Optional.empty())) {
                        LocalDateTime thoiGianKetThuc = dateTime.minusSeconds(1);
                        LichSuGiaBan giaBanCu = giaBanCuOptional.get();
                        giaBanCu.setThoiGianKetThuc(DateTimeUtils.asDate(thoiGianKetThuc));
                        return Optional.ofNullable(lichSuGiaBanService.save(giaBanCu))
                                .map(temp -> {
                                    lichSuGiaBan.setId(0);
                                    lichSuGiaBan.setThoiGianBatDau(thoiGian);
                                    lichSuGiaBan.setThoiGianKetThuc(null);
                                    return Optional.ofNullable(lichSuGiaBanService.save(lichSuGiaBan))
                                            .map(JsonResult::uploaded)
                                            .orElse(JsonResult.saveError("LichSuGiaBan"));
                                }).orElse(JsonResult.parentNotFound("GiaBanCu"));
                    } else {
                        lichSuGiaBan.setThoiGianBatDau(thoiGian);
                        lichSuGiaBan.setThoiGianKetThuc(null);
                        return Optional.of(lichSuGiaBanService.save(lichSuGiaBan))
                                .map(JsonResult::uploaded)
                                .orElse(JsonResult.saveError("LichSuGiaBan"));
                    }
                }).orElse(JsonResult.parentNotFound("DonViHangHoa"));
    }

    @GetMapping("/search")
    public ResponseEntity<JsonResult> search(@RequestParam(name = "chi-nhanh-id", defaultValue = "0", required = false) int chiNhanhId,
                                             @RequestParam(name = "text", defaultValue = "", required = false) String text,
                                             @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                             @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<LichSuGiaBan> lichSuGiaBanPage;
        if (text.equals("") && chiNhanhId == 0) {
            lichSuGiaBanPage = lichSuGiaBanService.findAll(pageable);
        } else {
            lichSuGiaBanPage = lichSuGiaBanService.search(text, pageable);
        }
        List<ThietLapGiaJson> thietLapGiaJsonList = new ArrayList<>();
        List<LichSuGiaBan> lichSuGiaBanList = lichSuGiaBanPage.getContent();
        for (LichSuGiaBan gb : lichSuGiaBanList) {
            PhieuNhapHangChiTiet phieuNhapHangChiTiet = null;
            try {
                phieuNhapHangChiTiet = phieuNhapHangChiTietService.findGiaNhapGanNhat(chiNhanhId, gb.getDonViHangHoa().getHangHoa().getId()).get();
            } catch (Exception ex) {
            }
            ThietLapGiaJson thietLapGiaJson = new ThietLapGiaJson();
            thietLapGiaJson.setLichSuGiaBan(gb);
            thietLapGiaJsonList.add(thietLapGiaJson);
            if (phieuNhapHangChiTiet != null) {
                thietLapGiaJson.setGiaNhapGanNhat(phieuNhapHangChiTiet.getGiaNhap());
                thietLapGiaJson.setNgayNhapGanNhat(phieuNhapHangChiTiet.getPhieuNhapHang().getThoiGian());
            }
        }
        Page<ThietLapGiaJson> thietLapGiaJsonPage = new PageImpl<>(thietLapGiaJsonList, pageable, lichSuGiaBanPage.getTotalElements());
        return Optional.ofNullable(thietLapGiaJsonPage)
                .map(thietLapGiaJsons -> thietLapGiaJsons.getTotalElements() != 0 ? JsonResult.found(PageJson.build(thietLapGiaJsons)) : JsonResult.notFound("GiaBan"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

}
