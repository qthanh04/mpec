package com.tavi.tavi_mrs.repository.van_chuyen;

import com.tavi.tavi_mrs.entities.van_chuyen.Transport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportRepo extends JpaRepository<Transport, Integer> {

    @Query(value = "from Transport t")
    Page<Transport> findAllVanChuyen(Pageable pageable);

    @Query(value = "from Transport t where t.hoaDon.ma is not null and upper (t.hoaDon.ma) like concat('%', upper(?1) ,'%') " +
            " or t.doiTac.name is not null and upper (t.doiTac.name) like concat('%', upper(?1) ,'%') order by t.id desc ")
    Page<Transport> searchVanChuyen(String text,Pageable pageable);

}
