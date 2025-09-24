package com.tavi.tavi_mrs.controller.van_chuyen;

import com.tavi.tavi_mrs.entities.van_chuyen.ghtk.DataGHTK;
import com.tavi.tavi_mrs.entities.van_chuyen.json.JsonGHTK;
import com.tavi.tavi_mrs.entities.van_chuyen.json.JsonResult;
import com.tavi.tavi_mrs.service.van_chuyen.OrderGHTKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/public/transport-ghtk")
public class GHTKController {

    @Autowired
    private OrderGHTKService orderGHTKService;

    @PostMapping("/order")
    public ResponseEntity<JsonResult> createOrder(HttpServletRequest request,
                                                  @RequestBody DataGHTK data,
                                                  @RequestParam(name = "partner-id", required = false) Integer partnerId) {
        try {
            return JsonResult.success(orderGHTKService.orderCreate(data, request, partnerId));
        } catch (Exception e) {
            return JsonResult.error(e);
        }
    }

    @GetMapping("/order-detail/{id}")
    public ResponseEntity<JsonResult> getOrderDetail(HttpServletRequest request,
                                                     @PathVariable(name = "id", required = false) String lableId) {
        try {
            System.out.println(lableId);
            return JsonResult.success(orderGHTKService.orderDetail(lableId, request));
        } catch (Exception e) {
            return JsonResult.error(e);
        }
    }

    @DeleteMapping("/cancel/{id}")
    public JsonGHTK cancel(HttpServletRequest request,
                           @PathVariable(name = "id", required = false) String lableId) {
        try {
            return orderGHTKService.cancel(lableId, request);
        } catch (Exception e) {
            return null;
        }
    }

}
