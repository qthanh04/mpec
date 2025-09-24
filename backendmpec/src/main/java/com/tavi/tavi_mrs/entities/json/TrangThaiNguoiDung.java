package com.tavi.tavi_mrs.entities.json;

import java.util.LinkedHashMap;
import java.util.Map;

public class TrangThaiNguoiDung {
    public static Map<Integer, String> trangThai = new LinkedHashMap<>();

    public TrangThaiNguoiDung() {
        trangThai.put(1, "Kích hoạt");
        trangThai.put(2, "Khóa");
    }
}