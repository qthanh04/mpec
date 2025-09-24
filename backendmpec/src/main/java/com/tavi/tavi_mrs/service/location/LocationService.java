package com.tavi.tavi_mrs.service.location;

import com.tavi.tavi_mrs.entities.location.Location;

import java.util.List;

public interface LocationService {

    List<Location> listLocation(String level);
    List<Location> findQuanHuyenByThanhPhoId(String thanhPhoId);
    List<Location> findPhuongXaByQuanHuyenId(String phuongXaId);

}
