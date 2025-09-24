package com.tavi.tavi_mrs.repository.so_quy;

import com.tavi.tavi_mrs.entities.so_quy.PhieuThuHoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhieuThuHoaDonRepo extends JpaRepository<PhieuThuHoaDon, Integer> {

    @Query(value = "from PhieuThuHoaDon p where p.id = ?1")
    Optional<PhieuThuHoaDon> findById(int id);

}
