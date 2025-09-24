package com.tavi.tavi_mrs.payload.hoa_don;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TienKhachTraList {

    List<TienKhachTra> tienKhachTras;
}
