package com.tavi.tavi_mrs.repository.van_chuyen;

import com.tavi.tavi_mrs.entities.van_chuyen.DoiTac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoiTacRepo extends JpaRepository<DoiTac, Integer> {

    @Query(value = "from DoiTac d where d.id = ?1")
    DoiTac findById(int id);

    @Query(value = "from DoiTac d where d.xoa=false " +
            "and d.id = ?1")
    Optional<DoiTac> findByIdOptionnal(int id);

    @Query("from DoiTac d where d.xoa=false " +
            "order by d.name asc")
    List<DoiTac> findAll();

}
