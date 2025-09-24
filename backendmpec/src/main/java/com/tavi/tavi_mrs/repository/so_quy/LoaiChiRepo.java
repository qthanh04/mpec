package com.tavi.tavi_mrs.repository.so_quy;

import com.tavi.tavi_mrs.entities.so_quy.LoaiChi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface LoaiChiRepo extends JpaRepository<LoaiChi, Integer> {

    @Query(value = "from LoaiChi lc where lc.xoa = false ")
    Page<LoaiChi> findAll(Pageable pageable);

    @Query(value = "from LoaiChi lc where lc.xoa = false and lc.id = ?1")
    Optional<LoaiChi> findById(int id, boolean xoa);

    @Query(value = "from LoaiChi lc " +
            "where lc.xoa = false " +
            "and upper(lc.tenLoaiChi) like concat('%',upper(?1),'%') " +
            "order by lc.id")
    Optional<LoaiChi> findByTenLoaiChi(String tenLoaiChi);

    @Query(value = "from LoaiChi lc " +
            "where lc.xoa = false " +
            "and upper(lc.maLoaiChi) like concat('%',upper(?1),'%') " +
            "order by lc.id")
    Optional<LoaiChi> findByMaLoaiChi(String maLoaiChi);

    @Query(value = "from LoaiChi lc " +
            "where lc.xoa = false " +
            "and upper(lc.tenLoaiChi) like concat('%', upper(?1), '%') " +
            "and upper(lc.maLoaiChi) like concat('%', upper(?2), '%') " +
            "order by lc.id")
    Page<LoaiChi> findByTenLoaiChiAndMaLoaiChi(String tenLoaiChi, String maLoaiChi, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "update LoaiChi lc set lc.xoa = true where lc.id = ?1")
    int delete(Integer id);

}
