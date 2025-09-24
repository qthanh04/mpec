package com.tavi.tavi_mrs.entities.van_chuyen.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonGHTK {

    private Boolean success;

    private String message;

    private Object order;
}
