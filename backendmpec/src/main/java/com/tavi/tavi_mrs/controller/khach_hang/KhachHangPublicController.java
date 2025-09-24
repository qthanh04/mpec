package com.tavi.tavi_mrs.controller.khach_hang;

import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.entities.khach_hang.KhachHang;
import com.tavi.tavi_mrs.service.khach_hang.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/app/public/khach-hang")
public class KhachHangPublicController {

    @Autowired
    private KhachHangService khachHangService;

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
    ResponseEntity<JsonResult> searchText(@RequestParam(name = "text", required = false, defaultValue = "") String text) {
        int page =1;
        int size= 5;
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
}
