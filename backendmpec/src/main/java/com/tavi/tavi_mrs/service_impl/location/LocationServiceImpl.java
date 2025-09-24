package com.tavi.tavi_mrs.service_impl.location;

import com.tavi.tavi_mrs.entities.location.Location;
import com.tavi.tavi_mrs.repository.location.LocationRepo;
import com.tavi.tavi_mrs.service.location.LocationService;
import com.tavi.tavi_mrs.service_impl.khach_hang.KhachHangServiceIml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepo locationRepo;

    private static final Logger LOGGER = Logger.getLogger(LocationServiceImpl.class.getName());

    @Override
    public List<Location> listLocation(String level) {
        try {
            return locationRepo.locationList(level);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-location-by-level-error : " + ex);
            return null;
        }
    }

    @Override
    public List<Location> findQuanHuyenByThanhPhoId(String thanhPhoId) {
        try {
            return locationRepo.findQuanHuyenByThanhPhoId(thanhPhoId);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-location-by-level-error : " + ex);
            return null;
        }
    }

    @Override
    public List<Location> findPhuongXaByQuanHuyenId(String phuongXaId) {
        try {
            return locationRepo.findPhuongXaByQuanHuyenId(phuongXaId);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-location-by-level-error : " + ex);
            return null;
        }
    }
}
