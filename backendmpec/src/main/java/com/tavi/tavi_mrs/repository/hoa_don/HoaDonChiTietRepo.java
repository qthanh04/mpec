package com.tavi.tavi_mrs.repository.hoa_don;

import com.tavi.tavi_mrs.entities.hoa_don.HoaDonChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface HoaDonChiTietRepo extends JpaRepository<HoaDonChiTiet, Integer> {

    List<HoaDonChiTiet> findAll();

    @Query(value = " select h from HoaDonChiTiet h where h.xoa = false and h.hoaDon.id=?1 order by h.id")
    Page<HoaDonChiTiet> findAllByIdHoaDon(int idHoaDon, Pageable pageable);

    @Query(value = " select h from HoaDonChiTiet h where h.xoa = false and h.hoaDon.id=?1 order by h.id")
    List<HoaDonChiTiet> findListByIdHoaDon(int idHoaDon);

    Optional<HoaDonChiTiet> findById(int id);

    @Modifying
    @Transactional
    @Query(value = "update HoaDonChiTiet hd  set hd.xoa = true where hd.id = ?1")
    int deleted(Integer id);
}
