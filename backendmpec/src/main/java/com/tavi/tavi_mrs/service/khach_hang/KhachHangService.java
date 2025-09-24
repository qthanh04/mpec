package com.tavi.tavi_mrs.service.khach_hang;

import com.tavi.tavi_mrs.entities.khach_hang.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface KhachHangService {

    List<KhachHang> findAll();

    Optional<KhachHang> findByIdAndXoa(int id, boolean xoa);

    Page<KhachHang> findByTenKhachHangAndDienThoaiAndEmail(String tenKhachHang, String dienThoai, String email, String facebook, String diaChi, Pageable pageable);

    Page<KhachHang> findAll(Pageable pageable);

    Optional<KhachHang> save(KhachHang khachHang);

    List<KhachHang> findListKhachHang(List<Integer> list);

    Page<KhachHang> search(String text, Pageable pageable);

    Integer countCustomer();

    Integer countCustomerTransaction(Date start, Date end);

    Integer countNewMember(Date start, Date end);

    Boolean deleted(int id);
}
