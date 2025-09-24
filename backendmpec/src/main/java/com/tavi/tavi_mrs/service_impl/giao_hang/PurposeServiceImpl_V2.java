package com.tavi.tavi_mrs.service_impl.giao_hang;

import com.tavi.tavi_mrs.entities.giao_hang.Purpose_v2;
import com.tavi.tavi_mrs.repository.giao_hang.PurposeRepo_V2;
import com.tavi.tavi_mrs.service.giao_hang.PurposeService_V2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurposeServiceImpl_V2 implements PurposeService_V2 {

    private static final Logger logger = LoggerFactory.getLogger(PurposeServiceImpl_V2.class);

    @Autowired
    private PurposeRepo_V2 purposeRepo;

    @Override
    public Purpose_v2 findByPartnerAndName(Integer parterId, String name) {
        try{
            return purposeRepo.findByPartnerAndName(parterId,name);
        }catch (Exception ex){
            logger.error("findByPartnerAndName-err : " + ex);
            return null;
        }
    }
}
