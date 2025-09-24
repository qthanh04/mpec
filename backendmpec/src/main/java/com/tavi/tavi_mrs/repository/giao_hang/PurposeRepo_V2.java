package com.tavi.tavi_mrs.repository.giao_hang;

import com.tavi.tavi_mrs.entities.giao_hang.Purpose_v2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PurposeRepo_V2 extends JpaRepository<Purpose_v2,Integer> {

    @Query("from  Purpose_v2 p where p.partner.id = ?1 and p.name=?2")
    Purpose_v2 findByPartnerAndName(Integer parterId, String name);
}
