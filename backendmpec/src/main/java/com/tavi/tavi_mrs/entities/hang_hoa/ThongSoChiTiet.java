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
@Table(name = "Thong_So_Chi_Tiet")
public class ThongSoChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ten")
    private String ten;

    @Column(name = "gia_tri")
    private String giaTri;

    @ManyToOne
    @JoinColumn(name = "thong_so_ki_thuat_id")
    private ThongSoKiThuat thongSoKiThuat;

    @Column(name = "xoa")
    private boolean xoa;
}
