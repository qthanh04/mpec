package com.tavi.tavi_mrs.entities.ca_lam_viec;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Ca_Lam_Viec")
public class CaLamViec implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(name = "ten")
    private String ten;

    @NotNull
    @Column(name = "check_in")
    private String checkIn;

    @NotNull
    @Column(name = "check_out")
    private String checkOut;

    @Column(name = "bat_dau_cho_phep_check_in")
    private String batDauChoPhepCheckIn;

    @Column(name = "bat_dau_cho_phep_check_out")
    private String batDauChoPhepCheckOut;

    @Column(name = "ket_thuc_cho_phep_check_in")
    private String ketThucChoPhepCheckIn;

    @Column(name = "ket_thuc_cho_phep_check_out")
    private String ketThucChoPhepCheckOut;

    @NotNull
    @Column(name = "so_nhan_vien_toi_da")
    private Integer soLuongNhanVienToiDa;

    @Column(name = "status")
    private Integer status;

    @Column(name = "xoa")
    private Boolean xoa;
}
