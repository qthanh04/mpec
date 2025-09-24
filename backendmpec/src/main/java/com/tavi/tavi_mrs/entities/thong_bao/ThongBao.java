package com.tavi.tavi_mrs.entities.thong_bao;


import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDung;
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
@Table(name = "Thong_Bao", schema = "dbo")
public class ThongBao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "tieu_de")
    private String tieuDe;

    @Column(name = "noi_dung")
    private String noiDung;

    @Column(name = "hinh_thuc_gui")
    private Integer hinhThucGui;

    @Column(name = "thoi_gian_gui")
    @Temporal(TemporalType.TIMESTAMP)
    private Date thoiGianGui;

    @Column(name = "da_nhan_phan_hoi")
    private Boolean daNhanPhanHoi;

    @Column(name = "yeu_cau_phan_hoi")
    private Boolean yeuCauPhanHoi;

    @Column(name = "tai_lieu_dinh_kem")
    private String taiLieuDinhKem;

//    @ManyToMany
//    @JoinTable(name = "Thong_Bao_Nguoi_Nhan",
//            joinColumns = @JoinColumn(name = "thong_bao_id"),
//            inverseJoinColumns = @JoinColumn(name = "nguoi_dung_id"))
//    private List<NguoiDung> thongBaoNguoiNhan;

    @Column(name = "xoa")
    private Boolean xoa;
}
