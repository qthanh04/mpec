package com.tavi.tavi_mrs.service.van_chuyen;

import com.tavi.tavi_mrs.entities.van_chuyen.ghn.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderGHNService {

    OrderCreate orderCreate(DataGHN data, HttpServletRequest request, int partnerId);

    OrderInfor getOrderInfor(OrderInforForm odf, HttpServletRequest request);

    List<OrderDeleted> cancelOrder(OrderInforforms listOdfs, HttpServletRequest request);

}
