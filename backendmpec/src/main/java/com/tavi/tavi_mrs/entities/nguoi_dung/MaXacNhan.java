package com.tavi.tavi_mrs.entities.nguoi_dung;

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
@Table(name = "ma_xac_nhan", schema = "dbo")
public class MaXacNhan implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "nguoi_dung_id")
    @ManyToOne
    private NguoiDung nguoiDung;

    @Column(name = "ma_token")
    private String maToken;

    @Column(name = "thoi_gian_bat_dau")
    private Date thoiGianBatDau;

    @Column(name = "thoi_gian_ket_thuc")
    private Date thoiGianKetThuc;

    @Column(name = "trang_thai")
    private Integer trangThai;
}