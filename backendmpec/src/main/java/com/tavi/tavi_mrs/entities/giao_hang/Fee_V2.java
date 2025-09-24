package com.tavi.tavi_mrs.entities.giao_hang;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fee_V2 {

    private String partnerName;

    private Integer totalFee;
}
