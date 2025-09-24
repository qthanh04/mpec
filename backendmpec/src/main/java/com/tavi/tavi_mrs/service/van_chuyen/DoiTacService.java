package com.tavi.tavi_mrs.service.van_chuyen;

import com.tavi.tavi_mrs.entities.van_chuyen.DoiTac;

import java.util.List;
import java.util.Optional;

public interface DoiTacService {

    DoiTac findById(int id);

    Optional<DoiTac> findByIdOptionnal(int id);

    Optional<DoiTac> save(DoiTac doiTac);

    List<DoiTac> findAll();
}
