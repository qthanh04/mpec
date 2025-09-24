package com.tavi.tavi_mrs.controller.location;

import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.service.location.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/public/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/find-by-level")
    public ResponseEntity<JsonResult> findByLevel(@RequestParam("level") String level) {
        return Optional.ofNullable(locationService.listLocation(level))
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }
    @GetMapping("/find-quan-huyen-by-thanh-pho-id")
    public ResponseEntity<JsonResult> findQuanHuyenByThanhPhoId(@RequestParam("thanh-pho-id") String thanhPhoId){
        return Optional.ofNullable(locationService.findQuanHuyenByThanhPhoId(thanhPhoId))
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }
    @GetMapping("/find-phuong-xa-by-quan-huyen-id")
    public ResponseEntity<JsonResult> findPhuongXaByQuanHuyenId(@RequestParam("quan-huyen-id") String quanHuyenId){
        return Optional.ofNullable(locationService.findPhuongXaByQuanHuyenId(quanHuyenId))
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }
}
