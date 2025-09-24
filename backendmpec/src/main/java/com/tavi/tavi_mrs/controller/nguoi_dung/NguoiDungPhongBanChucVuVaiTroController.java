package com.tavi.tavi_mrs.controller.nguoi_dung;

import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.service.nguoi_dung.NguoiDungPhongBanChucVuVaiTroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/v1/admin/nguoi-dung-phong-ban-chuc-vu-vai-tro")
public class NguoiDungPhongBanChucVuVaiTroController {

    @Autowired
    private NguoiDungPhongBanChucVuVaiTroService nguoiDungPhongBanChucVuVaiTroService;

    @GetMapping("/find-by-nguoi-dung-id")
    public ResponseEntity<JsonResult> findByNguoiDungId(@RequestParam("nguoi-dung-id") int nguoiDungId) {
        return nguoiDungPhongBanChucVuVaiTroService.findByNguoiDungId(nguoiDungId)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/find-by-nguoi-dung-tai-khoan")
    public ResponseEntity<JsonResult> findByTaiKhoan(@RequestParam("tai-khoan") String taiKhoan) {
        return nguoiDungPhongBanChucVuVaiTroService.findByTaiKhoan(taiKhoan)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }
}
