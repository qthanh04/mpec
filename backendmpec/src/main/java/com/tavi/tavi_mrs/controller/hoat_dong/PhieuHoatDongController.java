package com.tavi.tavi_mrs.controller.hoat_dong;

import com.tavi.tavi_mrs.entities.hoat_dong.PhieuHoatDong;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.payload.hoat_dong.PhieuHoatDongResponse;
import com.tavi.tavi_mrs.service.hoa_don.HoaDonService;
import com.tavi.tavi_mrs.service.hoat_dong.HoatDongService;
import com.tavi.tavi_mrs.service.hoat_dong.PhieuHoatDongService;
import com.tavi.tavi_mrs.service.nguoi_dung.NguoiDungService;
import com.tavi.tavi_mrs.service.phieu_nhap_hang.PhieuNhapHangService;
import com.tavi.tavi_mrs.service.phieu_tra_hang_nhap.PhieuTraHangNhapService;
import com.tavi.tavi_mrs.service.phieu_tra_khach.PhieuTraKhachService;
import com.tavi.tavi_mrs.service.so_quy.PhieuChiService;
import com.tavi.tavi_mrs.service.so_quy.PhieuThuService;
import com.tavi.tavi_mrs.utils.DateTimeUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/phieu-hoat-dong")
public class PhieuHoatDongController {

    @Autowired
    private PhieuHoatDongService phieuHoatDongService;

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private PhieuTraKhachService phieuTraKhachService;

    @Autowired
    private PhieuNhapHangService phieuNhapHangService;

    @Autowired
    private PhieuTraHangNhapService phieuTraHangNhapService;

    @Autowired
    private PhieuChiService phieuChiService;

    @Autowired
    private PhieuThuService phieuThuService;

    @Autowired
    private HoatDongService hoatDongService;


    @GetMapping("/find-all")
    ResponseEntity<JsonResult> findAllToPage(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                             @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<PhieuHoatDong> phieuHoatDongPageable = phieuHoatDongService.findAll(pageable);
        List<PhieuHoatDong> phieuHoatDongList = phieuHoatDongPageable.toList();
        List<PhieuHoatDongResponse> phieuHoatDongResponseList = new ArrayList<>();
        for (int i = 0; i < phieuHoatDongList.size(); i++) {
            PhieuHoatDongResponse phieuHoatDongResponse = new PhieuHoatDongResponse();
            PhieuHoatDong phieuHoatDongObj = phieuHoatDongList.get(i);
            phieuHoatDongResponse.setTenNguoiDung(phieuHoatDongObj.getNguoiDung().getHoVaTen());
            phieuHoatDongResponse.setNguoiDungId(phieuHoatDongObj.getNguoiDung().getId());
            phieuHoatDongResponse.setGiaTri(phieuHoatDongObj.getGiaTri());
            phieuHoatDongResponse.setThoiGian(phieuHoatDongObj.getThoiGian());
            phieuHoatDongResponse.setHoatDong(phieuHoatDongObj.getHoatDong().getTen());
            if (phieuHoatDongObj.getHoaDon() != null) {
                phieuHoatDongResponse.setMaPhieu(phieuHoatDongObj.getHoaDon().getMa());
                phieuHoatDongResponse.setPhieuId(phieuHoatDongObj.getHoaDon().getId());
            }

            if (phieuHoatDongObj.getPhieuTraKhach() != null) {
                phieuHoatDongResponse.setMaPhieu(phieuHoatDongObj.getPhieuTraKhach().getMa());
                phieuHoatDongResponse.setPhieuId(phieuHoatDongObj.getPhieuTraKhach().getId());
            }

            if (phieuHoatDongObj.getPhieuNhapHang() != null) {
                phieuHoatDongResponse.setMaPhieu(phieuHoatDongObj.getPhieuNhapHang().getMaPhieu());
                phieuHoatDongResponse.setPhieuId(phieuHoatDongObj.getPhieuNhapHang().getId());
            }

            if (phieuHoatDongObj.getPhieuTraHangNhap() != null) {
                phieuHoatDongResponse.setMaPhieu(phieuHoatDongObj.getPhieuTraHangNhap().getMaPhieu());
                phieuHoatDongResponse.setPhieuId(phieuHoatDongObj.getPhieuTraHangNhap().getId());
            }

            if (phieuHoatDongObj.getPhieuThu() != null) {
                phieuHoatDongResponse.setMaPhieu(phieuHoatDongObj.getPhieuThu().getMaPhieu());
                phieuHoatDongResponse.setPhieuId(phieuHoatDongObj.getPhieuThu().getId());
            }

            if (phieuHoatDongObj.getPhieuChi() != null) {
                phieuHoatDongResponse.setMaPhieu(phieuHoatDongObj.getPhieuChi().getMaPhieu());
                phieuHoatDongResponse.setPhieuId(phieuHoatDongObj.getPhieuChi().getId());
            }

            phieuHoatDongResponseList.add(phieuHoatDongResponse);
        }

        Page<PhieuHoatDongResponse> phieuHoatDongResponsepage = new PageImpl<>(phieuHoatDongResponseList, pageable, phieuHoatDongPageable.getTotalElements());
        return Optional.ofNullable(phieuHoatDongResponsepage)
                .map(phieuHoatDongs -> phieuHoatDongs.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phieuHoatDongs)) : JsonResult.notFound("PhieuHoatDong/Page"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }


    @GetMapping("/search")
    public ResponseEntity<JsonResult> search(@RequestParam(value = "ma-phieu", defaultValue = "", required = false) String maPhieu,
                                             @RequestParam(value = "ten-hoat-dong", defaultValue = "", required = false) String tenHoatDong,
                                             @RequestParam(name = "ngay-dau", defaultValue = "", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayDau,
                                             @RequestParam(name = "ngay-cuoi", defaultValue = "", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayCuoi,
                                             @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                             @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        if (maPhieu.equals("") && tenHoatDong.equals("") && ngayDau == null && ngayCuoi == null) {
            Page<PhieuHoatDong> phieuHoatDongPageable = phieuHoatDongService.findAll(pageable);
            List<PhieuHoatDong> phieuHoatDongList = phieuHoatDongPageable.toList();
            List<PhieuHoatDongResponse> phieuHoatDongResponseList = new ArrayList<>();
            for (int i = 0; i < phieuHoatDongList.size(); i++) {
                PhieuHoatDongResponse phieuHoatDongResponse = new PhieuHoatDongResponse();
                PhieuHoatDong phieuHoatDongObj = phieuHoatDongList.get(i);
                phieuHoatDongResponse.setTenNguoiDung(phieuHoatDongObj.getNguoiDung().getHoVaTen());
                phieuHoatDongResponse.setNguoiDungId(phieuHoatDongObj.getNguoiDung().getId());
                phieuHoatDongResponse.setGiaTri(phieuHoatDongObj.getGiaTri());
                phieuHoatDongResponse.setThoiGian(phieuHoatDongObj.getThoiGian());
                phieuHoatDongResponse.setHoatDong(phieuHoatDongObj.getHoatDong().getTen());
                if (phieuHoatDongObj.getHoaDon() != null) {
                    phieuHoatDongResponse.setMaPhieu(phieuHoatDongObj.getHoaDon().getMa());
                    phieuHoatDongResponse.setPhieuId(phieuHoatDongObj.getHoaDon().getId());
                }

                if (phieuHoatDongObj.getPhieuTraKhach() != null) {
                    phieuHoatDongResponse.setMaPhieu(phieuHoatDongObj.getPhieuTraKhach().getMa());
                    phieuHoatDongResponse.setPhieuId(phieuHoatDongObj.getPhieuTraKhach().getId());
                }

                if (phieuHoatDongObj.getPhieuNhapHang() != null) {
                    phieuHoatDongResponse.setMaPhieu(phieuHoatDongObj.getPhieuNhapHang().getMaPhieu());
                    phieuHoatDongResponse.setPhieuId(phieuHoatDongObj.getPhieuNhapHang().getId());
                }

                if (phieuHoatDongObj.getPhieuTraHangNhap() != null) {
                    phieuHoatDongResponse.setMaPhieu(phieuHoatDongObj.getPhieuTraHangNhap().getMaPhieu());
                    phieuHoatDongResponse.setPhieuId(phieuHoatDongObj.getPhieuTraHangNhap().getId());
                }

                if (phieuHoatDongObj.getPhieuThu() != null) {
                    phieuHoatDongResponse.setMaPhieu(phieuHoatDongObj.getPhieuThu().getMaPhieu());
                    phieuHoatDongResponse.setPhieuId(phieuHoatDongObj.getPhieuThu().getId());
                }

                if (phieuHoatDongObj.getPhieuChi() != null) {
                    phieuHoatDongResponse.setMaPhieu(phieuHoatDongObj.getPhieuChi().getMaPhieu());
                    phieuHoatDongResponse.setPhieuId(phieuHoatDongObj.getPhieuChi().getId());
                }

                phieuHoatDongResponseList.add(phieuHoatDongResponse);
            }

            Page<PhieuHoatDongResponse> phieuHoatDongResponsepage = new PageImpl<>(phieuHoatDongResponseList, pageable, phieuHoatDongPageable.getTotalElements());
            return Optional.ofNullable(phieuHoatDongResponsepage)
                    .map(phieuHoatDongs -> phieuHoatDongs.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phieuHoatDongs)) : JsonResult.notFound("PhieuHoatDong/MaPhieu/TenHoatDong"))
                    .orElse(JsonResult.serverError("Internal Server error"));
        } else {
            Page<PhieuHoatDong> phieuHoatDongPageable = phieuHoatDongService.findByMaPhieuAndTenHoatDongAndThoiGian(maPhieu, tenHoatDong, DateTimeUtils.asDate(ngayDau), DateTimeUtils.asDate(ngayCuoi), pageable);
            List<PhieuHoatDong> phieuHoatDongList = phieuHoatDongPageable.toList();
            List<PhieuHoatDongResponse> phieuHoatDongResponseList = new ArrayList<>();
            for (int i = 0; i < phieuHoatDongList.size(); i++) {
                PhieuHoatDongResponse phieuHoatDongResponse = new PhieuHoatDongResponse();
                PhieuHoatDong phieuHoatDongObj = phieuHoatDongList.get(i);
                phieuHoatDongResponse.setTenNguoiDung(phieuHoatDongObj.getNguoiDung().getHoVaTen());
                phieuHoatDongResponse.setNguoiDungId(phieuHoatDongObj.getNguoiDung().getId());
                phieuHoatDongResponse.setGiaTri(phieuHoatDongObj.getGiaTri());
                phieuHoatDongResponse.setThoiGian(phieuHoatDongObj.getThoiGian());
                phieuHoatDongResponse.setHoatDong(phieuHoatDongObj.getHoatDong().getTen());
                if (phieuHoatDongObj.getHoaDon() != null) {
                    phieuHoatDongResponse.setMaPhieu(phieuHoatDongObj.getHoaDon().getMa());
                    phieuHoatDongResponse.setPhieuId(phieuHoatDongObj.getHoaDon().getId());
                }

                if (phieuHoatDongObj.getPhieuTraKhach() != null) {
                    phieuHoatDongResponse.setMaPhieu(phieuHoatDongObj.getPhieuTraKhach().getMa());
                    phieuHoatDongResponse.setPhieuId(phieuHoatDongObj.getPhieuTraKhach().getId());
                }

                if (phieuHoatDongObj.getPhieuNhapHang() != null) {
                    phieuHoatDongResponse.setMaPhieu(phieuHoatDongObj.getPhieuNhapHang().getMaPhieu());
                    phieuHoatDongResponse.setPhieuId(phieuHoatDongObj.getPhieuNhapHang().getId());
                }

                if (phieuHoatDongObj.getPhieuTraHangNhap() != null) {
                    phieuHoatDongResponse.setMaPhieu(phieuHoatDongObj.getPhieuTraHangNhap().getMaPhieu());
                    phieuHoatDongResponse.setPhieuId(phieuHoatDongObj.getPhieuTraHangNhap().getId());
                }

                if (phieuHoatDongObj.getPhieuThu() != null) {
                    phieuHoatDongResponse.setMaPhieu(phieuHoatDongObj.getPhieuThu().getMaPhieu());
                    phieuHoatDongResponse.setPhieuId(phieuHoatDongObj.getPhieuThu().getId());
                }

                if (phieuHoatDongObj.getPhieuChi() != null) {
                    phieuHoatDongResponse.setMaPhieu(phieuHoatDongObj.getPhieuChi().getMaPhieu());
                    phieuHoatDongResponse.setPhieuId(phieuHoatDongObj.getPhieuChi().getId());
                }

                phieuHoatDongResponseList.add(phieuHoatDongResponse);
            }

            Page<PhieuHoatDongResponse> phieuHoatDongResponsepage = new PageImpl<>(phieuHoatDongResponseList, pageable, phieuHoatDongPageable.getTotalElements());
            return Optional.ofNullable(phieuHoatDongResponsepage)
                    .map(phieuHoatDongs -> phieuHoatDongs.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phieuHoatDongs)) : JsonResult.notFound("PhieuHoatDong/MaPhieu/TenHoatDong"))
                    .orElse(JsonResult.serverError("Internal Server error"));
        }

    }

    @PostMapping("/upload")
    @ApiOperation(value = "post phieu hoat dong", response = PhieuHoatDong.class)
    public ResponseEntity<JsonResult> post(@RequestBody PhieuHoatDong phieuHoatDong,//gia-tri:tongTien
                                           @RequestParam(name = "nhan-vien-id", defaultValue = "0") int nguoiDungId,
                                           @RequestParam(name = "hoa-don-id", defaultValue = "0") int hoaDonId,
                                           @RequestParam(name = "phieu-tra-khach-id", defaultValue = "0") int phieuTraKhachId,
                                           @RequestParam(name = "phieu-nhap-hang-id", defaultValue = "0") int phieuNhapHangId,
                                           @RequestParam(name = "phieu-tra-hang-nhap-id", defaultValue = "0") int phieuTraHangNhapId,
                                           @RequestParam(name = "phieu-thu-id", defaultValue = "0") int phieuThuId,
                                           @RequestParam(name = "phieu-chi-id", defaultValue = "0") int phieuChiId,
                                           @RequestParam(name = "hoat-dong-id", defaultValue = "0") int hoatDongId) {

        try {
            if (nguoiDungId != 0) {
                phieuHoatDong.setNguoiDung(nguoiDungService.findById(nguoiDungId, false).get());
            }
            if (hoaDonId != 0) {
                phieuHoatDong.setHoaDon(hoaDonService.findById(hoaDonId, false).get());
            }
            if (phieuTraKhachId != 0) {
                phieuHoatDong.setPhieuTraKhach(phieuTraKhachService.findByIdAndXoa(phieuTraKhachId, false).get());
            }
            if (phieuNhapHangId != 0) {
                phieuHoatDong.setPhieuNhapHang(phieuNhapHangService.findById(phieuNhapHangId).get());
            }
            if (phieuTraHangNhapId != 0) {
                phieuHoatDong.setPhieuTraHangNhap(phieuTraHangNhapService.findByIdAndXoa(phieuTraHangNhapId, false).get());
            }
            if (phieuThuId != 0) {
                phieuHoatDong.setPhieuThu(phieuThuService.findByIdAndXoa(phieuThuId, false).get());
            }
            if (phieuChiId != 0) {
                phieuHoatDong.setPhieuChi(phieuChiService.findByIdAndXoa(phieuChiId, false).get());
            }
            if (hoatDongId != 0) {
                phieuHoatDong.setHoatDong(hoatDongService.findById(hoatDongId).get());
            }
            LocalDateTime now = LocalDateTime.now();
            Date out = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
            phieuHoatDong.setThoiGian(out);
            return phieuHoatDongService.save(phieuHoatDong)
                    .map(JsonResult::uploaded)
                    .orElse(JsonResult.serverError("Internal Server Error"));
        } catch (Exception ex) {
            System.out.println(ex);
            return JsonResult.badRequest("PhieuHoatDong");
        }
    }
}
