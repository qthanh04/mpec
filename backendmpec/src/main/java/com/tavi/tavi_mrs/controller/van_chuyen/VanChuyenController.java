package com.tavi.tavi_mrs.controller.van_chuyen;

import com.tavi.tavi_mrs.entities.van_chuyen.Transport;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.entities.van_chuyen.OrderFee;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.service.van_chuyen.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/transport")
public class VanChuyenController {

    @Autowired
    private TransportService service;

    @GetMapping("/fee")
    public ResponseEntity<JsonResult> getFee(HttpServletRequest request,
                                             @RequestBody OrderFee order) {
        try {
            return Optional.ofNullable(service.getFee(request, order))
                    .map(JsonResult::success)
                    .orElse(JsonResult.badRequest("failed"));
        } catch (Exception e) {
            return JsonResult.serverError("Internal Server Error");
        }
    }
    @GetMapping("/find-all-van-chuyen")
    public ResponseEntity<JsonResult> findAllVanChuyen(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                                              @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Transport> vanChuyenPage = service.findAllVanChuyen(pageable);
        return Optional.ofNullable(vanChuyenPage)
                .map(vanChuyens -> vanChuyens.getTotalElements() != 0 ? JsonResult.found(PageJson.build(vanChuyens)) : JsonResult.notFound("BaoCao/Page"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }
    @GetMapping("/search-van-chuyen")
    public ResponseEntity<JsonResult> searchVanChuyen( @RequestParam(value = "text", defaultValue = "") String text,
                                                       @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                       @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Transport> vanChuyenPage;
        if(text.equals("")){
             vanChuyenPage = service.findAllVanChuyen(pageable);
        }else {
             vanChuyenPage = service.searchVanChuyen(text,pageable);
        }
        return Optional.ofNullable(vanChuyenPage)
                .map(vanChuyens -> vanChuyens.getTotalElements() != 0 ? JsonResult.found(PageJson.build(vanChuyens)) : JsonResult.notFound("BaoCao/Page"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }
}
