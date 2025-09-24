package com.tavi.tavi_mrs.repository.hoat_dong;

import com.tavi.tavi_mrs.entities.hoat_dong.HoatDong;
import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HoatDongRepo extends JpaRepository<HoatDong, Integer> {

    @Query("select h from HoatDong h where h.id = ?1 ")
    Optional<HoatDong> findById(int id);
}
