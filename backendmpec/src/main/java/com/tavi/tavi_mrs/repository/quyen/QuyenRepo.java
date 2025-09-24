package com.tavi.tavi_mrs.repository.quyen;

import com.tavi.tavi_mrs.entities.quyen.Quyen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuyenRepo extends JpaRepository<Quyen, Integer> {

    Optional<Quyen> findById(int id);

    @Query(value = "from Quyen q order by q.id asc ")
    Page<Quyen> findAllToPage(Pageable pageable);

}
