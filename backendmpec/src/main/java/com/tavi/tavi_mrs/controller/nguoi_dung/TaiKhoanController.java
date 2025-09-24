package com.tavi.tavi_mrs.controller.nguoi_dung;

import com.tavi.tavi_mrs.entities.constant.DatabaseConstant;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDung;
import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDungPhongBanChucVuVaiTro;
import com.tavi.tavi_mrs.service.chi_nhanh.ChiNhanhService;
import com.tavi.tavi_mrs.service.chuc_vu_vai_tro.ChucVuService;
import com.tavi.tavi_mrs.service.chuc_vu_vai_tro.VaiTroService;
import com.tavi.tavi_mrs.service.nguoi_dung.NguoiDungPhongBanChucVuVaiTroService;
import com.tavi.tavi_mrs.service.nguoi_dung.NguoiDungService;
import com.tavi.tavi_mrs.service.phong_ban.PhongBanService;
import com.tavi.tavi_mrs.utils.ZXingHelper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/tai-khoan")
public class TaiKhoanController {

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private NguoiDungPhongBanChucVuVaiTroService nguoiDungPhongBanChucVuVaiTroService;

    @Autowired
    private PhongBanService phongBanService;

    @Autowired
    private ChucVuService chucVuService;

    @Autowired
    private VaiTroService vaiTroService;

    @Autowired
    private ChiNhanhService chiNhanhService;

    @GetMapping("/find-all")
    ResponseEntity<JsonResult> findAllToPage(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                             @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<NguoiDung> nguoiDungs = nguoiDungService.findAll(false, pageable);
        System.out.println(nguoiDungs.getSize());
        return Optional.ofNullable(nguoiDungs)
                .map(nguoiDung -> nguoiDungs.getTotalElements() != 0 ? JsonResult.found(PageJson.build(nguoiDungs)) : JsonResult.notFound("NguoiDung/Page"))
                .orElse(JsonResult.serverError("Internal Server error"));
    }


    @GetMapping("/search")
    @ApiOperation(value = "find by phong ban chuc vu vai tro text", response = NguoiDung.class, responseContainer = "List")
    public ResponseEntity<JsonResult> findByCty(@RequestParam(value = "chi-nhanh-id", required = false, defaultValue = "0") int chiNhanhId,
                                                @RequestParam(value = "text", defaultValue = "") String text,
                                                @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                @RequestParam(value = "size", defaultValue = "5") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return Optional.ofNullable(nguoiDungPhongBanChucVuVaiTroService.findNguoiDungByPhongBanAndText(chiNhanhId, text, pageable))
                .map(JsonResult::found)
                .orElse(JsonResult.serverError("TaiKhoan"));
    }


    @GetMapping("/find-by-id")
    ResponseEntity<JsonResult> findById(@RequestParam("id") int id) {
        return nguoiDungService.findById(id, false)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/count-account")
    Long countTaiKhoan() {
        return nguoiDungService.countTaiKhoanAndXoa(false);
    }


    @GetMapping("/qrcode/{id}")
    public ResponseEntity<JsonResult> qrcode(@PathVariable("id") int id, HttpServletResponse response) throws Exception {
        Optional<NguoiDung> nguoiDung = nguoiDungService.findById(id, false);
//        response.setContentType("image/png");
        String codeString = DatabaseConstant.Host.URL_LOCAL_BACKEND + "api/v1/public/user/find-nguoi-dung-by-tai-khoan?tai-khoan=" + nguoiDung.get().getTaiKhoan();
//        OutputStream outputStream = response.getOutputStream();
//        outputStream.write(ZXingHelper.getQRCodeImage(nguoiDung.get().getFacebook(), 200, 200));
//        //outputStream.write(ZXingHelper.getQRCodeImage(nguoiDung.toString(), 200, 200));
//        outputStream.flush();
//        outputStream.close();
        return JsonResult.success(ZXingHelper.getQRCodeImage(codeString, 200, 200));
    }

    @PutMapping("/trang-thai")
    @ApiOperation(value = "thay doi trang thai nguoi dung")
    public ResponseEntity<JsonResult> setTrangThai(@RequestParam("id") int id,
                                                   @RequestParam("trang-thai") int trangThai) {
        if (nguoiDungService.setTrangThai(trangThai, id))
            return JsonResult.success("Thay doi trang thai thanh cong");
        else return JsonResult.serverError("Thay doi trang thai that bai");
    }

    @PutMapping("/delete")
    @ApiOperation(value = "xoa tai khoan")
    public ResponseEntity<JsonResult> delete(@RequestParam("id") int id) {
        Optional<NguoiDung> nguoiDung = nguoiDungService.findById(id, false);
        if (nguoiDung.isPresent()) {
            if (nguoiDungService.delete(id))
                return JsonResult.deleted();
            else return JsonResult.serverError("Nguoi Dung");
        } else {
            return JsonResult.idNotFound();
        }
    }

    @PostMapping("/upload")
    @ApiOperation(value = "them moi nguoi dung")
    public ResponseEntity<JsonResult> upload(@RequestBody NguoiDung nguoiDung,
                                             @RequestParam("chi-nhanh-id") int chiNhanhId,
                                             @RequestParam("phong-ban-id") int phongBanId,
                                             @RequestParam("chuc-vu-id") int chucVuId,
                                             @RequestParam("vai-tro-id") int vaiTroId) {
        if ((nguoiDung.getEmail() != null && nguoiDung.getSoDienThoai() != null)) {
            Optional<NguoiDung> nguoiDungOptional = nguoiDungService.save(nguoiDung);
            if (!nguoiDungOptional.isPresent()) {
                return JsonResult.saveError("TaiKhoan");
            }
            int nguoiDungId = nguoiDungOptional.get().getId();
            NguoiDungPhongBanChucVuVaiTro nguoiDungPhongBanChucVuVaiTro = new NguoiDungPhongBanChucVuVaiTro();
            return chiNhanhService.findByIdAndXoa(chiNhanhId, false)
                    .map(chiNhanh -> {
                        nguoiDungPhongBanChucVuVaiTro.setChiNhanh(chiNhanh);
                        return phongBanService.findByIdAndXoa(phongBanId, false)
                                .map(phongBan -> {
                                    nguoiDungPhongBanChucVuVaiTro.setPhongBan(phongBan);
                                    return chucVuService.findByIdAndXoa(chucVuId, false)
                                            .map(chucVu -> {
                                                nguoiDungPhongBanChucVuVaiTro.setChucVu(chucVu);
                                                return vaiTroService.findByIdAndXoa(vaiTroId, false)
                                                        .map(vaiTro -> {
                                                            nguoiDungPhongBanChucVuVaiTro.setVaiTro(vaiTro);
                                                            return nguoiDungService.findById(nguoiDungId, false)
                                                                    .map(canBo -> {
                                                                        nguoiDungPhongBanChucVuVaiTro.setNguoiDung(canBo);
                                                                        nguoiDungPhongBanChucVuVaiTro.setXoa(false);
                                                                        return nguoiDungPhongBanChucVuVaiTroService.save(nguoiDungPhongBanChucVuVaiTro)
                                                                                .map(JsonResult::uploaded)
                                                                                .orElse(JsonResult.saveError("Internal Server Error"));
                                                                    }).orElse(JsonResult.parentNotFound("Nguoi Dung"));
                                                        }).orElse(JsonResult.parentNotFound("Vai Tro"));
                                            }).orElse(JsonResult.parentNotFound("Chuc Vu"));
                                }).orElse(JsonResult.parentNotFound("Phong Ban"));
                    }).orElse(JsonResult.parentNotFound("ChiNhanh"));
        }
        return ResponseEntity.badRequest().build();
    }


    @PutMapping(value = "/update")
    @ApiOperation(value = "thay doi thong tin nguoi dung")
    public ResponseEntity<JsonResult> update(@RequestBody NguoiDung nguoiDung,
                                             @RequestParam("chi-nhanh-id") int chiNhanhId,
                                             @RequestParam("phong-ban-id") int phongBanId,
                                             @RequestParam("chuc-vu-id") int chucVuId,
                                             @RequestParam("vai-tro-id") int vaiTroId) {
        return Optional.ofNullable(nguoiDungService.updateNguoiDung(nguoiDung))
                .map(nguoiDungRs -> {
                    NguoiDungPhongBanChucVuVaiTro nguoiDungPhongBanChucVuVaiTro = new NguoiDungPhongBanChucVuVaiTro();
                    nguoiDungPhongBanChucVuVaiTro.setNguoiDung(nguoiDungRs);
                    return chiNhanhService.findByIdAndXoa(chiNhanhId, false)
                            .map(chiNhanh -> {
                                nguoiDungPhongBanChucVuVaiTro.setChiNhanh(chiNhanh);
                                return phongBanService.findByIdAndXoa(phongBanId, false)
                                        .map(phongBan -> {
                                            nguoiDungPhongBanChucVuVaiTro.setPhongBan(phongBan);
                                            return chucVuService.findByIdAndXoa(chucVuId, false)
                                                    .map(chucVu -> {
                                                        nguoiDungPhongBanChucVuVaiTro.setChucVu(chucVu);
                                                        return vaiTroService.findByIdAndXoa(vaiTroId, false)
                                                                .map(vaiTro -> {
                                                                    nguoiDungPhongBanChucVuVaiTro.setVaiTro(vaiTro);
                                                                    return nguoiDungService.findById(nguoiDungRs.getId(), false)
                                                                            .map(canBo -> {
                                                                                nguoiDungPhongBanChucVuVaiTro.setNguoiDung(canBo);
                                                                                nguoiDungPhongBanChucVuVaiTro.setXoa(false);
                                                                                return nguoiDungPhongBanChucVuVaiTroService.save(nguoiDungPhongBanChucVuVaiTro)
                                                                                        .map(JsonResult::updated)
                                                                                        .orElse(JsonResult.saveError("Internal Server Error"));
                                                                            }).orElse(JsonResult.parentNotFound("Nguoi Dung"));
                                                                }).orElse(JsonResult.parentNotFound("Vai Tro"));
                                                    }).orElse(JsonResult.parentNotFound("Chuc Vu"));
                                        }).orElse(JsonResult.parentNotFound("Phong Ban"));
                            }).orElse(JsonResult.parentNotFound("ChiNhanh"));
                }).orElse(JsonResult.saveError("Internal Server Error"));
    }


    @PutMapping("/set-pb-cv-vt")
    @ApiOperation(value = "thay doi chuc vu vai tro")
    public ResponseEntity<JsonResult> setChucVuVaiTro(@RequestParam("chu-vu-id") int chucVuId,
                                                      @RequestParam("vai-tro-id") int vaiTroId,
                                                      @RequestParam("new-phong-ban-id") int newPhongBanId,
                                                      @RequestParam("nguoi-dung-id") int nguoiDungId,
                                                      @RequestParam("phong-ban-id") int phongBanId
    ) {
        if (nguoiDungPhongBanChucVuVaiTroService.setPhongBanChucVuVaiTro(chucVuId, vaiTroId, newPhongBanId, nguoiDungId, phongBanId) != 0)
            return JsonResult.success("Thay doi chuc vu vai tro thanh cong");
        else return JsonResult.serverError("Thay doi chuc vu vai tro that bai");
    }

    @GetMapping("/search-fbcvvt")
    @ApiOperation(value = "find by phong ban chuc vu vai tro text", response = NguoiDung.class, responseContainer = "List")
    public ResponseEntity<JsonResult> findPBCVVT(@RequestParam(value = "chi-nhanh-id", required = false, defaultValue = "0") int chiNhanhId,
                                                 @RequestParam(value = "text", defaultValue = "") String text,
                                                 @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                 @RequestParam(value = "size", defaultValue = "5") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return Optional.ofNullable(nguoiDungPhongBanChucVuVaiTroService.findNguoiDungFullInfor(chiNhanhId, text, pageable))
                .map(JsonResult::found)
                .orElse(JsonResult.serverError("find by phong ban and text error"));
    }

}
