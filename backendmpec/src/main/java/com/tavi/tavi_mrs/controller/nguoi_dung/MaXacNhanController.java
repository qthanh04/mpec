package com.tavi.tavi_mrs.controller.nguoi_dung;

import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.security.SecurityConstants;
import com.tavi.tavi_mrs.service_impl.nguoi_dung.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/admin/ma-xac-nhan")
public class MaXacNhanController {

    @Autowired
    private JWTService jwtService;

    @GetMapping("/validate-token")
    public ResponseEntity<JsonResult> validateToken(@RequestParam("id") int nguoiDungId,
                                                    HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);
        return Optional.ofNullable(jwtService.validateToken(token, nguoiDungId))
                .map(rs -> JsonResult.success(rs))
                .orElse(JsonResult.serverError("Server Internal Error"));
    }
}
