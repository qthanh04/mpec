package com.tavi.tavi_mrs.repository.ca_lam_viec;

import com.tavi.tavi_mrs.entities.ca_lam_viec.CaLamViec;
import com.tavi.tavi_mrs.entities.chinh_sach_dieu_khoan.ChinhSach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CaLamViecRepo extends JpaRepository<CaLamViec, Integer> {

    @Query(value = "from CaLamViec clv where clv.xoa=false ")
    Page<CaLamViec> findAll(Pageable pageable);

    @Query("select c from CaLamViec c where c.xoa = false ")
    List<CaLamViec> findAll();

    Optional<CaLamViec> findByIdAndXoa(int id, boolean xoa);

    @Query(value = "from CaLamViec clv where clv.xoa = false " +
            "and clv.ten like concat('%', ?1, '%')")
    Page<CaLamViec> searchByName(String text, Pageable pageable);

    @Query(value = "update CaLamViec clv set clv.xoa = true where clv.id = ?1")
    int deleted(Integer id);


}
