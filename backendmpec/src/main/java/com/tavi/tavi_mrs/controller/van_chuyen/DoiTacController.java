package com.tavi.tavi_mrs.controller.van_chuyen;

import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.van_chuyen.DoiTac;
import com.tavi.tavi_mrs.service.van_chuyen.DoiTacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/public/partner")
public class DoiTacController {

    @Autowired
    private DoiTacService doiTacService;

    @PostMapping
    public ResponseEntity<JsonResult> upload(@RequestBody DoiTac partner) {
        try {
            return doiTacService.save(partner)
                    .map(JsonResult::uploaded)
                    .orElse(JsonResult.badRequest("data is invalid"));
        } catch (Exception ex) {
            return JsonResult.existed("DoiTac");
        }
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findById(@PathVariable("id") int id) {
        try {
            return JsonResult.success(doiTacService.findById(id));
        } catch (Exception ex) {
            return JsonResult.idNotFound();
        }
    }

    @GetMapping("/find-by-id-optional")
    public ResponseEntity<JsonResult> findByIdOptional(@RequestParam("id") int id) {
        try {
            return JsonResult.success(doiTacService.findByIdOptionnal(id));
        } catch (Exception ex) {
            return JsonResult.idNotFound();
        }
    }

    @GetMapping("/find-all")
    public ResponseEntity<JsonResult> findAll() {
        return Optional.ofNullable(doiTacService.findAll())
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }
}

