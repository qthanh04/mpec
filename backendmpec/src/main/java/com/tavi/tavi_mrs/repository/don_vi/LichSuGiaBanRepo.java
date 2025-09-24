package com.tavi.tavi_mrs.repository.don_vi;

import com.tavi.tavi_mrs.entities.don_vi.LichSuGiaBan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Repository
public interface LichSuGiaBanRepo extends JpaRepository<LichSuGiaBan, Integer> {

    @Query(value = "from LichSuGiaBan gb  " +
            "where " +
            "gb.thoiGianKetThuc is null ")
    Page<LichSuGiaBan> findAll(Pageable pageable);

    Optional<LichSuGiaBan> findById(int id);

    @Query(value = "from LichSuGiaBan gb  " +
            "where " +
            "gb.donViHangHoa.id = ?1 ")
    Page<LichSuGiaBan> findByDonViHangHoaId(int donViHangHoaId, Pageable pageable);

    @Query(value = "from LichSuGiaBan gb " +
            "where " +
            "gb.donViHangHoa.id = ?1 " +
            "and " +
            "(?2 >= gb.thoiGianBatDau) and (?2 <= gb.thoiGianKetThuc or gb.thoiGianKetThuc is null)")
    Optional<LichSuGiaBan> findByDonViHangHoaIdAndThoiGian(int donViHangHoaId, Date thoiGian);

    @Query(value = "from LichSuGiaBan gb " +
            "where " +
            "gb.donViHangHoa.id = ?1 " +
            "and (gb.thoiGianKetThuc is null )")
    Optional<LichSuGiaBan> findByDonViHangHoaAndThoiGianHienTai(int donViHangHoaId);

    @Query(value = "from LichSuGiaBan gb " +
            "where " +
            "( gb.donViHangHoa.hangHoa.nhomHang.tenNhomHang like concat('%', ?1, '%') " +
            "or gb.donViHangHoa.hangHoa.tenHangHoa like concat('%', ?1, '%') " +
            "or gb.donViHangHoa.donVi.tenDonVi like concat('%', ?1, '%') " +
            ") " +
            "and gb.thoiGianKetThuc is null " +
            "order by gb.donViHangHoa.hangHoa.tenHangHoa")
    Page<LichSuGiaBan> search(String text, Pageable pageable);

    @Query(nativeQuery = true, value = "select lich_su_gia_ban.id, lich_su_gia_ban.don_vi_hang_hoa_id,lich_su_gia_ban.gia_ban,lich_su_gia_ban.thoi_gian_bat_dau,lich_su_gia_ban.thoi_gian_ket_thuc from lich_su_gia_ban " +
            "inner join don_vi_hang_hoa on lich_su_gia_ban.don_vi_hang_hoa_id = don_vi_hang_hoa.id and don_vi_hang_hoa.hang_hoa_id = :id limit 1")
    Optional<LichSuGiaBan> findDistinctGiaBan(@Param("id") int hangHoaId);
}
