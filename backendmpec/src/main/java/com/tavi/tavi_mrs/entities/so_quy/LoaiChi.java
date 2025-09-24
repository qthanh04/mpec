package com.tavi.tavi_mrs.entities.so_quy;

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
@Table(name = "Loai_Chi", schema = "dbo")
public class LoaiChi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ten_loai_chi")
    private String tenLoaiChi;

    @Column(name = "ma_loai_chi")
    private String maLoaiChi;

    @Column(name = "xoa")
    private Boolean xoa;
}
