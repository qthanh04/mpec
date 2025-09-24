package com.tavi.tavi_mrs.payload.khach_hang;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KhachHangForm {

    int id;

    String diaChi;

    String dienThoai;

    String email;
}
