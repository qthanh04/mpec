package com.tavi.tavi_mrs.entities.phieu_tra_khach;

import com.tavi.tavi_mrs.entities.hoa_don.HoaDonChiTiet;
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
@Table(name = "Phieu_Khach_Tra_Chi_Tiet", schema = "dbo")
public class PhieuTraKhachChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "tong_tien")
    private Double tongTien;

    @ManyToOne
    @JoinColumn(name = "phieu_tra_khach_id")
    private PhieuTraKhach phieuTraKhach;

    @ManyToOne
    @JoinColumn(name = "hoa_don_chi_tiet_id")
    private HoaDonChiTiet hoaDonChiTiet;

    @Column(name = "xoa")
    private Boolean xoa;

}
