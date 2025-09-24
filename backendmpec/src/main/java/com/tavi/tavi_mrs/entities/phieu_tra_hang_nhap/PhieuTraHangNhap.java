package com.tavi.tavi_mrs.entities.phieu_tra_hang_nhap;

import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDung;
import com.tavi.tavi_mrs.entities.nha_cung_cap.NhaCungCap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Phieu_Tra_Hang_Nhap", schema = "dbo")
public class PhieuTraHangNhap implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ma_phieu")
    private String maPhieu;

    @ManyToOne
    @JoinColumn(name = "nguoi_dung_id")
    private NguoiDung nguoiDung;

    @ManyToOne
    @JoinColumn(name = "nha_cung_cap_id")
    private NhaCungCap nhaCungCap;

    @Column(name = "thoi_gian")
    @Temporal(TemporalType.TIMESTAMP)
    private Date thoiGian;

    @Column(name = "tong_tien")
    private Double tongTien;

    @Column(name = "tien_phai_tra")
    private Double tienPhaiTra;

    @Column(name = "tien_da_tra")
    private Double tienDaTra;

    @Column(name = "con_no")
    private Double conNo;

    @Column(name = "giam_gia")
    private Double giamGia;

    @Column(name = "ly_do")
    private String lyDo;

    @Column(name = "trang_thai")
    private  Integer trangThai;

    @Column(name = "xoa")
    private Boolean xoa;
}
