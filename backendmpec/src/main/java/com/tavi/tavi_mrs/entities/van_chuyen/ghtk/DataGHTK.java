package com.tavi.tavi_mrs.entities.van_chuyen.ghtk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataGHTK  {
   //model tong khi posst, tao don hang
    List<Product> products;

    OrderGHTK order;
}
