package com.tavi.tavi_mrs.service_impl.giao_hang;

import com.tavi.tavi_mrs.entities.giao_hang.Partner_V2;
import com.tavi.tavi_mrs.repository.giao_hang.PartnerRepo_V2;
import com.tavi.tavi_mrs.service.giao_hang.PartnerService_V2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartnerServiceImpl_V2 implements PartnerService_V2 {

    private static final Logger logger = LoggerFactory.getLogger(PartnerServiceImpl_V2.class);

    @Autowired
    private PartnerRepo_V2 partnerRepo;

    @Override
    public Partner_V2 findById(int id) {
        try {
            return partnerRepo.findById(id);
        } catch (Exception ex) {
            logger.error("find-partner-by-id-err : " + ex);
            return null;
        }
    }

    @Override
    public Optional<Partner_V2> save(Partner_V2 partner) {
        try {
            return Optional.ofNullable(partnerRepo.save(partner));
        } catch (Exception ex) {
            logger.error("save-partner-err : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public List<Partner_V2> findAll() {
        try {
            return partnerRepo.findAll();
        } catch (Exception ex) {
            logger.error("find-all-partner-err : " + ex);
            return null;
        }
    }
}
