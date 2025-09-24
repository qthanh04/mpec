package com.tavi.tavi_mrs.controller.van_chuyen;

import com.tavi.tavi_mrs.entities.van_chuyen.ghn.DataGHN;
import com.tavi.tavi_mrs.entities.van_chuyen.ghn.OrderInforForm;
import com.tavi.tavi_mrs.entities.van_chuyen.ghn.OrderInforforms;
import com.tavi.tavi_mrs.entities.van_chuyen.json.JsonResult;
import com.tavi.tavi_mrs.service.van_chuyen.OrderGHNService;
import com.tavi.tavi_mrs.service.van_chuyen.OrderGHTKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/public/transport-ghn")
public class GHNController {

    @Autowired
    private OrderGHNService orderGHNService;

    @PostMapping("/order")
    public ResponseEntity<JsonResult> createOrder(HttpServletRequest request,
                                                  @RequestBody DataGHN data,
                                                  @RequestParam(name = "partner-id", required = false) Integer partnerId) {
        try {
            return JsonResult.success(orderGHNService.orderCreate(data, request, partnerId));
        } catch (Exception e) {
            return JsonResult.error(e);
        }
    }

    @PostMapping("/order-detail")
    public ResponseEntity<JsonResult> getOrderDetail(HttpServletRequest request,
                                                     @RequestBody OrderInforForm orderInforForm,
                                                     @RequestParam(name = "partner-id", required = false) Integer partnerId) {
        try {
            return JsonResult.success(orderGHNService.getOrderInfor(orderInforForm, request));
        } catch (Exception e) {
            return JsonResult.error(e);
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<JsonResult> getOrderDetail(HttpServletRequest request,
                                                     @RequestBody OrderInforforms orderInforForms) {
        try {
            return JsonResult.success(orderGHNService.cancelOrder(orderInforForms, request));
        } catch (Exception e) {
            return JsonResult.error(e);
        }
    }

}
