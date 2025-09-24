package com.tavi.tavi_mrs.entities.nguoi_dung;

import com.tavi.tavi_mrs.entities.chi_nhanh.ChiNhanh;
import com.tavi.tavi_mrs.entities.chuc_vu_vai_tro.ChucVu;
import com.tavi.tavi_mrs.entities.chuc_vu_vai_tro.VaiTro;
import com.tavi.tavi_mrs.entities.phong_ban.PhongBan;
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
@IdClass(value=NguoiDungPhongBanId.class)
@Table(name = "Nguoi_Dung_Phong_Ban_Chuc_Vu_Vai_Tro", schema = "dbo")
public class NguoiDungPhongBanChucVuVaiTro {


    @Id
    @JoinColumn(name = "phong_ban_id")
    @ManyToOne
    private PhongBan phongBan;

    @Id
    @JoinColumn(name = "nguoi_dung_id")
    @ManyToOne
    private NguoiDung nguoiDung;
    //    @Id
//    @AttributeOverrides(
//            {
//                    @AttributeOverride(name = "phongBan",column = @Column(name="phong_ban_id")),
//                    @AttributeOverride(name = "nguoiDung", column = @Column(name="nguoi_dung_id"))
//            }
//    )
    @JoinColumn(name = "chuc_vu_id")
    @ManyToOne
    private ChucVu chucVu;

    @JoinColumn(name = "vai_tro_id")
    @ManyToOne
    private VaiTro vaiTro;

    @JoinColumn(name = "chi_nhanh_id")
    @ManyToOne
    private ChiNhanh chiNhanh;

    @Column(name = "xoa")
    private boolean xoa;
}
