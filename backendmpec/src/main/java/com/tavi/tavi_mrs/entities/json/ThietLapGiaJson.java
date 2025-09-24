package com.tavi.tavi_mrs.entities.json;

import com.tavi.tavi_mrs.entities.don_vi.LichSuGiaBan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThietLapGiaJson {

    private LichSuGiaBan lichSuGiaBan;

    private Float giaNhapGanNhat;

    private Date ngayNhapGanNhat;
}
