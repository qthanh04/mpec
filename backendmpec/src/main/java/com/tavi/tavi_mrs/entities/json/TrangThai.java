package com.tavi.tavi_mrs.entities.json;

import java.util.LinkedHashMap;
import java.util.Map;

public class TrangThai {


    public static Map<Integer, String> trangThai = new LinkedHashMap<>();

    public TrangThai() {
        trangThai.put(1, "Đã mua");
        trangThai.put(2, "Chưa mua");
    }
}

