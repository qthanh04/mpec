package com.tavi.tavi_mrs.entities.json;

import java.util.LinkedHashMap;
import java.util.Map;

public class TrangThaiKhachHang {

    public static Map<Integer, String> trangThai = new LinkedHashMap<>();

    public TrangThaiKhachHang() {
        trangThai.put(1, "Hoạt động");
        trangThai.put(0, "Ngừng hoạt động");
    }
}
