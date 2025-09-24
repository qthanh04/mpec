package com.tavi.tavi_mrs.service.ca_lam_viec;

import com.tavi.tavi_mrs.entities.ca_lam_viec.NguoiDungCaLamViec;
import com.tavi.tavi_mrs.entities.hoa_don.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface NguoiDungCaLamViecService {

    List<NguoiDungCaLamViec> findAll();

    Page<NguoiDungCaLamViec> findAll(Pageable pageable);

    Page<NguoiDungCaLamViec> search(String text,Pageable pageable);

    Optional<NguoiDungCaLamViec> findByIdAndXoa(int id, boolean xoa);

    Page<NguoiDungCaLamViec> findByNguoiDungAndNgayThang(Integer nguoiDung, String ngayDau, String ngayCuoi,
                                                         int status , Pageable pageable);

    Page<NguoiDungCaLamViec> findByCaLamViecAndThoiGian( Integer caLamViec, String ngayDau, String ngayCuoi,
                                                         int status, Pageable pageable);

    Optional<NguoiDungCaLamViec> findByNguoiDungAndCaLamViecAndNgayThang(Integer nguoiDungId, Integer calamviecId, String ngayThang);

    List<NguoiDungCaLamViec> findListNguoiDungCaLamViec(List<Integer> listHoaDonId);

    Optional<NguoiDungCaLamViec> save(NguoiDungCaLamViec nguoiDungCaLamViec);

    Boolean setStatus(int id, int status);

    Boolean delete(int id);

}
