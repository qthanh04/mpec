package com.tavi.tavi_mrs.repository.nguoi_dung;

import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDungPhongBanChucVuVaiTro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface NguoiDungPhongBanChucVuVaiTroRepo extends JpaRepository<NguoiDungPhongBanChucVuVaiTro, Integer> {

    @Query(value = "select distinct n from NguoiDungPhongBanChucVuVaiTro n " +
            "where n.xoa = false and n.nguoiDung.xoa = false " +
            "and (0 = ?1 or n.chiNhanh.id = ?1) " +
            "and ( " +
            "(n.nguoiDung.hoVaTen is not null and upper(n.nguoiDung.hoVaTen) like concat('%', upper(?2) ,'%')) " +
            "or (n.nguoiDung.email is not null and upper(n.nguoiDung.email) like concat('%', upper(?2) ,'%')) " +
            "or (n.nguoiDung.soDienThoai is not null and upper(n.nguoiDung.soDienThoai) like concat('%', upper(?2) ,'%')) " +
            "or (n.phongBan.tenPhongBan is not null and upper(n.phongBan.tenPhongBan) like concat('%', upper(?2) ,'%')) " +
            "or (n.chucVu.tenChucVu is not null and upper(n.chucVu.tenChucVu) like concat('%', upper(?2) ,'%')) " +
            "or (n.vaiTro.tenVaiTro is not null and upper(n.vaiTro.tenVaiTro) like concat('%', upper(?2) ,'%')) " +
            ") " +
            "order by n.nguoiDung.id ")
    Page<NguoiDungPhongBanChucVuVaiTro> findNguoiDungByPhongBanAndText(int chiNhanhId, String text, Pageable pageable);

    @Query(value = "select distinct n from NguoiDungPhongBanChucVuVaiTro n " +
            "where n.xoa = false " +
            "and n.nguoiDung.xoa = false " +
            "and n.nguoiDung.id = ?1 ")
    Optional<NguoiDungPhongBanChucVuVaiTro> findByNguoiDungId(int nguoiDungID);

    @Query(value = "select distinct n from NguoiDungPhongBanChucVuVaiTro n " +
            "where n.xoa = false " +
            "and n.nguoiDung.taiKhoan = ?1 " +
            "and n.nguoiDung.xoa = false ")
    Optional<NguoiDungPhongBanChucVuVaiTro> findByTaiKhoan(String taiKhoan);


    @Query(value = "select  n from NguoiDungPhongBanChucVuVaiTro n " +
            "where n.xoa = false and n.nguoiDung.xoa = false " +
            "and (0 = ?1 or n.chiNhanh.id = ?1) " +
            "and ( " +
            "(n.nguoiDung.hoVaTen is not null and upper(n.nguoiDung.hoVaTen) like concat('%', upper(?2) ,'%'))" +
            "or (n.nguoiDung.email is not null and upper(n.nguoiDung.email) like concat('%', upper(?2) ,'%') )" +
            "or (n.nguoiDung.soDienThoai is not null and upper(n.nguoiDung.soDienThoai) like concat('%', upper(?2) ,'%') )" +
            "or (n.phongBan.tenPhongBan is not null and upper(n.phongBan.tenPhongBan) like concat('%', upper(?2) ,'%') )" +
            "or (n.chucVu.tenChucVu is not null and upper(n.chucVu.tenChucVu) like concat('%', upper(?2) ,'%') )" +
            "or (n.vaiTro.tenVaiTro is not null and upper(n.vaiTro.tenVaiTro) like concat('%', upper(?2) ,'%') )" +
            ")")
    Page<NguoiDungPhongBanChucVuVaiTro> findNguoiDungFullInfor(int chiNhanhId, String text, Pageable pageable);


    @Transactional
    @Modifying
    @Query("update NguoiDungPhongBanChucVuVaiTro n set n.chucVu.id = ?1, n.vaiTro.id = ?2, n.phongBan.id = ?3 where n.nguoiDung.id = ?4 and n.phongBan.id = ?5")
    int setPhongBanChucVuVaiTro(int chucVuId, int vaiTroId, int newPhongBanId, int nguoiDungId, int phongBanId);
}
