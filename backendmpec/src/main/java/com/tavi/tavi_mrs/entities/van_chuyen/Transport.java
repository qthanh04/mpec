package com.tavi.tavi_mrs.entities.van_chuyen;

import com.tavi.tavi_mrs.entities.hoa_don.HoaDon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "van_chuyen")
public class Transport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "doi_tac_id")
    private DoiTac doiTac;

    @OneToOne
    @JoinColumn(name = "hoa_don_id")
    private HoaDon hoaDon;

    @Column(name = "ma_hoa_don")
    private String orderCode;

    @Column(name = "phi")
    private double fee;

    @Column(name = "thoi_gian_lay")
    private String pickTime;

    @Column(name = "thoi_gian_giao")
    private String deliverTime;
}
