package com.tavi.tavi_mrs.repository.giao_hang;

import com.tavi.tavi_mrs.entities.giao_hang.Transport_V2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface TransportRepo_V2 extends JpaRepository<Transport_V2,Integer> {

    @Query(value = "from Transport_V2 t where t.orderCode = ?1")
    Optional<Transport_V2> findByCode(String code);

    @Query("SELECT t.orderId from Transport_V2 t where t.orderId = ?1")
    Integer checkOrder(int orderId);

    @Query("SELECT t.status from Transport_V2 t where t.orderCode = ?1")
    Integer checkOrderDeleted(String orderCode);

    @Modifying
    @Transactional
    @Query("update Transport_V2 t set t.status = ?2 where t.orderCode = ?1")
    void setStatus(String code, int trangThai);

    @Query("SELECT t.status from Transport_V2 t where t.orderCode = ?1")
    int getStatus(String code);
}
