package com.tavi.tavi_mrs.service.don_vi;

import com.tavi.tavi_mrs.entities.don_vi.DonVi;
import com.tavi.tavi_mrs.entities.don_vi.DonViHangHoa;
import com.tavi.tavi_mrs.entities.hang_hoa.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DonViService {

    Page<DonVi> findAll(Pageable pageable);

    Page<DonVi> search(String tenDonVi, Pageable pageable);

    Optional<DonVi> findByIdAndXoa(int id, boolean xoa);

    DonVi findById(int id);

    Optional<DonVi> save(DonVi donVi);

    Boolean deleted(int id);

    Boolean findByTenDonVi(String tenDonVi);

    DonVi findByTenDonViExcel(String tenDonVi);
}
