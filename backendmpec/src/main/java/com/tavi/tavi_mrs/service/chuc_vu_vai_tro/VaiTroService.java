package com.tavi.tavi_mrs.service.chuc_vu_vai_tro;

import com.tavi.tavi_mrs.entities.chuc_vu_vai_tro.VaiTro;
import com.tavi.tavi_mrs.entities.dto.SelectResultListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VaiTroService {

    List<VaiTro> findAll();

    Optional<VaiTro> findByIdAndXoa(int id, boolean xoa);

    Optional<VaiTro> save(VaiTro vaiTro);

    Boolean findByTenVaiTroCheck(String tenVaiTro);

    Page<VaiTro> findByTenVaiTro(String tenVaiTro, Pageable pageable);

    Page<VaiTro> findAllToPage(Pageable pageable);

    SelectResultListDto selectSearch(String tenVaiTro, Pageable pageable);

    Boolean deleted(Integer id);
}
