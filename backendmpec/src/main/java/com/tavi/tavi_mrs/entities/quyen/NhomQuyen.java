package com.tavi.tavi_mrs.entities.quyen;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tavi.tavi_mrs.entities.van_chuyen.ghtk.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Nhom_Quyen", schema = "dbo")
public class NhomQuyen implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ten_nhom_quyen")
    private String tenNhomQuyen;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(name = "Nhom_Quyen_Co_Quyen",
//            joinColumns = @JoinColumn(name = "nhom_quyen_id"),
//            inverseJoinColumns = @JoinColumn(name = "quyen_id"))
//    @JsonManagedReference
//    private List<Quyen> nhomQuyenCoQuyen = new ArrayList<Quyen>();

    @ManyToMany(mappedBy = "nhomQuyenCoQuyen", targetEntity = Quyen.class)
    @JsonBackReference
    private List<Quyen> nhomQuyen;
}
