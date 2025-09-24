package com.tavi.tavi_mrs.service_impl.thong_bao;

import com.tavi.tavi_mrs.entities.json.AnnounceForm;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDung;
import com.tavi.tavi_mrs.entities.thong_bao.TaiLieuDinhKem;
import com.tavi.tavi_mrs.entities.thong_bao.ThongBao;
import com.tavi.tavi_mrs.repository.nguoi_dung.NguoiDungRepo;
import com.tavi.tavi_mrs.repository.thong_bao.TaiLieuDinhKemRepo;
import com.tavi.tavi_mrs.repository.thong_bao.ThongBaoRepo;
import com.tavi.tavi_mrs.service.mail.MailService;
import com.tavi.tavi_mrs.service.thong_bao.ThongBaoService;
import com.tavi.tavi_mrs.service_impl.hang_hoa.HangHoaServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.util.*;

@Service
public class ThongBaoServiceImpl implements ThongBaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThongBaoServiceImpl.class);

    @Autowired
    private ThongBaoRepo thongBaoRepo;

    @Autowired
    private NguoiDungRepo nguoiDungRepo;

    @Autowired
    private TaiLieuDinhKemRepo taiLieuDinhKemRepo;

    @Autowired
    private MailService mailService;

    @Value("${spring.upload.link-prefix}")
    String linkPrefix;

    @Override
    public Optional<ThongBao> findByIdAndXoa(int id, boolean xoa) {
        try {
            return thongBaoRepo.findByIdAndXoa(id,xoa);
        }catch (Exception ex) {
            LOGGER.error("find-thong-bao-by-id-error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<ThongBao> save(ThongBao thongBao) {
        try{
            return Optional.ofNullable(thongBaoRepo.save(thongBao));
        }catch (Exception ex) {
            LOGGER.error("save-thong-bao-error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public boolean deleted(int id) {
        try{
            return thongBaoRepo.deleted(id)>0?true:false;
        }catch (Exception ex) {
            LOGGER.error("delete-thong-bao-error :" + ex);
            return false;
        }
    }

    @Override
    public Page<ThongBao> findAll(Pageable pageable) {
        try {
            return thongBaoRepo.findAll(pageable);
        }catch (Exception ex) {
            LOGGER.error("save-thong-bao-error : " + ex);
            return null;
        }
    }

    @Override
    public Page<ThongBao> findByThoiGianGuiAndTieuDe(Date ngayDau, Date ngayCuoi, String tieuDe, Pageable pageable) {
        try {
            return thongBaoRepo.findByThoiGianGuiAndTieuDe(ngayDau,ngayCuoi,tieuDe,pageable);
        }catch (Exception ex) {
            LOGGER.error("find-thong-bao-by-thoi-gian-and-tieu-de: "+ex);
            return null;
        }
    }

//    @Override
//    public ResponseEntity<JsonResult> sendAnnouncement(AnnounceForm announceForm) throws MessagingException {
//        try{
//            ThongBao tb = new ThongBao();
//            tb.setTieuDe(announceForm.getTieuDe());
//            tb.setNoiDung(announceForm.getNoiDung());
//            Timestamp date = new Timestamp(new Date().getTime());
//            tb.setThoiGianGui(date);
//            tb.setSoLuongDonViNhan(announceForm.getSoLuongDonViNhan());
//            tb.setYeuCauPhanHoi(announceForm.getYeuCauPhanHoi());
//            List<NguoiDung> nguoiDungs = nguoiDungRepo.findByIdList(announceForm.getListId());
////            tb.setThongBaoNguoiNhan(nguoiDungs);
//            List<String> listEmail = new ArrayList<>();
//            nguoiDungs.forEach(l -> listEmail.add(l.getEmail()));
//            tb.setXoa(false);
//            boolean sendMail = sendAnnouncementViaEmail(announceForm.getTieuDe(),announceForm.getNoiDung()
//                                    ,listEmail,announceForm.getFileDinhKemList());
//            if (!sendMail){
//                return JsonResult.serverError("Khong the gui duoc mail");
//            }
//            tb.setHinhThucGui(1);
//            return Optional.ofNullable(thongBaoRepo.save(tb))
//                    .map(thongBao -> {
//                        List<String> taiLieus = announceForm.getFileDinhKemList() ;
//                        for(String str : taiLieus){
//                            TaiLieuDinhKem tl = new TaiLieuDinhKem();
//                            tl.setThongBao(thongBao);
//                            tl.setDuongDan(str);
//                            if (taiLieuDinhKemRepo.save(tl) == null) {
//                                JsonResult.saveError("Dinh kem tai lieu khong thanh cong");
//                            }
//                        }
//                        return JsonResult.success(thongBao);
//                    })
//                    .orElse(JsonResult.saveError("Da gui nhung khong the luu thong bao vao he thong"));
//        }catch (Exception ex){
//            System.err.println("Error : " + ex);
//            return null;
//        }
//    }

    private boolean sendAnnouncementViaEmail(String tieuDe, String noiDung, List<String> emailList, List<String> fileDinhKemList) throws MessagingException {
        try {
            mailService.sendMailWithAttachment(Arrays.copyOf(emailList.toArray(), emailList.size(), String[].class), tieuDe, noiDung, fileDinhKemList);
            return true;
        } catch (Exception ex) {
            LOGGER.error("sendAnnounmentViaEmail error", ex);
            ex.printStackTrace();
            return false;
        }
    }
}
