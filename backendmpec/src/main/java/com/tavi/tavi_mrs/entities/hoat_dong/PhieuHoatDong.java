package com.tavi.tavi_mrs.entities.hoat_dong;

import com.tavi.tavi_mrs.entities.hoa_don.HoaDon;
import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDung;
import com.tavi.tavi_mrs.entities.phieu_nhap_hang.PhieuNhapHang;
import com.tavi.tavi_mrs.entities.phieu_tra_hang_nhap.PhieuTraHangNhap;
import com.tavi.tavi_mrs.entities.phieu_tra_khach.PhieuTraKhach;
import com.tavi.tavi_mrs.entities.so_quy.PhieuChi;
import com.tavi.tavi_mrs.entities.so_quy.PhieuThu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Phieu_Hoat_Dong", schema = "dbo")
public class PhieuHoatDong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "nguoi_dung_id")
    private NguoiDung nguoiDung;

    @ManyToOne
    @JoinColumn(name = "hoa_don_id")
    private HoaDon hoaDon;

    @ManyToOne
    @JoinColumn(name = "phieu_tra_khach_id")
    private PhieuTraKhach phieuTraKhach;

    @ManyToOne
    @JoinColumn(name = "phieu_nhap_hang_id")
    private PhieuNhapHang phieuNhapHang;

    @ManyToOne
    @JoinColumn(name = "phieu_tra_hang_nhap_id")
    private PhieuTraHangNhap phieuTraHangNhap;

    @ManyToOne
    @JoinColumn(name = "phieu_thu_id")
    private PhieuThu phieuThu;

    @ManyToOne
    @JoinColumn(name = "phieu_chi_id")
    private PhieuChi phieuChi;

    @ManyToOne
    @JoinColumn(name = "hoat_dong_id")
    private HoatDong hoatDong;

    @Column(name = "gia_tri")
    private Double giaTri;

    @NotNull
    @Column(name = "thoi_gian")
    @Temporal(TemporalType.TIMESTAMP)
    private Date thoiGian;

    @Column(name = "link")
    private String link;
}
