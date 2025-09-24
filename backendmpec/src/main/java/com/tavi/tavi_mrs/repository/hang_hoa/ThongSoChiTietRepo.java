package com.tavi.tavi_mrs.repository.hang_hoa;

import com.tavi.tavi_mrs.entities.hang_hoa.ThongSoChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface ThongSoChiTietRepo extends JpaRepository<ThongSoChiTiet, Integer>  {

    @Query(value = "from ThongSoChiTiet t where t.xoa = false and t.id = ?1")
    Optional<ThongSoChiTiet> findById(int id, boolean xoa);

    @Query(value = "from ThongSoChiTiet t where t.xoa = false ")
    Page<ThongSoChiTiet> findAllToPage(Pageable pageable);

    @Query(value = "from ThongSoChiTiet t " +
            "where t.xoa = false " +
            "and t.thongSoKiThuat.ten like concat('%',?1,'%') ")
    Page<ThongSoChiTiet> findByThongSoKiThuat( String tenThongSoKiThuat, Pageable pageable);

    @Query(value = "from ThongSoChiTiet t " +
            "where t.xoa = false " +
            "and t.ten like concat('%',?1,'%') " +
            "order by t.id ")
    Page<ThongSoChiTiet> search(String tenThongSoChiTiet, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "update ThongSoChiTiet t set t.xoa = true where t.id = ?1")
    int delete(Integer id);

}