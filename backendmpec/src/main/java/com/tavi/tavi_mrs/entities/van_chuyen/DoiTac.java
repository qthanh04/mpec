package com.tavi.tavi_mrs.entities.van_chuyen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "doi_tac")
public class DoiTac implements Serializable  {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "tai_khoan")
    private String taiKhoan;

    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "token")
    private String token;

    @Column(name = "khac")//thong tin them
    private String khac;

    @Column(name = "trang_thai")
    private Integer trang_thai;

    @Column(name = "xoa")
    private Boolean xoa;
}
