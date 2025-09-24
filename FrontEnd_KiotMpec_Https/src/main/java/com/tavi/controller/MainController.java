package com.tavi.controller;

import com.tavi.model.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

@Controller
public class MainController {

    @RequestMapping(value = {"/","/dang-nhap"}, method = RequestMethod.GET)
    public String dangNhap() {
        return "dang-nhap";
    }

    @RequestMapping(value = {"/tim-tai-khoan"}, method = RequestMethod.GET)
    public String timTaiKhoan() { return "tim-tai-khoan";
    }

    @RequestMapping(value = {"/dat-lai-mat-khau"}, method = RequestMethod.GET)
    public String datLaiMatKhau() {
        return "dat-lai-mat-khau";
    }

    @RequestMapping(value = {"/nhap-ma-bao-mat"}, method = RequestMethod.GET)
    public String nhapMaBaoMat() {
        return "nhap-ma-bao-mat";
    }

    @RequestMapping(value = {"/trang-dau"}, method = RequestMethod.GET)
    public String trangChu() {
        if(Session.isValiadteToken()) {
            Session.setCurrentUrl("/trang-dau");
            return "trang-dau";
        }else{
            Session.setCurrentUrl("/trang-dau");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/huong-dan-su-dung"}, method = RequestMethod.GET)
    public String huongDanSuDung() {
        if(Session.isValiadteToken()) {
            return "huong-dan-su-dung";
        }else{
            Session.setCurrentUrl("/huong-dan-su-dung");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/hang-hoa"}, method = RequestMethod.GET)
    public String hangHoa() {
        if(Session.isValiadteToken()) {
            return "hang-hoa";
        }else{
            Session.setCurrentUrl("/hang-hoa");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/danh-sach-hang-hoa"}, method = RequestMethod.GET)
    public String danhSachHangHoa() {
        if(Session.isValiadteToken()) {
            return "danh-sach-hang-hoa";
        }else{
            Session.setCurrentUrl("/danh-sach-hang-hoa");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/danh-muc-hang-hoa"}, method = RequestMethod.GET)
    public String danhMucHangHoa() {
        if(Session.isValiadteToken()) {

            return "danh-muc-hang-hoa";
        }else{
            Session.setCurrentUrl("/danh-muc-hang-hoa");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/thong-tin-nhan-vien"}, method = RequestMethod.GET)
    public String quanLyThongTinNhanVien() {
        if(Session.isValiadteToken()) {
            return "thong-tin-nhan-vien";
        }else{
            Session.setCurrentUrl("/thong-tin-nhan-vien");
            return dangNhap();
        }
    }

    @RequestMapping(value = {"/chi-tiet-tai-khoan"}, method = RequestMethod.GET)
    public String chiTietTaiKhoan() {
        if(Session.isValiadteToken()) {
            return "chi-tiet-tai-khoan";
        }else{
            Session.setCurrentUrl("/chi-tiet-tai-khoan");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/danh-muc-nhan-vien"}, method = RequestMethod.GET)
    public String danhMucNhanVien() {
        if(Session.isValiadteToken()) {
            return "danh-muc-nhan-vien";
        }else{
            Session.setCurrentUrl("/danh-muc-nhan-vien");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/danh-sach-nha-cung-cap"}, method = RequestMethod.GET)
    public String danhSachNhaCungCap() {
        if(Session.isValiadteToken()) {
            return "danh-sach-nha-cung-cap";
        }else{
            Session.setCurrentUrl("/danh-sach-nha-cung-cap");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/danh-sach-khach-hang"}, method = RequestMethod.GET)
    public String danhSachKhachHang() {
        if(Session.isValiadteToken()) {
            return "danh-sach-khach-hang";
        }else{
            Session.setCurrentUrl("/danh-sach-khach-hang");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/thong-tin-cong-ty"}, method = RequestMethod.GET)
    public String quanLyThongTinCongTy() {
        if(Session.isValiadteToken()) {
            return "thong-tin-cong-ty";
        }else{
            Session.setCurrentUrl("/thong-tin-cong-ty");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/chinh-sach"}, method = RequestMethod.GET)
    public String xemChinhSach() {
        if(Session.isValiadteToken()) {
            return "chinh-sach";
        }else{
            Session.setCurrentUrl("/chinh-sach");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/quan-ly-chi-nhanh"}, method = RequestMethod.GET)
    public String quanLyThongTinChiNhanh() {
        if(Session.isValiadteToken()) {
            return "quan-ly-chi-nhanh";
        }else{
            Session.setCurrentUrl("/quan-ly-chi-nhanh");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/sua-dieu-khoan"}, method = RequestMethod.GET)
    public String suaDieuKhoan() {
        if(Session.isValiadteToken()) {
            return "sua-dieu-khoan";
        }else{
            Session.setCurrentUrl("/sua-dieu-khoan");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/dieu-khoan"}, method = RequestMethod.GET)
    public String xemDieuKhoan() {
        if(Session.isValiadteToken()) {
            return "dieu-khoan";
        }else{
            Session.setCurrentUrl("/dieu-khoan");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/sua-chinh-sach"}, method = RequestMethod.GET)
    public String suaChinhSach() {
        if(Session.isValiadteToken()) {
            return "sua-chinh-sach";
        }else{
            Session.setCurrentUrl("/sua-chinh-sach");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/danh-sach-nhap-hang"}, method = RequestMethod.GET)
    public String quanLyNhapHang() {
        if(Session.isValiadteToken()) {
            return "danh-sach-nhap-hang";
        }else{
            Session.setCurrentUrl("/danh-sach-nhap-hang");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/chi-tiet-nhap-hang"}, method = RequestMethod.GET)
    public String chiTietNhapHang() {
        if(Session.isValiadteToken()) {
            return "chi-tiet-nhap-hang";
        }else{
            Session.setCurrentUrl("/chi-tiet-nhap-hang");
            return "dang-nhap";
        }
    }


    @RequestMapping(value = {"/nhap-hang-chi-tiet"}, method = RequestMethod.GET)
    public String nhapHangChiTiet() {
        if(Session.isValiadteToken()) {
            return "nhap-hang-chi-tiet";
        }else{
            Session.setCurrentUrl("/nhap-hang-chi-tiet");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/chat"}, method = RequestMethod.GET)
    public String chat() {
        if(Session.isValiadteToken()) {
            return "chat";
        }else{
            Session.setCurrentUrl("/chat");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/video-call"}, method = RequestMethod.GET)
    public String videoCall() {
        if(Session.isValiadteToken()) {
            return "video-call";
        }else{
            Session.setCurrentUrl("/video-call");
            return "dang-nhap";
        }
    }


    @RequestMapping(value = {"/danh-sach-tra-khach"}, method = RequestMethod.GET)
    public String danhSachTraKhach() {
        if(Session.isValiadteToken()) {
            return "danh-sach-tra-khach";
        }else{
            Session.setCurrentUrl("/danh-sach-tra-khach");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/danh-sach-hoa-don-de-tra"}, method = RequestMethod.GET)
    public String danhSachHoaDon() {
        if(Session.isValiadteToken()) {
            return "danh-sach-hoa-don-de-tra";
        }else{
            Session.setCurrentUrl("/danh-sach-hoa-don-de-tra");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/chi-tiet-hoa-don-de-tra"}, method = RequestMethod.GET)
    public String danhSachChiTietHoaDon() {
        if(Session.isValiadteToken()) {
            return "chi-tiet-hoa-don-de-tra";
        }else{
            Session.setCurrentUrl("/chi-tiet-hoa-don-de-tra");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/danh-sach-phieu-nhap-de-tra"}, method = RequestMethod.GET)
    public String danhSachPhieuNhapDeTra() {
        if(Session.isValiadteToken()) {
            return "danh-sach-phieu-nhap-de-tra";
        }else{
            Session.setCurrentUrl("/danh-sach-phieu-nhap-de-tra");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/danh-sach-phieu-tra-hang-nhap"}, method = RequestMethod.GET)
    public String danhSachPhieuTraHang() {
        if(Session.isValiadteToken()) {
            return "danh-sach-phieu-tra-hang-nhap";
        }else{
            Session.setCurrentUrl("/danh-sach-phieu-tra-hang-nhap");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/phieu-nhap-chi-tiet-de-tra"}, method = RequestMethod.GET)
    public String danhSachPhieuNhapChiTietDeTra() {
        if(Session.isValiadteToken()) {
            return "phieu-nhap-chi-tiet-de-tra";
        }else{
            Session.setCurrentUrl("/phieu-nhap-chi-tiet-de-tra");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/chi-tiet-hoa-don"}, method = RequestMethod.GET)
    public String chiTietHoaDon() {
        if(Session.isValiadteToken()) {
            return "chi-tiet-hoa-don";
        }else{
            Session.setCurrentUrl("/chi-tiet-hoa-don");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/hoa-don-chi-tiet"}, method = RequestMethod.GET)
    public String hoaDonChiTiet() {
        if(Session.isValiadteToken()) {
            return "hoa-don-chi-tiet";
        }else{
            Session.setCurrentUrl("/hoa-don-chi-tiet");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/danh-sach-hoa-don"}, method = RequestMethod.GET)
    public String danhSachHoaDon1() {
        if(Session.isValiadteToken()) {
            return "danh-sach-hoa-don";
        }else{
            Session.setCurrentUrl("/danh-sach-hoa-don");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/thiet-lap-gia"}, method = RequestMethod.GET)
    public String thietLapGia() {
        if(Session.isValiadteToken()) {
            return "thiet-lap-gia";
        }else{
            Session.setCurrentUrl("/thiet-lap-gia");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/tong-quan"}, method = RequestMethod.GET)
    public String tongQuan(HttpServletResponse httpServletResponse) {
        if(Session.isValiadteToken()) {
            return "tong-quan";
        }else{
            Session.setCurrentUrl("/tong-quan");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/quan-ly-file"}, method = RequestMethod.GET)
    public String quanLyFileExcel() {
        if(Session.isValiadteToken()) {
           return "quan-ly-file";
       }else{
          Session.setCurrentUrl("/quan-ly-file");
           return "dang-nhap";
        }
    }
    @RequestMapping(value = {"/quan-ly-giao-hang"}, method = RequestMethod.GET)
    public String quanLyGiaoHang() {
        if(Session.isValiadteToken()) {
            return "quan-ly-giao-hang";
        }else{
            Session.setCurrentUrl("/quan-ly-giao-hang");
            return "dang-nhap";
        }
    }
    @RequestMapping(value = {"/don-vi-giao-hang"}, method = RequestMethod.GET)
    public String donViGiaoHang() {
        if(Session.isValiadteToken()) {
            return "don-vi-giao-hang";
        }else{
            Session.setCurrentUrl("/don-vi-giao-hang");
            return "dang-nhap";
        }
    }


    @RequestMapping(value = {"/thong-bao"}, method = RequestMethod.GET)
    public String thongBaoNguoiNhan() {
        if(Session.isValiadteToken()) {
            return "thong-bao";
        }else{
            Session.setCurrentUrl("/thong-bao");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/quan-ly-thong-bao"}, method = RequestMethod.GET)
    public String quanLyThongBao() {
        if(Session.isValiadteToken()) {
            return "quan-ly-thong-bao";
        }else{
            Session.setCurrentUrl("/quan-ly-thong-bao");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/ca-lam-viec"}, method = RequestMethod.GET)
    public String caLamViec() {
        if(Session.isValiadteToken()) {
            return "ca-lam-viec";
        }else{
            Session.setCurrentUrl("/ca-lam-viec");
            return "dang-nhap";
        }
    }
    @RequestMapping(value = {"/chi-tiet-van-chuyen"}, method = RequestMethod.GET)
    public String chiTietVanChuyen() {
        if(Session.isValiadteToken()) {
            return "chi-tiet-van-chuyen";
        }else{
            Session.setCurrentUrl("/chi-tiet-van-chuyen");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/phieu-thu"}, method = RequestMethod.GET)
    public String phieuThu() {
        if(Session.isValiadteToken()) {
            return "phieu-thu";
        }else{
            Session.setCurrentUrl("/phieu-thu");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/phieu-chi"}, method = RequestMethod.GET)
    public String phieuChi() {
        if(Session.isValiadteToken()) {
            return "phieu-chi";
        }else{
            Session.setCurrentUrl("/phieu-chi");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/nhat-ki-hoat-dong"}, method = RequestMethod.GET)
    public String nhatKiHoatDong() {
        if(Session.isValiadteToken()) {
            return "nhat-ki-hoat-dong";
        }else{
            Session.setCurrentUrl("/nhat-ki-hoat-dong");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/cong-no"}, method = RequestMethod.GET)
    public String congNo() {
        if(Session.isValiadteToken()) {
            return "cong-no";
        }else{
            Session.setCurrentUrl("/cong-no");
            return "dang-nhap";
        }
    }

    @RequestMapping(value = {"/cong-no-nha-cung-cap"}, method = RequestMethod.GET)
    public String congNoNhaCungCap() {
        if(Session.isValiadteToken()) {
            return "cong-no-nha-cung-cap";
        }else{
            Session.setCurrentUrl("/cong-no-nha-cung-cap");
            return "dang-nhap";
        }
    }

}
