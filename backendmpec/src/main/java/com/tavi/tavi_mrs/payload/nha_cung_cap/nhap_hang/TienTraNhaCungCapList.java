package com.tavi.tavi_mrs.payload.nha_cung_cap.nhap_hang;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TienTraNhaCungCapList {
    List<TienTraNhaCungCap> tienTraNhaCungCaps;
}
