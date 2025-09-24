package com.tavi.tavi_mrs.service_impl.van_chuyen;

import com.tavi.tavi_mrs.entities.hoa_don.HoaDon;
import com.tavi.tavi_mrs.entities.van_chuyen.*;
import com.tavi.tavi_mrs.entities.van_chuyen.ghn.*;
import com.tavi.tavi_mrs.entities.van_chuyen.json.RestBuilder;
import com.tavi.tavi_mrs.service.hoa_don.HoaDonService;
import com.tavi.tavi_mrs.service.van_chuyen.DoiTacService;
import com.tavi.tavi_mrs.service.van_chuyen.OrderGHNService;
import com.tavi.tavi_mrs.service.van_chuyen.TransportService;
import com.tavi.tavi_mrs.service_impl.van_chuyen.rest.RestGHN;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderGHNServiceImpl implements OrderGHNService {

    private static final Logger logger = LoggerFactory.getLogger(OrderGHNServiceImpl.class);

    @Autowired
    private RestGHN restService;

    @Autowired
    private TransportService transportService;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private DoiTacService doiTacService;

    @Override
    public OrderCreate orderCreate(DataGHN data, HttpServletRequest request, int partnerId) {
        try {
            OrderGHN orderGHN = new OrderGHN();
            orderGHN.setPayment_type_id(data.getPayment_type_id());
            orderGHN.setNote(data.getNote());
            orderGHN.setRequired_note(data.getRequired_note());
            orderGHN.setClient_order_code(data.getClient_order_code());
            orderGHN.setTo_name(data.getTo_name());
            orderGHN.setTo_phone(data.getTo_phone());
            orderGHN.setCod_amount(data.getCod_amount());
            orderGHN.setContent(data.getContent());
            orderGHN.setWeight(data.getWeight());
            orderGHN.setLength(data.getLength());
            orderGHN.setWidth(data.getWidth());
            orderGHN.setHeight(data.getHeight());
            orderGHN.setPick_station_id(data.getPick_station_id());
            orderGHN.setInsurance_value(data.getInsurance_value());
            orderGHN.setService_id(data.getService_id());
            orderGHN.setService_type_id(data.getService_type_id());
            orderGHN.setCoupon(data.getCoupon());
            orderGHN.setReturn_address(data.getReturn_address() + ", " + data.getReturn_ward() + ", " + data.getReturn_district() + ", " + data.getReturn_province());
            orderGHN.setTo_address(data.getTo_address() + ", " + data.getTo_ward() + ", " + data.getTo_district() + ", " + data.getTo_province());

            List<ProvinceGHN> provinces = Arrays.asList(new ModelMapper().map(restService.callGetListLocation(RestBuilder.build().uri("province"), request), ProvinceGHN[].class));
            for (ProvinceGHN province : provinces) {
                if (province.getProvinceName().equalsIgnoreCase(data.getReturn_province()) || province.getProvinceName().equalsIgnoreCase(data.getTo_province())) {
                    List<DistrictGHN> districts = Arrays.asList(new ModelMapper().map(restService.callGetListLocation(RestBuilder.build().param("province_id", Integer.toString(province.getProvinceID())).uri("district"), request), DistrictGHN[].class));
                    for (DistrictGHN district : districts) {
                        if (district.getDistrictName().equalsIgnoreCase(data.getReturn_district()) || district.getDistrictName().equalsIgnoreCase(data.getTo_district())) {
                            if (district.getDistrictName().equalsIgnoreCase(data.getReturn_district())) {
                                orderGHN.setReturn_district_id(district.getDistrictID());
                            } else if (district.getDistrictName().equalsIgnoreCase(data.getTo_district())) {
                                orderGHN.setTo_district_id(district.getDistrictID());
                            }
                            List<WardGHN> wards = Arrays.asList(new ModelMapper().map(restService.callGetListLocation(RestBuilder.build().param("district_id", Integer.toString(district.getDistrictID())).uri("ward"), request), WardGHN[].class));
                            for (WardGHN ward : wards) {
                                if (ward.getWardName().equalsIgnoreCase(data.getReturn_ward()) || ward.getWardName().equalsIgnoreCase(data.getTo_ward())) {
                                    if (ward.getWardName().equalsIgnoreCase(data.getReturn_ward())) {
                                        orderGHN.setReturn_ward_code(ward.getWardCode());
                                    } else if (ward.getWardName().equalsIgnoreCase(data.getTo_ward())) {
                                        orderGHN.setTo_ward_code(ward.getWardCode());
                                    }

                                }
                            }
                        }
                    }
                }
            }

            OrderCreate orderCreate = new ModelMapper().map(restService.callPost(RestBuilder.build().uri("create"), request, orderGHN), OrderCreate.class);

            HoaDon hoaDon = hoaDonService.findByIdAndXoa(data.getOrderId(), false);
            DoiTac doiTac = doiTacService.findById(partnerId);
            Transport transport = new Transport();
            transport.setHoaDon(hoaDon);
            transport.setDoiTac(doiTac);
            transport.setOrderCode(orderCreate.getOrder_code());
            transport.setFee(orderCreate.getTotal_fee());
            transport.setPickTime("Đối tác tự liên hệ");
            transport.setDeliverTime(orderCreate.getExpected_delivery_time());
            transportService.save(transport);
            return orderCreate;
        } catch (Exception ex) {
            logger.error("create order err {0}", ex);
            throw ex;
        }
    }

    @Override
    public OrderInfor getOrderInfor(OrderInforForm odf, HttpServletRequest request) {
        try {
            return new ModelMapper().map(restService.callGet(RestBuilder.build().uri("detail"), request, odf), OrderInfor.class);
        } catch (Exception ex) {
            logger.error("get order detail err {0}", ex);
            throw ex;
        }
    }

    @Override
    public List<OrderDeleted> cancelOrder(OrderInforforms listOdfs, HttpServletRequest request) {
        try {
            List<OrderDeleted> result = Arrays.asList(new ModelMapper().map(restService.callDelete(request, listOdfs), OrderDeleted[].class));
            return result;
        } catch (Exception ex) {
            logger.error("cancel order err {0}", ex);
            throw ex;
        }
    }
}
