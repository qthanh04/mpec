package com.tavi.tavi_mrs.entities.dto;

import com.tavi.tavi_mrs.entities.hoa_don.HoaDon;
import com.tavi.tavi_mrs.entities.hoa_don.HoaDonChiTiet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoaDonDto {

    private HoaDon hoaDon;

    private List<Integer> lichSuGiaBanIdList;

    private List<HoaDonChiTiet> hoaDonChiTietList;
}
