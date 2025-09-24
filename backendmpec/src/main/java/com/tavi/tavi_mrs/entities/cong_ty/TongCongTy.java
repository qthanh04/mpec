package com.tavi.tavi_mrs.entities.cong_ty;

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
@Table(name = "Tong_Cong_Ty")
public class TongCongTy implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ma_doanh_nghiep")
    private String maDoanhNghiep;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "email")
    private String email;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "website")
    private String website;

    @Column(name = "nguoi_dai_dien")
    private String nguoiDaiDien;

    @Column(name = "ten_doanh_nghiep")
    private String tenDoanhNghiep;

    @Column(name = "ten_tieng_anh")
    private String tenTiengAnh;

    @Column(name = "logo")
    private String logo;

    @Column(name = "logo2")
    private String logo2;

    @Column(name = "xoa")
    private Boolean xoa;


}
