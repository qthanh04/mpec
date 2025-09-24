package com.tavi.tavi_mrs.repository.hang_hoa;

import com.tavi.tavi_mrs.entities.hang_hoa.HangHoaThongSo;
import com.tavi.tavi_mrs.entities.hang_hoa.NhomHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface HangHoaThongSoRepo extends JpaRepository<HangHoaThongSo, Integer> {

    @Query(value = "from HangHoaThongSo h where h.xoa = false and h.id = ?1")
    Optional<HangHoaThongSo> findById(int id, boolean xoa);

    @Query(value = "from HangHoaThongSo h where h.xoa = false ")
    Page<HangHoaThongSo> findAllToPage(Pageable pageable);

    @Query(value = "from HangHoaThongSo h " +
            "where h.xoa = false " +
            "and (?1 is null or h.thongSoKiThuat.ten like concat('%',?1,'%')) " +
            "and (?2 is null or h.thongSoChiTiet.ten like concat('%',?2,'%')) " +
            "and (?3 is null or h.hangHoa.tenHangHoa like concat('%',?3,'%')) " +
            "order by h.id asc ")
    Page<HangHoaThongSo> search(String tenTSKT, String tenTSCT, String tenHangHoa, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "update HangHoaThongSo h set h.xoa = true where h.id = ?1")
    int delete(Integer id);
}
