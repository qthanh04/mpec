package com.tavi.tavi_mrs.repository.hang_hoa;

import com.tavi.tavi_mrs.entities.hang_hoa.NhomHang;
import com.tavi.tavi_mrs.entities.hang_hoa.ThongSoKiThuat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface ThongSoKiThuatRepo extends JpaRepository<ThongSoKiThuat, Integer> {

    @Query(value = "from ThongSoKiThuat t where t.xoa = false and t.id = ?1 ")
    Optional<ThongSoKiThuat> findById(int id, boolean xoa);

    @Query(value = "from ThongSoKiThuat t where t.xoa = false and t.nhomHang.tenNhomHang like concat('%',?1,'%') ")
    Page<ThongSoKiThuat> findThongSoKiThuatByTenNhomHang(String tenNhomHang, Pageable pageable);

    @Query(value = "from ThongSoKiThuat t where t.xoa = false ")
    Page<ThongSoKiThuat> findAllToPage(Pageable pageable);

    @Query(value = "from ThongSoKiThuat t " +
            "where t.xoa = false " +
            "and t.ten like concat('%', ?1, '%') " +
            "order by t.id")
    Page<ThongSoKiThuat> search(String tenThongSo, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "update ThongSoKiThuat t set t.xoa = true where t.id = ?1")
    int delete(Integer id);
}
