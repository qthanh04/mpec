package com.tavi.tavi_mrs.entities.ca_lam_viec;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
public class NguoiDungCaLamViecId implements Serializable {

    private int caLamViec;

    private int nguoiDung;

    public NguoiDungCaLamViecId(int caLamViec, int nguoiDung) {
        this.caLamViec = caLamViec;
        this.nguoiDung = nguoiDung;
    }


}
