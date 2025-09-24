package com.tavi.tavi_mrs.payload.so_quy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhieuChiRequest {

    private Float tienDaTra;

    private String ghiChu;
}
