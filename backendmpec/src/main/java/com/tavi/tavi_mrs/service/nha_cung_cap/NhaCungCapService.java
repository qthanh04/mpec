package com.tavi.tavi_mrs.service.nha_cung_cap;

import com.tavi.tavi_mrs.entities.khach_hang.KhachHang;
import com.tavi.tavi_mrs.entities.nha_cung_cap.NhaCungCap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface NhaCungCapService {

    Page<NhaCungCap> findAllToPage(Pageable pageable);

    Optional<NhaCungCap> findByIdAndXoa(int id, boolean xoa);

    Page<NhaCungCap> findByTenAndDiaChiAndEmailAndDienThoaiAndFacebook(String ten, String diaChi, String email,
                                                                       String dienThoai, String facebook,
                                                                       Pageable pageable);

    Page<NhaCungCap> search(String text, Pageable pageable);

    List<NhaCungCap> findListNhaCungCap(List<Integer> list);

    Optional<NhaCungCap> save(NhaCungCap nhaCungCap);

    Boolean deleted(Integer id);
}
