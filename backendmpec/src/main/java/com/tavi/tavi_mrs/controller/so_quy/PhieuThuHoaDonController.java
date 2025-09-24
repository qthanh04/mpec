package com.tavi.tavi_mrs.controller.so_quy;

import com.tavi.tavi_mrs.entities.hoa_don.HoaDon;
import com.tavi.tavi_mrs.entities.json.JsonResult;
import com.tavi.tavi_mrs.entities.phieu_tra_hang_nhap.PhieuTraHangNhap;
import com.tavi.tavi_mrs.entities.so_quy.PhieuThuHoaDon;
import com.tavi.tavi_mrs.payload.hoa_don.TienKhachTra;
import com.tavi.tavi_mrs.payload.hoa_don.TienKhachTraList;
import com.tavi.tavi_mrs.payload.nha_cung_cap.tra_hang.TienNhaCungCapTra;
import com.tavi.tavi_mrs.payload.nha_cung_cap.tra_hang.TienNhaCungCapTraList;
import com.tavi.tavi_mrs.service.hoa_don.HoaDonService;
import com.tavi.tavi_mrs.service.phieu_tra_hang_nhap.PhieuTraHangNhapService;
import com.tavi.tavi_mrs.service.so_quy.PhieuThuHoaDonService;
import com.tavi.tavi_mrs.service.so_quy.PhieuThuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin/phieu-thu-hoa-don-tra-hang-nhap")
public class PhieuThuHoaDonController {

    @Autowired
    PhieuTraHangNhapService phieuTraHangNhapService;

    @Autowired
    PhieuThuHoaDonService phieuThuHoaDonService;

    @Autowired
    HoaDonService hoaDonService;

    @Autowired
    PhieuThuService phieuThuService;

    //upload phieu thu binh thuong khi tao 1 hoa don
    @PostMapping("/hoa-don/upload")
    @ApiOperation(value = "post Phieu Thu hoa don", response = PhieuThuHoaDon.class)
    public ResponseEntity<JsonResult> savePhieuThuHoaDonKhiTaoHoaDon(@RequestParam("hoa-don-id") int hoaDonId,
                                           @RequestParam("phieu-thu-id") int phieuThuId,
                                           @RequestParam("tien-da-tra") float tienDaTra) {
        PhieuThuHoaDon phieuThuHoaDon = new PhieuThuHoaDon();
        return hoaDonService.findById(hoaDonId, false)
                .map(hoaDon -> {
                    phieuThuHoaDon.setHoaDon(hoaDon);
                    return phieuThuService.findByIdAndXoa(phieuThuId, false)
                            .map(phieuThu -> {
                                phieuThuHoaDon.setPhieuThu(phieuThu);
                                phieuThuHoaDon.setTienDaTra(tienDaTra);
                                return phieuThuHoaDonService.save(phieuThuHoaDon)
                                        .map(JsonResult::uploaded)
                                        .orElse(JsonResult.saveError("PhieuThuHoaDon"));
                            }).orElse(JsonResult.parentNotFound("PhieuThu"));
                }).orElse(JsonResult.parentNotFound("HoaDon"));
    }

    //upload phieu thu binh thuong khi tao 1 phieu tra hang nhap
    @PostMapping("/phieu-tra-hang-nhap/upload")
    @ApiOperation(value = "post Phieu Thu hoa don", response = PhieuThuHoaDon.class)
    public ResponseEntity<JsonResult> savePhieuThuHoaDonKhiTaoPTHN(@RequestParam("phieu-tra-hang-nhap-id") int phieuTraHangNhapId,
                                           @RequestParam("phieu-thu-id") int phieuThuId,
                                           @RequestParam("tien-da-tra") float tienDaTra) {
        PhieuThuHoaDon phieuThuHoaDon = new PhieuThuHoaDon();
        return phieuTraHangNhapService.findByIdAndXoa(phieuTraHangNhapId, false)
                .map(phieuTraHangNhap -> {
                    phieuThuHoaDon.setPhieuTraHangNhap(phieuTraHangNhap);
                    return phieuThuService.findByIdAndXoa(phieuThuId, false)
                            .map(phieuThu -> {
                                phieuThuHoaDon.setPhieuThu(phieuThu);
                                phieuThuHoaDon.setTienDaTra(tienDaTra);
                                return phieuThuHoaDonService.save(phieuThuHoaDon)
                                        .map(JsonResult::uploaded)
                                        .orElse(JsonResult.saveError("PhieuThuHoaDon"));
                            }).orElse(JsonResult.parentNotFound("PhieuThu"));
                }).orElse(JsonResult.parentNotFound("HoaDon"));
    }



    //o trang so quy va lap 1 phieu thu hoa don
    @PostMapping("/upload-phieu-thu")
    @ApiOperation(value = "post Phieu Thu hoa don", response = PhieuThuHoaDon.class)
    public ResponseEntity<JsonResult> upLoad(@RequestBody TienKhachTraList tienKhachTraList,
                                             @RequestParam("phieu-thu-id") int phieuThuId,
                                             @RequestParam("tong-tien-tra") Float tongTienTra) {

        List<TienKhachTra> tienKhachTraLists = tienKhachTraList.getTienKhachTras();
        try {
            for (int i = 0; i < tienKhachTraLists.size(); i++) {
                PhieuThuHoaDon phieuThuHoaDon = new PhieuThuHoaDon();
                HoaDon hoaDon = hoaDonService.findByIdAndXoa(tienKhachTraLists.get(i).getHoaDonId(), false);
                hoaDonService.updateTienKhachTra(hoaDon.getTienKhachTra() + tienKhachTraLists.get(i).getTienKhachTra(), tienKhachTraLists.get(i).getHoaDonId(),
                        hoaDon.getTongTien()-(hoaDon.getTienKhachTra() + tienKhachTraLists.get(i).getTienKhachTra()));
                phieuThuHoaDon.setHoaDon(hoaDon);
                phieuThuHoaDon.setPhieuThu(phieuThuService.findByIdAndXoa(phieuThuId, false).get());
                phieuThuHoaDon.setTienDaTra(tongTienTra);
                phieuThuHoaDonService.save(phieuThuHoaDon)
                        .map(JsonResult::uploaded)
                        .orElse(JsonResult.saveError("PhieuThuHoaDon"));
            }
            return JsonResult.uploaded("PhieuThu");
        } catch (Exception ex) {
            return JsonResult.saveError("PhieuThuHoaDon");
        }
    }

//    o trang so quy va lap 1 phieu thu tra hang nhap, trả theo phiếu nhập hàng
    @PostMapping("/upload-phieu-tra-hang-nhap")
    @ApiOperation(value = "post Phieu Thu Hoa Don", response = PhieuThuHoaDon.class)
    public ResponseEntity<JsonResult> uploadPhieuThuHoaDonKhiTaoPhieuThuTraHangNhap(@RequestBody TienNhaCungCapTraList tienNhaCungCapTraList,
                                                                                    @RequestParam("phieu-tra-hang-id") int phieuTraHangId,
                                                                                    @RequestParam("tong-tien-tra") Float tongTienTra) {
        List<TienNhaCungCapTra> tienNhaCungCapTraList1 = tienNhaCungCapTraList.getTienNhaCungCapTras();
        try {
            for (int i = 0; i< tienNhaCungCapTraList1.size(); i++) {
                PhieuThuHoaDon phieuThuHoaDon = new PhieuThuHoaDon();
                Optional<PhieuTraHangNhap>  phieuTraHangNhap = phieuTraHangNhapService.findByIdAndXoa(tienNhaCungCapTraList1.get(i).getPhieuTraHangNhapId(), false);
                phieuTraHangNhapService.updateTienNhaCungCapTra(tienNhaCungCapTraList1.get(i).getTienDaTra() + phieuTraHangNhap.get().getTienDaTra(), tienNhaCungCapTraList1.get(i).getPhieuTraHangNhapId(),
                        phieuTraHangNhap.get().getTongTien() - (phieuTraHangNhap.get().getTienDaTra() + tienNhaCungCapTraList1.get(i).getTienDaTra()));
                phieuThuHoaDon.setPhieuTraHangNhap(phieuTraHangNhap.get());
                phieuThuHoaDon.setPhieuThu(phieuThuService.findByIdAndXoa(phieuTraHangId, false).get());
                phieuThuHoaDon.setTienDaTra(tongTienTra);
                phieuThuHoaDonService.save(phieuThuHoaDon)
                        .map(JsonResult::uploaded)
                        .orElse(JsonResult.saveError("PhieuThuHoaDon"));
            }
            return JsonResult.uploaded("PhieuThu");
        } catch (Exception ex) {
            return JsonResult.saveError("PhieuThuHoaDon");
        }
    }
}
