package com.tavi.tavi_mrs.service.hang_hoa;

import com.tavi.tavi_mrs.entities.hang_hoa.HangHoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface HangHoaService {

    Optional<HangHoa> findById(int id, boolean xoa);

    Optional<HangHoa> findByMaHangHoa(String ma, boolean xoa);

    Optional<HangHoa> findByMaVach(String ma, boolean xoa);

    Optional<HangHoa> save(HangHoa hangHoa);

    Optional<HangHoa> update(HangHoa hangHoaDto);

    Page<HangHoa> findAllToPage(Pageable pageable);

    Page<HangHoa> findByTenHangHoaAndNhomHangAndThuongHieu(String tenHangHoa, int nhomHangId, int thuongHieuId, Pageable pageable);

    Page<HangHoa> search(String text, Pageable pageable);

    Boolean deleted(Integer id);

    void saveByExcel(MultipartFile file);

}
