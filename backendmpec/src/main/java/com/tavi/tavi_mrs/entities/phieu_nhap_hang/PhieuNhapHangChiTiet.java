package com.tavi.tavi_mrs.entities.phieu_nhap_hang;

import com.tavi.tavi_mrs.entities.hang_hoa.ChiNhanhHangHoa;
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
@Table(name = "Phieu_Nhap_Hang_Chi_tiet", schema = "dbo")
public class PhieuNhapHangChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "tong_tien")
    private Float tongTien;

    @NotNull
    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "gia_nhap")
    private Float giaNhap;

    @ManyToOne
    @JoinColumn(name = "phieu_nhap_hang_id")
    private PhieuNhapHang phieuNhapHang;

    @ManyToOne
    @JoinColumn(name = "chi_nhanh_hang_hoa_id")
    private ChiNhanhHangHoa chiNhanhHangHoa;

    @Column(name = "xoa")
    private Boolean xoa;
}
