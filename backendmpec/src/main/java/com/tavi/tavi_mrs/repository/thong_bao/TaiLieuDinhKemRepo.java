package com.tavi.tavi_mrs.repository.thong_bao;

import com.tavi.tavi_mrs.entities.thong_bao.TaiLieuDinhKem;
import com.tavi.tavi_mrs.entities.thong_bao.ThongBao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface TaiLieuDinhKemRepo extends JpaRepository<TaiLieuDinhKem, Integer> {

//    @Query(value = "")
//    Optional<ThongBao> findByIdTaiLieuDinhKem(Integer idTaiLieuDinhKem);

    @Modifying
    @Transactional
    @Query("update TaiLieuDinhKem tl set tl.thongBao = ?2 where tl.duongDan = ?1")
    int updateThongBaoByDuongDan(ThongBao thongBao, String duongDan);
}