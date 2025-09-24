package com.tavi.tavi_mrs.controller.nha_cung_cap;

import com.tavi.tavi_mrs.entities.bao_cao.ExcelFile;
import com.tavi.tavi_mrs.entities.constant.DatabaseConstant;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.entities.khach_hang.KhachHang;
import com.tavi.tavi_mrs.entities.khach_hang.KhachHangDto;
import com.tavi.tavi_mrs.entities.nha_cung_cap.NhaCungCap;
import com.tavi.tavi_mrs.entities.nha_cung_cap.NhaCungCapDto;
import com.tavi.tavi_mrs.payload.khach_hang.KhachHangForm;
import com.tavi.tavi_mrs.payload.nha_cung_cap.NhaCungCapForm;
import com.tavi.tavi_mrs.service.aws.AWSS3Service;
import com.tavi.tavi_mrs.service.bao_cao.ExcelFileService;
import com.tavi.tavi_mrs.service.nguoi_dung.NguoiDungService;
import com.tavi.tavi_mrs.service.nha_cung_cap.NhaCungCapService;
import com.tavi.tavi_mrs.utils.DateTimeUtils;
import com.tavi.tavi_mrs.utils.ExcelUtils;
import com.tavi.tavi_mrs.utils.Random;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

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
@RequestMapping("api/v1/admin/nha-cung-cap")
public class NhaCungCapController {

    @Autowired
    private NhaCungCapService nhaCungCapService;

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private ExcelFileService excelFileService;

    @Autowired
    private AWSS3Service awss3Service;

    private String UPLOAD_DIRECTORY = DatabaseConstant.Directory.URL_DIRECTORY;

    @GetMapping("/find-all")
    public ResponseEntity<JsonResult> findAll(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                              @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<NhaCungCap> nhaCungCapPage = nhaCungCapService.findAllToPage(pageable);
        return Optional.ofNullable(nhaCungCapPage)
                .map(nhaCungCaps -> nhaCungCaps.getTotalElements() != 0 ? JsonResult.found(PageJson.build(nhaCungCaps)) : JsonResult.notFound("NhaCungCap/Page"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findById(@RequestParam("id") int id) {
        return nhaCungCapService.findByIdAndXoa(id, false)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/search")
    public ResponseEntity<JsonResult> search(@RequestParam(value = "ten", required = false, defaultValue = "") String ten,
                                             @RequestParam(value = "dia-chi", required = false, defaultValue = "") String diaChi,
                                             @RequestParam(value = "email", required = false, defaultValue = "") String email,
                                             @RequestParam(value = "dien-thoai", required = false, defaultValue = "") String dienThoai,
                                             @RequestParam(value = "facebook", required = false, defaultValue = "") String facebook,
                                             @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                             @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<NhaCungCap> nhaCungCapPage;
        if ("".equals(ten) && "".equals(diaChi) && "".equals(dienThoai) && "".equals(facebook)) {
            nhaCungCapPage = nhaCungCapService.findAllToPage(pageable);
        } else {
            nhaCungCapPage = nhaCungCapService.findByTenAndDiaChiAndEmailAndDienThoaiAndFacebook(ten, diaChi, email, dienThoai, facebook, pageable);
        }
        return Optional.ofNullable(nhaCungCapPage)
                .map(nhaCungCaps -> nhaCungCaps.getTotalElements() != 0 ? JsonResult.found(PageJson.build(nhaCungCaps)) : JsonResult.notFound("NhaCungCap/Ten/DiaChi/DienThoai/Facebook"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/search-text")
    public ResponseEntity<JsonResult> searchText(@RequestParam(value = "text", required = false, defaultValue = "") String text,
                                                 @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                 @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<NhaCungCap> nhaCungCapPage;
        if ("".equals(text)) {
            nhaCungCapPage = nhaCungCapService.findAllToPage(pageable);
        } else {
            nhaCungCapPage = nhaCungCapService.search(text, pageable);
        }
        return Optional.ofNullable(nhaCungCapPage)
                .map(nhaCungCaps -> nhaCungCaps.getTotalElements() != 0 ? JsonResult.found(PageJson.build(nhaCungCaps)) : JsonResult.notFound("NhaCungCap/Ten/DiaChi/DienThoai/Facebook"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @PostMapping("/upload")
    public ResponseEntity<JsonResult> upload(@RequestBody NhaCungCap nhaCungCap) {
        nhaCungCap.setXoa(false);
        return nhaCungCapService.save(nhaCungCap)
                .map(JsonResult::uploaded)
                .orElse(JsonResult.saveError("NhaCungCap"));
    }

    @PutMapping("/update")
    public ResponseEntity<JsonResult> update(@RequestBody NhaCungCap nhaCungCap) {
        return nhaCungCapService.save(nhaCungCap)
                .map(JsonResult::updated)
                .orElse(JsonResult.saveError("NhaCungCap"));
    }

    @PutMapping("/nhap-hang/update")
    public ResponseEntity<JsonResult> updateNhapHang(@RequestBody NhaCungCapForm nhaCungCap) {
        Optional<NhaCungCap> nhaCungCapOptional=nhaCungCapService.findByIdAndXoa(nhaCungCap.getId(),false);
        if (nhaCungCapOptional.isPresent()){
            NhaCungCap nhaCungCapUpdate=new NhaCungCap();
            nhaCungCapUpdate.setId(nhaCungCap.getId());
            nhaCungCapUpdate.setDiaChi(nhaCungCap.getDiaChi());
            nhaCungCapUpdate.setDienThoai(nhaCungCap.getDienThoai());
            nhaCungCapUpdate.setEmail(nhaCungCap.getEmail());

            nhaCungCapUpdate.setTrangThai(nhaCungCapOptional.get().getTrangThai());
            nhaCungCapUpdate.setFacebook(nhaCungCapOptional.get().getFacebook());
            nhaCungCapUpdate.setGhiChu(nhaCungCapOptional.get().getGhiChu());
            nhaCungCapUpdate.setTongMua(nhaCungCapOptional.get().getTongMua());
            nhaCungCapUpdate.setTongNo(nhaCungCapOptional.get().getTongNo());
            nhaCungCapUpdate.setTen(nhaCungCapOptional.get().getTen());
            nhaCungCapUpdate.setXoa(false);

            return nhaCungCapService.save(nhaCungCapUpdate)
                    .map(JsonResult::updated)
                    .orElse(JsonResult.saveError("NhaCungCap"));
        }else {
            JsonResult.idNotFound();
        }
        return JsonResult.serverError("Internal Server error");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<JsonResult> delete(@RequestParam("id") int id) {
        Optional<NhaCungCap> nhaCungCap = nhaCungCapService.findByIdAndXoa(id, false);
        if (nhaCungCap.isPresent()) {
            Boolean bool = nhaCungCapService.deleted(id);
            if (bool) {
                return JsonResult.deleted();
            }
            return JsonResult.saveError("NhaCungCap");
        } else {
            return JsonResult.idNotFound();
        }
    }

    @GetMapping("/excel")
    public ResponseEntity<JsonResult> getExcel(@RequestParam("nguoi-dung-id") int nguoiDungId,
                                               @RequestParam("list-nha-cung-cap") List<Integer> list) {
        return nguoiDungService.findById(nguoiDungId, false)
                .map(nguoiDung -> {
                    ExcelFile excelFile = new ExcelFile();
                    excelFile.setNguoiDung(nguoiDung);
                    List<NhaCungCap> listNCC = nhaCungCapService.findListNhaCungCap(list);
                    XSSFWorkbook workbook = ExcelUtils.createDanhSachNhaCungCapExcel(listNCC);
                    try {
                        String fileName = "ListNCC_" + LocalDateTime.now().getNano() + ".xlsx";
                        File file = new File(UPLOAD_DIRECTORY + fileName);
                        file.getParentFile().mkdirs();
                        FileOutputStream outFile = new FileOutputStream(file);
                        workbook.write(outFile);
                        String urlFile = awss3Service.uploadFileExcel(file);
                        file.delete();
                        excelFile.setKieuFile(DatabaseConstant.TypeOfFile.EXCEL_FILE);
                        excelFile.setTenLoai(DatabaseConstant.File.Excel.NHA_CUNG_CAP_EXCEL_FILE);
                        excelFile.setMaPhieu("DSNCC-"+ Random.randomCode());
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
