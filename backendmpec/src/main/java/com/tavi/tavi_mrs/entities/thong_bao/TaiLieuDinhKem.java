package com.tavi.tavi_mrs.entities.thong_bao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tavi.tavi_mrs.entities.bao_cao.BaoCao;
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
@Table(name = "Tai_Lieu_Dinh_Kem", schema = "dbo")
public class TaiLieuDinhKem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "duong_dan")
    private String duongDan;

    @ManyToOne
    @JoinColumn(name = "bao_cao_id")
    @JsonBackReference
    private BaoCao baoCao;

    @ManyToOne
    @JoinColumn(name = "thong_bao_id")
    private ThongBao thongBao;
}