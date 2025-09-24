package com.tavi.tavi_mrs.controller.nhan_vien;

import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDung;
import com.tavi.tavi_mrs.service.nguoi_dung.NguoiDungService;
import com.tavi.tavi_mrs.utils.ZXingHelper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/nhan-vien")
public class NhanVienController {
    @Autowired
    private NguoiDungService nguoiDungService;

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

    @PutMapping("/update")
    public ResponseEntity<JsonResult> update(@RequestBody NguoiDung nguoiDung) {
        return nguoiDungService.save(nguoiDung)
                .map(JsonResult::updated)
                .orElse(JsonResult.saveError("NguoiDung"));
    }

    @RequestMapping(value = "qrcode/{id}", method = RequestMethod.GET)
    public void qrcode(@PathVariable("id") int id, HttpServletResponse response) throws Exception {
        Optional<NguoiDung> nguoiDung = nguoiDungService.findById(id, false);
        response.setContentType("image/png");
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(ZXingHelper.getQRCodeImage(nguoiDung.toString(), 200, 200));
        outputStream.flush();
        outputStream.close();
    }

    @PostMapping("/upload")
    @ApiOperation(value = "them moi nguoi dung")
    public ResponseEntity<JsonResult> upload(@RequestBody NguoiDung nguoiDung) {
        if ((nguoiDung.getEmail() != null && nguoiDung.getTaiKhoan() != null && nguoiDung.getMatKhau() != null && nguoiDung.getSoDienThoai() != null)) {
            long countTaiKhoan = nguoiDungService.countByTaiKhoanAndXoa(nguoiDung.getTaiKhoan(), false);
            long countEmail = nguoiDungService.countByEmailAndXoa(nguoiDung.getEmail(), false);
            long countSDT = nguoiDungService.countBySoDienThoaiAndXoa(nguoiDung.getSoDienThoai(), false);
            String text = "";
            if (countTaiKhoan > 0 || countEmail > 0 || countSDT > 0) {
                if (countTaiKhoan > 0) text += "tai khoan da ton tai";
                if (countEmail > 0) text += " email da ton tai";
                if (countSDT > 0) text += " sdt da ton tai";
                return ResponseEntity.ok(JsonResult.build("khong the them moi", text));
            }
            NguoiDung newNguoiDung = nguoiDungService.register(nguoiDung);
            if (newNguoiDung != null) {
                newNguoiDung.setMatKhau(null);
                return ResponseEntity.ok(JsonResult.build("them thanh cong", newNguoiDung));
            }
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
