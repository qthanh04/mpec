package com.tavi.tavi_mrs.service.giao_hang;

import com.tavi.tavi_mrs.entities.giao_hang.Fee_V2;
import com.tavi.tavi_mrs.entities.giao_hang.Order_V2;
import com.tavi.tavi_mrs.entities.giao_hang.Transport_V2;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface TransportService_V2 {

    Transport_V2 save(Order_V2 order, HttpServletRequest request, int partnerId, JSONObject job, String jsonStr) throws ParseException;

    Transport_V2 orderCreate(Order_V2 order, HttpServletRequest httpServletRequest, int partnerId) throws Exception;

    Optional<Transport_V2> findByCode(String code);

    List<Fee_V2> getFee(HttpServletRequest request, Order_V2 order) throws Exception;

    Boolean cancel(HttpServletRequest request, String code, Integer partnerId) throws ParseException;

    Integer checkOrder(int orderId);

    Integer checkOrderDeleted(String orderCode);

}
