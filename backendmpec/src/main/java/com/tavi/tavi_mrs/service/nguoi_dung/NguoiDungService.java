package com.tavi.tavi_mrs.service.nguoi_dung;

import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDung;
import com.tavi.tavi_mrs.payload.user.RegisterForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface NguoiDungService {

    Optional<NguoiDung> findById(int id, boolean xoa);

    Optional<NguoiDung> findByEmail(String email);

    NguoiDung updateNguoiDung(NguoiDung nguoiDung);

    NguoiDung findByTaiKhoan(String taiKhoan);

    NguoiDung findBySoDienThoai(String soDienThoai);

    NguoiDung register(RegisterForm registerForm);

    NguoiDung register(NguoiDung nguoiDung);

    Optional<NguoiDung> findByTK(String taiKhoan);

    NguoiDung findByTaiKhoanAndMatKhauAndXoa(String taiKhoan, String matKhau, boolean xoa);

    Integer findIdByTaiKhoan(String taiKhoan);

    Page<NguoiDung> findAll(Boolean xoa, Pageable pageable);

    Optional<NguoiDung> save(NguoiDung nguoiDung);

    long countByTaiKhoanAndXoa(String taiKhoan, boolean xoa);

    long countTaiKhoanAndXoa(boolean xoa);

    long countByEmailAndXoa(String email, boolean xoa);

    long countBySoDienThoaiAndXoa(String soDienThoai, boolean xoa);

    NguoiDung findNguoiDungByEmail(String email);

    boolean setTrangThai(int trangThai, int id);

    boolean delete(int id);
}
