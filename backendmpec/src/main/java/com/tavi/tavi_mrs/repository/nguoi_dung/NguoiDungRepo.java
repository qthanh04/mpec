package com.tavi.tavi_mrs.repository.nguoi_dung;

import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDung;
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
public interface NguoiDungRepo extends JpaRepository<NguoiDung, Integer> {

    @Query("select n from NguoiDung n where n.xoa = false and n.id = ?1 ")
    Optional<NguoiDung> findById(int id, boolean xoa);

    @Query("select n from NguoiDung n where n.email = ?1")
    Optional<NguoiDung> findByEmail(String email);

    @Query("select n from NguoiDung n where n.email = ?1 ")
    NguoiDung findNguoiDungByEmail(String email);

    @Transactional
    @Query("select n from NguoiDung n where n.xoa = false and n.taiKhoan = ?1 ")
    NguoiDung findByTaiKhoan(String taiKhoan);

    @Query("select n from NguoiDung n where n.xoa = false and n.soDienThoai = ?1 ")
    NguoiDung findBySoDienThoai(String soDienThoai);

    @Query("select n from NguoiDung n where (?1 is null or n.xoa=?1) order by n.id")
    Page<NguoiDung> findAll(Boolean xoa, Pageable pageable);

    @Query(value = "from NguoiDung n where " +
            " n.xoa = ?3 " +
            "and n.taiKhoan = ?1 " +
            "and n.matKhau = ?2 ")
    NguoiDung findByTaiKhoanAndMatKhauAndXoa(String taiKhoan, String matKhau, boolean xoa);

    @Query(value = "select n from NguoiDung n where n.xoa = false and n.id in (?1)")
    List<NguoiDung> findByIdList(List<Integer> listId);

    @Query("select n from NguoiDung n where n.xoa = false and n.taiKhoan = ?1 ")
    Optional<NguoiDung> findByTK(String taiKhoan);

    @Query("select n from NguoiDung n where n.xoa = false and n.maTaiKhoan = ?1 ")
    Optional<NguoiDung> findByMaTK(String maTaiKhoan);

    @Query(nativeQuery = true,
            value = "select * from nguoi_dung n where n.tai_khoan REGEXP ?1 " +
                    " order by n.id desc limit 1")
    Optional<NguoiDung> findTheLastTK(String taiKhoan);

    @Query("select count(n) from NguoiDung n where n.taiKhoan = ?1 and n.xoa = ?2")
    long countByTaiKhoanAndXoa(String taiKhoan, boolean xoa);

    @Query("select count(n) from NguoiDung n where  n.xoa = ?1")
    long countTaiKhoanAndXoa(boolean xoa);

    @Query("select count(n) from NguoiDung n where n.email = ?1 and n.xoa = ?2")
    long countByEmailAndXoa(String email, boolean xoa);

    @Query("select count(n) from NguoiDung n where n.soDienThoai = ?1 and n.xoa = ?2")
    long countBySoDienThoaiAndXoa(String soDienThoai, boolean xoa);

    @Query(value = "select n.id from NguoiDung as n where n.xoa=false and n.taiKhoan=?1")
    Integer findIdByTaiKhoan(String taiKhoan);

    @Transactional
    @Modifying
    @Query("update NguoiDung n set n.trangThai = ?1 where n.id = ?2")
    int setTrangThai(int trangThai, int id);

    @Transactional
    @Modifying
    @Query("update NguoiDung n set n.xoa = true where n.id = ?1")
    int delete(int id);
}