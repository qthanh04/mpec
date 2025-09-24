package com.tavi.tavi_mrs.controller.hang_hoa;

import com.tavi.tavi_mrs.entities.bao_cao.ExcelFile;
import com.tavi.tavi_mrs.entities.constant.DatabaseConstant;
import com.tavi.tavi_mrs.entities.hang_hoa.ChiNhanhHangHoa;
import com.tavi.tavi_mrs.entities.hang_hoa.HangHoa;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDung;
import com.tavi.tavi_mrs.entities.so_quy.PhieuThu;
import com.tavi.tavi_mrs.service.aws.AWSS3Service;
import com.tavi.tavi_mrs.service.bao_cao.ExcelFileService;
import com.tavi.tavi_mrs.service.hang_hoa.ChiNhanhHangHoaService;
import com.tavi.tavi_mrs.service.nguoi_dung.NguoiDungService;
import com.tavi.tavi_mrs.utils.ExcelUtils;
import com.tavi.tavi_mrs.utils.Random;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/chi-nhanh-hang-hoa")
public class ChiNhanhHangHoaController {

    @Autowired
    ChiNhanhHangHoaService chiNhanhHangHoaService;

    private String UPLOAD_DIRECTORY = DatabaseConstant.Directory.URL_DIRECTORY;

    @Autowired
    private AWSS3Service awss3Service;

    @Autowired
    NguoiDungService nguoiDungService;

    @Autowired
    private ExcelFileService excelFileService;

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findById(@RequestParam("id") int idHoaDon) {
        return chiNhanhHangHoaService.findById(idHoaDon, false)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/search")
    ResponseEntity<JsonResult> search(
            @RequestParam(value = "chi-nhanh-id", required = false, defaultValue = "0") int chiNhanhId,
            @RequestParam(value = "text", defaultValue = "") String text,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ChiNhanhHangHoa> chiNhanhHangHoaPage;
        if (chiNhanhId == 0 && text == "") {
            chiNhanhHangHoaPage = chiNhanhHangHoaService.findAllToPage(pageable);
        } else {
            chiNhanhHangHoaPage = chiNhanhHangHoaService.findByChiNhanhAndText(chiNhanhId, text, pageable);
        }
        return Optional.ofNullable(chiNhanhHangHoaPage)
                .map(hangHoas -> hangHoas.getTotalElements() != 0 ? JsonResult.found(PageJson.build(hangHoas)) : JsonResult.notFound("ChiNhanhHangHoa"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/excel")
    public ResponseEntity<JsonResult> getExcel(@RequestParam("nguoi-dung-id") int nguoiDungId,
                                               @RequestParam("list-hang-hoa") List<Integer> list) {
        return nguoiDungService.findById(nguoiDungId, false)
                .map(nguoiDung -> {
                    ExcelFile excelFile = new ExcelFile();
                    excelFile.setNguoiDung(nguoiDung);
                    List<ChiNhanhHangHoa> listBill = chiNhanhHangHoaService.findListChiNhanhHangHoa(list);
                    XSSFWorkbook workbook = ExcelUtils.createListBillExcelHangHoa(listBill);
                    try {
                        String fileName = "ListHangHoa_" + LocalDateTime.now().getNano() + ".xlsx";
                        File file = new File(UPLOAD_DIRECTORY + fileName);
                        file.getParentFile().mkdirs();
                        FileOutputStream outFile = new FileOutputStream(file);
                        workbook.write(outFile);
                        String urlFile = awss3Service.uploadFileExcel(file);
                        file.delete();
                        excelFile.setKieuFile(DatabaseConstant.TypeOfFile.EXCEL_FILE);
                        excelFile.setTenLoai(DatabaseConstant.File.Excel.CHI_NHANH_HANG_HOA_EXCEL_FILE);
                        excelFile.setMaPhieu("DSCNHH-"+ Random.randomCode());
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
