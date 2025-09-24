package com.tavi.tavi_mrs.entities.bieu_do;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BieuDo {

    private String x;
    private Double y;

    public BieuDo(Object x, Double y) {
        this.x = String.valueOf(x);
        this.y = y;
    }
}
