package com.tavi.tavi_mrs.repository.chi_nhanh;

import com.tavi.tavi_mrs.entities.chi_nhanh.ChiNhanh;
import com.tavi.tavi_mrs.entities.chuc_vu_vai_tro.VaiTro;
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
public interface ChiNhanhRepo extends JpaRepository<ChiNhanh, Integer> {

    @Query(value = "from ChiNhanh c where c.xoa=false " +
            "order by c.id ")
    List<ChiNhanh> findAll();

    Optional<ChiNhanh> findByIdAndXoa(int id, boolean xoa);

    @Query(value = "from ChiNhanh c where c.id = ?1 and c.xoa = false")
    Optional<ChiNhanh> findById(Integer id);

    @Query(value = "from ChiNhanh c where c.maChiNhanh = ?1 and c.xoa = false")
    ChiNhanh findByMaChiNhanhExcel(String ma);

    @Query(value = "from ChiNhanh where xoa = false and (0=?1 or tongCongTy.id = ?1)")
    List<ChiNhanh> findByTongCty(Integer idTongCty);

    @Modifying
    @Transactional
    @Query("update ChiNhanh as cn set cn.xoa=true where cn.id=?1")
    int deleted(Integer id);

    @Query("select cn from ChiNhanh as cn where (cn.xoa=?2 or ?2 is null) and cn.id=?1")
    ChiNhanh findByIdAndXoa(Integer chiNhanhId, Boolean xoa);

    @Query("select cn from ChiNhanh as cn where cn.xoa=false and (cn.tongCongTy.id=?1 or ?1 is null)")
    List<ChiNhanh> findAllByDoanhNghiep(Integer doanhNghiepId);

    @Query(value = "from ChiNhanh cn where " +
            "upper(cn.diaChi) like concat('%',?1,'%') " +
            "or upper(cn.maChiNhanh) like concat('%',?1,'%')")
    List<ChiNhanh> searchSelect(String text);


    @Query(value = "from ChiNhanh  cn " +
            "where cn.xoa = false " +
            "and upper(cn.diaChi) like concat('%',?1, '%') " +
            "or upper(cn.maChiNhanh) like concat('%',?1,'%') ")
    Page<ChiNhanh> findByDiaChi(String diaChi, Pageable pageable);

    @Query("select cn from ChiNhanh as cn where cn.xoa=false and (cn.tongCongTy.id=?1 or ?1 is null)")
    Page<ChiNhanh> findAllByDoanhNghiep1(Integer doanhNghiepId,Pageable pageable);

    @Query(value = "from ChiNhanh c where " +
            "c.xoa = false " +
            "and upper(c.diaChi) like concat('%', upper(?1), '%')" +
            " order by c.id")
    Page<ChiNhanh> search(String text, Pageable pageable);
}
