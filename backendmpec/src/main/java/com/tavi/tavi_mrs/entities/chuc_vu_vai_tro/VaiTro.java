package com.tavi.tavi_mrs.entities.chuc_vu_vai_tro;

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
@Table(name = "Vai_Tro")
public class VaiTro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ten_vai_tro")
    private String tenVaiTro;

    @Column(name = "xoa")
    private Boolean xoa;
}
