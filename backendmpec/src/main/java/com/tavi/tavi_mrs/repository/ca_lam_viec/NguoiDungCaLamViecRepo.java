package com.tavi.tavi_mrs.repository.ca_lam_viec;

import com.tavi.tavi_mrs.entities.ca_lam_viec.NguoiDungCaLamViec;
import com.tavi.tavi_mrs.entities.hoa_don.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface NguoiDungCaLamViecRepo extends JpaRepository<NguoiDungCaLamViec, Integer> {

    @Query(value = "select ndclv from NguoiDungCaLamViec ndclv where ndclv.xoa = false order by ndclv.id")
    List<NguoiDungCaLamViec> findAll();

    Optional<NguoiDungCaLamViec> findByIdAndXoa(int id, boolean xoa);

    @Query("from NguoiDungCaLamViec ndclv where ndclv.xoa = false and ndclv.id = ?1 ")
    Optional<NguoiDungCaLamViec> findById(int id, boolean xoa);

    @Query(value = "select ndclv from NguoiDungCaLamViec ndclv where ndclv.xoa = false order by ndclv.id")
    Page<NguoiDungCaLamViec> findAll(Pageable pageable);

    @Query(value = "from NguoiDungCaLamViec n " +
            "where " +
            "n.xoa = false " +
            "and (?1 is null or n.nguoiDung.hoVaTen like concat('%', ?1, '%')) " +
            "and (?2 is null or n.ngayThang >= ?2 ) and (?3 is null or n.ngayThang <= ?3) " +
            "and (?4 = -1 or n.status = ?4 ) " +
            "order by n.ngayThang asc ")
    Page<NguoiDungCaLamViec> findByNguoiDungAndNgayThang(Integer nguoiDung, String ngayDau, String ngayCuoi, int status, Pageable pageable);

    @Query(value = "from NguoiDungCaLamViec n " +
            "where " +
            "n.xoa = false " +
            "and n.nguoiDung is not null and upper (n.nguoiDung.hoVaTen) like concat('%', upper(?1) ,'%') " +
            "or n.caLamViec is not null and upper (n.caLamViec.ten) like concat('%', upper(?1) ,'%') " +
            "or n.checkin is not null and upper (n.checkin) like concat('%', upper(?1) ,'%') " +
            "or n.checkout is not null and upper (n.checkout) like concat('%', upper(?1) ,'%') " +
            "or n.statusCheckin is not null and upper (n.statusCheckin) like concat('%', upper(?1) ,'%') " +
            "or n.statusCheckout is not null and upper (n.statusCheckout) like concat('%', upper(?1) ,'%') ")
    Page<NguoiDungCaLamViec> search(String text, Pageable pageable);

    @Query(value = "from NguoiDungCaLamViec  n " +
            "where " +
            "n.nguoiDung.id = ?1 and n.caLamViec.id = ?2 " +
            "and n.ngayThang = ?3 and n.xoa = false")
    Optional<NguoiDungCaLamViec> findByNguoiDungAndCaLamViecAndNgayThang(Integer nguoiDungId, Integer calamviecId, String ngayThang);

    @Query(value = "from NguoiDungCaLamViec n " +
            "where " +
            "n.xoa = false " +
            "and n.id = ?1 " +
            "and (?2 is null or n.ngayThang >= ?2 ) and (?3 is null or n.ngayThang <= ?3) " +
            "and (?4 = -1 or n.status = ?4 ) " +
            "order by n.ngayThang asc")

    Page<NguoiDungCaLamViec> findByCaLamViecAndThoiGian(Integer caLamViecId, String ngayDau, String ngayCuoi, int status, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update NguoiDungCaLamViec n set n.status = ?2 where n.id = ?1")
    int setStatus(int id, int status);

    @Modifying
    @Query(value = "update NguoiDungCaLamViec n set n.xoa = true where n.id = ?1")
    int delete(Integer id);
}
