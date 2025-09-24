/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tavi.tavi_mrs.entities.hang_hoa;

import com.tavi.tavi_mrs.entities.bieu_do.BieuDo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@NamedNativeQueries({
        @NamedNativeQuery(name="HangHoa.tongHop",
        query = "select h.ten_hang_hoa x, sum(gb.gia_ban * hdct.so_luong) y, hd.thoi_gian tg from hoa_don hd " +
                "join hoa_don_chi_tiet hdct on hdct.hoa_don_id = hd.id " +
                "join lich_su_gia_ban gb on gb.id = hdct.gia_ban_id " +
                "join hang_hoa h on h.id = (select dvhh.hang_hoa_id from don_vi_hang_hoa dvhh where dvhh.id = gb.don_vi_hang_hoa_id) " +
                "group by x " +
                "having tg >= ?1 and tg <= ?2 " +
                "order by y desc, x asc limit 10",
        resultSetMapping = "HangHoaMapping"),
        @NamedNativeQuery(name="HangHoa.tongHopTuan",
                query = "select h.ten_hang_hoa x, sum(gb.gia_ban * hdct.so_luong) y, hd.thoi_gian tg from hoa_don hd " +
                        "join hoa_don_chi_tiet hdct on hdct.hoa_don_id = hd.id " +
                        "join lich_su_gia_ban gb on gb.id = hdct.gia_ban_id " +
                        "join hang_hoa h on h.id = (select dvhh.hang_hoa_id from don_vi_hang_hoa dvhh where dvhh.id = gb.don_vi_hang_hoa_id) " +
                        "group by x " +
                        "having week(tg) = ?1 and year(tg) = ?2 " +
                        "order by y desc, x asc limit 10",
                resultSetMapping = "HangHoaMapping"),
        @NamedNativeQuery(name="HangHoa.tongHopThang",
                query = "select h.ten_hang_hoa x, sum(gb.gia_ban * hdct.so_luong) y, hd.thoi_gian tg from hoa_don hd " +
                        "join hoa_don_  chi_tiet hdct on hdct.hoa_don_id = hd.id " +
                        "join lich_su_gia_ban gb on gb.id = hdct.gia_ban_id " +
                        "join hang_hoa h on h.id = (select dvhh.hang_hoa_id from don_vi_hang_hoa dvhh where dvhh.id = gb.don_vi_hang_hoa_id) " +
                        "group by x " +
                        "having month(tg) = ?1 and year(tg) = ?2 " +
                        "order by y desc, x asc limit 10",
                resultSetMapping = "HangHoaMapping")
        
})

@SqlResultSetMapping(
        name = "HangHoaMapping",
        classes = {@ConstructorResult(
                targetClass = BieuDo.class,
                columns = { @ColumnResult(name = "x"), @ColumnResult(name = "y") })}
)

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Hang_Hoa")
public class HangHoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ma")
    private String ma;

    @NotNull
    @Column(name = "ten_hang_hoa")
    private String tenHangHoa;

    @Column(name = "ma_vach")
    private String maVach;

    @ManyToOne
    @JoinColumn(name = "nhom_hang_id")
    private NhomHang nhomHang;

    @ManyToOne
    @JoinColumn(name = "thuong_hieu_id")
    private ThuongHieu thuongHieu;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "tich_diem")
    private Integer tichDiem;

    @Column(name = "ma_giam_gia")
    private String maGiamGia;

    @Column(name = "phan_tram_giam_gia")
    private Float phanTramGiamGia;

    @Column(name = "url_hinh_anh_1")
    private String urlHinhAnh1;

    @Column(name = "url_hinh_anh_2")
    private String urlHinhAnh2;

    @Column(name = "url_hinh_anh_3")
    private String urlHinhAnh3;

    @Column(name = "xoa")
    private Boolean xoa;
}
