package com.tavi.tavi_mrs.entities.don_vi;

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
@Table(name = "Lich_Su_Gia_Ban")
public class LichSuGiaBan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "gia_ban")
    private Float giaBan;

    @Column(name = "thoi_gian_bat_dau")
    private Date thoiGianBatDau;

    @Column(name = "thoi_gian_ketThuc")
    private Date thoiGianKetThuc;

    @ManyToOne
    @JoinColumn(name = "don_vi_hang_hoa_id")
    private DonViHangHoa donViHangHoa;
}
