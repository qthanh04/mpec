package com.tavi.tavi_mrs.controller.phieu_tra_hang_nhap;

import com.tavi.tavi_mrs.entities.bao_cao.ExcelFile;
import com.tavi.tavi_mrs.entities.constant.DatabaseConstant;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.entities.phieu_tra_hang_nhap.PhieuTraHangNhap;
import com.tavi.tavi_mrs.entities.phieu_tra_hang_nhap.PhieuTraHangNhapChiTiet;
import com.tavi.tavi_mrs.payload.nha_cung_cap.tra_hang.TienNhaCungCapTra;
import com.tavi.tavi_mrs.payload.nha_cung_cap.tra_hang.TienNhaCungCapTraList;
import com.tavi.tavi_mrs.service.aws.AWSS3Service;
import com.tavi.tavi_mrs.service.bao_cao.ExcelFileService;
import com.tavi.tavi_mrs.service.mail.MailService;
import com.tavi.tavi_mrs.service.nguoi_dung.NguoiDungService;
import com.tavi.tavi_mrs.service.nha_cung_cap.NhaCungCapService;
import com.tavi.tavi_mrs.service.phieu_tra_hang_nhap.PhieuTraHangNhapChiTietService;
import com.tavi.tavi_mrs.service.phieu_tra_hang_nhap.PhieuTraHangNhapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/phieu-tra-hang-nhap")
public class PhieuTraHangNhapController {

    @Autowired
    private PhieuTraHangNhapService phieuTraHangNhapService;

    @Autowired
    private NhaCungCapService nhaCungCapService;

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private PhieuTraHangNhapChiTietService phieuTraHangNhapChiTietService;

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
        Page<PhieuTraHangNhap> phieuTraHangNhapPage = phieuTraHangNhapService.findAll(pageable);
        return Optional.ofNullable(phieuTraHangNhapPage)
                .map(phieuTraHangNhaps -> phieuTraHangNhaps.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phieuTraHangNhaps)) : JsonResult.notFound("PhieuTraHangNhap/Page"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findById(@RequestParam("id") int id) {
        return phieuTraHangNhapService.findByIdAndXoa(id, false)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/find-by-id-nha-cung-cap")
    ResponseEntity<JsonResult> findByIdNhaCungCap(@RequestParam("nha-cung-cap-id") int idNhaCungCap,
                                                 @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                 @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PhieuTraHangNhap> phieuTraHangNhapPage = phieuTraHangNhapService.findByIdNhaCungCap(idNhaCungCap, false, pageable);
        return Optional.ofNullable(phieuTraHangNhapPage)
                .map(phieuTraHangNhaps -> phieuTraHangNhaps.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phieuTraHangNhaps)) : JsonResult.notFound("HoaDon/NhaCungCap/Page"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }

    @GetMapping("/search")
    public ResponseEntity<JsonResult> search(@RequestParam(value = "ten-nha-cung-cap", defaultValue = "", required = false) String tenNhaCungCap,
                                             @RequestParam(value = "ngay-dau", defaultValue = "1970-01-01", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayDau,
                                             @RequestParam(value = "ngay-cuoi", defaultValue = "2099-12-31", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayCuoi,
                                             @RequestParam(value = "trang-thai", defaultValue = "1", required = false) int trangThai,
                                             @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                             @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PhieuTraHangNhap> phieuTraHangNhapPage;
        if ("".equals(tenNhaCungCap) && ngayDau == null && ngayCuoi == null) {
            phieuTraHangNhapPage = phieuTraHangNhapService.findAll(pageable);
        } else {
            System.out.println(trangThai);
            System.out.println(size);
            phieuTraHangNhapPage = phieuTraHangNhapService.findByNhaCungCapAndThoiGianAndTrangThai(tenNhaCungCap, ngayDau, ngayCuoi, trangThai, pageable);
        }
        return Optional.ofNullable(phieuTraHangNhapPage)
                .map(phieuTraHangNhaps -> phieuTraHangNhaps.getTotalElements() != 0 ? JsonResult.found(PageJson.build(phieuTraHangNhaps)) : JsonResult.notFound("PhieuTraHangNhap/Search"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @PostMapping("/upload")
    public ResponseEntity<JsonResult> upload(@RequestParam("nguoi-dung-id") int nguoiDungId,
                                             @RequestParam("nha-cung-cap-id") int nhaCungCapId,
                                             @RequestBody PhieuTraHangNhap phieuTraHangNhap) {
        return nguoiDungService.findById(nguoiDungId, false)
                .map(nguoiDung -> {
                    phieuTraHangNhap.setNguoiDung(nguoiDung);
                    return nhaCungCapService.findByIdAndXoa(nhaCungCapId, false)
                            .map(nhaCungCap -> {
                                phieuTraHangNhap.setNhaCungCap(nhaCungCap);
                                phieuTraHangNhap.setTrangThai(1);
                                phieuTraHangNhap.setXoa(false);
                                return phieuTraHangNhapService.save(phieuTraHangNhap)
                                        .map(JsonResult::uploaded)
                                        .orElse(JsonResult.saveError("Phieu Tra Hang Nhap"));
                            }).orElse(JsonResult.parentNotFound("NhaCungCap"));
                }).orElse(JsonResult.parentNotFound("NguoiDung"));
    }

    @GetMapping("/pdf/{ma}")
    public ResponseEntity<JsonResult> createPDF(@PathVariable("ma") String ma,
                                                @RequestParam("nguoi-dung-id") int nguoiDungId) {
        return phieuTraHangNhapService.findByMa(ma)
                .map(phieuTraHangNhap -> {
                    List<PhieuTraHangNhapChiTiet> phieuTraHangNhapChiTietList = phieuTraHangNhapChiTietService.findListByPhieuTraHangNhapId(phieuTraHangNhap.getId());
                    File file = phieuTraHangNhapService.createPdf(phieuTraHangNhap, phieuTraHangNhapChiTietList);
                    return nguoiDungService.findById(nguoiDungId, false)
                            .map(nguoiDung -> {
                                String urlFile = awss3Service.uploadFileExcel(file);
                                file.delete();
                                String url = DatabaseConstant.URL.AWS_S3 + urlFile;
                                if (phieuTraHangNhap.getNhaCungCap().getEmail() != null && !phieuTraHangNhap.getNhaCungCap().getEmail().equals("")) {
                                    String header = "Xác nhận trả hàng";
                                    String content = "Nhà cung cấp : " + phieuTraHangNhap.getNhaCungCap().getTen()
                                            + "\nPhiếu trả hàng : " + url
                                            + "\nXin lỗi quý khách vì sự bất tiện này";
                                    String[] mails = {phieuTraHangNhap.getNhaCungCap().getEmail()};
                                    mailService.sendMail(mails, header, content);
                                }
                                ExcelFile excelFile = new ExcelFile();
                                excelFile.setUrl(url);
                                excelFile.setMaPhieu(phieuTraHangNhap.getMaPhieu());
                                excelFile.setKieuFile(DatabaseConstant.TypeOfFile.PDF_FILE);
                                excelFile.setTenLoai(DatabaseConstant.File.PDF.PHIEU_TRA_NHA_CUNG_CAP_PDF_FILE);
                                excelFile.setNguoiDung(nguoiDung);
                                return excelFileService.save(excelFile)
                                        .map(JsonResult::uploaded)
                                        .orElse(JsonResult.serverError("Internal Server Error"));
                            }).orElse(JsonResult.parentNotFound("NguoiDung"));
                }).orElse(JsonResult.parentNotFound("PhieuTraHangNhap"));
    }

    @PutMapping("/update-tien-tra")
    public ResponseEntity<JsonResult> updateTienNCCTra(@RequestBody TienNhaCungCapTraList tienNhaCungCapTraList) {
        List<TienNhaCungCapTra> tienNhaCungCapTras = tienNhaCungCapTraList.getTienNhaCungCapTras();
        try {
            for (int i = 0; i<tienNhaCungCapTras.size(); i++) {
                Optional<PhieuTraHangNhap> phieuTraHangNhap = phieuTraHangNhapService.findByIdAndXoa(tienNhaCungCapTras.get(i).getPhieuTraHangNhapId(), false);
                phieuTraHangNhapService.updateTienNhaCungCapTra(tienNhaCungCapTras.get(i).getTienDaTra(), tienNhaCungCapTras.get(i).getPhieuTraHangNhapId(),
                        phieuTraHangNhap.get().getTongTien() - (phieuTraHangNhap.get().getTienDaTra() + tienNhaCungCapTras.get(i).getTienDaTra()));
            }
            return JsonResult.success("update tien nha cung cap tra thanh cong");
        }catch (Exception ex) {
            return JsonResult.saveError("update tien nha cung cap tra error");
        }


    }

    @PutMapping("/update")
    public ResponseEntity<JsonResult> update(@RequestBody PhieuTraHangNhap phieuTraHangNhap) {
        return phieuTraHangNhapService.save(phieuTraHangNhap)
                .map(JsonResult::uploaded)
                .orElse(JsonResult.saveError("Phieu Tra Hang Nhap"));
    }

    @PutMapping("/trang-thai")
    public ResponseEntity<JsonResult> setTrangThai(@RequestParam("id") int id,
                                                   @RequestParam("trang-thai") int trangThai) {
        Boolean bool = phieuTraHangNhapService.setTrangThai(id, trangThai);
        if (bool) {
            return JsonResult.success("Thay doi trang thai thanh cong");
        } else {
            return JsonResult.serverError("Thay doi trang thai that bai");
        }
    }

}
