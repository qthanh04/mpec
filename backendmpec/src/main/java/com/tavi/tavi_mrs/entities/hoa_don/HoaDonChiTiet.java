/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tavi.tavi_mrs.entities.hoa_don;



import com.tavi.tavi_mrs.entities.don_vi.LichSuGiaBan;

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
@Table(name = "Hoa_Don_Chi_Tiet", schema = "dbo")
public class HoaDonChiTiet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "so_luong")
    private int soLuong;
    
    @Column(name = "giam_gia")
    private Double giamGia;

    @NotNull
    @Column(name = "tong_gia")
    private Double tongGia;
    
    @ManyToOne
    @JoinColumn(name = "hoa_don_id")
    private HoaDon hoaDon;
    
    @ManyToOne
    @JoinColumn(name = "gia_ban_id")
    private LichSuGiaBan lichSuGiaBan;

    @Column(name = "xoa")
    private Boolean xoa;
    
}
