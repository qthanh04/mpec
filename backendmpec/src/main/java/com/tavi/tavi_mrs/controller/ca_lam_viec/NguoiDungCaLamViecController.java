package com.tavi.tavi_mrs.controller.ca_lam_viec;

import com.tavi.tavi_mrs.entities.bao_cao.ExcelFile;
import com.tavi.tavi_mrs.entities.ca_lam_viec.CaLamViec;
import com.tavi.tavi_mrs.entities.ca_lam_viec.NguoiDungCaLamViec;
import com.tavi.tavi_mrs.entities.constant.DatabaseConstant;
import com.tavi.tavi_mrs.entities.hoa_don.HoaDon;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDung;
import com.tavi.tavi_mrs.entities.van_chuyen.Transport;
import com.tavi.tavi_mrs.payload.ca_lam_viec.NgayThang;
import com.tavi.tavi_mrs.payload.ca_lam_viec.NguoiDungCaLamViecUpload;
import com.tavi.tavi_mrs.service.aws.AWSS3Service;
import com.tavi.tavi_mrs.service.bao_cao.ExcelFileService;
import com.tavi.tavi_mrs.service.ca_lam_viec.CaLamViecService;
import com.tavi.tavi_mrs.service.ca_lam_viec.NguoiDungCaLamViecService;
import com.tavi.tavi_mrs.service.nguoi_dung.NguoiDungService;
import com.tavi.tavi_mrs.utils.ExcelUtils;
import com.tavi.tavi_mrs.utils.Random;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@RestController
@RequestMapping("api/v1/admin/nguoi-dung-ca-lam-viec")
public class NguoiDungCaLamViecController {

    @Autowired
    private NguoiDungCaLamViecService nguoiDungCaLamViecService;

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private CaLamViecService caLamViecService;

    @Autowired
    private AWSS3Service awss3Service;

    @Autowired
    private ExcelFileService excelFileService;

    private String UPLOAD_DIRECTORY = DatabaseConstant.Directory.URL_DIRECTORY;

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findById(@RequestParam("id") int idNguoiDungCaLamViec) {
        return nguoiDungCaLamViecService.findByIdAndXoa(idNguoiDungCaLamViec, false)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/find-all")
    public ResponseEntity<JsonResult> findAll(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                              @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<NguoiDungCaLamViec> nguoiDungCaLamViecPage = nguoiDungCaLamViecService.findAll(pageable);
        return Optional.ofNullable(nguoiDungCaLamViecPage)
                .map(nguoiDungCaLamViecs -> nguoiDungCaLamViecs.getTotalElements() != 0 ? JsonResult.found(PageJson.build(nguoiDungCaLamViecs))
                        : JsonResult.notFound("NguoiDungCaLamViec/Page"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }
    @GetMapping("/search")
    public ResponseEntity<JsonResult> search( @RequestParam(value = "text", defaultValue = "") String text,
                                                       @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                       @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<NguoiDungCaLamViec> nguoiDungCaLamViecPage;
        if(text == ""){
            nguoiDungCaLamViecPage = nguoiDungCaLamViecService.findAll(pageable);
        }else {
            nguoiDungCaLamViecPage = nguoiDungCaLamViecService.search(text,pageable);
        }
        return Optional.ofNullable(nguoiDungCaLamViecPage)
                .map(nguoiDungCaLamViecs -> nguoiDungCaLamViecs.getTotalElements() != 0 ? JsonResult.found(PageJson.build(nguoiDungCaLamViecs))
                        : JsonResult.notFound("NguoiDungCaLamViec/Page"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @PutMapping("/trang-thai")
    @ApiOperation(value = "thay doi trang thai nguoi dung ca lam viec")
    public ResponseEntity<JsonResult> setStatus(@RequestParam("id") int id,
                                                @RequestParam("status") int status) {
        if (nguoiDungCaLamViecService.setStatus(id, status)) {
            return JsonResult.success("thay doi trang thai thanh cong");
        } else return JsonResult.success("thay doi trang thai that bai");
    }

    @PutMapping("/check")
    @ApiOperation(value = "checkin ca lam viec", response = NguoiDungCaLamViec.class)
    public ResponseEntity<JsonResult> putCheck(@RequestParam("nguoi-dung-id") int nguoiDungId) {
        List<CaLamViec> caLamViecList = caLamViecService.findAll();
        String checkInTime = new SimpleDateFormat("HH:mm").format(new Date());
        Integer caLamViecId = null;
        boolean checkIO = false;
        for (int i = 0; i < caLamViecList.size(); i++) {
            System.out.println(checkInTime.compareTo(caLamViecList.get(i).getBatDauChoPhepCheckIn()));
            System.out.println(checkInTime.compareTo(caLamViecList.get(i).getKetThucChoPhepCheckIn()));
            if (checkInTime.compareTo(caLamViecList.get(i).getBatDauChoPhepCheckIn()) > 0
                    && checkInTime.compareTo(caLamViecList.get(i).getKetThucChoPhepCheckIn()) < 0) {
                caLamViecId = caLamViecList.get(i).getId();
                checkIO=true;//check in
            }

            if (checkInTime.compareTo(caLamViecList.get(i).getBatDauChoPhepCheckOut()) > 0
                    && checkInTime.compareTo(caLamViecList.get(i).getKetThucChoPhepCheckOut()) < 0) {
                caLamViecId = caLamViecList.get(i).getId();
                checkIO=false;//check out
            }
        }
        String thoiGian = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        boolean finalCheckIO = checkIO;
        return nguoiDungCaLamViecService.findByNguoiDungAndCaLamViecAndNgayThang(nguoiDungId, caLamViecId, thoiGian)
                .map(nguoiDungCaLamViec -> {
                    CaLamViec caLamViec = nguoiDungCaLamViec.getCaLamViec();
                    try {
                        int status = 0;
                        if (checkInTime.compareTo(caLamViec.getCheckIn()) >= 0) {
                            status = 1; // di dung gio
                        } else if (checkInTime.compareTo(caLamViec.getCheckIn()) < 0) {
                            status = 2; // di muon
                        }
                        TimeZone tz = TimeZone.getTimeZone("GMT+7:00");
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
                        df.setTimeZone(tz);
                        String nowAsISO = df.format(new Date());
                        nguoiDungCaLamViec.setStatusCheckin(status);
                        if (finalCheckIO){
                            nguoiDungCaLamViec.setCheckin(nowAsISO);
                        }else {
                            nguoiDungCaLamViec.setCheckout(nowAsISO);
                        }
                        return Optional.ofNullable(nguoiDungCaLamViecService.save(nguoiDungCaLamViec))
                                .map(JsonResult::updated)
                                .orElse(JsonResult.serverError("Internal Server Error"));
                    } catch (Exception ex) {
                        return JsonResult.badRequest("Thoi gian sai cu phap");
                    }
                }).orElse(JsonResult.parentNotFound("nguoiDungCaLamViec"));

    }

    @PostMapping(value = "/upload")
    @ApiOperation(value = "post nguoi dung ca lam viec", response = NguoiDungCaLamViec.class)
    public ResponseEntity<JsonResult> post(@RequestBody NguoiDungCaLamViecUpload nguoiDungCaLamViecUpload) {
        NguoiDungCaLamViec nguoiDungCaLamViec = new NguoiDungCaLamViec();
        return caLamViecService.findByIdAndXoa(nguoiDungCaLamViecUpload.getCaLamViecId(), false)
                .map(caLamViec -> {
                    nguoiDungCaLamViec.setCaLamViec(caLamViec);
                    return nguoiDungService.findById(nguoiDungCaLamViecUpload.getNguoiDungId(), false)
                            .map(nguoiDung -> {
                                nguoiDungCaLamViec.setNguoiDung(nguoiDung);
                                nguoiDungCaLamViec.setXoa(false);
//                                LocalDateTime now = LocalDateTime.now();
//                                Date out = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
                                //cho nay
                                nguoiDungCaLamViec.setNgayThang(nguoiDungCaLamViecUpload.getNgayThang());
                                return nguoiDungCaLamViecService.save(nguoiDungCaLamViec)
                                        .map(JsonResult::uploaded)
                                        .orElse(JsonResult.saveError("nguoi dung ca lam viec"));
                            }).orElse(JsonResult.parentNotFound("nguoi dung"));
                }).orElse(JsonResult.parentNotFound("ca lam viec"));
    }

    @PostMapping(value = "/upload-list")
    @ApiOperation(value = "post nguoi dung ca lam viec", response = NguoiDungCaLamViec.class)
    public ResponseEntity<JsonResult> post(@RequestBody NgayThang ngayThangObj, // format yyyy-MM-dd
                                           @RequestParam("ca-lam-viec-id") int caLamViecId,
                                           @RequestParam("nguoi-dung-id") int nguoiDungId) {
        List<String> ngayThangList = ngayThangObj.getNgayThangList();
        Optional<CaLamViec> caLamViecOptional = caLamViecService.findByIdAndXoa(caLamViecId, false);
        if (!caLamViecOptional.isPresent()) {
            return JsonResult.parentNotFound("CaLamViec");
        }
        Optional<NguoiDung> nguoiDungOptional = nguoiDungService.findById(nguoiDungId, false);
        if (!nguoiDungOptional.isPresent()) {
            return JsonResult.parentNotFound("NguoiDung");
        }
        for (String ngayThang : ngayThangList) {
            NguoiDungCaLamViec nguoiDungCaLamViec = new NguoiDungCaLamViec();
            nguoiDungCaLamViec.setNguoiDung(nguoiDungOptional.get());
            nguoiDungCaLamViec.setCaLamViec(caLamViecOptional.get());
            nguoiDungCaLamViec.setNgayThang(ngayThang);
            nguoiDungCaLamViec.setStatus(0);
            nguoiDungCaLamViec.setXoa(false);
            try {
                nguoiDungCaLamViecService.save(nguoiDungCaLamViec);
            } catch (Exception ex) {
                return JsonResult.serverError("Internal Server Error");
            }
        }
        return JsonResult.success("OK");
    }

    @GetMapping("/find-by-ca-lam-viec-va-thoi-gian")
    public ResponseEntity<JsonResult> findByCaLamViecVaThoiGian(@RequestParam("ca-lam-viec-id") int caLamViecId,
                                                                @RequestParam("ngay-dau") String ngayDau,
                                                                @RequestParam("ngay-cuoi") String ngayCuoi,
                                                                @RequestParam("status") int status,
                                                                @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                                @RequestParam(name = "size", defaultValue = "10", required = false) int size) {

        Pageable pageable = PageRequest.of(page - 1, size);
        return Optional.ofNullable(nguoiDungCaLamViecService.findByCaLamViecAndThoiGian(caLamViecId, ngayDau, ngayCuoi, status, pageable))
                .map(JsonResult::found)
                .orElse(JsonResult.serverError("Internal Server Error"));

    }

    @GetMapping("/find-by-nguoi-dung-va-thoi-gian")
    public ResponseEntity<JsonResult> findByNguoiDungAndNgayThang(@RequestParam("nguoi-dung-id") int nguoiDungId,
                                                                  @RequestParam("ngay-dau") String ngayDau,
                                                                  @RequestParam("ngay-cuoi") String ngayCuoi,
                                                                  @RequestParam("status") int status,
                                                                  @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                                  @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return Optional.ofNullable(nguoiDungCaLamViecService.findByNguoiDungAndNgayThang(nguoiDungId, ngayDau, ngayCuoi, status, pageable))
                .map(JsonResult::found)
                .orElse(JsonResult.serverError("Internal Server Error"));

    }

    @DeleteMapping("/delete")
    public ResponseEntity<JsonResult> delete(@RequestParam("id") int id) {
        Boolean bool = nguoiDungCaLamViecService.delete(id);
        if (bool) {
            return JsonResult.deleted();
        }
        return JsonResult.saveError("delete-nguoi-dung-ca-lam-viec-error");
    }

    @GetMapping("/excel")
    public ResponseEntity<JsonResult> getExcel(@RequestParam("nguoi-dung-id") int nguoiDungId,
                                               @RequestParam("list-nguoi-dung-ca-lam-viec") List<Integer> list) {
        return nguoiDungService.findById(nguoiDungId, false)
                .map(nguoiDung -> {
                    ExcelFile excelFile = new ExcelFile();
                    excelFile.setNguoiDung(nguoiDung);
                    List<NguoiDungCaLamViec> listBill = nguoiDungCaLamViecService.findListNguoiDungCaLamViec(list);
                    XSSFWorkbook workbook = ExcelUtils.createListBillExcelNguoiDungCaLamViec(listBill);
                    try {
                        String fileName = "CaLamViec" + LocalDateTime.now().getNano() + ".xlsx";
                        File file = new File(UPLOAD_DIRECTORY + fileName);
                        file.getParentFile().mkdirs();
                        FileOutputStream outFile = new FileOutputStream(file);
                        workbook.write(outFile);
                        String urlFile = awss3Service.uploadFileExcel(file);
                        file.delete();
                        excelFile.setMaPhieu("DSNDCLV-"+ Random.randomCode());
                        excelFile.setKieuFile(DatabaseConstant.TypeOfFile.EXCEL_FILE);
                        excelFile.setTenLoai(DatabaseConstant.File.Excel.NGUOI_DUNG_CA_LAM_VIEC_EXCEL_FILE);
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

}

