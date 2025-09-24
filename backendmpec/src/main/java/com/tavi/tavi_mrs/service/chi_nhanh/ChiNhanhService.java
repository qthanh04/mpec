package com.tavi.tavi_mrs.service.chi_nhanh;

import com.tavi.tavi_mrs.entities.chi_nhanh.ChiNhanh;
import com.tavi.tavi_mrs.entities.dto.SelectResultListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ChiNhanhService {

    List<ChiNhanh> findAll();

    Page<ChiNhanh> findAllToPage(Pageable pageable);

    Optional<ChiNhanh> findByIdAndXoa(int id, boolean xoa);

    List<ChiNhanh> findByTongCty(Integer idTongCty);

    Optional<ChiNhanh> save(ChiNhanh chiNhanh);

    List<ChiNhanh> selectSearch(String text);

    Boolean deleted(Integer chiNhanhId);

    List<ChiNhanh> findAll(Integer doanhNghiepId);

    Page<ChiNhanh> selectSearch(String text, Pageable pageable);

    SelectResultListDto select2Search(String text, Pageable pageable);

    Page<ChiNhanh> search(Integer doanhNghiepId,Pageable pageable);

    ChiNhanh findByMaChiNhanhExcel(String ma);


}
