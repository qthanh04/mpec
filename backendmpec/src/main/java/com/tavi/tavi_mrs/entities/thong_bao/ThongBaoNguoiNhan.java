package com.tavi.tavi_mrs.entities.thong_bao;

import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDung;
import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDungPhongBanId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Thong_Bao_Nguoi_Nhan", schema = "dbo")
public class ThongBaoNguoiNhan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @JoinColumn(name = "thong_bao_id")
    @ManyToOne
    private ThongBao thongBao;

    @JoinColumn(name = "nguoi_dung_id")
    @ManyToOne
    private NguoiDung nguoiDung;

    @Column(name = "xoa")
    private boolean xoa;
}
