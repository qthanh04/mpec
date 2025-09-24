package com.tavi.tavi_mrs.payload.nha_cung_cap.tra_hang;

import com.tavi.tavi_mrs.payload.nha_cung_cap.tra_hang.TienNhaCungCapTra;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TienNhaCungCapTraList {

    List<TienNhaCungCapTra> tienNhaCungCapTras;
}
