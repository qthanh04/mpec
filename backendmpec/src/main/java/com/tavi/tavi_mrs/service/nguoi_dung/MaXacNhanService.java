package com.tavi.tavi_mrs.service.nguoi_dung;

import com.tavi.tavi_mrs.entities.nguoi_dung.MaXacNhan;

import java.util.Optional;

public interface MaXacNhanService {

    Optional<MaXacNhan> save(MaXacNhan maXacNhan);

    Optional<MaXacNhan> findByNguoiDungAndMaToken(int nguoiDungId, String maToken);

    Optional<MaXacNhan> setTrangThai(int nguoiDungId, String token, int trangThai);

    String validateToken(int nguoiDungId, String maToken);
}