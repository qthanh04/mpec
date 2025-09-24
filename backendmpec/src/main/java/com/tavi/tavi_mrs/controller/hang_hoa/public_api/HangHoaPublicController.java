package com.tavi.tavi_mrs.controller.hang_hoa.public_api;

import com.tavi.tavi_mrs.entities.hang_hoa.HangHoa;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.service.hang_hoa.HangHoaService;
import com.tavi.tavi_mrs.utils.ZXingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/public/hang-hoa")
public class HangHoaPublicController {

    @Autowired
    private HangHoaService hangHoaService;

    @GetMapping("/find-by-ma/{ma}")
    public ResponseEntity<JsonResult> findByMa(@PathVariable("ma") String maHangHoa) {
        return hangHoaService.findByMaHangHoa(maHangHoa, false)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }

    @GetMapping("/barcode/{ma}")
    public ResponseEntity<JsonResult> findbBarcode(@PathVariable("ma") String maVach, HttpServletResponse response) throws Exception {
        Optional<HangHoa> hangHoaOptional = hangHoaService.findByMaVach(maVach, false);
        if (hangHoaOptional.isPresent()) {
            String codeString = hangHoaOptional.get().getMaVach();
            return JsonResult.success(ZXingHelper.getBarCodeImage(codeString, 200, 20));
        } else {
            return JsonResult.badRequest("HangHoaPublic");
        }
    }

    @GetMapping("/find-by-barcode/{ma}")
    public ResponseEntity<JsonResult> findbyBarcode(@PathVariable("ma") String maVach, HttpServletResponse response) throws Exception {
        return hangHoaService.findByMaVach(maVach, false)
                .map(JsonResult::found)
                .orElse(JsonResult.idNotFound());
    }
}