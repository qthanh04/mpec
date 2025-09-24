package com.tavi.tavi_mrs.controller.phieu_tra_khach;

import com.tavi.tavi_mrs.entities.bao_cao.ExcelFile;
import com.tavi.tavi_mrs.entities.constant.DatabaseConstant;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.entities.phieu_nhap_hang.PhieuNhapHang;
import com.tavi.tavi_mrs.entities.phieu_nhap_hang.PhieuNhapHangChiTiet;
import com.tavi.tavi_mrs.entities.phieu_tra_khach.PhieuTraKhach;
import com.tavi.tavi_mrs.entities.phieu_tra_khach.PhieuTraKhachChiTiet;
import com.tavi.tavi_mrs.payload.nha_cung_cap.nhap_hang.TienTraNhaCungCap;
import com.tavi.tavi_mrs.payload.nha_cung_cap.nhap_hang.TienTraNhaCungCapList;
import com.tavi.tavi_mrs.payload.phieu_tra_khach.TienTraKhach;
import com.tavi.tavi_mrs.payload.phieu_tra_khach.TienTraKhachList;
import com.tavi.tavi_mrs.service.aws.AWSS3Service;
import com.tavi.tavi_mrs.service.bao_cao.ExcelFileService;
import com.tavi.tavi_mrs.service.mail.MailService;
import com.tavi.tavi_mrs.service.nguoi_dung.NguoiDungService;
import com.tavi.tavi_mrs.service.phieu_tra_khach.PhieuTraKhachChiTietService;
import com.tavi.tavi_mrs.service.phieu_tra_khach.PhieuTraKhachService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/phieu-tra-khach")
public class PhieuTraKhachController {

    @Autowired
    private PhieuTraKhachService phieuTraKhachService;

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private PhieuTraKhachChiTietService phieuTraKhachChiTietService;

    @Autowired
    private AWSS3Service awss3Service;

    @Autowired
    private ExcelFileService excelFileService;

    @Autowired
    private MailService mailService;

    @GetMapping("/find-all")
    public ResponseEntity<JsonResult> findAll(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                              @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PhieuTraKhach> phieuTraKhachPage = phieuTraKhachService.findAll(pageable);
        return Optional.ofNullable(phieuTraKhachPage)
                .map(phieuTraKhaches -> phieuTraKhaches.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phieuTraKhaches)) : JsonResult.notFound("PhieuTraKhach/Page"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @GetMapping("/find-by-nguoi-dung")
    public ResponseEntity<JsonResult> findByNguoiDung(@RequestParam("nguoid-dung-id") int nguoiDungId,
                                                      @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                      @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PhieuTraKhach> phieuTraKhachPage = phieuTraKhachService.findByNguoiDung(nguoiDungId, pageable);
        return Optional.ofNullable(phieuTraKhachPage)
                .map(phieuTraKhaches -> phieuTraKhaches.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phieuTraKhaches)) : JsonResult.notFound("PhieuTraKhach/Page"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findById(@RequestParam("id") int id) {
        return phieuTraKhachService.findByIdAndXoa(id, false)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/search")
    public ResponseEntity<JsonResult> search(@RequestParam(value = "ngay-dau", defaultValue = "1970-01-01", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayDau,
                                             @RequestParam(value = "ngay-cuoi", defaultValue = "2099-12-31", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayCuoi,
                                             @RequestParam(value = "trang-thai", defaultValue = "1", required = false) int trangThai,
                                             @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                             @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PhieuTraKhach> phieuTraKhachPage = phieuTraKhachService.findByThoiGianAndTrangThai(ngayDau, ngayCuoi, trangThai, pageable);
        return Optional.ofNullable(phieuTraKhachPage)
                .map(phieuTraKhaches -> phieuTraKhaches.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phieuTraKhaches)) : JsonResult.notFound("PhieuTraKhach/Page"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @PostMapping("/upload")
    public ResponseEntity<JsonResult> upload(@RequestParam("nguoi-dung-id") int nguoiDungId,
                                             @RequestBody PhieuTraKhach phieuTraKhach) {
        return nguoiDungService.findById(nguoiDungId, false)
                .map(nguoiDung -> {
                    phieuTraKhach.setNguoiDung(nguoiDung);
                    LocalDateTime now = LocalDateTime.now();
                    Date out = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
                    phieuTraKhach.setThoiGian(out);
                    phieuTraKhach.setTrangThai(1);
                    phieuTraKhach.setXoa(false);
                    phieuTraKhach.setTienPhaiTra(phieuTraKhach.getTienPhaiTra());
                    phieuTraKhach.setConNo(phieuTraKhach.getTienPhaiTra() - phieuTraKhach.getTienDaTra());
                    phieuTraKhach.setGiamGia(phieuTraKhach.getGiamGia());
                    phieuTraKhach.setGhiChu(phieuTraKhach.getGhiChu());
                    phieuTraKhach.setLyDo(phieuTraKhach.getLyDo());
                    return phieuTraKhachService.save(phieuTraKhach)
                            .map(JsonResult::uploaded)
                            .orElse(JsonResult.saveError("PhieuTraKhach"));
                }).orElse(JsonResult.parentNotFound("NguoiDung"));
    }

    @GetMapping("/pdf/{ma}")
    public ResponseEntity<JsonResult> createPDF(@PathVariable("ma") String ma,
                                                @RequestParam("nguoi-dung-id") int nguoiDungId) {
        return phieuTraKhachService.findByMa(ma)
                .map(phieuTraKhach -> {
                    List<PhieuTraKhachChiTiet> phieuTraKhachChiTietList = phieuTraKhachChiTietService.findListByPhieuTraKhachId(phieuTraKhach.getId());
                    File file = phieuTraKhachService.createPDF(phieuTraKhach, phieuTraKhachChiTietList);
                    return nguoiDungService.findById(nguoiDungId, false)
                            .map(nguoiDung -> {
                                String urlFile = awss3Service.uploadFileExcel(file);
                                file.delete();
                                String url = DatabaseConstant.URL.AWS_S3 + urlFile;
                                String email = phieuTraKhachChiTietList.get(0).getHoaDonChiTiet().getHoaDon().getKhachHang().getEmail();
                                if (email != null && !email.equals("")) {
                                    String header = "Xác nhận trả hàng";
                                    String content = "Khách hàng : " + phieuTraKhachChiTietList.get(0).getHoaDonChiTiet().getHoaDon().getKhachHang().getTenKhachHang()
                                            + "\nPhiếu trả hàng : " + url
                                            + "\nXin lỗi quý khách vì sự bất tiện này";
                                    String[] mails = {email};
                                    mailService.sendMail(mails, header, content);
                                }
                                ExcelFile excelFile = new ExcelFile();
                                excelFile.setUrl(url);
                                excelFile.setMaPhieu(phieuTraKhach.getMa());
                                excelFile.setKieuFile(DatabaseConstant.TypeOfFile.PDF_FILE);
                                excelFile.setTenLoai(DatabaseConstant.File.PDF.PHIEU_TRA_KHACH_PDF_FILE);
                                excelFile.setNguoiDung(nguoiDung);
                                return excelFileService.save(excelFile)
                                        .map(JsonResult::uploaded)
                                        .orElse(JsonResult.serverError("Internal Server Error"));
                            }).orElse(JsonResult.parentNotFound("NguoiDung"));
                }).orElse(JsonResult.parentNotFound("PhieuTraKhach"));
    }

    @PutMapping("/update-tien-tra-khach")
    @ApiOperation(value = "update tien tra khach vi 1 phieu tra khach co the tra nhieu lan")
    public ResponseEntity<JsonResult> updateTienTraKhach(@RequestBody TienTraKhachList tienTraKhachList) {
        List<TienTraKhach> tienTraKhaches = tienTraKhachList.getTienTraKhachList();
        try {
            for (int i = 0; i < tienTraKhaches.size(); i++) {
                Optional<PhieuTraKhach> phieuTraKhach = phieuTraKhachService.findByIdAndXoa(tienTraKhaches.get(i).getPhieuTraKhachId(), false);
                phieuTraKhachService.updateTienTraKhach(phieuTraKhach.get().getTienDaTra() + tienTraKhaches.get(i).getTienDaTra(),
                        tienTraKhaches.get(i).getPhieuTraKhachId(), phieuTraKhach.get().getTienPhaiTra() - (phieuTraKhach.get().getTienDaTra() + tienTraKhaches.get(i).getTienDaTra()));
            }
            return JsonResult.success("Update Tien tra khach thanh cong");
        }catch (Exception ex) {
            return JsonResult.serverError("Update Tien tra khach error");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<JsonResult> update(@RequestBody PhieuTraKhach phieuTraKhach) {
        return phieuTraKhachService.save(phieuTraKhach)
                .map(JsonResult::uploaded)
                .orElse(JsonResult.saveError("save-phieu-tra-khach"));
    }

    @PutMapping("/trang-thai")
    public ResponseEntity<JsonResult> setTrangThai(@RequestParam("id") int id,
                                                   @RequestParam("trang-thai") int trangThai) {
        Boolean bool = phieuTraKhachService.setTrangThai(id, trangThai);
        if (bool) {
            return JsonResult.success("Thay doi trang thai thanh cong");
        } else {
            return JsonResult.serverError("Thay doi trang thai that bai");
        }
    }
}
