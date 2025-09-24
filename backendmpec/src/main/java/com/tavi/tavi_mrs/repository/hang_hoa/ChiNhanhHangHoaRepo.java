package com.tavi.tavi_mrs.repository.hang_hoa;

import com.tavi.tavi_mrs.entities.hang_hoa.ChiNhanhHangHoa;
import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDungPhongBanChucVuVaiTro;
import com.tavi.tavi_mrs.entities.so_quy.PhieuThu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ChiNhanhHangHoaRepo extends JpaRepository<ChiNhanhHangHoa, Integer> {

    @Query("select c from ChiNhanhHangHoa c where c.xoa = false and c.id = ?1  ")
    Optional<ChiNhanhHangHoa> findById(int id, boolean xoa);

    @Query(value = "from  ChiNhanhHangHoa c where c.xoa = false and c.id = ?1")
    Optional<ChiNhanhHangHoa> findByIdAndXoa(int id, boolean xoa);

    @Query(value = "from ChiNhanhHangHoa ch " +
            "where " +
            "ch.xoa = false " +
            "and ch.chiNhanh.id = ?1 " +
            "and ch.hangHoa.id = ?2 ")
    Optional<ChiNhanhHangHoa> findByChiNhanhIdAndHangHoaId(int chiNhanhId, int hangHoaId);

    @Query(value = " select c from ChiNhanhHangHoa c where c.xoa = false order by c.id")
    Page<ChiNhanhHangHoa> findAllToPage(Pageable pageable);

    @Transactional
    @Modifying
    @Query("update ChiNhanhHangHoa c set c.tonKho = ?1 where c.id = ?2")
    int setTonKho(int soLuong, int id);

    @Query(value = " select c from ChiNhanhHangHoa c where c.xoa = false and c.chiNhanh.id=?1 order by c.id")
    Page<ChiNhanhHangHoa> findByChiNhanh(int chiNhanhId, Pageable pageable);

    @Query(value = "select c from ChiNhanhHangHoa c " +
            "where c.xoa = false and c.hangHoa.xoa = false " +
            "and (0 = ?1 or c.chiNhanh.id = ?1) " +
            "and ( " +
            "(c.hangHoa.tenHangHoa is not null and upper(c.hangHoa.tenHangHoa) like concat('%', upper(?2) ,'%'))" +
            "or (c.hangHoa.maVach like concat('%', ?2, '%'))" +
            "or (c.hangHoa.ma is not null and upper(c.hangHoa.ma) like concat('%', upper(?2) ,'%') )" +
            "or (c.hangHoa.nhomHang.tenNhomHang is not null and upper(c.hangHoa.nhomHang.tenNhomHang) like concat('%', upper(?2) ,'%') )" +
            "or (c.hangHoa.thuongHieu.tenThuongHieu is not null and upper(c.hangHoa.thuongHieu.tenThuongHieu) like concat('%', upper(?2) ,'%') )" +
            ")")
    Page<ChiNhanhHangHoa> findByChiNhanhAndText(int chiNhanhId, String text, Pageable pageable);
}
