package com.tavi.tavi_mrs.repository.bao_cao;

import com.tavi.tavi_mrs.entities.bao_cao.BaoCao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Repository
public interface BaoCaoRepo extends JpaRepository<BaoCao, Integer> {

    @Modifying
    @Transactional
    @Query("update BaoCao b set b.xoa = true where b.id = ?1")
    int deleted(int id);

    Optional<BaoCao> findByIdAndXoa(int id, boolean xoa);

    @Query(value = "from BaoCao b where b.xoa = false order by b.thoiGianTao desc ")
    Page<BaoCao> findAll(Pageable pageable);

    @Query(value = "from BaoCao b " +
            "where b.xoa = false " +
            "and ( b.thongBao.id = ?1 and b.thongBao.xoa = false) " +
            "order by b.thoiGianTao desc ")
    Page<BaoCao> findByThongBao(int idThongBao, Pageable pageable);

    @Query(value = "from BaoCao b " +
            "where " +
            "b.xoa = ?4" +
            "and b.tieuDe like concat('%',?1,'%') " +
            "and (b.thoiGianTao >= ?2 and b.thoiGianTao <= ?3)" +
            "order by b.thoiGianTao desc ")
    Page<BaoCao> findByTieuDeAndThoiGian(String tieuDe, Date ngayDau, Date ngayCuoi, boolean xoa, Pageable pageable);
}
