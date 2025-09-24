package com.tavi.tavi_mrs.entities.ca_lam_viec;

import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDung;
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
@Table(name = "Nguoi_Dung_Ca_Lam_Viec")
public class NguoiDungCaLamViec implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @JoinColumn(name = "nguoi_dung_id")
    @ManyToOne
    private NguoiDung nguoiDung;

    @JoinColumn(name = "ca_lam_viec_id")
    @ManyToOne
    private CaLamViec caLamViec;

    @Column(name = "ngay_thang")
    private String ngayThang;

    @Column(name = "check_in")
    private String checkin;

    @Column(name = "check_out")
    private String checkout;

    @Column(name = "status_checkin")
    private Integer statusCheckin;

    @Column(name = "status_checkout")
    private Integer statusCheckout;

    @Column(name = "status")
    private Integer status;

    @Column(name = "xoa")
    private Boolean xoa;
}
