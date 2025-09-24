package com.tavi.tavi_mrs.service_impl.van_chuyen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tavi.tavi_mrs.entities.hoa_don.HoaDon;
import com.tavi.tavi_mrs.entities.van_chuyen.DoiTac;
import com.tavi.tavi_mrs.entities.van_chuyen.Transport;
import com.tavi.tavi_mrs.entities.van_chuyen.ahamove.DataAhamove;
import com.tavi.tavi_mrs.entities.van_chuyen.ahamove.Order;
import com.tavi.tavi_mrs.entities.van_chuyen.ahamove.OrderAhamoveCreated;
import com.tavi.tavi_mrs.entities.van_chuyen.ahamove.OrderDetailData;
import com.tavi.tavi_mrs.entities.van_chuyen.json.JsonAhamove;
import com.tavi.tavi_mrs.entities.van_chuyen.json.RestBuilder;
import com.tavi.tavi_mrs.service.hoa_don.HoaDonService;
import com.tavi.tavi_mrs.service.van_chuyen.DoiTacService;
import com.tavi.tavi_mrs.service.van_chuyen.OrderAhaMoveService;
import com.tavi.tavi_mrs.service.van_chuyen.TransportService;
import com.tavi.tavi_mrs.service_impl.van_chuyen.rest.RestAhamove;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@Service
public class OrderAhamoveServiceImpl implements OrderAhaMoveService {

    private static final Logger logger = LoggerFactory.getLogger(OrderAhamoveServiceImpl.class);

    @Autowired
    private RestAhamove restService;

    @Autowired
    private TransportService transportService;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private DoiTacService doiTacService;

    @Override
    public OrderAhamoveCreated orderCreate(DataAhamove data, HttpServletRequest request, int partnerId) throws IOException {
        try {
            ObjectMapper Obj = new ObjectMapper();
            String jsonStr = Obj.writeValueAsString(data.getPath());
            System.out.println(jsonStr);
            OrderAhamoveCreated orderCreated = new ModelMapper().map(restService.callPostAhamove(RestBuilder.build().uri("order/create")
                    .param("token", data.getToken())
                    .param("path", jsonStr)
                    .param("order_time", Integer.toString(data.getOrder_time()))
                    .param("service_id", data.getService_id())
                    .param("requests", data.getRequests().toString()), request, data), OrderAhamoveCreated.class);
//            Transport transport = new Transport();
//            transport.setOrderId(data.getId());
//            transport.setPartnerId(partnerId);
//            transport.setOrderCode(orderCreated.getOrder_id());
//            transport.setFee(orderCreated.getOrder().getTotal_fee());
//            transport.setPickTime(orderCreated.getOrder().getCreate_time().toString());
//            transport.setDeliverTime("Đối tác tự liên hệ");
//            transportService.save(transport);

            HoaDon hoaDon = hoaDonService.findByIdAndXoa(data.getId(), false);
            DoiTac doiTac = doiTacService.findById(partnerId);
            Transport transport = new Transport();
            transport.setHoaDon(hoaDon);
            transport.setDoiTac(doiTac);
            transport.setOrderCode(orderCreated.getOrder_id());
            transport.setFee(orderCreated.getOrder().getTotal_fee());
            transport.setPickTime(orderCreated.getOrder().getCreate_time().toString());
            transport.setDeliverTime("Đối tác tự liên hệ");
            transportService.save(transport);
            return orderCreated;
        } catch (Exception ex) {
            logger.error("create order err {0}", ex);
            throw ex;
        }

    }

    @Override
    public Order getOrderInfor(OrderDetailData odd) {
        try {
            return new ModelMapper().map(restService.callGetAhamove(RestBuilder.build().uri("order/detail").
                    param("token", odd.getToken()).
                    param("order_id", odd.getOrder_id())), Order.class);
        } catch (Exception ex) {
            logger.error("get order detail err {0}", ex);
            throw ex;
        }
    }

    @Override
    public JsonAhamove cancel(OrderDetailData odd) {
        try {
            restService.callGetAhamove(RestBuilder.build().uri("order/cancel").
                    param("token", odd.getToken()).
                    param("order_id", odd.getOrder_id()));
            JsonAhamove jsonAhamove = new JsonAhamove();
            jsonAhamove.setCode(200);
            jsonAhamove.setMessage("Xoa thanh cong");
            return jsonAhamove;
        } catch (Exception ex) {
            logger.error("get order detail err {0}", ex);
            throw ex;
        }

    }
}
