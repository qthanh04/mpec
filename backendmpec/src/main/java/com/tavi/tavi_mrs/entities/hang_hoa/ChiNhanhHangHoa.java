package com.tavi.tavi_mrs.entities.hang_hoa;

import com.tavi.tavi_mrs.entities.chi_nhanh.ChiNhanh;
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
@Table(name = "Chi_Nhanh_Hang_Hoa")
public class ChiNhanhHangHoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "chi_nhanh_id")
    private ChiNhanh chiNhanh;

    @ManyToOne
    @JoinColumn(name = "hang_hoa_id")
    private HangHoa hangHoa;

    @NotNull
    @Column(name = "ton_kho")
    private float tonKho;

    @Column(name = "xoa")
    private Boolean xoa;
}
