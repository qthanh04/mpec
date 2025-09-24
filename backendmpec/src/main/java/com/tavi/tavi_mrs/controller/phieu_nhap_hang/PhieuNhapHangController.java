package com.tavi.tavi_mrs.controller.phieu_nhap_hang;

import com.tavi.tavi_mrs.entities.bao_cao.ExcelFile;
import com.tavi.tavi_mrs.entities.constant.DatabaseConstant;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.entities.nha_cung_cap.NhaCungCapDto;
import com.tavi.tavi_mrs.entities.phieu_nhap_hang.PhieuNhapHang;
import com.tavi.tavi_mrs.entities.phieu_nhap_hang.PhieuNhapHangChiTiet;
import com.tavi.tavi_mrs.payload.nha_cung_cap.nhap_hang.TienTraNhaCungCap;
import com.tavi.tavi_mrs.payload.nha_cung_cap.nhap_hang.TienTraNhaCungCapList;
import com.tavi.tavi_mrs.service.aws.AWSS3Service;
import com.tavi.tavi_mrs.service.bao_cao.ExcelFileService;
import com.tavi.tavi_mrs.service.mail.MailService;
import com.tavi.tavi_mrs.service.nguoi_dung.NguoiDungService;
import com.tavi.tavi_mrs.service.nha_cung_cap.NhaCungCapService;
import com.tavi.tavi_mrs.service.phieu_nhap_hang.PhieuNhapHangChiTietService;
import com.tavi.tavi_mrs.service.phieu_nhap_hang.PhieuNhapHangService;
import com.tavi.tavi_mrs.utils.DateTimeUtils;
import com.tavi.tavi_mrs.utils.ExcelUtils;
import com.tavi.tavi_mrs.utils.Random;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/phieu-nhap-hang")
public class PhieuNhapHangController {

    @Autowired
    private PhieuNhapHangService phieuNhapHangService;

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private NhaCungCapService nhaCungCapService;

    @Autowired
    private PhieuNhapHangChiTietService phieuNhapHangChiTietService;

    @Autowired
    private AWSS3Service awss3Service;

    @Autowired
    private ExcelFileService excelFileService;

    @Autowired
    private MailService mailService;


    private String UPLOAD_DIRECTORY = DatabaseConstant.Directory.URL_DIRECTORY;

    @GetMapping("/find-all")
    public ResponseEntity<JsonResult> findAll(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                              @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PhieuNhapHang> phieuNhapHangPage = phieuNhapHangService.findAll(pageable);
        return Optional.ofNullable(phieuNhapHangPage)
                .map(phieuNhapHangs -> phieuNhapHangPage.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phieuNhapHangPage)) : JsonResult.notFound("PhieuNhapHang/Page"))
                .orElse(JsonResult.serverError("Internal Server Error!"));
    }

    @GetMapping("find-by-id-nha-cung-cap")
    public ResponseEntity<JsonResult> findByIdNhaCungCap(@RequestParam("nha-cung-cap-id") int idNhaCungCap,
                                                         @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                         @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PhieuNhapHang> phieuNhapHangPage = phieuNhapHangService.findByIdNhaCungCap(idNhaCungCap, false, pageable);
        return Optional.ofNullable(phieuNhapHangPage)
                .map(phieuNhapHangs -> phieuNhapHangPage.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phieuNhapHangs)) : JsonResult.notFound("PhieuNhapHang/NhaCungCap/Page"))
                .orElse(JsonResult.serverError("Internal Server Error!"));
    }

    @PostMapping("/upload")
    @ApiOperation(value = "post phieu nhap hang ", response = PhieuNhapHang.class)
    public ResponseEntity<JsonResult> post(@RequestBody PhieuNhapHang phieuNhapHang,
                                           @RequestParam("nguoi-dung-id") int nguoiDungId,
                                           @RequestParam("nha-cung-cap-id") int nhaCungCapId

    ) {
        return nguoiDungService.findById(nguoiDungId, false)
                .map(nguoiDung -> {
                    phieuNhapHang.setNguoiDung(nguoiDung);
                    return nhaCungCapService.findByIdAndXoa(nhaCungCapId, false)
                            .map(nhaCungCap -> {
                                LocalDateTime now = LocalDateTime.now();
                                Date out = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
                                phieuNhapHang.setThoiGian(out);
                                phieuNhapHang.setNhaCungCap(nhaCungCap);
                                phieuNhapHang.setXoa(false);
                                phieuNhapHang.setTienPhaiTra(phieuNhapHang.getTienPhaiTra());
                                phieuNhapHang.setConNo(phieuNhapHang.getTienPhaiTra()- phieuNhapHang.getTienDaTra());
                                phieuNhapHang.setGiamGia(phieuNhapHang.getGiamGia());
                                phieuNhapHang.setGhiChu(phieuNhapHang.getGhiChu());
                                return phieuNhapHangService.save(phieuNhapHang)
                                        .map(JsonResult::uploaded)
                                        .orElse(JsonResult.saveError("PhieuNhapHang"));
                            }).orElse(JsonResult.parentNotFound("NhaCungCap"));
                }).orElse(JsonResult.parentNotFound("NguoiDung"));
    }

    @PutMapping("/update")
    @ApiOperation(value = "post phieu nhap hang ", response = PhieuNhapHang.class)
    public ResponseEntity<JsonResult> put(@RequestBody PhieuNhapHang phieuNhapHang,
                                          @RequestParam("nguoi-dung-id") int nugoiDungId,
                                          @RequestParam("nha-cung-cap-id") int nhaCungCapId

    ) {
        return nguoiDungService.findById(nugoiDungId, false)
                .map(nguoiDung -> {
                    phieuNhapHang.setNguoiDung(nguoiDung);
                    return nhaCungCapService.findByIdAndXoa(nhaCungCapId, false)
                            .map(nhaCungCap -> {
                                phieuNhapHang.setNhaCungCap(nhaCungCap);
                                LocalDateTime now = LocalDateTime.now();
                                Date out = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
                                phieuNhapHang.setThoiGian(out);
                                return phieuNhapHangService.save(phieuNhapHang)
                                        .map(JsonResult::updated)
                                        .orElse(JsonResult.saveError("PhieuNhapHang "));
                            }).orElse(JsonResult.parentNotFound("NhaCungCap"));
                }).orElse(JsonResult.parentNotFound("NguoiDung"));
    }

    @PutMapping("/update-tien-tra-nha-cung-cap")
    @ApiOperation(value = "update tien tra nha cung cap vi 1 phieu nhap co the tra nhieu lan")
    public ResponseEntity<JsonResult> updateTienTraNhaCungCap(@RequestBody TienTraNhaCungCapList tienTraNhaCungCapList) {
        List<TienTraNhaCungCap> tienTraNhaCungCaps = tienTraNhaCungCapList.getTienTraNhaCungCaps();
        try {
            for (int i = 0; i < tienTraNhaCungCaps.size(); i++) {
                Optional<PhieuNhapHang> phieuNhapHang = phieuNhapHangService.findById(tienTraNhaCungCaps.get(i).getPhieuNhapHangId());
                phieuNhapHangService.updateTienTraNhaCungCap(phieuNhapHang.get().getTienDaTra() + tienTraNhaCungCaps.get(i).getTienDaTra(),
                        tienTraNhaCungCaps.get(i).getPhieuNhapHangId(), phieuNhapHang.get().getTienPhaiTra() - (phieuNhapHang.get().getTienDaTra() + tienTraNhaCungCaps.get(i).getTienDaTra()));
            }
            return JsonResult.success("Update Tien tra nha cung cap thanh cong");
        }catch (Exception ex) {
            return JsonResult.serverError("Update Tien tra nha cung cap error");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<JsonResult> search(@RequestParam(value = "ma-phieu-nhap", required = false, defaultValue = "") String maPhieu,
                                        @RequestParam(name = "ngay-dau", defaultValue = "", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayDau,
                                        @RequestParam(name = "ngay-cuoi", defaultValue = "", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayCuoi,
                                        @RequestParam(name = "nha-cung-cap-id", defaultValue = "0", required = false) int nhaCungCapId,
                                        @RequestParam(name = "trang-thai", defaultValue = "-1", required = false) int trangThai,
                                        @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                        @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PhieuNhapHang> phieuNhapHangPage;
        if (maPhieu.equals("") && ngayDau == null && ngayCuoi == null && nhaCungCapId == 0 && trangThai == -1) {
            phieuNhapHangPage = phieuNhapHangService.findAll(pageable);
        } else {
            phieuNhapHangPage = phieuNhapHangService.findByNhaCungCapAndThoiGianAndMaPhieu(maPhieu, DateTimeUtils.asDate(ngayDau), DateTimeUtils.asDate(ngayCuoi), nhaCungCapId,trangThai, pageable);
        }
        return Optional.ofNullable(phieuNhapHangPage)
                .map(phieuNhapHangs -> phieuNhapHangs.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phieuNhapHangs)) : JsonResult.notFound("PhieuNhap/MaPhieu/ThoiGian/NhaCungCap"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @PutMapping("/trang-thai")
    @ApiOperation(value = "thay doi trang thai phieu nhap")
    public ResponseEntity<JsonResult> setTrangThai(@RequestParam("id") int id,
                                                   @RequestParam("trang-thai") Integer trangThai) {
        if (phieuNhapHangService.setTrangThai(id, trangThai))
            return JsonResult.success("Thay doi trang thai thanh cong");
        else return JsonResult.serverError("Thay doi trang thai that bai");
    }



    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findById(@RequestParam("id") int phieuNhapHangId) {
        return phieuNhapHangService.findById(phieuNhapHangId)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/pdf/{ma}")
    public ResponseEntity<JsonResult> createPDF(@PathVariable("ma") String ma,
                                                @RequestParam("nguoi-dung-id") int nguoiDungId) {
        return phieuNhapHangService.findByMaPhieu(ma)
                .map(phieuNhapHang -> {
                    List<PhieuNhapHangChiTiet> phieuNhapHangChiTietList = phieuNhapHangChiTietService.findListByPhieuNhapId(phieuNhapHang.getId());
                    File file = phieuNhapHangService.createPdf(phieuNhapHang, phieuNhapHangChiTietList);
                    return nguoiDungService.findById(nguoiDungId, false)
                            .map(nguoiDung -> {
                                String urlFile = awss3Service.uploadFileExcel(file);
                                file.delete();
                                String url = DatabaseConstant.URL.AWS_S3 + urlFile;
                                if (phieuNhapHang.getNhaCungCap().getEmail() != null && !phieuNhapHang.getNhaCungCap().getEmail().equals("")) {
                                    String header = "Xác nhận nhập hàng";
                                    String content = "Nhà cung cấp : " + phieuNhapHang.getNhaCungCap().getTen()
                                            + "\nPhiếu nhập hàng : " + url
                                            + "\nCảm ơn vì sự hợp tác";
                                    String[] mails = {phieuNhapHang.getNhaCungCap().getEmail()};
                                    mailService.sendMail(mails, header, content);
                                }
                                ExcelFile excelFile = new ExcelFile();
                                excelFile.setMaPhieu(phieuNhapHang.getMaPhieu());
                                excelFile.setUrl("https://mpec.s3.us-east-2.amazonaws.com/" + urlFile);
                                excelFile.setKieuFile(DatabaseConstant.TypeOfFile.PDF_FILE);
                                excelFile.setTenLoai(DatabaseConstant.File.PDF.PHIEU_NHAP_PDF_FILE);
                                excelFile.setNguoiDung(nguoiDung);
                                return excelFileService.save(excelFile)
                                        .map(JsonResult::uploaded)
                                        .orElse(JsonResult.serverError("Internal Server Error"));
                            }).orElse(JsonResult.parentNotFound("NguoiDung"));
                }).orElse(JsonResult.parentNotFound("HoaDon"));
    }


//    @GetMapping("find-by-chi-nhanh")
//    public ResponseEntity<JsonResult> findByChiNhanhAndText(@RequestParam(value = "chi-nhanh-id", defaultValue = "0", required = false) int chiNhanhId,
//                                                            @RequestParam(value = "text", defaultValue = "", required = false) String text,
//                                                            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
//                                                            @RequestParam(name = "size", defaultValue = "10", required = false) int size){
//        Pageable pageable = PageRequest.of(page-1,size);
//        Page<PhieuNhapHang> phieuNhapHangPage = phieuNhapHangService.findByChiNhanhAndText(chiNhanhId,text, pageable);
//        return Optional.ofNullable(phieuNhapHangPage)
//                .map(phieuNhapHangs -> phieuNhapHangs.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phieuNhapHangs)) : JsonResult.notFound("PhieuNhapHang/Search"))
//                .orElse(JsonResult.serverError("Internal Server Error"));
//    }

    @DeleteMapping("/delete")
    public ResponseEntity<JsonResult> delete(@RequestParam("id") int id) {
        Optional<PhieuNhapHang> phieuNhapHang = phieuNhapHangService.findById(id);
        if (phieuNhapHang.isPresent()) {
            Boolean bool = phieuNhapHangService.deleted(id);
            if (bool) {
                return JsonResult.deleted();
            }
            return JsonResult.saveError("phieuNhapHang");
        } else {
            return JsonResult.idNotFound();
        }
    }

    @GetMapping("/excel")
    public ResponseEntity<JsonResult> getExcel(@RequestParam("nguoi-dung-id") int nguoiDungId,
                                               @RequestParam("list-phieu-nhap-hang") List<Integer> list) {
        return nguoiDungService.findById(nguoiDungId, false)
                .map(nguoiDung -> {
                    ExcelFile excelFile = new ExcelFile();
                    excelFile.setNguoiDung(nguoiDung);
                    List<PhieuNhapHang> listPNH = phieuNhapHangService.findListHoaDon(list);
                    XSSFWorkbook workbook = ExcelUtils.createDanhSachPhieuNhapExcel(listPNH);
                    try {
                        String fileName = "ListPNH_" + LocalDateTime.now().getNano() + ".xlsx";
                        File file = new File(UPLOAD_DIRECTORY + fileName);
                        file.getParentFile().mkdirs();
                        FileOutputStream outFile = new FileOutputStream(file);
                        workbook.write(outFile);
                        String urlFile = awss3Service.uploadFileExcel(file);
                        file.delete();
                        excelFile.setKieuFile(DatabaseConstant.TypeOfFile.EXCEL_FILE);
                        excelFile.setTenLoai(DatabaseConstant.File.Excel.PHIEU_NHAP_EXCEL_FILE);
                        excelFile.setMaPhieu("DSPNH-"+ Random.randomCode());
                        excelFile.setUrl("https://mpec.s3.us-east-2.amazonaws.com/" + urlFile);
                        outFile.close();
                        return excelFileService.save(excelFile)
                                .map(JsonResult::uploaded)
                                .orElse(JsonResult.serverError("Internal Server Error"));
                    } catch (IOException e) {
                        return JsonResult.badRequest("Create Excel fail");
                    }
                }).orElse(JsonResult.parentNotFound("NguoiDung"));
    }

    @GetMapping("/top-nha-cung-cap-theo-thang")
    ResponseEntity<JsonResult> findTopNhaCungCapTheoThang(
            @RequestParam(name = "ngay-dau", defaultValue = "1970-01-01T00:00:00", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayDau,
            @RequestParam(name = "ngay-cuoi", defaultValue = "9999-12-31T00:00:00", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayCuoi,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<NhaCungCapDto> phieuNhapHang;
        phieuNhapHang = phieuNhapHangService.topNhaCungCapTheoThang(DateTimeUtils.asDate(ngayDau), DateTimeUtils.asDate(ngayCuoi), pageable);
        return Optional.ofNullable(phieuNhapHang)
                .map(phieuNhapHangs -> phieuNhapHangs.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phieuNhapHangs)) : JsonResult.notFound("Nha cung cap"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/search-top-nha-cung-cap-theo-thang")
    ResponseEntity<JsonResult> searchTopNhaCungCapTheoThang(
            @RequestParam(name = "ngay-dau", defaultValue = "1970-01-01T00:00:00", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayDau,
            @RequestParam(name = "ngay-cuoi", defaultValue = "9999-12-31T00:00:00", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayCuoi,
            @RequestParam(value = "text", defaultValue = "") String text,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<NhaCungCapDto> phieuNhapHang;
        phieuNhapHang = phieuNhapHangService.searchTopNhaCungCapTheoThang(DateTimeUtils.asDate(ngayDau), DateTimeUtils.asDate(ngayCuoi), text, pageable);
        return Optional.ofNullable(phieuNhapHang)
                .map(phieuNhapHangs -> phieuNhapHangs.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phieuNhapHangs)) : JsonResult.notFound("Nha cung cap"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

}
