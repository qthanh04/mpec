package com.tavi.tavi_mrs.entities.hang_hoa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Thong_So_Ki_Thuat")
public class ThongSoKiThuat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "ten_thong_so")
    private String ten;

    @ManyToOne
    @JoinColumn(name = "nhom_hang_id")
    private NhomHang nhomHang;

    @Column(name = "xoa")
    private boolean xoa;
}
