package com.tavi.tavi_mrs.entities.so_quy;

import com.tavi.tavi_mrs.entities.hoa_don.HoaDon;
import com.tavi.tavi_mrs.entities.phieu_nhap_hang.PhieuNhapHang;
import com.tavi.tavi_mrs.entities.phieu_tra_hang_nhap.PhieuTraHangNhap;
import com.tavi.tavi_mrs.entities.phieu_tra_khach.PhieuTraKhach;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Phieu_Chi_Nhap_Hang_Tra_Khach", schema = "dbo")
public class PhieuChiNhapHangTraKhach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @JoinColumn(name = "phieu_chi_id")
    @ManyToOne
    private PhieuChi phieuChi;

    @ManyToOne
    @JoinColumn(name = "phieu_tra_khach_id")
    private PhieuTraKhach phieuTraKhach;

    @ManyToOne
    @JoinColumn(name = "phieu_nhap_hang_id")
    private PhieuNhapHang phieuNhapHang;

    @Column(name = "tien_da_tra")
    private Float tienDaTra;
}
