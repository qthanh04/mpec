package com.tavi.tavi_mrs.service.don_vi;

import com.tavi.tavi_mrs.entities.don_vi.DonViHangHoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DonViHangHoaService {

    List<DonViHangHoa> findAll();

    Optional<DonViHangHoa> findByIdAndXoa(int id, boolean xoa);

    Optional<DonViHangHoa> findDonViCoBanByHangHoaId(int hangHoaId);

    Optional<DonViHangHoa> save(DonViHangHoa donViHangHoa);

    Page<DonViHangHoa> findByHangHoaId(int id, Pageable pageable);

    Optional<DonViHangHoa> findByTyLeAndDonViAndHangHoa(int donViId, int hangHoaId);

    Boolean findByTyLeAndDonViHangHoa(int donViId, int hangHoaId);
}
