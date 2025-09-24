package com.tavi.tavi_mrs.service_impl.van_chuyen;

import com.tavi.tavi_mrs.entities.hoa_don.HoaDon;
import com.tavi.tavi_mrs.entities.van_chuyen.DoiTac;
import com.tavi.tavi_mrs.entities.van_chuyen.Transport;
import com.tavi.tavi_mrs.entities.van_chuyen.ghtk.DataGHTK;
import com.tavi.tavi_mrs.entities.van_chuyen.ghtk.OrderDetailGHTK;
import com.tavi.tavi_mrs.entities.van_chuyen.ghtk.OrderGHTKCreated;
import com.tavi.tavi_mrs.entities.van_chuyen.json.JsonGHTK;
import com.tavi.tavi_mrs.entities.van_chuyen.json.RestBuilder;
import com.tavi.tavi_mrs.service.hoa_don.HoaDonService;
import com.tavi.tavi_mrs.service.van_chuyen.DoiTacService;
import com.tavi.tavi_mrs.service.van_chuyen.OrderGHTKService;
import com.tavi.tavi_mrs.service.van_chuyen.TransportService;
import com.tavi.tavi_mrs.service_impl.van_chuyen.rest.RestGHTK;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class OrderGHTKServiceImpl implements OrderGHTKService {

    private static final Logger logger = LoggerFactory.getLogger(OrderGHTKServiceImpl.class);

    @Autowired
    private RestGHTK restService;

    @Autowired
    private TransportService transportService;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private DoiTacService doiTacService;

    @Override
    public OrderGHTKCreated orderCreate(DataGHTK data, HttpServletRequest request, int partnerId) {
        try {
            OrderGHTKCreated orderCreated = new ModelMapper().map(restService.callPostGHTK(RestBuilder.build().uri("shipment/order/?ver=1.5"), request, data), OrderGHTKCreated.class);
            HoaDon hoaDon = hoaDonService.findByIdAndXoa(Integer.parseInt(data.getOrder().getId()), false);
            DoiTac doiTac = doiTacService.findById(partnerId);
            Transport transport = new Transport();
            transport.setHoaDon(hoaDon);
            transport.setDoiTac(doiTac);
            transport.setOrderCode(orderCreated.getLabel());
            transport.setFee(Integer.parseInt(orderCreated.getFee()));
            transport.setPickTime("Đối tác tự liên hệ");
            transport.setDeliverTime(orderCreated.getEstimated_deliver_time());
            transportService.save(transport);

            return orderCreated;
        } catch (Exception ex) {
            logger.error("create order err {0}", ex);
            throw ex;
        }
    }

    @Override
    public OrderDetailGHTK orderDetail(String labelId, HttpServletRequest request) {
        try {
            OrderDetailGHTK orderDetailGHTK = new ModelMapper().map(restService.callGet(RestBuilder.build().uri("shipment/v2/"), request, labelId), OrderDetailGHTK.class);
            return orderDetailGHTK;
        } catch (Exception ex) {
            logger.error("get order err {0}", ex);
            throw ex;
        }
    }

    @Override
    public JsonGHTK cancel(String labelId, HttpServletRequest request) {
        try {
            JsonGHTK jsonGHTK = new ModelMapper().map(restService.callDelete(RestBuilder.build().uri("shipment/cancel/"), request, labelId), JsonGHTK.class);
            return jsonGHTK;
        } catch (Exception ex) {
            logger.error("cancel order err {0}", ex);
            throw ex;
        }
    }
}
