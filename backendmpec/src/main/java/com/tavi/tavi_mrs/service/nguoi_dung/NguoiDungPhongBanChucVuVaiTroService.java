package com.tavi.tavi_mrs.service.nguoi_dung;

import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDungPhongBanChucVuVaiTro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface NguoiDungPhongBanChucVuVaiTroService {

    Page<NguoiDungPhongBanChucVuVaiTro> findNguoiDungByPhongBanAndText(int chiNhanhId, String text, Pageable pageable);

    Optional<NguoiDungPhongBanChucVuVaiTro> save(NguoiDungPhongBanChucVuVaiTro nguoiDungPhongBanChucVuVaiTro);

    int setPhongBanChucVuVaiTro(int chucVuId, int vaiTroId, int newPhongBanId, int nguoiDungId, int phongBanId);

    Page<NguoiDungPhongBanChucVuVaiTro> findNguoiDungFullInfor(int chiNhanhId, String text, Pageable pageable);

    Optional<NguoiDungPhongBanChucVuVaiTro> findByTaiKhoan(String taiKhoan);

    Optional<NguoiDungPhongBanChucVuVaiTro> findByNguoiDungId(int nguoiDungID);
}
