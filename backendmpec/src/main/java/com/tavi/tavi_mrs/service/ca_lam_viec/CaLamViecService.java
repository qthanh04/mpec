package com.tavi.tavi_mrs.service.ca_lam_viec;

import com.tavi.tavi_mrs.entities.ca_lam_viec.CaLamViec;
//import jdk.nashorn.internal.runtime.options.Option;
import com.tavi.tavi_mrs.entities.chinh_sach_dieu_khoan.ChinhSach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CaLamViecService {

    Page<CaLamViec> findAll(Pageable pageable);

    Optional<CaLamViec> findByIdAndXoa(int id, boolean xoa);

    Page<CaLamViec> searchByName(String text,Pageable pageable );

    Boolean deleted(Integer id);

    Optional<CaLamViec> save(CaLamViec caLamViec);

    List<CaLamViec> findAll();


}
