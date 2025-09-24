package com.tavi.tavi_mrs.repository.nha_cung_cap;

import com.tavi.tavi_mrs.entities.nha_cung_cap.NhaCungCap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface NhaCungCapRepo extends JpaRepository<NhaCungCap, Integer> {

    @Query(value = "from NhaCungCap  ncc " +
            "where ncc.xoa = false order by ncc.id")
    Page<NhaCungCap> findAllToPage(Pageable pageable);

    Optional<NhaCungCap> findByIdAndXoa(int id, boolean xoa);

    @Modifying
    @Transactional
    @Query(value = "update NhaCungCap ncc set ncc.xoa = true where ncc.id = ?1")
    int deleted(Integer id);

    @Query(value = "from NhaCungCap  ncc " +

            "where ncc.xoa = false " +
            "and ncc.ten like concat('%', ?1, '%') " +

            "and ncc.diaChi like concat('%', ?2, '%') " +
            "and ncc.email like concat('%', ?3, '%') " +
            "and ncc.dienThoai like concat('%', ?4, '%') " +
            "and ncc.facebook like concat('%', ?5, '%') " +
            "order by ncc.id desc")
    Page<NhaCungCap> findByTenAndDiaChiAndDienThoaiAndEmailAndFacebook(String ten, String diaChi, String email,
                                                                       String dienThoai, String facebook,
                                                                       Pageable pageable);

    @Query(value = "from NhaCungCap  ncc " +
            " where ncc.xoa = false " +
            "and ncc.ten like concat('%', ?1, '%') " +
            " or ncc.diaChi like concat('%', ?1, '%') " +
            " or ncc.email like concat('%', ?1, '%') " +
            " or ncc.facebook like concat('%', ?1, '%') " +
            " or ncc.dienThoai like concat('%', ?1, '%') " +
            " order by ncc.id desc")
    Page<NhaCungCap> search(String text, Pageable pageable);
}
