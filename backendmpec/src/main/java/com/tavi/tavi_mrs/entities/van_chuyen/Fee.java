package com.tavi.tavi_mrs.entities.van_chuyen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fee {

    private String partnerName;

    private Integer totalFee;
}
