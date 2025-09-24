package com.tavi.tavi_mrs.controller.khach_hang;

import com.tavi.tavi_mrs.entities.bao_cao.ExcelFile;
import com.tavi.tavi_mrs.entities.constant.DatabaseConstant;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.entities.khach_hang.KhachHang;
import com.tavi.tavi_mrs.payload.khach_hang.KhachHangForm;
import com.tavi.tavi_mrs.service.aws.AWSS3Service;
import com.tavi.tavi_mrs.service.bao_cao.ExcelFileService;
import com.tavi.tavi_mrs.service.khach_hang.KhachHangService;
import com.tavi.tavi_mrs.service.nguoi_dung.NguoiDungService;
import com.tavi.tavi_mrs.utils.DateTimeUtils;
import com.tavi.tavi_mrs.utils.ExcelUtils;
import com.tavi.tavi_mrs.utils.Random;
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
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/khach-hang")
public class KhachHangController {

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private ExcelFileService excelFileService;

    @Autowired
    private AWSS3Service awss3Service;

    private String UPLOAD_DIRECTORY = DatabaseConstant.Directory.URL_DIRECTORY;

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findById(@RequestParam("id") int id) {
        return Optional.ofNullable(khachHangService.findByIdAndXoa(id, false))
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/find-all")
    public ResponseEntity<JsonResult> findAll(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                              @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<KhachHang> khachHangPage = khachHangService.findAll(pageable);

        return Optional.ofNullable(khachHangPage)
                .map(khachHangs -> khachHangs.getTotalElements() != 0 ? JsonResult.found(PageJson.build(khachHangs)) : JsonResult.notFound("KhachHang/Page"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/search")
    ResponseEntity<JsonResult> search(@RequestParam(value = "ten-khach-hang", defaultValue = "", required = false) String tenKhachHang,
                                      @RequestParam(value = "dien-thoai", defaultValue = "", required = false) String dienThoai,
                                      @RequestParam(value = "email", defaultValue = "", required = false) String email,
                                      @RequestParam(value = "facebook", defaultValue = "", required = false) String facebook,
                                      @RequestParam(value = "dia-chi", defaultValue = "", required = false) String diaChi,
                                      @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                      @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<KhachHang> khachHangPage;
        if (tenKhachHang.equals("") && dienThoai.equals("") && email.equals("") && facebook.equals("") && diaChi.equals("")) {
            khachHangPage = khachHangService.findAll(pageable);
        } else {
            khachHangPage = khachHangService.findByTenKhachHangAndDienThoaiAndEmail(tenKhachHang, dienThoai, email, facebook, diaChi, pageable);
        }
        return Optional.ofNullable(khachHangPage)
                .map(khachHangs -> khachHangs.getTotalElements() != 0 ? JsonResult.found(PageJson.build(khachHangs)) : JsonResult.notFound("TenKhachHang/DienThoai/Email"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/search-text")
    ResponseEntity<JsonResult> searchText(@RequestParam(name = "text", required = false, defaultValue = "") String text,
                                          @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                          @RequestParam(name = "size", defaultValue = "5", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<KhachHang> khachHangPage;
        if (text.equals("")) {
            khachHangPage = khachHangService.findAll(pageable);
        } else {
            khachHangPage = khachHangService.search(text, pageable);
        }
        System.out.println(khachHangPage.toString());
        return Optional.ofNullable(khachHangPage)
                .map(khachHangs -> khachHangs.getTotalElements() != 0 ? JsonResult.found(PageJson.build(khachHangs)) : JsonResult.notFound("TenKhachHang/DienThoai/Email"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/excel")
    public ResponseEntity<JsonResult> getExcel(@RequestParam("nguoi-dung-id") int nguoiDungId,
                                               @RequestParam("list-khach-hang") List<Integer> list) {
        return nguoiDungService.findById(nguoiDungId, false)
                .map(nguoiDung -> {
                    ExcelFile excelFile = new ExcelFile();
                    excelFile.setNguoiDung(nguoiDung);
                    List<KhachHang> listKH = khachHangService.findListKhachHang(list);
                    XSSFWorkbook workbook = ExcelUtils.createDanhSachKhachHangExcel(listKH);
                    try {
                        String fileName = "ListKH_" + LocalDateTime.now().getNano() + ".xlsx";
                        File file = new File(UPLOAD_DIRECTORY + fileName);
                        file.getParentFile().mkdirs();
                        FileOutputStream outFile = new FileOutputStream(file);
                        workbook.write(outFile);
                        String urlFile = awss3Service.uploadFileExcel(file);
                        file.delete();
                        excelFile.setKieuFile(DatabaseConstant.TypeOfFile.EXCEL_FILE);
                        excelFile.setTenLoai(DatabaseConstant.File.Excel.KHACH_HANG_EXCEL_FILE);
                        excelFile.setMaPhieu("DSKH-"+ Random.randomCode());
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

    @GetMapping("/count")
    ResponseEntity<JsonResult> count() {
        return Optional.ofNullable(khachHangService.countCustomer())
                .map(c -> c >= 0 ? JsonResult.success(c) : JsonResult.badRequest("Count Custormer"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/count-new-custormer")
    ResponseEntity<JsonResult> countNewCustormer(@RequestParam(name = "start-date", defaultValue = "1970-01-01T00:00:00+00:00", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate start,
                                                 @RequestParam(name = "end-date", defaultValue = "9999-12-31T00:00:00+00:00", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate end) {
        return Optional.ofNullable(khachHangService.countNewMember(DateTimeUtils.asDate(start), DateTimeUtils.asDate(end)))
                .map(c -> c >= 0 ? JsonResult.success(c) : JsonResult.badRequest("Count Custormer"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/count-transaction")
    ResponseEntity<JsonResult> countTransaction(@RequestParam(name = "start-date", defaultValue = "1970-01-01T00:00:00+00:00", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate start,
                                                @RequestParam(name = "end-date", defaultValue = "9999-12-31T00:00:00+00:00", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate end) {
        return Optional.ofNullable(khachHangService.countCustomerTransaction(DateTimeUtils.asDate(start), DateTimeUtils.asDate(end)))
                .map(c -> c >= 0 ? JsonResult.success(c) : JsonResult.badRequest("Count Custormer"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }


    @PostMapping("/upload")
    public ResponseEntity<JsonResult> upload(@RequestBody KhachHang khachHang) {
        String taiKhoan = "KH" + khachHang.getDienThoai();
        khachHang.setTaiKhoan(taiKhoan);
        khachHang.setTrangThai(1);
        khachHang.setXoa(false);
        khachHang.setLoaiKhach("Normal");
        Date date = new Date();
        khachHang.setThoiGian(date);
        return khachHangService.save(khachHang)
                .map(JsonResult::uploaded)
                .orElse(JsonResult.saveError("KhachHang"));
    }

    @PutMapping("/update")
    public ResponseEntity<JsonResult> update(@RequestBody KhachHang khachHang) {
        return khachHangService.save(khachHang)
                .map(JsonResult::updated)
                .orElse(JsonResult.saveError("KhachHang"));
    }

    @PutMapping("/hoa-don/update")
    public ResponseEntity<JsonResult> updateHoaDon(@RequestBody KhachHangForm khachHang) {
        LocalDateTime now = LocalDateTime.now();
        Date out = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        Optional<KhachHang> khachHangOptional = khachHangService.findByIdAndXoa(khachHang.getId(), false);
        if (khachHangOptional.isPresent()) {
            KhachHang khachHangUpdate = new KhachHang();

            khachHangUpdate.setId(khachHang.getId());
            khachHangUpdate.setThoiGian(out);
            khachHangUpdate.setDiaChi(khachHang.getDiaChi());
            khachHangUpdate.setDienThoai(khachHang.getDienThoai());
            khachHangUpdate.setEmail(khachHang.getEmail());

            khachHangUpdate.setTrangThai(khachHangOptional.get().getTrangThai());
            khachHangUpdate.setNgaySinh(khachHangOptional.get().getNgaySinh());
            khachHangUpdate.setFacebook(khachHangOptional.get().getFacebook());
            khachHangUpdate.setGhiChu(khachHangOptional.get().getGhiChu());
            khachHangUpdate.setTaiKhoan(khachHangOptional.get().getTaiKhoan());
            khachHangUpdate.setTenKhachHang(khachHangOptional.get().getTenKhachHang());
            khachHangUpdate.setLoaiKhach("Normal");
            khachHangUpdate.setXoa(false);

            return khachHangService.save(khachHangUpdate)
                    .map(JsonResult::updated)
                    .orElse(JsonResult.saveError("KhachHang"));
        } else {
            JsonResult.idNotFound();
        }
        return JsonResult.serverError("Internal Server error");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<JsonResult> delete(@RequestParam("id") int id) {
        Optional<KhachHang> khachHang = khachHangService.findByIdAndXoa(id, false);
        if (khachHang.isPresent()) {
            Boolean bool = khachHangService.deleted(id);
            if (bool) {
                return JsonResult.deleted();
            }
            return JsonResult.saveError("KhachHang");
        } else {
            return JsonResult.idNotFound();
        }
    }

}
