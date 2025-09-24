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
@Table(name = "Hang_Hoa_Thong_So")
public class HangHoaThongSo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "gia_tri")
    private String giaTri;

    @ManyToOne
    @JoinColumn(name = "thong_so_ki_thuat_id")
    private ThongSoKiThuat thongSoKiThuat;

    @ManyToOne
    @JoinColumn(name = "hang_hoa_id")
    private HangHoa hangHoa;

    @ManyToOne
    @JoinColumn(name = "thong_so_chi_tiet_id")
    private ThongSoChiTiet thongSoChiTiet;

    @Column(name = "xoa")
    private  boolean xoa;

}
