package com.tavi.tavi_mrs.entities.giao_hang.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonGHN {
    private int code;

    private String message;

    private Object data;
}
