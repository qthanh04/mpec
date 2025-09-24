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
@Table(name = "Loai_Thu", schema = "dbo")
public class LoaiThu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ten_loai_thu")
    private String tenLoaiThu;

    @Column(name = "ma_loai_thu")
    private String maLoaiThu;

    @Column(name = "xoa")
    private Boolean xoa;
}
