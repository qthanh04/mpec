package com.tavi.tavi_mrs.payload.nha_cung_cap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NhaCungCapForm {

    int id;

    String diaChi;

    String dienThoai;

    String email;
}
