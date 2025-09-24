package com.tavi.tavi_mrs.entities.so_quy;

import com.tavi.tavi_mrs.entities.hoa_don.HoaDon;
import com.tavi.tavi_mrs.entities.phieu_tra_hang_nhap.PhieuTraHangNhap;
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
@Table(name = "Phieu_Thu_Hoa_Don_Tra_Hang_Nhap", schema = "dbo")
public class PhieuThuHoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @JoinColumn(name = "phieu_thu_id")
    @ManyToOne
    private PhieuThu phieuThu;

    @ManyToOne
    @JoinColumn(name = "hoa_don_id")
    private HoaDon hoaDon;

    @ManyToOne
    @JoinColumn(name = "phieu_tra_hang_nhap_id")
    private PhieuTraHangNhap phieuTraHangNhap;

    @Column(name = "tien_da_tra")
    private Float tienDaTra;
}
