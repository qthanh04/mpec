package com.tavi.tavi_mrs.service.so_quy;

import com.tavi.tavi_mrs.entities.so_quy.PhieuThuHoaDon;

import java.util.Optional;

public interface PhieuThuHoaDonService {

    Optional<PhieuThuHoaDon> save (PhieuThuHoaDon phieuThuHoaDon);

    Optional<PhieuThuHoaDon> findById(int id);

}
