package com.tavi.tavi_mrs.entities.phong_ban;

import com.tavi.tavi_mrs.entities.chi_nhanh.ChiNhanh;
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
@Table(name = "Phong_Ban", schema = "dbo")
public class PhongBan implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    @Column(name = "ten_phong_ban")
    private String tenPhongBan;

    @Column(name = "ma_phong_ban")
    private String maPhongBan;

    @Column(name = "xoa")
    private Boolean xoa;
}
