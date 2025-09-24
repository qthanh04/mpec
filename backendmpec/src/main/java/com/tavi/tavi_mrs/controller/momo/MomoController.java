package com.tavi.tavi_mrs.controller.momo;

import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.momo.CaptureMoMoResponse;
import com.tavi.tavi_mrs.entities.momo.MomoRequest;
import com.tavi.tavi_mrs.entities.momo.PayGateResponse;
import com.tavi.tavi_mrs.entities.momo.QueryStatusTransactionResponse;
import com.tavi.tavi_mrs.entities.momo.shared.sharedmodels.Environment;
import com.tavi.tavi_mrs.entities.momo.shared.utils.LogUtils;
import com.tavi.tavi_mrs.service_impl.momo.CaptureMoMo;
import com.tavi.tavi_mrs.service_impl.momo.PaymentResult;
import com.tavi.tavi_mrs.service_impl.momo.QueryStatusTransaction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/public/momo")
public class MomoController {

    Environment environment = Environment.selectEnv("dev", Environment.ProcessType.PAY_GATE);

    @PostMapping("/pay")
    public ResponseEntity<JsonResult> pay(@RequestBody MomoRequest momoRequest) throws Exception {
        try{
            CaptureMoMoResponse captureMoMoResponse = CaptureMoMo.process(environment, momoRequest.getOrderId(), momoRequest.getRequestId(), Long.toString(momoRequest.getAmount()), momoRequest.getOrderInfo(), momoRequest.getReturnURL(), momoRequest.getNotifyURL(), "");
            return JsonResult.success(captureMoMoResponse);
        } catch (Exception ex){
            JsonResult.serverError("Momo Error");
        }
        return JsonResult.serverError("Momo Error");
    }

//    //      Remember to change the IDs at enviroment.properties file
//
//    //        Payment Method- Phương thức thanh toán
//    CaptureMoMoResponse captureMoMoResponse = CaptureMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "");
//        System.out.println("quy123"+captureMoMoResponse);
//    //        Transaction Query - Kiểm tra trạng thái giao dịch
//    QueryStatusTransactionResponse queryStatusTransactionResponse = QueryStatusTransaction.process(environment, orderId, requestId);
//
//    //      Process Payment Result - Xử lý kết quả thanh toán
//    PayGateResponse payGateResponse = PaymentResult.process(environment,new PayGateResponse());

}
