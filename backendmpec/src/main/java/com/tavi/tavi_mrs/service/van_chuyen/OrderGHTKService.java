package com.tavi.tavi_mrs.service.van_chuyen;

import com.tavi.tavi_mrs.entities.van_chuyen.ghtk.DataGHTK;
import com.tavi.tavi_mrs.entities.van_chuyen.ghtk.OrderDetailGHTK;
import com.tavi.tavi_mrs.entities.van_chuyen.ghtk.OrderGHTKCreated;
import com.tavi.tavi_mrs.entities.van_chuyen.json.JsonGHTK;

import javax.servlet.http.HttpServletRequest;

public interface OrderGHTKService {

    OrderGHTKCreated orderCreate(DataGHTK data, HttpServletRequest request, int partnerId);

    OrderDetailGHTK orderDetail(String labelId, HttpServletRequest request);

    JsonGHTK cancel(String labelId, HttpServletRequest request);

}
