package com.tavi.tavi_mrs.service_impl.van_chuyen;

import com.tavi.tavi_mrs.entities.van_chuyen.*;
import com.tavi.tavi_mrs.entities.van_chuyen.ghn.OrderCreate;
import com.tavi.tavi_mrs.entities.van_chuyen.ghn.OrderGHN;
import com.tavi.tavi_mrs.entities.van_chuyen.json.RestBuilder;
import com.tavi.tavi_mrs.repository.van_chuyen.TransportRepo;
import com.tavi.tavi_mrs.service.van_chuyen.DoiTacService;
import com.tavi.tavi_mrs.service.van_chuyen.TransportService;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TransportServiceImpl implements TransportService {

    private static final Logger logger = LoggerFactory.getLogger(TransportServiceImpl.class);

    @Autowired
    private TransportRepo transportRepo;

    @Autowired
    private DoiTacService doiTacService;

    @Override
    public Optional<Transport> save(Transport transport) {
        try {
            return Optional.ofNullable(transportRepo.save(transport));
        } catch (Exception ex) {
            logger.error("save-transport-err : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public List<Fee> getFee(HttpServletRequest request, OrderFee order) throws Exception {
        try {
            List<Fee> feeList = new ArrayList<>();
            List<DoiTac> partnerList = doiTacService.findAll();
            for (DoiTac partner : partnerList) {
                feeList.add(getAFee(request, order, partner.getId()));
            }
            return feeList;
        } catch (Exception ex) {
            logger.error("get-fee-err : " + ex);
            return null;
        }
    }

    @Override
    public Page<Transport> findAllVanChuyen(Pageable pageable) {
        try {
            return transportRepo.findAll(pageable);
        } catch (Exception ex) {
            logger.error("find-all-bao-cao-error : " + ex);
            return null;
        }
    }

    @Override
    public Page<Transport> searchVanChuyen(String text, Pageable pageable) {
        try {
            return transportRepo.searchVanChuyen(text , pageable);
        } catch (Exception ex) {
            logger.error("find-all-bao-cao-error : " + ex);
            return null;
        }
    }

    //=============get fee of a partner=============//
    public Fee getAFee(HttpServletRequest request, OrderFee data, int partnerId) throws Exception {
        Fee fee = new Fee();
        // call api lay phi van chuyen cua ghn va ghtk
        return fee;
    }
}
