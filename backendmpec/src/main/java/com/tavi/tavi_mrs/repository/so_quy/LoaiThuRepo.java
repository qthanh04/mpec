package com.tavi.tavi_mrs.repository.so_quy;

import com.tavi.tavi_mrs.entities.so_quy.LoaiChi;
import com.tavi.tavi_mrs.entities.so_quy.LoaiThu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface LoaiThuRepo extends JpaRepository<LoaiThu, Integer> {
    @Query(value = "from LoaiThu lt where lt.xoa = false ")
    Page<LoaiThu> findAll(Pageable pageable);

    @Query(value = "from LoaiThu lt where lt.xoa = false and lt.id = ?1")
    Optional<LoaiThu> findById(int id, boolean xoa);

    @Query(value = "from LoaiThu lt " +
            "where lt.xoa = false " +
            "and upper(lt.tenLoaiThu) like concat('%',upper(?1),'%') " +
            "order by lt.id")
    Optional<LoaiThu> findByTenLoaiThu(String tenLoaiThu);

    @Query(value = "from LoaiThu lt " +
            "where lt.xoa = false " +
            "and upper(lt.maLoaiThu) like concat('%',upper(?1),'%') " +
            "order by lt.id")
    Optional<LoaiThu> findByMaLoaiThu(String maLoaiThu);

    @Query(value = "from LoaiThu lt " +
            "where lt.xoa = false " +
            "and upper(lt.tenLoaiThu) like concat('%', upper(?1), '%') " +
            "and upper(lt.maLoaiThu) like concat('%', upper(?2), '%') " +
            "order by lt.id")
    Page<LoaiThu> findByTenLoaiThuAndMaLoaiThu(String tenLoaiThu, String maLoaiThu, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "update LoaiThu lt set lt.xoa = true where lt.id = ?1")
    int delete(Integer id);
}
