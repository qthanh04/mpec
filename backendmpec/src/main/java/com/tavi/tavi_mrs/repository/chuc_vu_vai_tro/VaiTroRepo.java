package com.tavi.tavi_mrs.repository.chuc_vu_vai_tro;

import com.tavi.tavi_mrs.entities.chuc_vu_vai_tro.VaiTro;
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
public interface VaiTroRepo extends JpaRepository<VaiTro, Integer> {

    @Query("from VaiTro v " +
            "order by v.id")
    List<VaiTro> findAll();

    @Query(value = "from VaiTro v where v.xoa = ?2 and v.id = ?1")
    Optional<VaiTro> findByIdAndXoa(int id, boolean xoa);

    @Modifying
    @Transactional
    @Query(value = "update VaiTro vt set vt.xoa = true where vt.id = ?1")
    int deleted(Integer id);

    @Query("from VaiTro vt where vt.xoa = false " +
            "order by vt.id")
    Page<VaiTro> findAllToPage(Pageable pageable);

    @Query(value = "from VaiTro v " +
            "where v.xoa = false " +
            "and upper(v.tenVaiTro) like concat('%', ?1, '%') " +
            "order by v.id")
    Optional<VaiTro> findByTenVaiTroCheck(String tenVaiTro);

    @Query(value = "from VaiTro  vt " +
            "where vt.xoa = false " +
            "and upper(vt.tenVaiTro) like concat('%',upper(?1), '%') " +
            "order by vt.id")
    Page<VaiTro> findByTenVaiTro(String tenVaiTro, Pageable pageable);
}
