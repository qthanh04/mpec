package com.tavi.tavi_mrs.repository.chuc_vu_vai_tro;

import com.tavi.tavi_mrs.entities.chuc_vu_vai_tro.ChucVu;
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
public interface ChucVuRepo extends JpaRepository<ChucVu, Integer> {

    @Query(value = "from ChucVu c where c.xoa = false" +
            " order by c.id")
    List<ChucVu> findAll();

    @Query(value = "from ChucVu c where c.xoa = false" +
            " order by c.id")
    Page<ChucVu> findAllToPage(Pageable pageable);

    Optional<ChucVu> findByIdAndXoa(int id, boolean xoa);

    @Query(value = "from ChucVu c " +
            "where c.xoa = false " +
            "and upper(c.tenChucVu) like concat('%', ?1, '%') " +
            "order by c.id")
    Optional<ChucVu> findByChucVuCheck(String tenChucVu);

    @Query(value = "from ChucVu  cv " +

            "where cv.xoa = false " +
            "and upper(cv.tenChucVu) like concat('%', upper(?1), '%')" +

            "order by cv.id")
    Page<ChucVu> findByTenChucVu(String tenChucVu, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "update ChucVu cv set cv.xoa = true where cv.id = ?1")
    int deleted(Integer id);
}
