package com.tavi.tavi_mrs.payload.phieu_tra_khach;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TienTraKhachList {

    List<TienTraKhach> tienTraKhachList;
}
