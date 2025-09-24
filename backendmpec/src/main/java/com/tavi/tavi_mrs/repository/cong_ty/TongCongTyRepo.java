package com.tavi.tavi_mrs.repository.cong_ty;

import com.tavi.tavi_mrs.entities.cong_ty.TongCongTy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface TongCongTyRepo extends JpaRepository<TongCongTy, Integer> {

    @Modifying
    @Transactional
    @Query("update TongCongTy as cn set cn.xoa=true where cn.id=?1")
    int deleted(Integer id);

    @Query("select cn from TongCongTy cn where cn.xoa = false and (cn.id=?1 or ?1 = 0)")
    Optional<TongCongTy> findByIdAndXoa(Integer congTyId);

    @Query("select cn from TongCongTy as cn where cn.xoa=false and (cn.id=?1 or ?1 = 0)")
    List<TongCongTy> findAllCongTy(Integer congTyId);

    @Query("select cn from TongCongTy as cn where cn.xoa=false")
    Page<TongCongTy> findAllToPageCongTy(Pageable pageable);
}
