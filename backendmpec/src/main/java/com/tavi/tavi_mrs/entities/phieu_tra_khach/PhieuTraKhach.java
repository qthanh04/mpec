package com.tavi.tavi_mrs.entities.phieu_tra_khach;

import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDung;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Phieu_Tra_Khach", schema = "dbo")
public class PhieuTraKhach implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ly_do")
    private String lyDo;

    @Column(name = "thoi_gian")
    private Date thoiGian;

    @Column(name = "tien_tra_khach")
    private Double tienTraKhach;

    @Column(name = "tien_phai_tra")
    private Double tienPhaiTra;

    @Column(name = "tien_da_tra")
    private Double tienDaTra;

    @Column(name = "giam_gia")
    private Double giamGia;

    @Column(name = "con_no")
    private Double conNo;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "ma")
    private String ma;

    @ManyToOne
    @JoinColumn(name = "nhan_vien_id")
    private NguoiDung nguoiDung;

    @Column(name = "xoa")
    private Boolean xoa;
}
