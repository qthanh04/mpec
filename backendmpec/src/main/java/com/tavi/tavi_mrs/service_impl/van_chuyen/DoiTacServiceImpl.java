package com.tavi.tavi_mrs.service_impl.van_chuyen;

import com.tavi.tavi_mrs.entities.van_chuyen.DoiTac;
import com.tavi.tavi_mrs.repository.van_chuyen.DoiTacRepo;
import com.tavi.tavi_mrs.service.van_chuyen.DoiTacService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoiTacServiceImpl implements DoiTacService {

    private static final Logger logger = LoggerFactory.getLogger(DoiTacServiceImpl.class);

    @Autowired
    private DoiTacRepo doiTacRepo;

    @Override
    public DoiTac findById(int id) {
        try {
            return doiTacRepo.findById(id);
        } catch (Exception ex) {
            logger.error("find-partner-by-id-err : " + ex);
            return null;
        }
    }

    @Override
    public Optional<DoiTac> findByIdOptionnal(int id) {
        try {
            return doiTacRepo.findByIdOptionnal(id);
        } catch (Exception ex) {
            logger.error("find-partner-by-id-err : " + ex);
            return null;
        }
    }

    @Override
    public Optional<DoiTac> save(DoiTac partner) {
        try {
            return Optional.ofNullable(doiTacRepo.save(partner));
        } catch (Exception ex) {
            logger.error("save-partner-err : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public List<DoiTac> findAll() {
        try {
            return doiTacRepo.findAll();
        } catch (Exception ex) {
            logger.error("find-all-partner-err : " + ex);
            return null;
        }
    }
}
