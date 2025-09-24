package com.tavi.tavi_mrs.entities.nguoi_dung;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tavi.tavi_mrs.entities.quyen.Quyen;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Nguoi_Dung")
public class NguoiDung implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ma_tai_khoan")
    private String maTaiKhoan;

    @Column(name = "ho_va_ten")
    private String hoVaTen;

    @Column(name = "tai_khoan")
    private String taiKhoan;

    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "email")
    private String email;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "ngay_sinh")
    @Temporal(TemporalType.DATE)
    private Date  ngaySinh;

    @Column(name = "gioi_tinh")
    private Byte gioiTinh;

    @Column(name = "thoi_gian_khoi_tao")
    @Temporal(TemporalType.DATE)
    private Date thoiGianKhoiTao;

    @Column(name = "thoi_gian_kich_hoat")
    @Temporal(TemporalType.DATE)
    private Date thoiGianKichHoat;

    @Column(name = "thoi_gian_het_han")
    @Temporal(TemporalType.DATE)
    private Date thoiGianHetHan;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "bat_dau_lam_viec")
    @Temporal(TemporalType.DATE)
    private Date batDauLamViec;

    @Column(name = "thoi_gian_hop_dong")
    private String thoiGianHopDong;

    @Column(name = "muc_luong")
    private Float mucLuong;

    @Column(name = "url_anh_dai_dien")
    private String  urlAnhDaiDien;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "nguoi_dung_co_quyen",
            joinColumns = @JoinColumn(name = "nguoi_dung_id"),
            inverseJoinColumns = @JoinColumn(name = "quyen_id"))
    @JsonIgnore
    private List<Quyen> quyenList=  new ArrayList<Quyen>();

    @Column(name = "xoa")
    private Boolean xoa;

    public Set<GrantedAuthority> grantedAuthorities(){
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Quyen q : this.quyenList){
            grantedAuthorities.add(new SimpleGrantedAuthority(q.getTenQuyen()));
        }
        return grantedAuthorities;
    }

    @Override
    public String toString() {
        return "NguoiDung{" +
                "id=" + id +
                ", maTaiKhoan='" + maTaiKhoan + '\'' +
                ", email='" + email + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", taiKhoan='" + taiKhoan + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", hoVaTen='" + hoVaTen + '\'' +
                ", diaChi='" + diaChi + '\'' +
                '}'+quyenList.toString();
    }


}
