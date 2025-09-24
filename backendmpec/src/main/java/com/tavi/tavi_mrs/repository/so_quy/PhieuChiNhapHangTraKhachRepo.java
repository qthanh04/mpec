package com.tavi.tavi_mrs.repository.so_quy;

import com.tavi.tavi_mrs.entities.so_quy.PhieuChiNhapHangTraKhach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuChiNhapHangTraKhachRepo extends JpaRepository<PhieuChiNhapHangTraKhach, Integer> {
}
