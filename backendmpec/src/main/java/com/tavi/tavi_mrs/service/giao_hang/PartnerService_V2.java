package com.tavi.tavi_mrs.service.giao_hang;

import com.tavi.tavi_mrs.entities.giao_hang.Partner_V2;

import java.util.List;
import java.util.Optional;

public interface PartnerService_V2 {

    Partner_V2 findById(int id);

    Optional<Partner_V2> save(Partner_V2 partner);

    List<Partner_V2> findAll();
}
