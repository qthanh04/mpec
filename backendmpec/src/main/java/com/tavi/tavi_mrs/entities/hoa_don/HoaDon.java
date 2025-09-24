package com.tavi.tavi_mrs.entities.hoa_don;

import com.tavi.tavi_mrs.entities.bieu_do.BieuDo;
import com.tavi.tavi_mrs.entities.chi_nhanh.ChiNhanh;
import com.tavi.tavi_mrs.entities.bao_cao.ThongKeDto;
import com.tavi.tavi_mrs.entities.khach_hang.KhachHang;
import com.tavi.tavi_mrs.entities.khach_hang.KhachHangDto;
import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDung;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@NamedNativeQueries(
    {
            @NamedNativeQuery(name = "HoaDon.bieuDoDoanhThuTong",
                query = "select date(hd.thoi_gian) x, sum(hd.tong_tien) y " +
                        " from hoa_don hd where hd.xoa = ?3" +
                        " group by x " +
                        " having x >= ?1 and x <= ?2 " +
                        " order by x ",
                resultSetMapping = "DoanhThuMapping"),
            @NamedNativeQuery(name = "HoaDon.bieuDoDoanhThuTrongThang",
                    query = "select date(hd.thoi_gian) x, sum(hd.tong_tien) y " +
                            " from hoa_don hd where hd.xoa = ?3" +
                            " group by x " +
                            " having month(x) = ?1 && year(x) = ?2" +
                            " order by x ",
                    resultSetMapping = "DoanhThuMapping"),
            @NamedNativeQuery(name = "HoaDon.bieuDoDoanhThuByNV",
                query = "select date(hd.thoi_gian) x, sum(hd.tong_tien) y " +
                        " from hoa_don hd where hd.xoa = ?4 and hd.nguoi_dung_id = ?3" +
                        " group by x " +
                        " having x >= ?1 and x <= ?2 " +
                        " order by x ",
                resultSetMapping = "DoanhThuMapping"),
            @NamedNativeQuery(name = "HoaDon.bieuDoDoanhThuTrongNam",
                    query = "select month(hd.thoi_gian) x, " +
                            " sum(hd.tong_tien) y, " +
                            " hd.thoi_gian tg " +
                            " from hoa_don hd where hd.xoa = ?2 " +
                            " group by x" +
                            " having year(tg) = ?1" +
                            " order by x;" ,
//                    query = "select sum(hd.tong_tien) y, month(hd.thoi_gian) x " +
//                            " from doan.hoa_don hd where hd.xoa = false  and hd.id in " +
//                            " (select hd.id from doan.hoa_don hd where year(hd.thoi_gian) = ?1) " +
//                            " group by x;",
                    resultSetMapping = "DoanhThuMapping"),
            @NamedNativeQuery(name = "HoaDon.bieuDoDoanhThuTrongTuan",
                    query = "select sum(hd.tong_tien) y, dayname(hd.thoi_gian) x," +
                            " hd.thoi_gian tg " +
                            " from hoa_don hd " +
                            " where hd.xoa = ?3 " +
                            " group by day(tg) " +
                            " having week(tg) = ?1 and year(tg) = ?2 " +
                            " order by tg",
                    resultSetMapping = "DoanhThuMapping"),
            @NamedNativeQuery(name = "HoaDon.bieuDoDoanhThuGioTrongNgay",
                    query = "select hour(hd.thoi_gian) x, " +
                            "sum(hd.tong_tien) y " +
                            " from hoa_don hd where hd.xoa = ?2 " +
                            " group by x " +
                            " having date(x) = ?1 " +
                            " order by x;",
                    resultSetMapping = "DoanhThuMapping"),
            @NamedNativeQuery(name = "HoaDon.bieuDoDoanhThuGioTrongTuan",
                    query = "select hour(hd.thoi_gian) x, " +
                            "sum(hd.tong_tien) y " +
                            " from hoa_don hd where hd.xoa = ?3 " +
                            " group by x " +
                            " having week(x) = ?1 and year(x) = ?2" +
                            " order by x;" ,
                    resultSetMapping = "DoanhThuMapping"),
            @NamedNativeQuery(name = "HoaDon.bieuDoDoanhThuGioTrongThang",
                    query = "select hour(hd.thoi_gian) x, " +
                            "sum(hd.tong_tien) y, hd.thoi_gian tg " +
                            " from hoa_don hd where hd.xoa = ?3 " +
                            " group by x " +
                            " having month(tg) = ?1 and year(tg) = ?2 " +
                            " order by x;" ,
                    resultSetMapping = "DoanhThuMapping"),
            @NamedNativeQuery(name = "HoaDon.tongDoanhThuNgay",
                    query = "select sum(hd.tong_tien) y, date(hd.thoi_gian) x " +
                            "from hoa_don hd " +
                            "where hd.xoa = ?2 " +
                            "group by x " +
                            "having x = ?1",
                    resultSetMapping = "DoanhThuMapping"),
            @NamedNativeQuery(name = "HoaDon.tongDoanhThuThang",
                    query = "select sum(hd.tong_tien) y, month(hd.thoi_gian) x " +
                            "from hoa_don hd " +
                            "where hd.xoa = ?2 " +
                            "group by x " +
                            "having x = ?1",
                    resultSetMapping = "DoanhThuMapping"),
            @NamedNativeQuery(name = "HoaDon.tongDoanhThuNam",
                    query = "select sum(hd.tong_tien) y, year(hd.thoi_gian) x " +
                            "from hoa_don hd " +
                            "where hd.xoa = ?2 " +
                            "group by x " +
                            "having x = ?1",
                    resultSetMapping = "DoanhThuMapping"),
            @NamedNativeQuery(name = "HoaDon.topDoanhThuNhanVienTheoThang",
                    query = "select hd.nguoi_dung_id as id, nd.ho_va_ten as ten, " +
                            " nd.tai_khoan as ma, sum(hd.tong_tien) as tongDoanhThu" +
                            " from hoa_don hd " +
                            " join nguoi_dung nd " +
                            "  on hd.xoa = false and month(hd.thoi_gian) = ?1 and year(hd.thoi_gian) = ?2 " +
                            " and hd.nguoi_dung_id = nd.id " +
                            " group by id order by tongDoanhThu desc ",
                    resultSetMapping = "ThongKeMapping"),
            @NamedNativeQuery(name = "HoaDon.bottomDoanhThuNhanVienTheoThang",
                    query = "select hd.nguoi_dung_id as id, nd.ho_va_ten as ten, " +
                            " nd.tai_khoan as ma, sum(hd.tong_tien) as tongDoanhThu " +
                            " from hoa_don hd " +
                            " join nguoi_dung nd" +
                            "  on hd.xoa = false and month(hd.thoi_gian) = ?1 and year(hd.thoi_gian) = ?2 " +
                            " and hd.nguoi_dung_id = nd.id " +
                            " group by id order by tongDoanhThu asc ",
                    resultSetMapping = "ThongKeMapping"),
            @NamedNativeQuery(name = "HoaDon.topDoanhThuChiNhanhTheoThang",
                    query = "select hd.chi_nhanh_id as id, cn.dia_chi as ten, " +
                            " cn.ma_chi_nhanh as ma, sum(hd.tong_tien) as tongDoanhThu " +
                            " from hoa_don hd " +
                            " join chi_nhanh cn " +
                            " on hd.xoa = false and month(hd.thoi_gian) = ?1 and year(hd.thoi_gian) = ?2 " +
                            " and cn.id = hd.chi_nhanh_id " +
                            " group by id order by tongDoanhThu desc ",
                    resultSetMapping = "ThongKeMapping"),
            @NamedNativeQuery(name = "HoaDon.bottomDoanhThuChiNhanhTheoThang",
                    query = "select hd.chi_nhanh_id as id, cn.dia_chi as ten, " +
                            " cn.ma_chi_nhanh as ma, sum(hd.tong_tien) as tongDoanhThu " +
                            " from hoa_don hd " +
                            " join chi_nhanh cn " +
                            "  on hd.xoa = false and month(hd.thoi_gian) = ?1 and year(hd.thoi_gian) = ?2 " +
                            "  and cn.id = hd.chi_nhanh_id " +
                            " group by id order by tongDoanhThu asc ",
                    resultSetMapping = "ThongKeMapping"),
            @NamedNativeQuery(name = "HoaDon.topKhachHangTheoThang",
                    query = "select hd.khach_hang_id as id, kh.ten_khach_hang as ten, " +
                            " kh.tai_khoan as ma, sum(hd.tong_tien) as tongTienMua, sum(hd.con_no) as conNo " +
                            " from hoa_don hd " +
                            " join khach_hang kh " +
                            "  on hd.xoa = false " +
                            " and date(hd.thoi_gian) >= ?1 and date(hd.thoi_gian) <= ?2 " +
                            "  and kh.id = hd.khach_hang_id " +
                            " group by id order by tongTienMua desc ",
                    resultSetMapping = "KhachHangMapping"),
            @NamedNativeQuery(name = "HoaDon.searchTopKhachHangTheoThang",
                    query = "select hd.khach_hang_id as id, kh.ten_khach_hang as ten, " +
                            " kh.tai_khoan as ma, sum(hd.tong_tien) as tongTienMua, sum(hd.con_no) as conNo " +
                            " from hoa_don hd " +
                            " join khach_hang kh " +
                            "  on hd.xoa = false and date(hd.thoi_gian) >= ?1 and date(hd.thoi_gian) <= ?2 " +
                            " and (kh.ten_khach_hang is not null and upper(kh.ten_khach_hang) like concat('%', upper(?3) ,'%')) " +
                            "  and kh.id = hd.khach_hang_id " +
                            " group by id order by tongTienMua desc ",
                    resultSetMapping = "KhachHangMapping")
    }
)
@SqlResultSetMapping(
        name = "KhachHangMapping",
        classes = {@ConstructorResult(
                targetClass = KhachHangDto.class,
                columns = { @ColumnResult(name = "id"), @ColumnResult(name = "ma"),
                        @ColumnResult(name = "ten"), @ColumnResult(name = "tongTienMua"),
                        @ColumnResult(name = "conNo")})}
)

@SqlResultSetMapping(
        name = "ThongKeMapping",
        classes = {@ConstructorResult(
                targetClass = ThongKeDto.class,
                columns = { @ColumnResult(name = "id"), @ColumnResult(name = "ma"),
                        @ColumnResult(name = "ten"), @ColumnResult(name = "tongDoanhThu")})}
)

@SqlResultSetMapping(
        name = "DoanhThuMapping",
        classes = {@ConstructorResult(
                targetClass = BieuDo.class,
                columns = { @ColumnResult(name = "x"), @ColumnResult(name = "y") })}
)



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Hoa_Don")
public class HoaDon  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "thoi_gian")
    @Temporal(TemporalType.TIMESTAMP)
    private Date thoiGian;

    @NotNull
    @Column(name = "ma")
    private String ma;

    @NotNull
    @Column(name = "tong_tien")
    private Float tongTien;

    @NotNull
    @Column(name = "tien_khach_tra")
    private Float tienKhachTra;

    @Column(name = "tien_tra_lai_khach")
    private Float tienTraLaiKhach;

    @Column(name = "con_no")
    private Float conNo;

    @Column(name = "giam_gia")
    private Float giamGia;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @ManyToOne
    @JoinColumn(name = "khach_hang_id")
    private KhachHang khachHang;
    
    @ManyToOne
    @JoinColumn(name = "nguoi_dung_id")
    private NguoiDung nguoiDung;

    @ManyToOne
    @JoinColumn(name = "chi_nhanh_id")
    private ChiNhanh chiNhanh;

    @Column(name = "xoa")
    private Boolean xoa;
}
