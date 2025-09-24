package com.tavi.tavi_mrs.repository.hoa_don;

import com.tavi.tavi_mrs.entities.bieu_do.BieuDo;
import com.tavi.tavi_mrs.entities.bao_cao.ThongKeDto;
import com.tavi.tavi_mrs.entities.hoa_don.HoaDon;
import com.tavi.tavi_mrs.entities.khach_hang.KhachHangDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface HoaDonRepo extends JpaRepository<HoaDon, Integer> {

    @Query("from HoaDon h where h.xoa = false and h.id = ?1 ")
    Optional<HoaDon> findById(int id, boolean xoa);

    @Query("from HoaDon h where h.xoa = false and h.id = ?1 ")
    HoaDon findByIdAndXoa(int id, Boolean xoa);

    @Query("from HoaDon h where h.xoa = false and h.khachHang.id = ?1 and h.tongTien > h.tienKhachTra order by h.thoiGian desc ")
    Page<HoaDon> findByIdKhachHang(int id, Boolean xoa,Pageable pageable);

    @Query("from HoaDon h where h.xoa = false and h.khachHang.id = ?1 order by h.thoiGian desc ")
    Page<HoaDon> findByIdKhachHangForAll(int id, boolean xoa, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update HoaDon h set h.tienKhachTra=?1 , h.conNo = ?3 where h.id = ?2 ")
    int updateTienKhachTra(Float tienKhachTra, int hoaDonId, float conNo);

    @Query(value = " select h from HoaDon h where h.xoa = false order by h.thoiGian desc ")
    Page<HoaDon> findAllToPage(Pageable pageable);

    @Query(value = "select h from HoaDon h where h.xoa = false order by h.id")
    List<HoaDon> findAll();

    @Query(value = "select h from HoaDon h where h.xoa = ?2 and h.ma = ?1 ")
    Optional<HoaDon> findByMa(String ma, boolean xoa);

    @Query(value = "from HoaDon h" +
            " where h.xoa = false and (0 = ?1 or h.chiNhanh.id = ?1) " +
            "and (?2 is null or h.ma like concat('%', ?2, '%')) " +
            "and (?3 is null or h.khachHang.tenKhachHang  like concat('%', ?3, '%')) " +
            "and (?4 is null or h.nguoiDung.hoVaTen like concat('%', ?4, '%')) " +
            "and (?5 is null or h.thoiGian >= ?5 ) and (?6 is null or h.thoiGian <= ?6) " +
            "and (?7 = -1 or h.trangThai = ?7 ) " +
            "order by h.thoiGian desc ")
    Page<HoaDon> findByMaHoaDonAndThoiGianAndTrangThai(Integer chinhanhId, String maHoaDon, String tenKhachHang, String tenNhanVien, Date ngayDau, Date ngayCuoi, int trangThai, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update HoaDon h set h.trangThai = ?2 where h.id = ?1")
    int setTrangThai(int id, int trangThai);


    @Query(value = "select h from HoaDon h " +
            "where h.xoa = false " +
            "and (0 = ?1 or h.chiNhanh.id = ?1) " +
            "and ( " +
            "(h.khachHang.tenKhachHang is not null and upper(h.khachHang.tenKhachHang) like concat('%', upper(?2) ,'%')) " +
            "or (h.nguoiDung.hoVaTen is not null and upper(h.nguoiDung.hoVaTen) like concat('%', upper(?2) ,'%') )" +
            "or (h.ma is not null and upper(h.ma) like concat('%', upper(?2) ,'%') )" +
            ") order by  h.thoiGian desc ")
    Page<HoaDon> findByChiNhanhAndText(int chiNhanhId, String text, Pageable pageable);

    @Query(nativeQuery = true, value = "select count(*) from hoa_don hd " +
            "where hd.thoi_gian >= ?1 and hd.thoi_gian <= ?2 " +
            " and hd.xoa = false;")
    Integer countBillByTime(Date start, Date end);

    @Query(nativeQuery = true, value = "select sum(hd.tong_tien) from hoa_don hd " +
            "where hd.xoa = false and hd.thoi_gian >= ?1 and hd.thoi_gian <= ?2 ")
    Integer sumBillByTime(Date start, Date end);


    @Query(nativeQuery = true)
    List<BieuDo> bieuDoDoanhThuTong(Date ngayDau, Date ngayCuoi, boolean xoa);

    @Query(nativeQuery = true)
    List<BieuDo> bieuDoDoanhThuTrongNam(int year, boolean xoa);

    @Query(nativeQuery = true)
    List<BieuDo> bieuDoDoanhThuTrongThang(int month, int year, boolean xoa);

    @Query(nativeQuery = true)
    List<BieuDo> bieuDoDoanhThuGioTrongThang(int month, int year, boolean xoa);

    @Query(nativeQuery = true)
    List<BieuDo> bieuDoDoanhThuByNV(Date ngayDau, Date ngayCuoi, int nguoiDungId, boolean xoa);

    @Query(nativeQuery = true)
    List<BieuDo> bieuDoDoanhThuTrongTuan(int week, int year, boolean xoa);

    @Query(value = "select * from hoa_don h where h.xoa = false order by h.thoi_gian DESC Limit 0,5", nativeQuery = true)
    List<HoaDon> findRecentOrder();

    @Query(value = "select h from HoaDon h where h.xoa=false " +
            "and (0 = ?1 or h.chiNhanh.id = ?1) " +
            "order by h.id desc ")
    Page<HoaDon> searchHoaDonHangChuaGiao(Integer chiNhanhId, int trangThai, Pageable pageable);

    @Query(nativeQuery = true)
    List<ThongKeDto> topDoanhThuNhanVienTheoThang(int month, int year, Pageable pageable);

    @Query(nativeQuery = true)
    List<ThongKeDto> bottomDoanhThuNhanVienTheoThang(int month, int year, Pageable pageable);

    @Query(nativeQuery = true)
    List<ThongKeDto> topDoanhThuChiNhanhTheoThang(int month, int year, Pageable pageable);

    @Query(nativeQuery = true)
    List<ThongKeDto> bottomDoanhThuChiNhanhTheoThang(int month, int year, Pageable pageable);

    @Query(nativeQuery = true)
    Page<KhachHangDto> topKhachHangTheoThang(Date ngayDau, Date ngayCuoi, Pageable pageable);

    @Query(nativeQuery = true)
    Page<KhachHangDto> searchTopKhachHangTheoThang(Date ngayDau, Date ngayCuoi,String text, Pageable pageable);



}
