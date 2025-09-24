package com.tavi.tavi_mrs.repository.don_vi;

import com.tavi.tavi_mrs.entities.don_vi.DonVi;
import io.swagger.models.auth.In;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface DonViRepo extends JpaRepository<DonVi, Integer> {

    @Query(value = "from DonVi  d where " +
            "d.xoa = false ")
    Page<DonVi> findAll(Pageable pageable);

    @Query(value = "from DonVi d where " +
            "d.xoa = ?2 " +
            "and " +
            "d.id = ?1 ")
    Optional<DonVi> findByIdAndXoa(int id, boolean xoa);

    @Query(value = "from DonVi d where " +
            "d.xoa = false " +
            "and " +
            "d.id = ?1 ")
    DonVi findById(int id);

    @Query(value = "from DonVi dv where " +
            "dv.xoa = false " +
            "and dv.tenDonVi = ?1")
    Optional<DonVi> findByTenDonVi(String tenDonVi);

    @Query(value = "from DonVi d where " +
            "d.xoa = false " +
            "and d.tenDonVi like concat('%', ?1, '%')")
    Page<DonVi> search(String tenDonVi, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "update DonVi d set d.xoa=true where d.id = ?1")
    int deleted(int id);
}
