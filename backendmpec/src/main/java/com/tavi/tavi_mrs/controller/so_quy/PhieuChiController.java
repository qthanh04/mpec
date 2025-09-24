package com.tavi.tavi_mrs.controller.so_quy;

import com.tavi.tavi_mrs.entities.bao_cao.ExcelFile;
import com.tavi.tavi_mrs.entities.constant.DatabaseConstant;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.entities.so_quy.PhieuChi;
import com.tavi.tavi_mrs.entities.so_quy.PhieuThu;
import com.tavi.tavi_mrs.payload.so_quy.PhieuChiRequest;
import com.tavi.tavi_mrs.service.aws.AWSS3Service;
import com.tavi.tavi_mrs.service.bao_cao.ExcelFileService;
import com.tavi.tavi_mrs.service.chi_nhanh.ChiNhanhService;
import com.tavi.tavi_mrs.service.hoa_don.HoaDonService;
import com.tavi.tavi_mrs.service.nguoi_dung.NguoiDungService;
import com.tavi.tavi_mrs.service.phieu_nhap_hang.PhieuNhapHangService;
import com.tavi.tavi_mrs.service.phieu_tra_khach.PhieuTraKhachService;
import com.tavi.tavi_mrs.service.so_quy.LoaiChiService;
import com.tavi.tavi_mrs.service.so_quy.PhieuChiService;
import com.tavi.tavi_mrs.utils.DateTimeUtils;
import com.tavi.tavi_mrs.utils.ExcelUtils;
import com.tavi.tavi_mrs.utils.Random;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/phieu-chi")
public class PhieuChiController {

    @Autowired
    PhieuChiService phieuChiService;

    @Autowired
    ChiNhanhService chiNhanhService;

    @Autowired
    NguoiDungService nguoiDungService;

    @Autowired
    LoaiChiService loaiChiService;

    @Autowired
    PhieuNhapHangService phieuNhapHangService;

    @Autowired
    PhieuTraKhachService phieuTraKhachService;

    @Autowired
    HoaDonService hoaDonService;

    private String UPLOAD_DIRECTORY = DatabaseConstant.Directory.URL_DIRECTORY;

    @Autowired
    private AWSS3Service awss3Service;

    @Autowired
    private ExcelFileService excelFileService;

    @GetMapping("/find-all-to-page")
    public ResponseEntity<JsonResult> findAll(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                              @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PhieuChi> phieuChiPage = phieuChiService.findAllToPage(pageable);
        return Optional.ofNullable(phieuChiPage)
                .map(phieuChis -> phieuChis.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phieuChis)) : JsonResult.notFound("PhieuChi/Page"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findById(@RequestParam("id") int id) {
        return phieuChiService.findByIdAndXoa(id, false)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/search")
    public ResponseEntity<JsonResult> search(@RequestParam(value = "chi-nhanh-id", required = false, defaultValue = "0") int chiNhanhId,
                                             @RequestParam(value = "ma-phieu-chi", required = false, defaultValue = "") String maPhieuChi,
                                             @RequestParam(value = "ten-loai-chi", required = false, defaultValue = "") String tenLoaiChi,
                                             @RequestParam(value = "ten-nhan-vien", required = false, defaultValue = "") String tenNhanVien,
                                             @RequestParam(name = "ngay-dau", defaultValue = "", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayDau,
                                             @RequestParam(name = "ngay-cuoi", defaultValue = "", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayCuoi,
                                             @RequestParam(name = "trang-thai", defaultValue = "-1", required = false) int trangThai,
                                             @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                             @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PhieuChi> phieuChiPage;
        if (chiNhanhId == 0 && maPhieuChi.equals("") && tenLoaiChi.equals("") && tenNhanVien.equals("") && ngayDau == null && ngayCuoi == null && trangThai == -1) {
            phieuChiPage = phieuChiService.findAllToPage(pageable);
        } else {
            phieuChiPage = phieuChiService.findByMaPhieuChiAndThoiGianAndTrangThai(chiNhanhId, maPhieuChi, tenLoaiChi, tenNhanVien, DateTimeUtils.asDate(ngayDau), DateTimeUtils.asDate(ngayCuoi), trangThai, pageable);
        }
        return Optional.ofNullable(phieuChiPage)
                .map(phieuChis -> phieuChis.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phieuChis)) : JsonResult.notFound("PhieuChi/MaPhieuChi/ThoiGian/TrangThai"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @GetMapping("/search-by-chi-nhanh-and-text")
    public ResponseEntity<JsonResult> searchByChiNhanhAndText(@RequestParam(value = "chi-nhanh-id", required = false,defaultValue = "0") int chiNhanhId,
                                                              @RequestParam(value = "text", required = false, defaultValue = "") String text,
                                                              @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                              @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page -1, size);
        Page<PhieuChi> phieuChiPage;
        if (chiNhanhId == 0 && text.equals("")) {
            phieuChiPage = phieuChiService.findAllToPage(pageable);
        } else {
            phieuChiPage = phieuChiService.findByChiNhanhAndText(chiNhanhId, text, pageable);
        }
        return Optional.ofNullable(phieuChiPage)
                .map(phieuChis -> phieuChis.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phieuChis)) : JsonResult.notFound("PhieuChi"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @PutMapping("/trang-thai")
    @ApiOperation(value = "thay doi trang thai hoa don")
    public ResponseEntity<JsonResult> setTrangThai(@RequestParam("id") int id,
                                                   @RequestParam("trang-thai") int trangThai) {
        if (phieuChiService.setTrangThai(id, trangThai))
            return JsonResult.success("Thay doi trang thai thanh cong");
        else return JsonResult.serverError("PhieuChi");
    }

    //upload 1 phieu chi khi tao 1 phieu nhap hang ( loai chi = 1 )
    @PostMapping("/upload")
    @ApiOperation(value = "post Phieu chi", response = PhieuChi.class)
    public ResponseEntity<JsonResult> save(@RequestBody PhieuChiRequest phieuChiRequest,
                                           @RequestParam("chi-nhanh-id") int chiNhanhId,
                                           @RequestParam("nguoi-dung-id") int nguoiDungId,
                                           @RequestParam(value = "loai-chi-id", defaultValue = "1") int loaiChiId) {
        PhieuChi phieuChi = new PhieuChi();
        return chiNhanhService.findByIdAndXoa(chiNhanhId,false)
                .map(chiNhanh -> {
                    phieuChi.setChiNhanh(chiNhanh);
                    return nguoiDungService.findById(nguoiDungId, false)
                            .map(nguoiDung -> {
                                phieuChi.setNguoiDung(nguoiDung);
                                return loaiChiService.findById(loaiChiId, false)
                                        .map(loaiChi -> {
                                            phieuChi.setLoaiChi(loaiChi);
                                            phieuChi.setDaTra(phieuChiRequest.getTienDaTra());
                                            phieuChi.setGhiChu(phieuChiRequest.getGhiChu());
                                            String ma = loaiChi.getMaLoaiChi() + "-" + Random.randomCode();
                                            phieuChi.setMaPhieu(ma);
                                            phieuChi.setTrangThai(0); //( 0 = da tra)
                                            return phieuChiService.save(phieuChi)
                                                    .map(JsonResult::uploaded)
                                                    .orElse(JsonResult.saveError("PhieuChi"));
                                        }).orElse(JsonResult.parentNotFound("LoaiChi"));
                            }).orElse(JsonResult.parentNotFound("NguoiDung"));
                }).orElse(JsonResult.parentNotFound("ChiNhanh"));
    }


//    @PostMapping("/upload")
//    @ApiOperation(value = "post Phieu chi", response = PhieuChi.class)
//    public ResponseEntity<JsonResult> save(@RequestBody PhieuChi phieuChi,
//                                           @RequestParam("chi-nhanh-id") int chiNhanhId,
//                                           @RequestParam("nguoi-dung-id") int nguoiDungId,
//                                           @RequestParam("loai-chi-id") int loaiChiId,
//                                           @RequestParam("da-tra") Double daTra,
//                                           @RequestParam(value = "phieu-nhap-hang-id", defaultValue = "0") int phieuNhapHangId,
//                                           @RequestParam(value = "phieu-tra-khach-id", defaultValue = "0") int phieuTraKhachId) {
//        return chiNhanhService.findByIdAndXoa(chiNhanhId, false)
//                .map(chiNhanh -> {
//                    phieuChi.setChiNhanh(chiNhanh);
//                    return nguoiDungService.findById(nguoiDungId, false)
//                            .map(nguoiDung -> {
//                                phieuChi.setNguoiDung(nguoiDung);
//                                return loaiChiService.findById(loaiChiId, false)
//                                        .map(loaiChi -> {
//                                            phieuChi.setLoaiChi(loaiChi);
//                                            String ma =loaiChi.getMaLoaiChi()+ Random.randomCode();
//                                            phieuChi.setMaPhieu(ma);
//                                            if (phieuTraKhachId == 0) {
//                                                return phieuNhapHangService.findById(phieuNhapHangId)
//                                                        .map(phieuNhapHang -> {
//                                                            phieuChi.setPhieuNhapHang(phieuNhapHang);
//                                                            phieuChi.setTongTien(phieuNhapHang.getTongTien());
//                                                            phieuChi.setDaTra(daTra);
//                                                            phieuChi.setConNo(phieuNhapHang.getTienPhaiTra() - daTra);
//                                                            phieuChi.setXoa(false);
//                                                            return phieuChiService.save(phieuChi)
//                                                                    .map(JsonResult::uploaded)
//                                                                    .orElse(JsonResult.saveError("PhieuChi"));
//                                                        }).orElse(JsonResult.parentNotFound("PhieuNhapHang"));
//                                            } else if (phieuNhapHangId == 0) {
//                                                return phieuTraKhachService.findByIdAndXoa(phieuTraKhachId, false)
//                                                        .map(phieuTraKhach -> {
//                                                            phieuChi.setPhieuTraKhach(phieuTraKhach);
//                                                            phieuChi.setTongTien(phieuTraKhach.getTienTraKhach());
//                                                            phieuChi.setDaTra(daTra);
//                                                            phieuChi.setXoa(false);
//                                                            return phieuChiService.save(phieuChi)
//                                                                    .map(JsonResult::uploaded)
//                                                                    .orElse(JsonResult.saveError("PhieuChi"));
//                                                        }).orElse(JsonResult.parentNotFound("PhieuTraKhach"));
//                                            } else if (phieuTraKhachId == 0 && phieuNhapHangId == 0) {
//                                                return JsonResult.badRequest("Phải điền đầy đủ phieuNhapHangId hoặc phieuTraKhachId ");
//                                            } else {
//                                                return JsonResult.saveError("PhieuChi");
//                                            }
//                                        }).orElse(JsonResult.parentNotFound("LoaiChi"));
//                            }).orElse(JsonResult.parentNotFound("NguoiDung"));
//                }).orElse(JsonResult.parentNotFound("ChiNhanh"));
//    }

//    @PutMapping("/update")
//    @ApiOperation(value = "put Phieu Chi", response = PhieuChi.class)
//    public ResponseEntity<JsonResult> update(@RequestBody PhieuChi phieuChi,
//                                             @RequestParam("chi-nhanh-id") int chiNhanhId,
//                                             @RequestParam("nguoi-dung-id") int nguoiDungId,
//                                             @RequestParam("loai-chi-id") int loaiChiId,
//                                             @RequestParam("da-tra") Double daTra,
//                                             @RequestParam(value = "phieu-nhap-hang-id", defaultValue = "0") int phieuNhapHangId,
//                                             @RequestParam(value = "phieu-tra-khach-id", defaultValue = "0") int phieuTraKhachId) {
//        return chiNhanhService.findByIdAndXoa(chiNhanhId, false)
//                .map(chiNhanh -> {
//                    phieuChi.setChiNhanh(chiNhanh);
//                    return nguoiDungService.findById(nguoiDungId, false)
//                            .map(nguoiDung -> {
//                                phieuChi.setNguoiDung(nguoiDung);
//                                return loaiChiService.findById(loaiChiId, false)
//                                        .map(loaiChi -> {
//                                            phieuChi.setLoaiChi(loaiChi);
//                                            if (phieuTraKhachId == 0) {
//                                                return phieuNhapHangService.findById(phieuNhapHangId)
//                                                        .map(phieuNhapHang -> {
//                                                            phieuChi.setPhieuNhapHang(phieuNhapHang);
//                                                            phieuChi.setTongTien(phieuNhapHang.getTongTien());
//                                                            phieuChi.setDaTra(daTra);
//                                                            phieuChi.setConNo(phieuNhapHang.getTienPhaiTra() - daTra);
//                                                            return phieuChiService.save(phieuChi)
//                                                                    .map(JsonResult::updated)
//                                                                    .orElse(JsonResult.saveError("PhieuChi"));
//                                                        }).orElse(JsonResult.parentNotFound("PhieuNhapHang"));
//                                            } else if (phieuNhapHangId == 0) {
//                                                return phieuTraKhachService.findByIdAndXoa(phieuTraKhachId, false)
//                                                        .map(phieuTraKhach -> {
//                                                            phieuChi.setPhieuTraKhach(phieuTraKhach);
//                                                            phieuChi.setTongTien(phieuTraKhach.getTienTraKhach());
//                                                            phieuChi.setDaTra(daTra);
//                                                            return phieuChiService.save(phieuChi)
//                                                                    .map(JsonResult::updated)
//                                                                    .orElse(JsonResult.saveError("PhieuChi"));
//                                                        }).orElse(JsonResult.parentNotFound("PhieuTraKhach"));
//                                            } else if (phieuTraKhachId == 0 && phieuNhapHangId == 0) {
//                                                return JsonResult.badRequest("Phải điền đầy đủ phieuNhapHangId hoặc phieuTraKhachId ");
//                                            } else {
//                                                return JsonResult.saveError("PhieuChi");
//                                            }
//                                        }).orElse(JsonResult.parentNotFound("LoaiChi"));
//                            }).orElse(JsonResult.parentNotFound("NguoiDung"));
//                }).orElse(JsonResult.parentNotFound("ChiNhanh"));
//    }

    @DeleteMapping("/delete")
    public ResponseEntity<JsonResult> delete(@RequestParam("id") int id) {
        Optional<PhieuChi> phieuChi = phieuChiService.findByIdAndXoa(id, false);
        if (phieuChi.isPresent()) {
            Boolean aBoolean = phieuChiService.delete(id);
            if (aBoolean) {
                return JsonResult.deleted();
            }
            return JsonResult.saveError("PhieuChi");
        } else {
            return JsonResult.idNotFound();
        }
    }

    @GetMapping("/pdf-phieu-nhap-hang")
    public ResponseEntity<JsonResult> createPhieuNhapHangPDF(@RequestParam("phieu-chi-id") int phieuChiId,
                                                @RequestParam("phieu-nhap-hang-id") int phieuNhapHangId,
                                                @RequestParam("nguoi-dung-id") int nguoiDungId) {
        return phieuChiService.findByIdAndXoa(phieuChiId, false)
                .map(phieuChi -> {
                    return phieuNhapHangService.findById(phieuNhapHangId)
                            .map(phieuNhapHang -> {
                                File file = phieuChiService.createPhieuChiPhieuNhapHangPdf(phieuChi, phieuNhapHang);
                                String urlFile = awss3Service.uploadFileExcel(file);
                                file.delete();
                                String url = DatabaseConstant.URL.AWS_S3 + urlFile;
                                ExcelFile excelFile = new ExcelFile();
                                excelFile.setUrl(url);
                                excelFile.setMaPhieu(phieuChi.getMaPhieu());
                                excelFile.setKieuFile(DatabaseConstant.TypeOfFile.PDF_FILE);
                                excelFile.setTenLoai(DatabaseConstant.File.PDF.PHIEU_NHAP_PDF_FILE);
                                excelFile.setNguoiDung(nguoiDungService.findById(nguoiDungId, false).get());
                                return excelFileService.save(excelFile)
                                        .map(JsonResult::uploaded)
                                        .orElse(JsonResult.serverError("Internal Server Error"));
                            }).orElse(JsonResult.serverError("PhieuNhapHang"));
                }).orElse(JsonResult.parentNotFound("PhieuChi"));
    }

    @GetMapping("/pdf-phieu-tra-khach")
    public ResponseEntity<JsonResult> createPhieuTraKhachPDF(@RequestParam("phieu-chi-id") int phieuChiId,
                                                             @RequestParam("hoa-don-id") int hoaDonId,
                                                             @RequestParam("nguoi-dung-id") int nguoiDungId) {
        return phieuChiService.findByIdAndXoa(phieuChiId, false)
                .map(phieuChi -> {
                    return hoaDonService.findById(hoaDonId, false)
                            .map(hoaDon -> {
                                File file = phieuChiService.createPhieuChiPhieuTraKhachPdf(phieuChi, hoaDon);
                                String urlFile = awss3Service.uploadFileExcel(file);
                                file.delete();
                                String url = DatabaseConstant.URL.AWS_S3 + urlFile;
                                ExcelFile excelFile = new ExcelFile();
                                excelFile.setUrl(url);
                                excelFile.setMaPhieu(phieuChi.getMaPhieu());
                                excelFile.setKieuFile(DatabaseConstant.TypeOfFile.PDF_FILE);
                                excelFile.setTenLoai(DatabaseConstant.File.PDF.PHIEU_TRA_KHACH_PDF_FILE);
                                excelFile.setNguoiDung(nguoiDungService.findById(nguoiDungId, false).get());
                                return excelFileService.save(excelFile)
                                        .map(JsonResult::uploaded)
                                        .orElse(JsonResult.serverError("Internal Server Error"));
                            }).orElse(JsonResult.serverError("HoaDon"));
                }).orElse(JsonResult.parentNotFound("PhieuChi"));
    }

    @GetMapping("/excel")
    public ResponseEntity<JsonResult> getExcel(@RequestParam("nguoi-dung-id") int nguoiDungId,
                                               @RequestParam("list-phieu-chi") List<Integer> list) {
        return nguoiDungService.findById(nguoiDungId, false)
                .map(nguoiDung -> {
                    ExcelFile excelFile = new ExcelFile();
                    excelFile.setNguoiDung(nguoiDung);
                    List<PhieuChi> listBill = phieuChiService.findListPhieuChi(list);
                    XSSFWorkbook workbook = ExcelUtils.createListBillExcelPhieuChi(listBill);
                    try {
                        String fileName = "PhieuChi" + LocalDateTime.now().getNano() + ".xlsx";
                        File file = new File(UPLOAD_DIRECTORY + fileName);
                        file.getParentFile().mkdirs();
                        FileOutputStream outFile = new FileOutputStream(file);
                        workbook.write(outFile);
                        String urlFile = awss3Service.uploadFileExcel(file);
                        file.delete();
                        excelFile.setKieuFile(DatabaseConstant.TypeOfFile.EXCEL_FILE);
                        excelFile.setTenLoai(DatabaseConstant.File.Excel.PHIEU_CHI_EXCEL_FILE);
                        excelFile.setUrl("https://mpec.s3.us-east-2.amazonaws.com/" + urlFile);
                        excelFile.setMaPhieu("DSPC-"+ Random.randomCode());
                        outFile.close();
                        return excelFileService.save(excelFile)
                                .map(JsonResult::uploaded)
                                .orElse(JsonResult.serverError("Internal Server Error"));
                    } catch (IOException e) {
                        return JsonResult.badRequest("Create Excel fail");
                    }
                }).orElse(JsonResult.parentNotFound("NguoiDung"));
    }
}
