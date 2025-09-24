package com.tavi.tavi_mrs.entities.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectResultListDto {
    private List<SelectResultDto> resultDtoList;
    private Boolean more;
}
