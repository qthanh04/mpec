package com.tavi.tavi_mrs.entities.momo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MomoRequest {

    String requestId;
    String orderId;
    long amount;

    String orderInfo;
    String returnURL;
    String notifyURL;
    //String extraData;
    //String bankCode;
}
