package com.tavi.tavi_mrs.repository.hang_hoa;

import com.tavi.tavi_mrs.entities.hang_hoa.HangHoa;
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
public interface HangHoaRepo extends JpaRepository<HangHoa, Integer> {

    @Query("select h from HangHoa h where h.xoa = false and h.id = ?1")
    Optional<HangHoa> findById(int id, boolean xoa);

    @Query("select h from HangHoa h where h.xoa = false and h.ma = ?1 ")
    Optional<HangHoa> findByMaHangHoa(String ma, boolean xoa);

    @Query("select h from HangHoa h where h.xoa = ?2 and h.maVach = ?1 ")
    Optional<HangHoa> findByMaVach(String ma, boolean xoa);

    @Query(value = " select h from HangHoa h where h.xoa = false order by h.id desc ")
    Page<HangHoa> findAllToPage(Pageable pageable);

    @Query(value = "from HangHoa h " +
            "where h.xoa = false " +
            "and h.tenHangHoa like concat('%', ?1, '%') " +
            "and (0 = ?2 or h.nhomHang.id = ?2 and h.nhomHang.xoa = false )  " +
            "and (0 = ?3 or h.thuongHieu.id = ?3 and h.thuongHieu.xoa = false )  " +
            "order by h.id desc ")
    Page<HangHoa> findByTenHangHoaAndNhomHangAndThuongHieu(String tenHangHoa, int nhomHangId, int thuongHieuId, Pageable pageable);

    @Query(value = "from HangHoa h " +
            "where h.xoa = false " +
            "and h.tenHangHoa like concat('%', ?1, '%') " +
            "or (h.maVach like concat('%', ?1, '%')) " +
            "or (h.nhomHang.tenNhomHang like concat('%', ?1, '%') and h.nhomHang.xoa = false ) " +
            "or (h.thuongHieu.tenThuongHieu like concat('%', ?1, '%') and h.thuongHieu.xoa = false )  " +
            "order by h.id desc ")
    Page<HangHoa> search(String text, Pageable pageable);

    @Query(nativeQuery = true,
            value = "select * from hang_hoa h where h.ma REGEXP ?1 " +
                    " order by h.id desc limit 1")
    Optional<HangHoa> findTheLastHH(String maHanghoa);

    @Modifying
    @Transactional
    @Query(value = "update HangHoa hh set hh.xoa = true where hh.id = ?1")
    int deleted(Integer id);
}