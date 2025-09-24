package com.tavi.tavi_mrs.repository.chinh_sach_dieu_khoan;

import com.tavi.tavi_mrs.entities.chinh_sach_dieu_khoan.DieuKhoan;
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
public interface DieuKhoanRepo extends JpaRepository<DieuKhoan, Integer> {

    @Query(value = "from DieuKhoan d where d.xoa = false and d.id = ?1")
    Optional<DieuKhoan> findById(int id);

    @Query("select d from DieuKhoan d where d.xoa = false ")
    List<DieuKhoan> findAll();

    @Query("select d from DieuKhoan d where d.xoa = false ")
    Page<DieuKhoan> findAll(Pageable pageable);

    @Transactional
    @Modifying
    @Query("update DieuKhoan d set d.xoa = true where d.id = ?1")
    int delete(int id);
}