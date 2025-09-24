package com.tavi.tavi_mrs.service_impl.giao_hang;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.tavi.tavi_mrs.entities.giao_hang.*;
import com.tavi.tavi_mrs.entities.giao_hang.json.RestBuilder;
import com.tavi.tavi_mrs.entities.giao_hang.locationghn.DistrictGHN;
import com.tavi.tavi_mrs.entities.giao_hang.locationghn.ProvinceGHN;
import com.tavi.tavi_mrs.entities.giao_hang.locationghn.WardGHN;
import com.tavi.tavi_mrs.repository.giao_hang.TransportRepo_V2;
import com.tavi.tavi_mrs.service.giao_hang.PartnerService_V2;
import com.tavi.tavi_mrs.service.giao_hang.PurposeService_V2;
import com.tavi.tavi_mrs.service.giao_hang.TransportService_V2;
import com.tavi.tavi_mrs.service_impl.van_chuyen.TransportServiceImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;

@Service
public class TransportServiceV2Impl_V2 implements TransportService_V2 {

    private static final Logger logger = LoggerFactory.getLogger(TransportServiceImpl.class);
    private static String file_base;

    @Value("${file_base}")
    public void setFile_base(String file_base){
        TransportServiceV2Impl_V2.file_base = file_base;
    }

    @Value("${url.ghn2}")
    String URL_GHN2;

    @Autowired
    private TransportRepo_V2 transportRepo;

    @Autowired
    private PartnerService_V2 partnerService;

    @Autowired
    private RestService_V2 restServiceV2;

    @Autowired
    private PurposeService_V2 purposeServiceV2;

    JSONObject jsonObject =new JSONObject();
    JSONObject obj=new JSONObject();

    //==================1. Create a order============//
    @Override
    public Transport_V2 orderCreate(Order_V2 order, HttpServletRequest request, int partnerId) throws Exception {
        JSONObject job;
        Transport_V2 transport;
        Partner_V2 partner=partnerService.findById(partnerId);
        try {
            if (partner.getType()==1){
                job=parse(partner.getJson(),order,request,partnerId);
                System.out.println("job" + job);
                System.out.println(job);
                transport = save(order,request,partnerId,job,null);

            }
            else {
                String jsonStr =parseAha(partner.getJson(),order);
                transport = save(order,request,partnerId,null,jsonStr);
            }
            return transport;
        }catch (Exception ex){
            return null;
        }
    }

    //====== 2. Find transport by code==============//
    @Override
    public Optional<Transport_V2> findByCode(String code) {
        try{
            return transportRepo.findByCode(code);
        }catch (Exception ex) {
            logger.error("find-transport-by-code-err : " + ex);
            return Optional.empty();
        }
    }
    //===========3. stimated fee transport===========//
    @Override
    public List<Fee_V2> getFee(HttpServletRequest request, Order_V2 order) throws Exception {
        try{
            List<Fee_V2> feeList = new ArrayList<>();
            List<Partner_V2> partnerList = partnerService.findAll();
            for (Partner_V2 partner:partnerList) {
                feeList.add(getAFee(request,order,partner.getId()));
            }
            return feeList;
        }catch (Exception ex){
            logger.error("get-fee-err : " + ex);
            return null;
        }
    }

    //===========4 .Cancel a order===========//
    @Override
    public Boolean cancel(HttpServletRequest request, String code, Integer partnerId) throws ParseException {
        Purpose_v2 purpose= purposeServiceV2.findByPartnerAndName(partnerId,"cancel");
        Partner_V2 partner=partnerService.findById(partnerId);
        JsonObject jo;
        JSONObject jo2;
        try {
            int check =transportRepo.getStatus(code);
            if (check==0){
                switch (purpose.getType()){
                    case 1 ://truyen duoi dang pathvariable
                        restServiceV2.callPost(RestBuilder.build().uri(purpose.getUri()+code),request,null,partner);
                        break;
                    case 2://truyen duoi dang array
                        JsonArray array = new JsonArray();
                        array.add(new JsonPrimitive(code));
                        jo = new JsonObject();
                        jo.add(purpose.getJsonSend(), array);;
                        System.out.println(jo);
                        restServiceV2.callPost(RestBuilder.build().uri(purpose.getUri()),request,jo.toString(),partner);
                        break;
                    case 3://truyen param
                        RestBuilder restBuilder = new RestBuilder();
                        jo2=parseParam( purpose.getParam(),null,request,3);
                        System.out.println(jo2);
                        restServiceV2.callGet(RestBuilder.build().uri(purpose.getUri()).
                                param("token",request.getHeader(partner.getTokenName())).
                                param("order_id",code),partner.getUrl());
                        break;
                }
            }
            else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
        transportRepo.setStatus(code,1);//1 = huy ; 0 = da tao
        return true;
    }

    //=====Kiem tra xem order da duoc giao cua don vi nao chua======//
    @Override
    public Integer checkOrder(int orderId) {
        try{
            return transportRepo.checkOrder(orderId);
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public Integer checkOrderDeleted(String orderCode) {
        try{
            return transportRepo.checkOrderDeleted(orderCode);
        }catch (Exception ex){
            logger.error("checkOrderDeleted-err : " + ex);
            return null;
        }
    }

    //=============get fee of a partner=============//
    public Fee_V2 getAFee(HttpServletRequest request, Order_V2 order, int partnerId) throws Exception {
        String json,orderCreated;
        JSONObject job = null;
        System.out.println("jobFee_V2" + job);
        JSONParser parser = new JSONParser();
        org.json.simple.JSONObject jsonObject = null;
        org.json.simple.JSONObject feeJson;
        Fee_V2 fee = new Fee_V2();
        Partner_V2 partner=partnerService.findById(partnerId);
        Purpose_v2 purpose= purposeServiceV2.findByPartnerAndName(partnerId,"fee");
        JSONObject jobject;
        RestBuilder restBuilder = new RestBuilder();
        switch (purpose.getType()){
            case 1 :
                jobject=parseParam(purpose.getParam(),order,request,partnerId);
                System.out.println(jobject);
                restBuilder.uri(purpose.getUri());
                for(int i = 0; i<jobject.names().length(); i++){
                    System.out.println(jobject.names().getString(i) +jobject.get(jobject.names().getString(i)));
                    restBuilder.param(jobject.names().getString(i), String.valueOf(jobject.get(jobject.names().getString(i))));
                }
                System.out.println(restBuilder.toString());
                orderCreated = restServiceV2.callPost(restBuilder,request,null,partner);
                System.out.println(orderCreated);
                jsonObject = (org.json.simple.JSONObject) parser.parse(orderCreated);
                feeJson = (org.json.simple.JSONObject) jsonObject.get("fee");
                fee.setPartnerName(partner.getName());
                fee.setTotalFee(Integer.parseInt(feeJson.get("fee").toString()));
                break;
            case 2:
                json = partner.getJson();
                job  = parse(json,order,request,partnerId);
                orderCreated =  restServiceV2.callPost(RestBuilder.build().uri(purpose.getUri()),request,job.toString(),partner);
                System.out.println(orderCreated);
                jsonObject = (org.json.simple.JSONObject) parser.parse(orderCreated);
                feeJson = (org.json.simple.JSONObject) jsonObject.get("data");
                fee.setPartnerName(partner.getName());
                fee.setTotalFee(Integer.parseInt(feeJson.get("total").toString()));
                System.out.println(fee.toString());
                break;
            case 3:
                json = partner.getJson();
                String jsonStr =parseAha(json,order);
                orderCreated =  restServiceV2.callPost(RestBuilder.build().uri(purpose.getUri())
                        .param("token",request.getHeader("token_aha"))
                        .param("path",jsonStr)
                        .param("order_time",Integer.toString(order.getOrder_time()))
                        .param("service_id",order.getService_id()),request,jsonStr,partnerService.findById(partnerId));
                System.out.println(orderCreated);
                jsonObject = (org.json.simple.JSONObject) parser.parse(orderCreated);
                fee.setPartnerName(partner.getName());
                fee.setTotalFee(Integer.parseInt(jsonObject.get("total_price").toString()));
                System.out.println(fee.toString());
                break;
        }
        return fee;
    }

    //==================save transport and return transport detail============//
    @Override
    public Transport_V2 save(Order_V2 order, HttpServletRequest request, int partnerId, JSONObject job, String jsonStr) throws ParseException {
        String orderCreated;
        JSONParser parser = new JSONParser();
        org.json.simple.JSONObject jsonObject;
        org.json.simple.JSONObject order1;
        Transport_V2 transport = new Transport_V2();
        Purpose_v2 purpose= purposeServiceV2.findByPartnerAndName(partnerId,"save");
        try{
            switch (partnerId){
                case 1:
                    orderCreated = restServiceV2.callPost(RestBuilder.build().uri(purpose.getUri()),request,job.toString(),partnerService.findById(partnerId));
                    String val2 = null,name;
                    Field[] fields = transport.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        if (field.getName().equalsIgnoreCase("id")){
                            //field.set(transport, 199);
                        }
                        else if (field.getName().equalsIgnoreCase("orderId")){
                            field.set(transport,Integer.parseInt(order.getOrderId()));
                        }
                        else if (field.getName().equalsIgnoreCase("partner")){
                            field.set(transport,partnerService.findById(partnerId));
                        }
                        else if (field.getName().equalsIgnoreCase("status")){
                            field.set(transport,0);
                        }else {
                             name= parseSave(purpose.getJson_result(),field.getName());
                            System.out.println(name);
                            JSONObject jsonObj = new JSONObject(orderCreated);
                            String val = parseJsonResult("","",jsonObj,name,0,order,request,partnerId);
                            System.out.println(val+"aaaaa");
                            if (field.getName().equalsIgnoreCase("fee")){
                                Long fee=Long.parseLong(val);
                                field.set(transport,fee);
                            }else {
                                field.set(transport, val);
                            }
                        }

                    }
                    System.out.println(transport);





//                    jsonObject = (org.json.simple.JSONObject) parser.parse(orderCreated);
//
//                    order1 = (org.json.simple.JSONObject) jsonObject.get("order");
//                    transport.setOrderId(Integer.parseInt(order.getOrderId()));
//                    transport.setPartner(partnerService.findById(partnerId));
 //                   transport.setOrderCode(order1.get("label").toString());
//                    transport.setFee(Long.parseLong((order1.get("fee").toString())));
//                    transport.setPickTime((String) order1.get("estimated_pick_time"));
//                    transport.setDeliverTime((String) order1.get("estimated_deliver_time"));
//                    transport.setStatus(0);

                    return transportRepo.save(transport);
                case 2 :
                    orderCreated =  restServiceV2.callPost(RestBuilder.build().uri(purpose.getUri()),request,job.toString(),partnerService.findById(partnerId));
                    jsonObject = (org.json.simple.JSONObject) parser.parse(orderCreated);
                    order1 = (org.json.simple.JSONObject) jsonObject.get("data");
                    transport.setOrderId(Integer.parseInt(order.getOrderId()));
                    transport.setPartner(partnerService.findById(partnerId));
                    transport.setOrderCode((String) order1.get("order_code"));
                    transport.setFee((Long) order1.get("total_fee"));
                    transport.setPickTime("Đối tác tự liên hệ");
                    transport.setDeliverTime((String) order1.get("expected_delivery_time"));
                    transport.setStatus(0);

                    return transportRepo.save(transport);
                case 3 :
                    orderCreated =  restServiceV2.callPost(RestBuilder.build().uri("order/create")
                            .param("token",request.getHeader("token_aha"))
                            .param("path",jsonStr)
                            .param("order_time",Integer.toString(order.getOrder_time()))
                            .param("service_id",order.getService_id()),request,null,partnerService.findById(partnerId));
                    jsonObject = (org.json.simple.JSONObject) parser.parse(orderCreated);
                    order1 = (org.json.simple.JSONObject) jsonObject.get("order");
                    transport.setOrderId(Integer.parseInt(order.getOrderId()));
                    transport.setPartner(partnerService.findById(partnerId));
                    transport.setOrderCode((String) order1.get("_id"));
                    transport.setFee((Long) order1.get("total_fee"));
                    transport.setPickTime("Đối tác tự liên hệ");
                    transport.setDeliverTime("Đối tác tự liên hệ");
                    System.out.println(transport);
                    transport.setStatus(0);
                    return transportRepo.save(transport);
            }
        }catch (Exception ex){
            logger.error("save-transport-err : " + ex);
            return null;
        }
        return null;
    }

    public String parseSave(String json,String name) {
        try{
            JSONObject jsonObj = new JSONObject(json);
            return parseSave(jsonObj,name);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String parseSave(JSONObject json, String name) throws JSONException {
        @SuppressWarnings("unchecked")
        Iterator<String> keys = json.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object val = null;
            if (key.equals(name)){
                val = json.get(key);
                return val.toString();
            }

        }
        return null;
    }
//
//    public String parseJsonResult(String json,String name,Order order,HttpServletRequest request,Integer partnerId ) {
//        try{
//            //JSONObject jsonObj = new JSONObject(json);
//            return parseJsonResult("","",jsonObj,name,0,order,request,partnerId);
//        }catch(Exception e){
//            System.out.println(e.getMessage());
//        }
//        return null;
//    }
String data = null;
    public String parseJsonResult(String grandParentKey, String parentKey, JSONObject json, String name, int count, Order_V2 order, HttpServletRequest request, Integer partnerId) throws JSONException, IllegalAccessException {
        @SuppressWarnings("unchecked")

        Iterator<String> keys = json.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object val = null;
            val = json.get(key);
            if (val.getClass().getTypeName().contains("JSONArray")) {
                JSONArray jArr = (JSONArray) val;
                for (int i = 0; i < jArr.length(); i++) {
                    count++;
                    org.json.JSONObject childJSONObject = jArr.getJSONObject(i);
                    parseJsonResult(parentKey, key, childJSONObject, name, count, order, request, partnerId);
                }
            } else if (val.getClass().getTypeName().equals("org.json.JSONObject")) {
                 data=val.toString();
                parseJsonResult(parentKey, key, (org.json.JSONObject) val, name, count, order, request, partnerId);
            } else {
                if (val.getClass().getTypeName().equals("org.json.JSONObject$Null")) {
                    val = "null";
                }
                if (val != null) {
                    if (!grandParentKey.isEmpty()) {
                    } else if (!parentKey.isEmpty()) {
                        if (key.equalsIgnoreCase(name)) {
                            System.out.println(val.toString()+"aaab");
                            data=val.toString();
                            System.out.println("data" + data);
                            return data;
                        }
                    }
                }
            }
        }
        System.out.println("data" + data);
        return data;
    }



//======================convert aha=============================//
    public String parseAha(String json, Order_V2 order){
        JSONObject jsnobject = new JSONObject(json);
        JSONObject jsnobject2 = new JSONObject();
        JSONObject jsnobject3 = new JSONObject();
        JSONArray ja = new JSONArray();
        JSONArray jsonArray = jsnobject.getJSONArray("path");
        System.out.println(jsonArray);
        String return_address=null;
        String to_address=null;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject explrObject = jsonArray.getJSONObject(i);
            if (return_address==null){
                return_address=explrObject.optString("return_address");
            }
            if(to_address==null){
                to_address=explrObject.optString("to_address");
            }
        }
        return_address=to_address;
        jsnobject2.put(return_address,order.getReturn_address());
        jsnobject3.put(to_address,order.getTo_address());
        ja.put(jsnobject2);
        ja.put(jsnobject3);
        System.out.println(ja);
        return ja.toString();
    }


    //check isnumber to convert type
    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    //===================convert ghn and ghtk==================//
    public JSONObject parse(String json, Order_V2 order, HttpServletRequest request, Integer partnerId) {
        try{
            JSONObject jsonObj = new JSONObject(json);
            return parse("","",jsonObj,0,order,request,partnerId);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public JSONObject parseParam(String json, Order_V2 order, HttpServletRequest request, Integer partnerId) {
        try{
            JSONObject jsonObj = new JSONObject(json);
            return parseParam(jsonObj,order,request,partnerId);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public JSONObject parseParam(JSONObject json, Order_V2 order, HttpServletRequest request, Integer partnerId) throws JSONException {
        @SuppressWarnings("unchecked")
        Partner_V2 partner=partnerService.findById(partnerId);
        Iterator<String> keys = json.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object val = null;
            val = json.get(key);
            jsonObject.put(key,getVal(order,val.toString()));
        }
        return jsonObject;
    }

    public JSONObject parse(String grandParentKey, String parentKey, JSONObject json, int count, Order_V2 order, HttpServletRequest request, Integer partnerId) throws JSONException {
        @SuppressWarnings("unchecked")
        Iterator<String> keys = json.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object val = null;
            val = json.get(key);
            if (val.getClass().getTypeName().contains("JSONArray")){
                JSONArray jArr = (JSONArray) val;
                for(int i=0; i<jArr.length(); i++){
                    count++;
                    org.json.JSONObject childJSONObject = jArr.getJSONObject(i);
                    parse(parentKey, key, childJSONObject, count,order,request,partnerId);
                }
            }else if (val.getClass().getTypeName().equals("org.json.JSONObject")){
                parse(parentKey, key, (org.json.JSONObject) val, count,order,request,partnerId);
            }else{
                if (val.getClass().getTypeName().equals("org.json.JSONObject$Null")){
                    val="null";
                }
                if (val != null) {
                    String s1 ="";
                    if(!grandParentKey.isEmpty()){
                    }else if (!parentKey.isEmpty()){
                        jsonObject.put(val.toString(),getVal(order,key));
                    } else {
                        if (partnerId==2){
                            if (isNumeric(getValue(order,request,key))&&!key.equalsIgnoreCase("to_phone")&&!key.equalsIgnoreCase("return_phone")){
                                jsonObject.put(val.toString(),Integer.parseInt(getValue(order,request,key)));
                            }
                            else {
                                jsonObject.put(val.toString(),getValue(order,request,key));
                            }
                        }
                    }
                    if (partnerId==1){
                        obj.put(parentKey,jsonObject);
                    }
                }
            }
        }
        System.out.println(obj);
        if (partnerId==1){
            return obj;
        }else if (partnerId==2){
            return jsonObject;
        }
        return null;
    }

    //-------------------- get Value BK to set Json partner---------------------------//
    String getVal(Order_V2 order, String nameFeild) {
        try{
            Field nameField = order.getClass().getDeclaredField(nameFeild);
            nameField.setAccessible(true);
            String val= (String) nameField.get(order);
            return val;
        } catch (IllegalAccessException e) {
            return null;
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    String getValue(Order_V2 order, HttpServletRequest request, String name){
        String returnDistrictId = null;
        String toDisttrictId=null;
        String returnWardCode=null;
        String toWardCode=null;
        if (name.equalsIgnoreCase("return_district") || name.equalsIgnoreCase("return_ward") || name.equalsIgnoreCase("to_district") || name.equalsIgnoreCase("to_ward")) {
            List<ProvinceGHN> provinces = Arrays.asList(new ModelMapper().map(restServiceV2.callGetListLocation(RestBuilder.build().uri("province"), request,URL_GHN2), ProvinceGHN[].class));
            for (ProvinceGHN province : provinces) {
                if (province.getProvinceName().equalsIgnoreCase(order.getReturn_province()) || province.getProvinceName().equalsIgnoreCase(order.getTo_province())) {
                    List<DistrictGHN> districts = Arrays.asList(new ModelMapper().map(restServiceV2.callGetListLocation(RestBuilder.build().param("province_id", Integer.toString(province.getProvinceID())).uri("district"), request,URL_GHN2), DistrictGHN[].class));
                    for (DistrictGHN district : districts) {
                        if (district.getDistrictName().equalsIgnoreCase(order.getReturn_district()) || district.getDistrictName().equalsIgnoreCase(order.getTo_district())) {
                            if (district.getDistrictName().equalsIgnoreCase(order.getReturn_district())) {
                                returnDistrictId = Integer.toString(district.getDistrictID());
                            } else if (district.getDistrictName().equalsIgnoreCase(order.getTo_district())) {
                                toDisttrictId = Integer.toString(district.getDistrictID());
                            }
                            List<WardGHN> wards = Arrays.asList(new ModelMapper().map(restServiceV2.callGetListLocation(RestBuilder.build().param("district_id", Integer.toString(district.getDistrictID())).uri("ward"), request,URL_GHN2), WardGHN[].class));
                            for (WardGHN ward : wards) {
                                if (ward.getWardName().equalsIgnoreCase(order.getReturn_ward()) || ward.getWardName().equalsIgnoreCase(order.getTo_ward())) {
                                    if (ward.getWardName().equalsIgnoreCase(order.getReturn_ward())) {
                                        returnWardCode = ward.getWardCode();
                                    } else if (ward.getWardName().equalsIgnoreCase(order.getTo_ward())) {
                                        toWardCode = ward.getWardCode();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        switch (name){
            case "orderId": return order.getOrderId();
            case "note"   :return order.getNote();
            case "required_note" :return  order.getRequired_note();
            case "return_address":return order.getReturn_address();
            case "return_district":return returnDistrictId;
            case "return_ward":return returnWardCode;
            case "return_name": order.getReturn_name();
            case "to_name":return order.getTo_name();
            case "to_phone":return order.getTo_phone();
            case "to_address":return order.getTo_address();
            case "to_ward":return toWardCode;
            case "to_district":return toDisttrictId;
            case "cod_amount":return order.getCod_amount();
            case "weight":order.getWeight();
            case "height":return order.getHeight();
            case "service_type_id":return order.getService_type_id();
            case "payment_type_id":return order.getPayment_type_id();
            default:
                return null;
        }
    }
}
