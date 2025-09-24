package com.tavi.tavi_mrs.controller.hoa_don.public_api;

import com.tavi.tavi_mrs.entities.hoa_don.HoaDon;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.service.hoa_don.HoaDonService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/public/hoa-don")
public class HoaDonPublicController {

    @Autowired
    private HoaDonService hoaDonService;

    @GetMapping("/find-by-ma/{ma}")
    public ResponseEntity<JsonResult> findByMa(@PathVariable("ma") String maHoaDon) {
        return hoaDonService.findByMa(maHoaDon, false)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

//    @GetMapping("/find-recent-order")
//    @ApiOperation(value = "find all", response = HoaDon.class, responseContainer = "List")
//    public ResponseEntity<JsonResult> findRecentOrder() {
//        return Optional.ofNullable(hoaDonService.findRecentOrder())
//                .map(rsList -> !rsList.isEmpty() ? JsonResult.found(rsList) : JsonResult.notFound("List of Recent Order"))
//                .orElse(JsonResult.serverError("Internal Server Error"));
//    }
}
