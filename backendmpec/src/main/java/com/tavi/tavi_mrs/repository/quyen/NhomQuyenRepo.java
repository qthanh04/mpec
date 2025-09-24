package com.tavi.tavi_mrs.repository.quyen;

import com.tavi.tavi_mrs.entities.quyen.NhomQuyen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NhomQuyenRepo extends JpaRepository<NhomQuyen, Integer> {

    Optional<NhomQuyen> findById(int id);

    @Query(value = "from NhomQuyen n order by n.id asc ")
    Page<NhomQuyen> findAllToPage(Pageable pageable);

}