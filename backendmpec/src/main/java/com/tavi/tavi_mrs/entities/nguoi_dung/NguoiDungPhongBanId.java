package com.tavi.tavi_mrs.entities.nguoi_dung;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
public class NguoiDungPhongBanId implements Serializable {

    private int phongBan;

    private int nguoiDung;

    // default constructor

    public NguoiDungPhongBanId(int phongBanId, int nguoiDungId) {
        this.phongBan = phongBan;
        this.nguoiDung = nguoiDung;
    }
}