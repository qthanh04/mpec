package com.tavi.tavi_mrs.service.thong_bao;

import com.tavi.tavi_mrs.entities.json.AnnounceForm;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.thong_bao.ThongBao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.Optional;

public interface ThongBaoService {

    Optional<ThongBao> findByIdAndXoa(int id, boolean xoa);

    Optional<ThongBao> save(ThongBao thongBao);

    boolean deleted(int id);

    Page<ThongBao> findAll(Pageable pageable);

    Page<ThongBao> findByThoiGianGuiAndTieuDe(Date ngayDau, Date ngayCuoi, String tieuDe, Pageable pageable);

//    ResponseEntity<JsonResult> sendAnnouncement(AnnounceForm announceForm) throws MessagingException;
}
