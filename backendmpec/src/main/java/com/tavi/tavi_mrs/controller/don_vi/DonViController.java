package com.tavi.tavi_mrs.controller.don_vi;

import com.tavi.tavi_mrs.entities.don_vi.DonVi;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.json.PageJson;
import com.tavi.tavi_mrs.service.don_vi.DonViService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/don-vi")
public class DonViController {

    @Autowired
    private DonViService donViService;

    @GetMapping("/find-all")
    public ResponseEntity<JsonResult> findAll(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                              @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<DonVi> donViPage = donViService.findAll(pageable);
        return Optional.ofNullable(donViPage)
                .map(donVis -> donVis.getTotalElements() != 0 ? JsonResult.found(PageJson.build(donVis)) : JsonResult.notFound("DonVi"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<JsonResult> findById(@RequestParam("id") int id) {
        return donViService.findByIdAndXoa(id, false)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/search-text")
    public ResponseEntity<JsonResult> searchText(@RequestParam(value = "text", required = false, defaultValue = "") String tenDonVi,
                                                 @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                 @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<DonVi> donViPage;
        if (tenDonVi.equals("")) {
            donViPage = donViService.findAll(pageable);
        } else {
            donViPage = donViService.search(tenDonVi, pageable);
        }
        return Optional.ofNullable(donViPage)
                .map(donVis -> donVis.getTotalElements() != 0 ? JsonResult.found(PageJson.build(donVis)) : JsonResult.notFound("DonVi"))
                .orElse(JsonResult.serverError("Internal Server Error"));
    }

    @PostMapping("/upload")
    public ResponseEntity<JsonResult> upload(@RequestBody DonVi donVi) {
        Boolean bool = donViService.findByTenDonVi(donVi.getTenDonVi());
        if (!bool) {
            donVi.setXoa(false);
            return donViService.save(donVi)
                    .map(JsonResult::uploaded)
                    .orElse(JsonResult.saveError("DonVi"));
        } else {
            return JsonResult.existed("DonVi");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<JsonResult> update(@RequestBody DonVi donVi) {
        Boolean bool = donViService.findByTenDonVi(donVi.getTenDonVi());
        if (!bool) {
            return donViService.save(donVi)
                    .map(JsonResult::updated)
                    .orElse(JsonResult.saveError("DonVi"));
        } else {
            return JsonResult.existed("DonVi");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<JsonResult> delete(@RequestParam("id") int id) {

        Optional<DonVi> donVi = donViService.findByIdAndXoa(id, false);

        if (donVi.isPresent()) {

            Boolean bool = donViService.deleted(id);
            if (bool) {
                return JsonResult.deleted();
            }
            return JsonResult.serverError("Internal Server Error");
        } else {
            return JsonResult.idNotFound();
        }


    }

}
