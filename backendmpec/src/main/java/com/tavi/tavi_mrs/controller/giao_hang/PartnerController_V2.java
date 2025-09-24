package com.tavi.tavi_mrs.controller.giao_hang;

import com.tavi.tavi_mrs.entities.giao_hang.Partner_V2;
import com.tavi.tavi_mrs.entities.giao_hang.json.JsonResult;
import com.tavi.tavi_mrs.service.giao_hang.PartnerService_V2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/private/partner")
public class PartnerController_V2 {

    @Autowired
    private PartnerService_V2 service;

    @PostMapping
    public ResponseEntity<JsonResult> upload(@RequestBody Partner_V2 partner){
        try {
            return service.save(partner)
                    .map(JsonResult::uploaded)
                    .orElse(JsonResult.badRequest("data is invalid"));
        } catch (Exception ex) {
            return JsonResult.error(ex);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<JsonResult> findById(@PathVariable("id") int id){
        try {
            return JsonResult.success(service.findById(id));
        }catch (Exception ex){
            return JsonResult.error(ex);
        }
    }
}


