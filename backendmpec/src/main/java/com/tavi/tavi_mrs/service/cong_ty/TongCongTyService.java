package com.tavi.tavi_mrs.service.cong_ty;

import com.tavi.tavi_mrs.entities.cong_ty.TongCongTy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TongCongTyService {

    Page<TongCongTy> findAll(Pageable pageable);

    Optional<TongCongTy> findById(Integer id);

    Optional<TongCongTy> save(TongCongTy tongCongTy);

    Boolean deleted(Integer tongCongTy);

    List<TongCongTy> findAll();

}
