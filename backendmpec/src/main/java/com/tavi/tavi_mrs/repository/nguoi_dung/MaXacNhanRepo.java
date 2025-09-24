package com.tavi.tavi_mrs.repository.nguoi_dung;


import com.tavi.tavi_mrs.entities.nguoi_dung.MaXacNhan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaXacNhanRepo extends JpaRepository<MaXacNhan, Integer> {

    @Query("select m from MaXacNhan m where m.nguoiDung.id = ?1 and m.maToken = ?2")
    Optional<MaXacNhan> findByNguoiDungAndMaToken(int nguoiDungId, String maToken);

    @Query(value = "update MaXacNhan m set m.trangThai = ?3 where m.nguoiDung.id = ?1 and m.maToken = ?2")
    Optional<MaXacNhan> setTrangThai(int nguoiDungId, String token, int trangThai);
}