package com.tavi.tavi_mrs.entities.quyen;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDung;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Quyen", schema = "dbo")
public class Quyen  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ten_quyen")
    private String tenQuyen;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "Nhom_Quyen_Co_Quyen",
            joinColumns = @JoinColumn(name = "quyen_id"),
            inverseJoinColumns = @JoinColumn(name = "nhom_quyen_id"))
    @JsonManagedReference
    private List<NhomQuyen> nhomQuyenCoQuyen = new ArrayList<NhomQuyen>();

    @ManyToMany(mappedBy = "quyenList", targetEntity = NguoiDung.class)
    @JsonIgnore
    private List<NguoiDung> nguoiDungList;

    @Column(name = "xoa")
    private Boolean xoa;
}