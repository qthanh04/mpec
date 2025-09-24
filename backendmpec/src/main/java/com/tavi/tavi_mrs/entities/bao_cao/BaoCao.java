package com.tavi.tavi_mrs.entities.bao_cao;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tavi.tavi_mrs.entities.base.BaseEntity;
import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDung;
import com.tavi.tavi_mrs.entities.thong_bao.TaiLieuDinhKem;
import com.tavi.tavi_mrs.entities.thong_bao.ThongBao;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Bao_Cao")
public class BaoCao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(notes = "id of the Bao_Cao , auto increment")
    private Integer id;

    @Column(name = "tieu_de")
    private String tieuDe;

    @Column(name = "noi_dung")
    private String noiDung;

    @Column(name = "thoi_gian_tao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date thoiGianTao;

    @ManyToOne
    @JoinColumn(name = "nguoi_tao_id")
    private NguoiDung nguoiDung;

    @ManyToOne
    @JoinColumn(name = "thong_bao_id")
    private ThongBao thongBao;

    @OneToMany(mappedBy = "baoCao")
    @JsonManagedReference
    private List<TaiLieuDinhKem> listTaiLieuDinhKem;

    private Boolean xoa;
}