package com.tavi.tavi_mrs.service.phieu_tra_khach;

import com.tavi.tavi_mrs.entities.phieu_tra_khach.PhieuTraKhach;
import com.tavi.tavi_mrs.entities.phieu_tra_khach.PhieuTraKhachChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PhieuTraKhachService {

    Page<PhieuTraKhach> findAll(Pageable pageable);

    Optional<PhieuTraKhach> findByIdAndXoa(int id, boolean xoa);

    Page<PhieuTraKhach> findByNguoiDung(int nguoiDungId, Pageable pageable);

    Page<PhieuTraKhach> findByThoiGianAndTrangThai(Date ngayDau, Date ngayCuoi, int trangThai, Pageable pageable);

    File createPDF(PhieuTraKhach phieuTraKhach, List<PhieuTraKhachChiTiet> phieuTraKhachChiTietList);

    Optional<PhieuTraKhach> findByMa(String ma);

    Boolean deleted(Integer id);

    Boolean setTrangThai(int id, int trangThai);

    Optional<PhieuTraKhach> save(PhieuTraKhach phieuTraKhach);

    boolean updateTienTraKhach(Double tienTraKhach, int phieuTraKhachId,Double conNo);
}
