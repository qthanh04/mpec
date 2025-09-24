package com.tavi.tavi_mrs.service.hang_hoa;

import com.tavi.tavi_mrs.entities.hang_hoa.ChiNhanhHangHoa;
import com.tavi.tavi_mrs.entities.hang_hoa.HangHoa;
import com.tavi.tavi_mrs.entities.so_quy.PhieuThu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ChiNhanhHangHoaService {

    Optional<ChiNhanhHangHoa> findById(int id, boolean xoa);

    Optional<ChiNhanhHangHoa> save(int chiNhanhId, HangHoa hangHoa);

    Optional<ChiNhanhHangHoa> save(int chiNhanhId, int hangHoaId);

    Optional<ChiNhanhHangHoa> findByChiNhanhIdAndHangHoaId(int chiNhanhId, int hangHoaId);

    Page<ChiNhanhHangHoa> findAllToPage(Pageable pageable);

    boolean setTonKho(int tonKho, int id);

    Page<ChiNhanhHangHoa> findByChiNhanh(int chiNhanhId, Pageable pageable);

    Page<ChiNhanhHangHoa> findByChiNhanhAndText(int chiNhanhId, String text, Pageable pageable);

    List<ChiNhanhHangHoa> findListChiNhanhHangHoa(List<Integer> listChiNhanhHangHoaId);

    Optional<ChiNhanhHangHoa> saveObject(ChiNhanhHangHoa chiNhanhHangHoa);

}
