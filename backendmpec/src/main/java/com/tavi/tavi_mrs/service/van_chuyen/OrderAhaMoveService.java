package com.tavi.tavi_mrs.service.van_chuyen;

import com.tavi.tavi_mrs.entities.van_chuyen.ahamove.DataAhamove;
import com.tavi.tavi_mrs.entities.van_chuyen.ahamove.Order;
import com.tavi.tavi_mrs.entities.van_chuyen.ahamove.OrderAhamoveCreated;
import com.tavi.tavi_mrs.entities.van_chuyen.ahamove.OrderDetailData;
import com.tavi.tavi_mrs.entities.van_chuyen.json.JsonAhamove;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface OrderAhaMoveService {

    OrderAhamoveCreated orderCreate(DataAhamove data, HttpServletRequest request, int partnerId) throws IOException;

    Order getOrderInfor(OrderDetailData odd);

    JsonAhamove cancel(OrderDetailData odd);
}
