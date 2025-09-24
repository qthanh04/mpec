
package com.tavi.tavi_mrs.entities.khach_hang;

import com.tavi.tavi_mrs.entities.bieu_do.BieuDo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


//@NamedNativeQueries(
//        {
//                @NamedNativeQuery(name = "KhachHang.tongKhachHang",
//
//                )
//        }
//)
//
//@SqlResultSetMapping(
//        name = "KhachHangMapping",
//        classes = {@ConstructorResult(
//                targetClass = BieuDo.class,
//                columns = { @ColumnResult(name = "x"), @ColumnResult(name = "y") })}
//)


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Khach_Hang", schema = "dbo")
public class KhachHang implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "tai_khoan")
    private String taiKhoan;

    @Column(name = "ten_khach_hang")
    private String tenKhachHang;

    @Column(name = "dien_thoai")
    private String dienThoai;

    @Column(name = "thoi_gian_tao")
    private Date thoiGian;

    @Column(name = "loai_khach")
    private String loaiKhach;

    @Column(name = "ngay_sinh")
    private String ngaySinh;

    @Column(name = "gioi_tinh")
    private Boolean gioiTinh;

    @Column(name = "email")
    private String email;

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "xoa")
    private Boolean xoa;



}


