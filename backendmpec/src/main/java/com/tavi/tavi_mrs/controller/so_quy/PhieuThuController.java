package com.tavi.tavi_mrs.controller.so_quy;

import com.tavi.tavi_mrs.entities.bao_cao.ExcelFile;
import com.tavi.tavi_mrs.entities.ca_lam_viec.NguoiDungCaLamViec;
import com.tavi.tavi_mrs.entities.constant.DatabaseConstant;
import com.tavi.tavi_mrs.entities.hoa_don.HoaDon;
import com.tavi.tavi_mrs.entities.hoa_don.HoaDonChiTiet;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.entities.so_quy.PhieuThu;
import com.tavi.tavi_mrs.entities.so_quy.PhieuThuHoaDon;
import com.tavi.tavi_mrs.payload.hoa_don.TienKhachTra;
import com.tavi.tavi_mrs.payload.hoa_don.TienKhachTraList;
import com.tavi.tavi_mrs.payload.so_quy.PhieuThuRequest;
import com.tavi.tavi_mrs.service.aws.AWSS3Service;
import com.tavi.tavi_mrs.service.bao_cao.ExcelFileService;
import com.tavi.tavi_mrs.service.chi_nhanh.ChiNhanhService;
import com.tavi.tavi_mrs.service.hoa_don.HoaDonChiTietService;
import com.tavi.tavi_mrs.service.hoa_don.HoaDonService;
import com.tavi.tavi_mrs.service.mail.MailService;
import com.tavi.tavi_mrs.service.nguoi_dung.NguoiDungService;
import com.tavi.tavi_mrs.service.phieu_tra_hang_nhap.PhieuTraHangNhapService;
import com.tavi.tavi_mrs.service.so_quy.LoaiThuService;
import com.tavi.tavi_mrs.service.so_quy.PhieuThuHoaDonService;
import com.tavi.tavi_mrs.service.so_quy.PhieuThuService;
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
@RequestMapping("api/v1/admin/phieu-thu")
public class PhieuThuController {

    @Autowired
    PhieuThuService phieuThuService;

    @Autowired
    ChiNhanhService chiNhanhService;

    @Autowired
    NguoiDungService nguoiDungService;

    @Autowired
    LoaiThuService loaiThuService;

    @Autowired
    HoaDonService hoaDonService;

    @Autowired
    HoaDonChiTietService hoaDonChiTietService;

    @Autowired
    PhieuThuHoaDonService phieuThuHoaDonService;

    @Autowired
    PhieuTraHangNhapService phieuTraHangNhapSevice;

    private String UPLOAD_DIRECTORY = DatabaseConstant.Directory.URL_DIRECTORY;

    @Autowired
    private AWSS3Service awss3Service;

    @Autowired
    private MailService mailService;

    @Autowired
    private ExcelFileService excelFileService;

    @GetMapping("/find-all-to-page")
    public ResponseEntity<JsonResult> findAll(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                              @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PhieuThu> phieuThuPage = phieuThuService.findAllToPage(pageable);
        return Optional.ofNullable(phieuThuPage)
                .map(phieuThus -> phieuThus.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phieuThus)) : JsonResult.notFound("PhieuThu/Page"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findById(@RequestParam("id") int id) {
        return phieuThuService.findByIdAndXoa(id, false)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/search")
    public ResponseEntity<JsonResult> search(@RequestParam(value = "chi-nhanh-id", required = false, defaultValue = "0") int chiNhanhId,
                                             @RequestParam(value = "ma-phieu-thu", required = false, defaultValue = "") String maPhieuThu,
                                             @RequestParam(value = "ten-loai-thu", required = false, defaultValue = "") String tenLoaiThu,
                                             @RequestParam(value = "ten-nhan-vien", required = false, defaultValue = "") String tenNhanVien,
                                             @RequestParam(name = "ngay-dau", defaultValue = "", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayDau,
                                             @RequestParam(name = "ngay-cuoi", defaultValue = "", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayCuoi,
                                             @RequestParam(name = "trang-thai", defaultValue = "-1", required = false) int trangThai,
                                             @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                             @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PhieuThu> phieuThuPage;
        if (chiNhanhId == 0 && maPhieuThu.equals("") && tenLoaiThu.equals("") && tenNhanVien.equals("") && ngayDau == null && ngayCuoi == null && trangThai == -1) {
            phieuThuPage = phieuThuService.findAllToPage(pageable);
        } else {
            phieuThuPage = phieuThuService.findByMaPhieuThuAndThoiGianAndTrangThai(chiNhanhId, maPhieuThu, tenLoaiThu, tenNhanVien, DateTimeUtils.asDate(ngayDau), DateTimeUtils.asDate(ngayCuoi), trangThai, pageable);
        }
        return Optional.ofNullable(phieuThuPage)
                .map(phieuThus -> phieuThus.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phieuThus)) : JsonResult.notFound("PhieuThu/MaPhieuThu/ThoiGian/TrangThai"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @GetMapping("/search-by-chi-nhanh-and-text")
    public ResponseEntity<JsonResult> searchByChiNhanhAndText(@RequestParam(value = "chi-nhanh-id", required = false, defaultValue = "0") int chiNhanhId,
                                                              @RequestParam(value = "text", required = false, defaultValue = "") String text,
                                                              @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                              @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PhieuThu> phieuThuPage;
        if (chiNhanhId == 0 && text.equals("")) {
            phieuThuPage = phieuThuService.findAllToPage(pageable);
        } else {
            phieuThuPage = phieuThuService.findByChiNhanhAndText(chiNhanhId, text, pageable);
        }
        return Optional.ofNullable(phieuThuPage)
                .map(phieuThus -> phieuThuPage.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phieuThus)) : JsonResult.notFound("PhieuThu/ChiNhanh/Page"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @PutMapping("/trang-thai")
    @ApiOperation(value = "thay doi trang thai hoa don")
    public ResponseEntity<JsonResult> setTrangThai(@RequestParam("id") int id,
                                                   @RequestParam("trang-thai") int trangThai) {
        if (phieuThuService.setTrangThai(id, trangThai))
            return JsonResult.success("Thay doi trang thai thanh cong");
        else return JsonResult.serverError("PhieuThu");
    }

    //upload phieu thu binh thuong khi tao 1 hoa don
    @PostMapping("/upload")
    @ApiOperation(value = "post Phieu Thu", response = PhieuThu.class)
    public ResponseEntity<JsonResult> save(@RequestBody PhieuThuRequest phieuThuRequest,
                                           @RequestParam("chi-nhanh-id") int chiNhanhId,
                                           @RequestParam("nguoi-dung-id") int nguoiDungId,
                                           @RequestParam(value = "loai-thu-id", defaultValue = "1") int loaiThuId) {
        PhieuThu phieuThu = new PhieuThu();
        return chiNhanhService.findByIdAndXoa(chiNhanhId, false)
                .map(chiNhanh -> {
                    phieuThu.setChiNhanh(chiNhanh);
                    return nguoiDungService.findById(nguoiDungId, false)
                            .map(nguoiDung -> {
                                phieuThu.setNguoiDung(nguoiDung);
                                return loaiThuService.findById(loaiThuId, false)
                                        .map(loaiThu -> {
                                            String ma = loaiThu.getMaLoaiThu() + "-" + Random.randomCode();
                                            phieuThu.setMaPhieu(ma);
                                            phieuThu.setLoaiThu(loaiThu);
                                            phieuThu.setTienDaTra(phieuThuRequest.getTienDaTra());
                                            phieuThu.setGhiChu(phieuThuRequest.getGhiChu());
                                            phieuThu.setTrangThai(0); // 0 == da thanh toan
                                            phieuThu.setXoa(false);
                                            return phieuThuService.save(phieuThu)
                                                    .map(JsonResult::uploaded)
                                                    .orElse(JsonResult.saveError("PhieuThu"));
                                        }).orElse(JsonResult.parentNotFound("LoaiThu"));
                            }).orElse(JsonResult.parentNotFound("NguoiDung"));
                }).orElse(JsonResult.parentNotFound("ChiNhanh"));
    }

    //upload 1 phieu thu khi tạo 1 phieu tra hang nhap
    @PostMapping("/upload2")
    @ApiOperation(value = "post Phieu Thu", response = PhieuThu.class)
    public ResponseEntity<JsonResult> save2(@RequestBody PhieuThuRequest phieuThuRequest,
                                            @RequestParam("chi-nhanh-id") int chiNhanhId,
                                            @RequestParam("nguoi-dung-id") int nguoiDungId,
                                            @RequestParam(value = "loai-thu-id", defaultValue = "2") int loaiThuId) {
        PhieuThu phieuThu = new PhieuThu();
        return chiNhanhService.findByIdAndXoa(chiNhanhId, false)
                .map(chiNhanh -> {
                    phieuThu.setChiNhanh(chiNhanh);
                    return nguoiDungService.findById(nguoiDungId, false)
                            .map(nguoiDung -> {
                                phieuThu.setNguoiDung(nguoiDung);
                                return loaiThuService.findById(loaiThuId, false)
                                        .map(loaiThu -> {
                                            String ma = loaiThu.getMaLoaiThu() + Random.randomCode();
                                            phieuThu.setMaPhieu(ma);
                                            phieuThu.setLoaiThu(loaiThu);
                                            phieuThu.setTienDaTra(phieuThuRequest.getTienDaTra());
                                            phieuThu.setGhiChu(phieuThuRequest.getGhiChu());
                                            phieuThu.setTrangThai(0); // 0 == da thanh toan
                                            phieuThu.setXoa(false);
                                            return phieuThuService.save(phieuThu)
                                                    .map(JsonResult::uploaded)
                                                    .orElse(JsonResult.saveError("PhieuThu"));
                                        }).orElse(JsonResult.parentNotFound("LoaiThu"));
                            }).orElse(JsonResult.parentNotFound("NguoiDung"));
                }).orElse(JsonResult.parentNotFound("ChiNhanh"));
    }


    @DeleteMapping("/delete")
    public ResponseEntity<JsonResult> delete(@RequestParam("id") int id) {
        Optional<PhieuThu> phieuThu = phieuThuService.findByIdAndXoa(id, false);
        if (phieuThu.isPresent()) {
            Boolean aBoolean = phieuThuService.delete(id);
            if (aBoolean) {
                return JsonResult.deleted();
            }
            return JsonResult.saveError("PhieuThu");
        } else {
            return JsonResult.idNotFound();
        }
    }

    @GetMapping("/excel")
    public ResponseEntity<JsonResult> getExcel(@RequestParam("nguoi-dung-id") int nguoiDungId,
                                               @RequestParam("list-phieu-thu") List<Integer> list) {
        return nguoiDungService.findById(nguoiDungId, false)
                .map(nguoiDung -> {
                    ExcelFile excelFile = new ExcelFile();
                    excelFile.setNguoiDung(nguoiDung);
                    List<PhieuThu> listBill = phieuThuService.findListPhieuThu(list);
                    XSSFWorkbook workbook = ExcelUtils.createListBillExcelPhieuThu(listBill);
                    try {
                        String fileName = "PhieuThu" + LocalDateTime.now().getNano() + ".xlsx";
                        File file = new File(UPLOAD_DIRECTORY + fileName);
                        file.getParentFile().mkdirs();
                        FileOutputStream outFile = new FileOutputStream(file);
                        workbook.write(outFile);
                        String urlFile = awss3Service.uploadFileExcel(file);
                        file.delete();
                        excelFile.setKieuFile(DatabaseConstant.TypeOfFile.EXCEL_FILE);
                        excelFile.setTenLoai(DatabaseConstant.File.Excel.PHIEU_THU_EXCEL_FILE);
                        excelFile.setUrl("https://mpec.s3.us-east-2.amazonaws.com/" + urlFile);
                        excelFile.setMaPhieu("DSPT-" + Random.randomCode());
                        outFile.close();
                        return excelFileService.save(excelFile)
                                .map(JsonResult::uploaded)
                                .orElse(JsonResult.serverError("Internal Server Error"));
                    } catch (IOException e) {
                        return JsonResult.badRequest("Create Excel fail");
                    }
                }).orElse(JsonResult.parentNotFound("NguoiDung"));
    }

    @GetMapping("/pdf")
    public ResponseEntity<JsonResult> createPDF(@RequestParam("phieu-thu-id") int phieuThuId,
                                                @RequestParam("hoa-don-id") int hoaDonId,
                                                @RequestParam("nguoi-dung-id") int nguoiDungId) {
        return phieuThuService.findByIdAndXoa(phieuThuId, false)
                .map(phieuThu -> {
                    return hoaDonService.findById(hoaDonId, false)
                            .map(hoaDon -> {
                                File file = phieuThuService.createPdf(phieuThu, hoaDon);
                                String urlFile = awss3Service.uploadFileExcel(file);
                                file.delete();
                                String url = DatabaseConstant.URL.AWS_S3 + urlFile;
                                ExcelFile excelFile = new ExcelFile();
                                excelFile.setUrl(url);
                                excelFile.setMaPhieu(phieuThu.getMaPhieu());
                                excelFile.setKieuFile(DatabaseConstant.TypeOfFile.PDF_FILE);
                                excelFile.setTenLoai(DatabaseConstant.File.PDF.HOA_DON_PDF_FILE);
                                excelFile.setNguoiDung(nguoiDungService.findById(nguoiDungId, false).get());
                                return excelFileService.save(excelFile)
                                        .map(JsonResult::uploaded)
                                        .orElse(JsonResult.serverError("Internal Server Error"));
                            }).orElse(JsonResult.serverError("HoaDon"));
                }).orElse(JsonResult.parentNotFound("PhieuThu"));
    }

    @GetMapping("/pdf-phieu-tra-hang-nhap")
    public ResponseEntity<JsonResult> createPhieuTraHangNhapPDF(@RequestParam("phieu-thu-id") int phieuThuId,
                                                                @RequestParam("phieu-tra-hang-nhap-id") int phieuTraHangNhapId,
                                                                @RequestParam("nguoi-dung-id") int nguoiDungId) {
        return phieuThuService.findByIdAndXoa(phieuThuId, false)
                .map(phieuThu -> {
                    return phieuTraHangNhapSevice.findByIdAndXoa(phieuTraHangNhapId, false)
                            .map(phieuTraHangNhap -> {
                                File file = phieuThuService.createPhieuTraHangNhapPdf(phieuThu, phieuTraHangNhap);
                                String urlFile = awss3Service.uploadFileExcel(file);
                                file.delete();
                                String url = DatabaseConstant.URL.AWS_S3 + urlFile;
                                ExcelFile excelFile = new ExcelFile();
                                excelFile.setUrl(url);
                                excelFile.setMaPhieu(phieuThu.getMaPhieu());
                                excelFile.setKieuFile(DatabaseConstant.TypeOfFile.PDF_FILE);
                                excelFile.setTenLoai(DatabaseConstant.File.PDF.PHIEU_TRA_NHA_CUNG_CAP_PDF_FILE);
                                excelFile.setNguoiDung(nguoiDungService.findById(nguoiDungId, false).get());
                                return excelFileService.save(excelFile)
                                        .map(JsonResult::uploaded)
                                        .orElse(JsonResult.serverError("Internal Server Error"));
                            }).orElse(JsonResult.serverError("PhieuTraHangNhap"));
                }).orElse(JsonResult.parentNotFound("PhieuThu"));
    }
}
