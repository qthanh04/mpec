package com.tavi.tavi_mrs.repository.hang_hoa;

import com.tavi.tavi_mrs.entities.hang_hoa.NhomHang;
import com.tavi.tavi_mrs.entities.hang_hoa.ThuongHieu;
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
public interface NhomHangRepo extends JpaRepository<NhomHang, Integer> {

    @Query("select n from NhomHang n where n.xoa = false")
    List<NhomHang> findAll();

    @Query(value = "from NhomHang n where n.xoa = false and n.id = ?1 ")
    Optional<NhomHang> findById(int id, boolean xoa);

    Optional<NhomHang> findByIdAndXoa(int id, boolean xoa);

    @Query(value = "from NhomHang  n where n.xoa = false and n.maNhomHang = ?1 ")
    Optional<NhomHang> findByMaNhomHangAndXoa(String maNhomHang);

    @Query(value = "from NhomHang n where n.xoa = false and n.tenNhomHang = ?1 ")
    Optional<NhomHang> findByTenNhomHangAndXoa(String tenNhomHang);

    @Modifying
    @Transactional
    @Query(value = "update NhomHang nh set nh.xoa = true where nh.id = ?1")
    int deleted(Integer id);

    @Query(value = " select n from NhomHang n where n.xoa = false order by n.id")
    Page<NhomHang> findAllToPage(Pageable pageable);

    @Query(value = "from NhomHang nh " +
            "where nh.xoa = false " +
            "and nh.tenNhomHang like concat('%', ?1, '%') " +
            "and nh.maNhomHang like concat('%', ?2, '%') " +
            "order by nh.id")
    Page<NhomHang> findByTenNhomHangAndMaNhomHang(String tenNhomHang, String maNhomHang, Pageable pageable);


}
