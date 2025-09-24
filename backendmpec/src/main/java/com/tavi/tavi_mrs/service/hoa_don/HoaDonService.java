package com.tavi.tavi_mrs.service.hoa_don;

import com.tavi.tavi_mrs.entities.bieu_do.BieuDo;
import com.tavi.tavi_mrs.entities.dto.HoaDonDto;
import com.tavi.tavi_mrs.entities.bao_cao.ThongKeDto;
import com.tavi.tavi_mrs.entities.hoa_don.HoaDon;
import com.tavi.tavi_mrs.entities.hoa_don.HoaDonChiTiet;
import com.tavi.tavi_mrs.entities.khach_hang.KhachHangDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface HoaDonService {

    List<HoaDon> findAll();

    Optional<HoaDon> findById(int id);

    HoaDon findByIdAndXoa(int id, Boolean xoa);

    Optional<HoaDon> findById(int id, boolean xoa);

    Page<HoaDon> findByIdKhachHang(int id, boolean xoa, Pageable pageable);

    Page<HoaDon> findByIdKhachHangForAll(int id, boolean xoa, Pageable pageable);

    boolean updateTienKhachTra(Float tienKhachTra, int hoaDonId,float conNo);

    Optional<HoaDon> findByMa(String ma, boolean xoa);

    Page<HoaDon> findAllToPage(Pageable pageable);

    String saveHoaDonDto(HoaDonDto hoaDonDto);

    boolean setTrangThai(int id, int trangThai);

    Page<HoaDon> findByMaHoaDonAndThoiGianAndTrangThai(Integer chiNhanhId, String maHoaDon, String tenKhachHang, String tenNhanVien, Date ngayDau, Date ngayCuoi, int trangThai, Pageable pageable);

    Page<HoaDon> findByChiNhanhAndText(int chiNhanhId, String text, Pageable pageable);

    List<HoaDon> findListHoaDon(List<Integer> listHoaDonId);

    Integer countBillByTime(Date start, Date end);

    Integer sumBillByTime(Date start, Date end);

    File createPdf(HoaDon hoaDon, List<HoaDonChiTiet> hoaDonChiTietList);

    List<BieuDo> bieuDoDoanhThuTong(Date ngayDau, Date ngayCuoi, boolean xoa);

    List<BieuDo> bieuDoDoanhThuTrongNam(int year, boolean xoa);

    List<BieuDo> bieuDoDoanhThuTrongThang(int month, int year, boolean xoa);

    List<BieuDo> bieuDoDoanhThuGioTrongThang(int month, int year, boolean xoa);

    List<BieuDo> bieuDoDoanhThuTrongTuan(int week, int year, boolean xoa);

    List<BieuDo> bieuDoDoanhThuByNV(Date ngayDau, Date ngayCuoi, int nguoiDungId, boolean xoa);

    List<HoaDon> findRecentOrder();

    Page<HoaDon> searchHoaDonHangChuaGiao(Integer chiNhanhId, int trangThai, Pageable pageable);

    List<ThongKeDto> findTopNhanVienTrongThang(int month, int year, boolean top, Pageable pageable);

    List<ThongKeDto> findTopChiNhanhTrongThang(int month, int year, boolean top, Pageable pageable);

    Page<KhachHangDto> topKhachHangTheoThang(Date ngayDau, Date ngayCuoi, Pageable pageable);

    Page<KhachHangDto> searchTopKhachHangTheoThang(Date ngayDau, Date ngayCuoi,String text, Pageable pageable);



}

