package com.tavi.tavi_mrs.entities.van_chuyen.ghn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDeleted {
    //data tra ve khi xoa
    private String order_code;

    private Boolean result;

    private String message;
}
