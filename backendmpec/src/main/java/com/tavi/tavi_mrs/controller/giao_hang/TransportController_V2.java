package com.tavi.tavi_mrs.controller.giao_hang;

import com.tavi.tavi_mrs.entities.giao_hang.Order_V2;
import com.tavi.tavi_mrs.entities.giao_hang.json.JsonResult;
import com.tavi.tavi_mrs.service.giao_hang.TransportService_V2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/transport")
public class TransportController_V2 {

    @Autowired
    private TransportService_V2 service;

    @PostMapping("/order")
    public ResponseEntity<JsonResult> createOrder(HttpServletRequest request,
                                                  @RequestBody Order_V2 order,
                                                  @RequestParam(name = "partner-id", required = false) Integer partnerId){
        try {
//            if (order.getOrderId().equalsIgnoreCase(String.valueOf(service.checkOrder(Integer.parseInt(order.getOrderId()))))){
//                return JsonResult.badRequest("Order Existed");
//            }else {
                return Optional.ofNullable(service.orderCreate(order, request,partnerId))
                        .map(JsonResult::uploaded)
                        .orElse(JsonResult.badRequest("failed"));
//            }
        }catch (Exception e){
            return JsonResult.error(e);
        }
    }

    @GetMapping("/order-detail/{code}")
    public ResponseEntity<JsonResult> getOrderDetail(@PathVariable("code") String code){
        try {
            return Optional.ofNullable(service.findByCode(code))
                    .map(JsonResult::success)
                    .orElse(JsonResult.badRequest("failed"));
        }catch (Exception e){
            return JsonResult.error(e);
        }
    }

    @PostMapping("/fee")
    public ResponseEntity<JsonResult> getFee(HttpServletRequest request,
                                             @RequestBody Order_V2 order){
        try {
            return Optional.ofNullable(service.getFee(request,order))
                    .map(JsonResult::success)
                    .orElse(JsonResult.badRequest("failed"));
        }catch (Exception e){
            return JsonResult.error(e);
        }
    }

    @DeleteMapping("/cancel/{code}")
    public ResponseEntity<JsonResult> cancelOrder(HttpServletRequest request,
                                                  @PathVariable("code") String code,
                                                  @RequestParam(name = "partner-id", required = false) Integer partnerId){
        try {
            if (service.checkOrderDeleted(code)==0){
                if (service.cancel(request,code,partnerId)){
                    return JsonResult.success("deleted");
                }else{
                    return JsonResult.badRequest("failed");
                }
            }else {
                return JsonResult.badRequest("Order Deleted");
            }
        }catch (Exception e){
            return JsonResult.error(e);
        }
    }
}
