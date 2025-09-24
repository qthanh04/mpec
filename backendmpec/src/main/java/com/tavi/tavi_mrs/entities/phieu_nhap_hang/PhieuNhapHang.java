package com.tavi.tavi_mrs.entities.phieu_nhap_hang;

import com.tavi.tavi_mrs.entities.khach_hang.KhachHangDto;
import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDung;
import com.tavi.tavi_mrs.entities.nha_cung_cap.NhaCungCap;
import com.tavi.tavi_mrs.entities.nha_cung_cap.NhaCungCapDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
@NamedNativeQueries(
        {
                @NamedNativeQuery(name = "PhieuNhapHang.topNhaCungCapTheoThang",
                        query = "select pnh.nha_cung_cap_id as id, ncc.ten as ten, " +
                                " sum(pnh.tong_tien) as tongTien, sum(pnh.con_no) as conNo " +
                                " from phieu_nhap_hang pnh " +
                                " join nha_cung_cap ncc " +
                                "  on pnh.xoa = false and date(pnh.thoi_gian) >= ?1 and date(pnh.thoi_gian) <= ?2 " +
                                "  and ncc.id = pnh.nha_cung_cap_id " +
                                " group by id order by tongTien desc",
                        resultSetMapping = "NhaCungCapMapping"),
                @NamedNativeQuery(name = "PhieuNhapHang.searchTopNhaCungCapTheoThang",
                        query = "select pnh.nha_cung_cap_id as id, ncc.ten as ten, " +
                                " sum(pnh.tong_tien) as tongTien, sum(pnh.con_no) as conNo " +
                                " from phieu_nhap_hang pnh " +
                                " join nha_cung_cap ncc " +
                                " on pnh.xoa = false and date(pnh.thoi_gian) >= ?1 and date(pnh.thoi_gian) <= ?2 " +
                                " and (ncc.ten is not null and upper(ncc.ten) like concat('%', upper(?3) ,'%')) " +
                                " and ncc.id = pnh.nha_cung_cap_id " +
                                " group by id order by tongTien desc",
                        resultSetMapping = "NhaCungCapMapping"),
        })
@SqlResultSetMapping(
        name = "NhaCungCapMapping",
        classes = {@ConstructorResult(
                targetClass = NhaCungCapDto.class,
                columns = { @ColumnResult(name = "id"), @ColumnResult(name = "ten"),
                        @ColumnResult(name = "tongTien"), @ColumnResult(name = "conNo")})}
)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Phieu_Nhap_Hang", schema = "dbo")
public class PhieuNhapHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ma_phieu")
    private String maPhieu;

    @Column(name = "thoi_gian")
    private Date thoiGian;

    @NotNull
    @Column(name = "tong_tien")
    private Double tongTien;

    @NotNull
    @Column(name = "tien_da_tra")
    private Double tienDaTra;

    @Column(name = "tien_phai_tra")
    private Double tienPhaiTra;

    @Column(name = "con_no")
    private Double conNo;

    @Column(name = "giam_gia")
    private Double giamGia;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @ManyToOne
    @JoinColumn(name = "nha_cung_cap_id")
    private NhaCungCap nhaCungCap;

    @ManyToOne
    @JoinColumn(name = "nguoi_dung_id")
    private NguoiDung nguoiDung;

    @Column(name = "xoa")
    private Boolean xoa;

}
