package com.tavi.tavi_mrs.controller.hang_hoa;

import com.tavi.tavi_mrs.entities.constant.DatabaseConstant;
import com.tavi.tavi_mrs.entities.don_vi.DonViHangHoa;
import com.tavi.tavi_mrs.entities.hang_hoa.HangHoa;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.service.chi_nhanh.ChiNhanhService;
import com.tavi.tavi_mrs.service.don_vi.DonViHangHoaService;
import com.tavi.tavi_mrs.service.don_vi.DonViService;
import com.tavi.tavi_mrs.service.hang_hoa.ChiNhanhHangHoaService;
import com.tavi.tavi_mrs.service.hang_hoa.HangHoaService;
import com.tavi.tavi_mrs.service.hang_hoa.NhomHangService;
import com.tavi.tavi_mrs.service.hang_hoa.ThuongHieuService;
import com.tavi.tavi_mrs.utils.ZXingHelper;
import com.tavi.tavi_mrs.utils.hang_hoa.ImportExcelHangHoa;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/hang-hoa")
public class HangHoaController {

    @Autowired
    private HangHoaService hangHoaService;

    @Autowired
    private ThuongHieuService thuongHieuService;

    @Autowired
    private NhomHangService nhomHangService;

    @Autowired
    private DonViHangHoaService donViHangHoaService;

    @Autowired
    private ChiNhanhHangHoaService chiNhanhHangHoaService;

    @Autowired
    private DonViService donViService;

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findById(@RequestParam("id") int idThietBi) {
        return hangHoaService.findById(idThietBi, false)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/find-all")
    ResponseEntity<JsonResult> findAllToPage(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                             @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<HangHoa> hangHoaPage = hangHoaService.findAllToPage(pageable);
        return Optional.ofNullable(hangHoaPage)
                .map(hangHoas -> hangHoas.getTotalElements() != 0 ? JsonResult.found(PageJson.build(hangHoas)) : JsonResult.notFound("HangHoa/Page"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/search")
    ResponseEntity<JsonResult> search(@RequestParam(value = "tenHangHoa", required = false, defaultValue = "") String tenHangHoa,
                                      @RequestParam(value = "nhomHangId", required = false, defaultValue = "0") int nhomHangId,
                                      @RequestParam(value = "thuongHieuId", required = false, defaultValue = "0") int thuongHieuId,
                                      @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                      @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<HangHoa> hangHoaPage;
        if (tenHangHoa.equals("") && nhomHangId == 0 && thuongHieuId == 0) {
            hangHoaPage = hangHoaService.findAllToPage(pageable);
        }else {
            hangHoaPage = hangHoaService.findByTenHangHoaAndNhomHangAndThuongHieu(tenHangHoa, nhomHangId, thuongHieuId, pageable);
        }
        return Optional.ofNullable(hangHoaPage)
                .map(hangHoas -> hangHoas.getTotalElements() != 0 ? JsonResult.found(PageJson.build(hangHoas)) : JsonResult.notFound("ThietBi/TenHangHoa/NhomHang/ThuongHieu"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/search-text")
    ResponseEntity<JsonResult> searchText(@RequestParam(value = "text", required = false, defaultValue = "") String text,
                                          @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                          @RequestParam(name = "size", defaultValue = "9999", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<HangHoa> hangHoaPage;
        if (text.equals("")) {
            hangHoaPage = hangHoaService.findAllToPage(pageable);
        } else {
            hangHoaPage = hangHoaService.search(text, pageable);
        }
        return Optional.ofNullable(hangHoaPage)
                .map(hangHoas -> hangHoas.getTotalElements() != 0 ? JsonResult.found(PageJson.build(hangHoas)) : JsonResult.notFound("HangHoa"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/search-ma-vach")
    ResponseEntity<JsonResult> searchMaVach(@RequestParam(value = "ma-vach", required = false, defaultValue = "") String maVach) {
        return hangHoaService.findByMaVach(maVach, false)
                .map(JsonResult::found)
                .orElse(JsonResult.notFound("MaVach"));
    }

    @PostMapping("/upload")
    @ApiOperation(value = "post hang hoa", response = HangHoa.class)
    public ResponseEntity<JsonResult> post(@RequestBody HangHoa hangHoa,
                                           @RequestParam("chi-nhanh-id") int chiNhanhId,
                                           @RequestParam("don-vi-id") int donViId,
                                           @RequestParam("nhom-hang-id") int nhomHangId,
                                           @RequestParam("thuong-hieu-id") int thuongHieuId) {
        DonViHangHoa donViHangHoa = new DonViHangHoa();
        return nhomHangService.findById(nhomHangId, false)
                .map(nhomHang -> {
                    hangHoa.setNhomHang(nhomHang);
                    return thuongHieuService.findByIdAndXoa(thuongHieuId, false)
                            .map(thuongHieu -> {
                                hangHoa.setThuongHieu(thuongHieu);
                                return hangHoaService.save(hangHoa).map(hangHoa1 -> {
                                    chiNhanhHangHoaService.save(chiNhanhId,hangHoa1.getId());
                                    donViHangHoa.setHangHoa(hangHoa1);
                                    donViHangHoa.setDonVi(donViService.findById(donViId));
                                    donViHangHoa.setTyLe((float) 1.0);
                                    donViHangHoa.setXoa(false);
                                    return donViHangHoaService.save(donViHangHoa).map(JsonResult::uploaded)
                                            .orElse(JsonResult.serverError("DonViHangHoa"));
                                }).orElse(JsonResult.saveError("Hang Hoa"));
                            }).orElse(JsonResult.parentNotFound("Thuong Hieu"));
                }).orElse(JsonResult.parentNotFound("Nhom Hang"));
    }

    @PutMapping("/update")
    @ApiOperation(value = "put hang hoa", response = HangHoa.class)
    public ResponseEntity<JsonResult> update(@RequestBody HangHoa hangHoa,
                                             @RequestParam("don-vi-id") int donViId,
                                             @RequestParam("nhom-hang-id") int nhomHangId,
                                             @RequestParam("don-vi-hang-hoa-id") int donViHangHoaId,
                                             @RequestParam("thuong-hieu-id") int thuongHieuId) {
        hangHoa.setXoa(false);
        DonViHangHoa donViHangHoa = new DonViHangHoa();
        return nhomHangService.findById(nhomHangId, false)
                .map(nhomHang -> {
                    hangHoa.setNhomHang(nhomHang);
                    return thuongHieuService.findByIdAndXoa(thuongHieuId, false)
                            .map(thuongHieu -> {
                                hangHoa.setThuongHieu(thuongHieu);
                                return hangHoaService.update(hangHoa).map(hangHoa1 -> {
                                    donViHangHoa.setId(donViHangHoaId);
                                    donViHangHoa.setHangHoa(hangHoa1);
                                    donViHangHoa.setDonVi(donViService.findById(donViId));
                                    donViHangHoa.setTyLe((float) 1.0);
                                    donViHangHoa.setXoa(false);
                                    return donViHangHoaService.save(donViHangHoa).map(JsonResult::updated)
                                            .orElse(JsonResult.serverError("DonViHangHoa"));
                                }).orElse(JsonResult.saveError("HangHoa"));
                            }).orElse(JsonResult.parentNotFound("Thuong Hieu"));
                }).orElse(JsonResult.parentNotFound("Nhom Hang"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<JsonResult> delete(@RequestParam("id") int id) {
        Optional<HangHoa> hangHoa = hangHoaService.findById(id, false);

        if (hangHoa.isPresent()) {

            Boolean bool = hangHoaService.deleted(id);
            if (bool) {
                return JsonResult.deleted();
            } else {
                return JsonResult.saveError("Delete HangHoa");
            }
        } else {
            return JsonResult.idNotFound();
        }
    }

    @GetMapping("/qrcode/{ma}")
    public ResponseEntity<JsonResult> qrcode(@PathVariable("ma") String maHangHoa, HttpServletResponse response) throws Exception {
        Optional<HangHoa> hangHoaOptional = hangHoaService.findByMaHangHoa(maHangHoa, false);
        if (hangHoaOptional.isPresent()) {
//            response.setContentType("image/png");
            String codeString = DatabaseConstant.Host.URL_LOCAL_BACKEND + "api/v1/public/hang-hoa/find-by-ma/" + hangHoaOptional.get().getMa();

            return JsonResult.success(ZXingHelper.getQRCodeImage(codeString, 200, 200));
        } else {
            return JsonResult.badRequest("Hàng hóa không tồn tại QR Code");
        }
    }

    //ham sinh anh barcode, ben trong la ma vach san pham
    @GetMapping("/barcode/{ma}")
    public ResponseEntity<JsonResult> findbBarcode(@PathVariable("ma") String maVach, HttpServletResponse response) throws Exception {
        Optional<HangHoa> hangHoaOptional = hangHoaService.findByMaVach(maVach, false);
        if (hangHoaOptional.isPresent()) {
            String codeString = hangHoaOptional.get().getMaVach();
            return JsonResult.success(ZXingHelper.getBarCodeImage(codeString, 200, 50));
        } else {
            return JsonResult.badRequest("Hàng hóa không tồn tại Bar Code");
        }
    }

    @GetMapping("/find-by-barcode/{ma}")
    public ResponseEntity<JsonResult> findbyBarcode(@PathVariable("ma") String maVach, HttpServletResponse response) throws Exception {
        return hangHoaService.findByMaVach(maVach, false)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }


    @PostMapping("/import")
    public ResponseEntity<JsonResult> uploadFile(@RequestPart("file") MultipartFile file) {
        String message = "";

        if (ImportExcelHangHoa.hasExcelFormat(file)) {
            try {
                hangHoaService.saveByExcel(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return JsonResult.success(message);
            } catch (Exception e) {
                System.out.println(e);
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return JsonResult.serverError(message);
            }
        }

        message = "Please upload an excel file!";
        return JsonResult.badRequest(message);
    }


}
