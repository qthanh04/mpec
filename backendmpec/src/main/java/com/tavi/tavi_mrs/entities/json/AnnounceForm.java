package com.tavi.tavi_mrs.entities.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnounceForm {

    private String tieuDe;
    private String noiDung;
    private List<String> fileDinhKemList = new ArrayList<>();
    private List<Integer> listId = new ArrayList<>();
    private int hinhThucGui;
    private Boolean yeuCauPhanHoi;
    private int soLuongDonViNhan;
}
