package com.tavi.tavi_mrs.repository.khach_hang;

import com.tavi.tavi_mrs.entities.khach_hang.KhachHang;
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
public interface KhachHangRepo extends JpaRepository<KhachHang, Integer> {

    @Query(value = "from KhachHang kh where kh.xoa = false")
    List<KhachHang> findAll();

    Optional<KhachHang> findByIdAndXoa(int id, boolean xoa);

    @Query(value = "from KhachHang k " +
            "where " +
            "k.xoa = false " +
            "and (?1 is null or k.tenKhachHang like concat('%', ?1, '%')) " +
            "and (?2 is null or  k.dienThoai like concat('%', ?2, '%')) " +
            "and (?3 is null or  k.email like concat('%', ?3, '%')) " +
            "and (?4 is null or k.facebook like concat('%', ?4, '%')) " +
            "and (?5 is null or k.diaChi like concat('%', ?5, '%')) " +
            "order by k.id desc ")
    Page<KhachHang> findByTenKhachHangAndDienThoaiAndEmail(String tenKhachHang, String dienThoai, String email, String facebook, String diaChi, Pageable pageable);

    @Query(value = "from KhachHang k " +
            "where " +
            "k.xoa = false " +
            "and " +
            "(  (k.tenKhachHang like concat('%', ?1, '%')) " +
            "or (k.dienThoai like concat('%', ?1, '%')) " +
            "or (k.email like concat('%', ?1, '%')) " +
            ") " +
            "order by k.id desc ")
    Page<KhachHang> search(String text, Pageable pageable);

    @Query(value = "select k from KhachHang k where k.xoa = false")
    Page<KhachHang> findAll(Pageable pageable);

    @Query(nativeQuery = true, value = "select  count(khach_hang.id) from khach_hang " +
            "where khach_hang.id in (select distinct hd.khach_hang_id from hoa_don hd " +
            "where hd.thoi_gian >= ?1 and hd.thoi_gian <= ?2 " +
            "group by hd.khach_hang_id) ")
    Integer countCustomerTransaction(Date start, Date end);

    @Query(nativeQuery = true, value = "select count(*) from khach_hang where khach_hang.xoa = false")
    Integer countCustomer();

    @Query(nativeQuery = true, value = "select count(*) from khach_hang kh " +
            "where kh.xoa = false and kh.thoi_gian_tao >= ?1 and kh.thoi_gian_tao <= ?2 ")
    Integer countNewMember(Date start, Date end);

    @Modifying
    @Transactional
    @Query(value = "update KhachHang kh set kh.xoa = true where kh.id= ?1")
    int deleted(Integer id);

}
