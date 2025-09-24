package com.tavi.tavi_mrs.entities.don_vi;

import com.tavi.tavi_mrs.entities.hang_hoa.HangHoa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Don_Vi_Hang_Hoa")
public class DonViHangHoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ti_le")
    private Float tyLe;

    @ManyToOne
    @JoinColumn(name = "don_vi_id")
    private DonVi donVi;

    @ManyToOne
    @JoinColumn(name = "hang_hoa_id")
    private HangHoa hangHoa;

    @Column(name = "xoa")
    private Boolean xoa;

}
