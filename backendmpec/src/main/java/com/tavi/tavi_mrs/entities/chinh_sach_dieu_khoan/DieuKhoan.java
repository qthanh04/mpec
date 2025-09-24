package com.tavi.tavi_mrs.entities.chinh_sach_dieu_khoan;

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
@Table(name = "Dieu_Khoan")
public class DieuKhoan implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "noi_dung")
    private String noiDung;

    @Column(name = "xoa")
    private boolean xoa;
}