package com.tavi.tavi_mrs.entities.json;

import com.tavi.tavi_mrs.entities.hoa_don.HoaDonChiTiet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoaDonChiTietForm implements Serializable {

    private Integer hoaDonId;

    private List<Integer> lichSuGiaBanIdList;

    private List<HoaDonChiTiet> hoaDonChiTietList;
}
