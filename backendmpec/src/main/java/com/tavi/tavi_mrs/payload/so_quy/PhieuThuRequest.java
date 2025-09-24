package com.tavi.tavi_mrs.payload.so_quy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhieuThuRequest {

    private Float tienDaTra;

    private String ghiChu;
}
