package com.tavi.tavi_mrs.entities.phieu_tra_hang_nhap;

import com.tavi.tavi_mrs.entities.hang_hoa.ChiNhanhHangHoa;
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
@Table(name = "Phieu_Tra_Hang_Nhap_Chi_Tiet", schema = "dbo")
public class PhieuTraHangNhapChiTiet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "tong_tien")
    private Double tongTien;

    @ManyToOne
    @JoinColumn(name = "chi_nhanh_hang_hoa_id")
    private ChiNhanhHangHoa chiNhanhHangHoa;

    @ManyToOne
    @JoinColumn(name = "phieu_tra_hang_id")
    private PhieuTraHangNhap phieuTraHangNhap;
}
