package com.tavi.tavi_mrs.repository.giao_hang;

import com.tavi.tavi_mrs.entities.giao_hang.Partner_V2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerRepo_V2 extends JpaRepository<Partner_V2,Integer> {

    @Query(value = "from Partner_V2 p where p.id = ?1")
    Partner_V2 findById(int id);

    @Query("from Partner_V2 ")
    List<Partner_V2> findAll();
}
