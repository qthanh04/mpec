package com.tavi.tavi_mrs.entities.chi_nhanh;

import com.tavi.tavi_mrs.entities.cong_ty.TongCongTy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Chi_Nhanh")
public class ChiNhanh implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ma_chi_nhanh")
    private String maChiNhanh;

    @ManyToOne
    @JoinColumn(name = "cong_ty_id")
    private TongCongTy tongCongTy;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "dia_chi_ip")
    private String diaChiIp;

    @Column(name = "trang_thai_ket_noi")
    private int trangThaiKetNoi;

    @Column(name = "trang_thai_hoat_dong")
    private int  trangThaiHoatDong;

    @Column(name = "xoa")
    private Boolean xoa;

}
