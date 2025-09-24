package com.tavi.tavi_mrs.repository.location;

import com.tavi.tavi_mrs.entities.location.Location;
import com.tavi.tavi_mrs.entities.nguoi_dung.MaXacNhan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepo extends JpaRepository<MaXacNhan, Integer> {

    @Query("select l from Location l where l.loclevel = ?1 ")
    List<Location> locationList(String level);

    @Query("select l from Location l where l.loclevel like concat('%', '2' ,'%') AND l.pid = ?1 ")
    List<Location> findQuanHuyenByThanhPhoId(String thanhPhoId);

    @Query("select l from Location l where l.loclevel like concat('%', '3' ,'%') AND l.pid = ?1 ")
    List<Location> findPhuongXaByQuanHuyenId(String quanHuyenId);
}
