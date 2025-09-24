package com.tavi.tavi_mrs.controller.van_chuyen;

import com.tavi.tavi_mrs.entities.van_chuyen.ahamove.DataAhamove;
import com.tavi.tavi_mrs.entities.van_chuyen.ahamove.OrderDetailData;
import com.tavi.tavi_mrs.entities.van_chuyen.json.JsonResult;
import com.tavi.tavi_mrs.service.van_chuyen.OrderAhaMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/public/transport-ahamove")
public class AhaMoveController {

    @Autowired
    private OrderAhaMoveService orderAhaMoveService;

    @PostMapping("/order")
    public ResponseEntity<JsonResult> createOrder(HttpServletRequest request,
                                                  @RequestBody DataAhamove data,
                                                  @RequestParam(name = "partner-id", required = false) Integer partnerId) {
        try {
            return JsonResult.success(orderAhaMoveService.orderCreate(data, request, partnerId));
        } catch (Exception e) {
            return JsonResult.error(e);
        }
    }

    @GetMapping("/order-detail")
    public ResponseEntity<JsonResult> getOrderDetail(@RequestBody OrderDetailData orderDetailData) {
        try {
            return JsonResult.success(orderAhaMoveService.getOrderInfor(orderDetailData));
        } catch (Exception e) {
            return JsonResult.error(e);
        }
    }

    @GetMapping("/cancel")
    public ResponseEntity<JsonResult> cancelOrder(@RequestBody OrderDetailData orderDetailData) {
        try {
            return JsonResult.success(orderAhaMoveService.cancel(orderDetailData));
        } catch (Exception e) {
            return JsonResult.error(e);
        }
    }

}
