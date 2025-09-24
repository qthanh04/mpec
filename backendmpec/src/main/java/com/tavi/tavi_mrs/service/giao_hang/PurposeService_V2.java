package com.tavi.tavi_mrs.service.giao_hang;

import com.tavi.tavi_mrs.entities.giao_hang.Purpose_v2;

public interface PurposeService_V2 {

    Purpose_v2 findByPartnerAndName(Integer parterId, String name);
}
