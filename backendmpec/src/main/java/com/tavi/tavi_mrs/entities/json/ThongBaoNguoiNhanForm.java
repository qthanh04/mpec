package com.tavi.tavi_mrs.entities.json;

import lombok.Data;

import java.util.List;

@Data
public class ThongBaoNguoiNhanForm {

    private int thongBaoId;

    List<Integer> nguoiDungId;
}
