package com.tavi.tavi_mrs.controller.hoa_don;

import com.tavi.tavi_mrs.entities.bao_cao.ExcelFile;
import com.tavi.tavi_mrs.entities.constant.DatabaseConstant;
import com.tavi.tavi_mrs.entities.dto.HoaDonDto;
import com.tavi.tavi_mrs.entities.hoa_don.HoaDon;
import com.tavi.tavi_mrs.entities.hoa_don.HoaDonChiTiet;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.entities.khach_hang.KhachHangDto;
import com.tavi.tavi_mrs.payload.hoa_don.TienKhachTra;
import com.tavi.tavi_mrs.payload.hoa_don.TienKhachTraList;
import com.tavi.tavi_mrs.service.aws.AWSS3Service;
import com.tavi.tavi_mrs.service.bao_cao.ExcelFileService;
import com.tavi.tavi_mrs.service.chi_nhanh.ChiNhanhService;
import com.tavi.tavi_mrs.service.hoa_don.HoaDonChiTietService;
import com.tavi.tavi_mrs.service.hoa_don.HoaDonService;
import com.tavi.tavi_mrs.service.khach_hang.KhachHangService;
import com.tavi.tavi_mrs.service.mail.MailService;
import com.tavi.tavi_mrs.service.nguoi_dung.NguoiDungService;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@RestController
@RequestMapping("api/v1/admin/hoa-don")
public class HoaDonController {

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private ChiNhanhService chiNhanhService;

    @Autowired
    private AWSS3Service awss3Service;

    @Autowired
    private ExcelFileService excelFileService;

    @Autowired
    private MailService mailService;

    private String UPLOAD_DIRECTORY = DatabaseConstant.Directory.URL_DIRECTORY;


    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findById(@RequestParam("id") int idHoaDon) {
        return hoaDonService.findById(idHoaDon, false)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }


    @GetMapping("/find-by-id-khach-hang")
    ResponseEntity<JsonResult> findByIdKhachHang(@RequestParam("khach-hang-id") int idKhachHang,
                                                 @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                 @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<HoaDon> hoaDonPage = hoaDonService.findByIdKhachHang(idKhachHang, false, pageable);
        return Optional.ofNullable(hoaDonPage)
                .map(hoaDons -> hoaDons.getTotalElements() != 0 ? JsonResult.found(PageJson.build(hoaDons)) : JsonResult.notFound("HoaDon/KhachHang/Page"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/find-by-id-khach-hang-for-all")
    ResponseEntity<JsonResult> findByIdKhachHangForAll(@RequestParam("khach-hang-id") int idKhachHang,
                                                 @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                 @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<HoaDon> hoaDonPage = hoaDonService.findByIdKhachHangForAll(idKhachHang, false, pageable);
        return Optional.ofNullable(hoaDonPage)
                .map(hoaDons -> hoaDons.getTotalElements() != 0 ? JsonResult.found(PageJson.build(hoaDons)) : JsonResult.notFound("HoaDon/KhachHang/Page"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }


    @GetMapping("/find-all")
    ResponseEntity<JsonResult> findAllToPage(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                             @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<HoaDon> hoaDonPage = hoaDonService.findAllToPage(pageable);
        return Optional.ofNullable(hoaDonPage)
                .map(hoaDons -> hoaDons.getTotalElements() != 0 ? JsonResult.found(PageJson.build(hoaDons)) : JsonResult.notFound("HoaDon/Page"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/find-all-excel")
    ResponseEntity<JsonResult> findAllToExcel() {
        return Optional.ofNullable(hoaDonService.findAll())
                .map(JsonResult::found)
                .orElse(JsonResult.notFound("HoaDon"));
    }

    @GetMapping("/search")
    public ResponseEntity<JsonResult> findHoaDon(@RequestParam(value = "chi-nhanh-id", required = false, defaultValue = "0") int chiNhanhId,
                                                 @RequestParam(value = "ma-hoa-don", required = false, defaultValue = "") String maHoaDon,
                                                 @RequestParam(value = "ten-khach-hang", required = false, defaultValue = "") String tenKhachHang,
                                                 @RequestParam(value = "ten-nhan-vien", required = false, defaultValue = "") String tenNhanVien,
                                                 @RequestParam(name = "ngay-dau", defaultValue = "", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayDau,
                                                 @RequestParam(name = "ngay-cuoi", defaultValue = "", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayCuoi,
                                                 @RequestParam(name = "trang-thai", defaultValue = "-1", required = false) int trangThai,
                                                 @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                 @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<HoaDon> hoaDonPage;
        if (chiNhanhId == 0 && maHoaDon.equals("") && tenKhachHang.equals("") && tenNhanVien.equals("") && ngayDau == null && ngayCuoi == null && trangThai == -1) {
            hoaDonPage = hoaDonService.findAllToPage(pageable);
        } else {
            hoaDonPage = hoaDonService.findByMaHoaDonAndThoiGianAndTrangThai(chiNhanhId, maHoaDon, tenKhachHang, tenNhanVien, DateTimeUtils.asDate(ngayDau), DateTimeUtils.asDate(ngayCuoi), trangThai, pageable);
        }
        return Optional.ofNullable(hoaDonPage)
                .map(hoaDons -> hoaDons.getTotalElements() != 0 ? JsonResult.found(PageJson.build(hoaDons)) : JsonResult.notFound("HoaDon/MaHoaDon/ThoiGian/TrangThai"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/search-hoa-don-hang-chua-giao")
    ResponseEntity<JsonResult> searchHoaDonHangChuaGiao(
            @RequestParam(value = "chi-nhanh-id", required = false, defaultValue = "0") int chiNhanhId,
            @RequestParam(name = "trang-thai", defaultValue = "3", required = false) int trangThai,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<HoaDon> hoaDonPage;
        hoaDonPage = hoaDonService.searchHoaDonHangChuaGiao(chiNhanhId, trangThai, pageable);
        return Optional.ofNullable(hoaDonPage)
                .map(hoaDons -> hoaDons.getTotalElements() != 0 ? JsonResult.found(PageJson.build(hoaDons)) : JsonResult.notFound("HoaDon/MaHoaDon/ThoiGian/TrangThai"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/search-by-chi-nhanh-and-text")
    ResponseEntity<JsonResult> search(
            @RequestParam(value = "chi-nhanh-id", required = false, defaultValue = "0") int chiNhanhId,
            @RequestParam(value = "text", defaultValue = "") String text,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<HoaDon> hoaDonPage;
        if (chiNhanhId == 0 && text.equals("")) {
            hoaDonPage = hoaDonService.findAllToPage(pageable);
        } else {
            hoaDonPage = hoaDonService.findByChiNhanhAndText(chiNhanhId, text, pageable);
        }
        return Optional.ofNullable(hoaDonPage)
                .map(hoaDons -> hoaDons.getTotalElements() != 0 ? JsonResult.found(PageJson.build(hoaDons)) : JsonResult.notFound("HoaDon"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/count-bill")
    public ResponseEntity<JsonResult> countBill(@RequestParam(name = "start-date", defaultValue = "1970-01-01T00:00:00+00:00", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate start,
                                                @RequestParam(name = "end-date", defaultValue = "9999-12-31T00:00:00+00:00", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate end) {
        return Optional.ofNullable(hoaDonService.countBillByTime(DateTimeUtils.asDate(start), DateTimeUtils.asDate(end)))
                .map(c -> c >= 0 ? JsonResult.success(c) : JsonResult.badRequest("Count Bill"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/sum-bill")
    public ResponseEntity<JsonResult> sumBill(@RequestParam(name = "start-date", defaultValue = "1970-01-01T00:00:00+00:00", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate start,
                                              @RequestParam(name = "end-date", defaultValue = "9999-12-31T00:00:00+00:00", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate end) {
        return Optional.ofNullable(hoaDonService.sumBillByTime(DateTimeUtils.asDate(start), DateTimeUtils.asDate(end)))
                .map(c -> c >= 0 ? JsonResult.success(c) : JsonResult.badRequest("Sum Bill"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @PostMapping("/upload")
    @ApiOperation(value = "post hoa don", response = HoaDon.class)
    public ResponseEntity<JsonResult> post(@RequestBody HoaDonDto hoaDonDto,
                                           @RequestParam("nguoi-dung-id") int nguoiDungId,
                                           @RequestParam("khach-hang-id") int khachHangId,
                                           @RequestParam("chi-nhanh-id") int chiNhanhId) {

        return chiNhanhService.findByIdAndXoa(chiNhanhId, false)
                .map(chiNhanh -> {
                    hoaDonDto.getHoaDon().setChiNhanh(chiNhanh);
                    return nguoiDungService.findById(nguoiDungId, false)
                            .map(nguoiDung -> {
                                hoaDonDto.getHoaDon().setNguoiDung(nguoiDung);
                                return khachHangService.findByIdAndXoa(khachHangId, false)
                                        .map(khachHang -> {
                                            hoaDonDto.getHoaDon().setKhachHang(khachHang);
                                            hoaDonDto.getHoaDon().setConNo(hoaDonDto.getHoaDon().getTongTien()-hoaDonDto.getHoaDon().getTienKhachTra());
                                            hoaDonDto.getHoaDon().setGiamGia(hoaDonDto.getHoaDon().getGiamGia());
                                            hoaDonDto.getHoaDon().setGhiChu(hoaDonDto.getHoaDon().getGhiChu());
                                            String result = hoaDonService.saveHoaDonDto(hoaDonDto);
                                            if (result.equals("Success")) {
                                                return JsonResult.uploaded(hoaDonDto.getHoaDon());
                                            } else if (result.equals("Bad Request")) {
                                                return JsonResult.badRequest(result);
                                            } else {
                                                return JsonResult.serverError(result);
                                            }
                                        }).orElse(JsonResult.parentNotFound("KhachHang"));
                            }).orElse(JsonResult.parentNotFound("NguoiDung"));
                }).orElse(JsonResult.parentNotFound("Chi nhanh hang hoa"));
    }

    @PutMapping("/update")
    @ApiOperation(value = "put phieu nhap hang chi tiet", response = HoaDon.class)
    public ResponseEntity<JsonResult> put(@RequestBody HoaDonDto hoaDonDto,
                                          @RequestParam("nguoi-dung-id") int nguoiDungId,
                                          @RequestParam("khach-hang-id") int khachHangId

    ) {
        return nguoiDungService.findById(nguoiDungId, false)
                .map(nguoiDung -> {
                    hoaDonDto.getHoaDon().setNguoiDung(nguoiDung);
                    return khachHangService.findByIdAndXoa(khachHangId, false)
                            .map(khachHang -> {
                                hoaDonDto.getHoaDon().setKhachHang(khachHang);
                                String result = hoaDonService.saveHoaDonDto(hoaDonDto);
                                if (result.equals("Success")) {
                                    return JsonResult.updated("updated");
                                } else if (result.equals("Bad Request")) {
                                    return JsonResult.badRequest(result);
                                } else {
                                    return JsonResult.serverError(result);
                                }
                            }).orElse(JsonResult.parentNotFound("KhachHang"));
                }).orElse(JsonResult.parentNotFound("NguoiDung"));
    }

    @PutMapping("/trang-thai")
    @ApiOperation(value = "thay doi trang thai hoa don")
    public ResponseEntity<JsonResult> setTrangThai(@RequestParam("id") int id,
                                                   @RequestParam("trang-thai") int trangThai) {
        if (hoaDonService.setTrangThai(id, trangThai))
            return JsonResult.success("Thay doi trang thai thanh cong");
        else return JsonResult.serverError("HoaDon");
    }

    @PutMapping("/update-tien-khach-tra")
    @ApiOperation(value = "update tien khach hang tra vi 1 hoa don co the tra nhieu lan")
    public ResponseEntity<JsonResult> updateTienKhachTra(@RequestBody TienKhachTraList tienKhachTraList) {

        List<TienKhachTra> tienKhachTraLists = tienKhachTraList.getTienKhachTras();
        try {
            for (int i = 0; i < tienKhachTraLists.size(); i++) {
                HoaDon hoaDon = hoaDonService.findByIdAndXoa(tienKhachTraLists.get(i).getHoaDonId(),false);
                hoaDonService.updateTienKhachTra(hoaDon.getTienKhachTra()+tienKhachTraLists.get(i).getTienKhachTra(), tienKhachTraLists.get(i).getHoaDonId(),
                        hoaDon.getTongTien()-(hoaDon.getTienKhachTra() + tienKhachTraLists.get(i).getTienKhachTra()));
            }
            return JsonResult.success("Update Tien khach tra thanh cong");
        } catch (Exception ex) {
            return JsonResult.serverError("Update Tien khach tra error");
        }
    }

    @GetMapping("/pdf/{ma}")
    public ResponseEntity<JsonResult> createPDF(@PathVariable("ma") String ma,
                                                @RequestParam("nguoi-dung-id") int nguoiDungId) {
        return hoaDonService.findByMa(ma, false)
                .map(hoaDon -> {
                    List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietService.findListByHoaDonId(hoaDon.getId());
                    File file = hoaDonService.createPdf(hoaDon, hoaDonChiTietList);
                    return nguoiDungService.findById(nguoiDungId, false)
                            .map(nguoiDung -> {
                                String urlFile = awss3Service.uploadFileExcel(file);
                                file.delete();
                                String url = DatabaseConstant.URL.AWS_S3 + urlFile;
                                if (hoaDon.getKhachHang().getEmail() != null && !hoaDon.getKhachHang().getEmail().equals("")) {
                                    String header = "Xác nhận mua hàng";
                                    String content = "Khách hàng : " + hoaDon.getKhachHang().getTenKhachHang()
                                            + "\nPhiếu hóa đơn mua hàng : " + url
                                            + "\nCảm ơn quý khách đã mua hàng và hẹn gặp lại lần sau";
                                    String[] mails = {hoaDon.getKhachHang().getEmail()};
                                    mailService.sendMail(mails, header, content);
                                }
                                ExcelFile excelFile = new ExcelFile();
                                excelFile.setUrl(url);
                                excelFile.setMaPhieu(hoaDon.getMa());
                                excelFile.setKieuFile(DatabaseConstant.TypeOfFile.PDF_FILE);
                                excelFile.setTenLoai(DatabaseConstant.File.PDF.HOA_DON_PDF_FILE);
                                excelFile.setNguoiDung(nguoiDung);
                                return excelFileService.save(excelFile)
                                        .map(JsonResult::uploaded)
                                        .orElse(JsonResult.serverError("Internal Server Error"));
                            }).orElse(JsonResult.parentNotFound("NguoiDung"));
                }).orElse(JsonResult.parentNotFound("HoaDon"));
    }

    @GetMapping("/excel")
    public ResponseEntity<JsonResult> getExcel(@RequestParam("nguoi-dung-id") int nguoiDungId,
                                               @RequestParam("list-hoa-don") List<Integer> list) {
        return nguoiDungService.findById(nguoiDungId, false)
                .map(nguoiDung -> {
                    ExcelFile excelFile = new ExcelFile();
                    excelFile.setNguoiDung(nguoiDung);
                    List<HoaDon> listBill = hoaDonService.findListHoaDon(list);
                    XSSFWorkbook workbook = ExcelUtils.createListBillExcelHoaDon(listBill);
                    try {
                        String fileName = "ListBill_" + LocalDateTime.now().getNano() + ".xlsx";
                        File file = new File(UPLOAD_DIRECTORY + fileName);
                        file.getParentFile().mkdirs();
                        FileOutputStream outFile = new FileOutputStream(file);
                        workbook.write(outFile);
                        String urlFile = awss3Service.uploadFileExcel(file);
                        file.delete();
                        excelFile.setKieuFile(DatabaseConstant.TypeOfFile.EXCEL_FILE);
                        excelFile.setTenLoai(DatabaseConstant.File.Excel.HOA_DON_EXCEL_FILE);
                        excelFile.setMaPhieu("DSHD-"+ Random.randomCode());
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

    @GetMapping("/doanh-thu")
    public ResponseEntity<JsonResult> doanhThuTong(@RequestParam(name = "ngay-dau", defaultValue = "1970-01-01T00:00:00", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayDau,
                                                   @RequestParam(name = "ngay-cuoi", defaultValue = "9999-12-31T00:00:00", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayCuoi) {
        System.out.println(DateTimeUtils.asDate(ngayDau));
        return Optional.ofNullable(hoaDonService.bieuDoDoanhThuTong(DateTimeUtils.asDate(ngayDau), DateTimeUtils.asDate(ngayCuoi), false))
                .map(JsonResult::found)
                .orElse(JsonResult.serverError("Internal Server Error"));

    }

    @GetMapping("/doanh-thu-thang/{year}/{month}")
    public ResponseEntity<JsonResult> doanhThuTrongThang(@PathVariable("year") int year,
                                                         @PathVariable("month") int month) {
        return Optional.ofNullable(hoaDonService.bieuDoDoanhThuTrongThang(month, year, false))
                .map(JsonResult::found)
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @GetMapping("/doanh-thu-gio-trong-thang/{year}/{month}")
    public ResponseEntity<JsonResult> doanhThuGioTrongThang(@PathVariable("year") int year,
                                                            @PathVariable("month") int month) {
        return Optional.ofNullable(hoaDonService.bieuDoDoanhThuGioTrongThang(month, year, false))
                .map(JsonResult::found)
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @GetMapping("/doanh-thu-tuan/{year}/{week}")
    public ResponseEntity<JsonResult> doanhThuTrongTuan(@PathVariable("year") int year,
                                                        @PathVariable("week") int week) {
        return Optional.ofNullable(hoaDonService.bieuDoDoanhThuTrongTuan(week, year, false))
                .map(JsonResult::found)
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @GetMapping("/doanh-thu-nam/{year}")
    public ResponseEntity<JsonResult> doanhThuTrongNam(@PathVariable("year") int year) {
        return Optional.ofNullable(hoaDonService.bieuDoDoanhThuTrongNam(year, false))
                .map(JsonResult::found)
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @GetMapping("/doanh-thu-nhan-vien/{id}")
    public ResponseEntity<JsonResult> doanhThuByNv(@RequestParam(name = "ngay-dau", defaultValue = "1970-01-01T00:00:00", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayDau,
                                                   @RequestParam(name = "ngay-cuoi", defaultValue = "9999-12-31T00:00:00", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayCuoi,
                                                   @PathVariable("id") int nhanVienId) {
        System.out.println(DateTimeUtils.asDate(ngayDau));
        return Optional.ofNullable(hoaDonService.bieuDoDoanhThuByNV(DateTimeUtils.asDate(ngayDau), DateTimeUtils.asDate(ngayCuoi), nhanVienId, false))
                .map(JsonResult::found)
                .orElse(JsonResult.serverError("Internal Server Error"));

    }

    @GetMapping("/find-recent-order")
    @ApiOperation(value = "find all", response = HoaDon.class, responseContainer = "List")
    public ResponseEntity<JsonResult> findRecentOrder() {
        return Optional.ofNullable(hoaDonService.findRecentOrder())
                .map(rsList -> !rsList.isEmpty() ? JsonResult.found(rsList) : JsonResult.notFound("List of Recent Order"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @GetMapping("/top-doanh-thu-nhan-vien-theo-thang")
    public ResponseEntity<JsonResult> topDoanhThuByNhanVien(@RequestParam(name = "month", required = false, defaultValue = "1") int month,
                                                            @RequestParam(name = "year", required = false, defaultValue = "2021") int year,
                                                            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                                                            @RequestParam(value = "top", required = false, defaultValue = "true") boolean top) {
        Pageable pageable = PageRequest.of(0, size);
        return Optional.ofNullable(hoaDonService.findTopNhanVienTrongThang(month, year, top, pageable))
                .map(rs -> !rs.isEmpty() ? JsonResult.found(rs) : JsonResult.notFound("List nhan vien"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @GetMapping("/top-doanh-thu-chi-nhanh-theo-thang")
    public ResponseEntity<JsonResult> topChiNhanhByNhanVien(@RequestParam(name = "month", required = false, defaultValue = "1") int month,
                                                            @RequestParam(name = "year", required = false, defaultValue = "2021") int year,
                                                            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                                                            @RequestParam(value = "top", required = false, defaultValue = "true") boolean top) {
        Pageable pageable = PageRequest.of(0, size);
        return Optional.ofNullable(hoaDonService.findTopChiNhanhTrongThang(month, year, top, pageable))
                .map(rs -> !rs.isEmpty() ? JsonResult.found(rs) : JsonResult.notFound("List nhan vien"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @GetMapping("/top-khach-hang-theo-thang")
    ResponseEntity<JsonResult> findTopKhachHangTheoThang(
            @RequestParam(name = "ngay-dau", defaultValue = "1970-01-01T00:00:00", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayDau,
            @RequestParam(name = "ngay-cuoi", defaultValue = "9999-12-31T00:00:00", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayCuoi,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<KhachHangDto> hoaDonPage;
        hoaDonPage = hoaDonService.topKhachHangTheoThang(DateTimeUtils.asDate(ngayDau), DateTimeUtils.asDate(ngayCuoi), pageable);
        return Optional.ofNullable(hoaDonPage)
                .map(hoaDons -> hoaDons.getTotalElements() != 0 ? JsonResult.found(PageJson.build(hoaDons)) : JsonResult.notFound("Khach hang"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/search-top-khach-hang-theo-thang")
    ResponseEntity<JsonResult> searchTopKhachHangTheoThang(
            @RequestParam(name = "ngay-dau", defaultValue = "", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayDau,
            @RequestParam(name = "ngay-cuoi", defaultValue = "", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayCuoi,
            @RequestParam(value = "text", defaultValue = "") String text,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<KhachHangDto> hoaDonPage;
        hoaDonPage = hoaDonService.searchTopKhachHangTheoThang(DateTimeUtils.asDate(ngayDau), DateTimeUtils.asDate(ngayCuoi), text, pageable);
        return Optional.ofNullable(hoaDonPage)
                .map(hoaDons -> hoaDons.getTotalElements() != 0 ? JsonResult.found(PageJson.build(hoaDons)) : JsonResult.notFound("Khach hang"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

}
