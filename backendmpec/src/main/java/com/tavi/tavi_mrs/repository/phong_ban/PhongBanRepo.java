package com.tavi.tavi_mrs.repository.phong_ban;

import com.tavi.tavi_mrs.entities.chuc_vu_vai_tro.ChucVu;
import com.tavi.tavi_mrs.entities.hang_hoa.NhomHang;
import com.tavi.tavi_mrs.entities.phong_ban.PhongBan;
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
public interface PhongBanRepo extends JpaRepository<PhongBan, Integer> {
    @Query(value = "from PhongBan p where p.xoa = false" +
            " order by p.id")
    List<PhongBan> findAll();

    Optional<PhongBan> findByIdAndXoa(int id, boolean xoa);

    @Query(value = "from PhongBan p where p.xoa = false" +
            " order by p.id")
    Page<PhongBan> findAllToPage(Pageable pageable);

    @Query(value = "from PhongBan p where " +
            "p.xoa = false " +
            "and upper(p.tenPhongBan) like concat('%', upper(?1), '%')" +

            " order by p.id")
    Page<PhongBan> search(String text, Pageable pageable);

    @Query(value = "from PhongBan p " +
            "where p.xoa = false " +
            "and upper(p.maPhongBan) like concat('%', upper(?1), '%' ) " +
            "order by p.id")
    Optional<PhongBan> findByMaPhongBan(String maPhongBan);

    @Query(value = "from PhongBan  pb " +
            "where pb.xoa = false " +
            "and upper(pb.tenPhongBan) like concat('%', upper(?1), '%')" +
            "order by pb.id")
    Optional<PhongBan> findByTenPhongBan(String tenPhongBan);

    @Query(value = "from PhongBan pb " +
            "where pb.xoa = false " +
            "and pb.tenPhongBan like concat('%', ?1, '%') " +
            "and pb.maPhongBan like concat('%', ?2, '%') " +
            "order by pb.id")
    Page<PhongBan> findByTenPhongBanAndMaPhongBan(String tenPhongBan, String maPhongban, Pageable pageable);

    @Modifying
    @Transactional
    @Query("update PhongBan as pb set pb.xoa=true where pb.id=?1")
    int deleted(Integer id);

}
