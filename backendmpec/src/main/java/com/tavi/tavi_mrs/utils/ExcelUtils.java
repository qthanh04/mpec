package com.tavi.tavi_mrs.utils;

import com.tavi.tavi_mrs.entities.ca_lam_viec.NguoiDungCaLamViec;
import com.tavi.tavi_mrs.entities.hang_hoa.ChiNhanhHangHoa;
import com.tavi.tavi_mrs.entities.hang_hoa.HangHoa;
import com.tavi.tavi_mrs.entities.hoa_don.HoaDon;
import com.tavi.tavi_mrs.entities.json.TrangThai;
import com.tavi.tavi_mrs.entities.json.TrangThaiKhachHang;
import com.tavi.tavi_mrs.entities.json.TrangThaiNguoiDung;
import com.tavi.tavi_mrs.entities.khach_hang.KhachHang;
import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDung;
import com.tavi.tavi_mrs.entities.nha_cung_cap.NhaCungCap;
import com.tavi.tavi_mrs.entities.phieu_nhap_hang.PhieuNhapHang;
import com.tavi.tavi_mrs.entities.phieu_nhap_hang.PhieuNhapHangChiTiet;
import com.tavi.tavi_mrs.entities.phieu_tra_hang_nhap.PhieuTraHangNhap;
import com.tavi.tavi_mrs.entities.phieu_tra_khach.PhieuTraKhach;
import com.tavi.tavi_mrs.entities.so_quy.PhieuChi;
import com.tavi.tavi_mrs.entities.so_quy.PhieuThu;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ExcelUtils {


    public static Object getCellValue(Cell cell) {
        if (cell != null) {
            CellType cellType = cell.getCellTypeEnum();
            switch (cellType) {
                case BOOLEAN:
                    return cell.getBooleanCellValue();
                case NUMERIC:
                    return (int) cell.getNumericCellValue();
                case STRING:
                    return cell.getStringCellValue();
                default:
                    return null;
            }
        } else return "";
    }

    private static String formatDateTime(Date date) {
        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String dateTimeFormat = simpleDateFormat.format(date);
        return dateTimeFormat;
    }

    private static String formatDate(Date date) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String dateTimeFormat = simpleDateFormat.format(date);
        return dateTimeFormat;
    }


    public static Iterator<Row> getRows(String url, int sheetIndex) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(url));
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
        return sheet.iterator();
    }

    public static Iterator<Row> getRows(File file, int sheetIndex) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
        return sheet.iterator();
    }

    public static XSSFWorkbook createListBillExcelHoaDon(List<HoaDon> billList) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Bill Information");
        XSSFFont font = workbook.createFont();
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        //Write Title
        {
            Row row = sheet.createRow(0);
            //STT
            Cell cellSTT = row.createCell(0, CellType.STRING);
            cellSTT.setCellValue("STT");
            cellSTT.setCellStyle(style);
            //Mã hoa don
            Cell cellMHD = row.createCell(1, CellType.STRING);
            cellMHD.setCellValue("Code");
            cellMHD.setCellStyle(style);
            //Thoi gian tao
            Cell cellTTK = row.createCell(2, CellType.STRING);
            cellTTK.setCellValue("Created Time");
            cellTTK.setCellStyle(style);
            //Ten khach hang
            Cell cellTT = row.createCell(3, CellType.STRING);
            cellTT.setCellValue("Employee name");
            cellTT.setCellStyle(style);
            //ten to chuc
            Cell cellCN = row.createCell(4, CellType.STRING);
            cellTT.setCellValue("Customer name");
            cellTT.setCellStyle(style);
            //So dien thoai khach hang
            Cell cellHT = row.createCell(5, CellType.STRING);
            cellHT.setCellValue("Total money");
            cellHT.setCellStyle(style);
            //Dia chi
            Cell cellEmail = row.createCell(6, CellType.STRING);
            cellEmail.setCellValue("Customer pay money");
            cellEmail.setCellStyle(style);

            Cell cellpay = row.createCell(7, CellType.STRING);
            cellpay.setCellValue("Customer payed money");
            cellpay.setCellStyle(style);
            //Trang Thai
            Cell cellSDT = row.createCell(8, CellType.STRING);
            cellSDT.setCellValue("Status");
            cellSDT.setCellStyle(style);
            //Note bill
            Cell cellDC = row.createCell(9, CellType.STRING);
            cellDC.setCellValue("Note bill");
            cellDC.setCellStyle(style);

        }
        //Write data
        int rowNum = 1;
        for (HoaDon bill : billList) {
            Row row = sheet.createRow(rowNum++);
            //STT
            Cell cellSTT = row.createCell(0, CellType.STRING);
            cellSTT.setCellValue(rowNum - 1);

            Cell cellMTK = row.createCell(1, CellType.STRING);
            cellMTK.setCellValue(bill.getMa());

            Cell cellTTK = row.createCell(2, CellType.STRING);
            cellTTK.setCellValue(formatDateTime(bill.getThoiGian()));

            Cell cellTT = row.createCell(3, CellType.STRING);
            cellTT.setCellValue(bill.getNguoiDung().getHoVaTen());

            Cell cellCN = row.createCell(4, CellType.STRING);
            cellTT.setCellValue(bill.getKhachHang().getTenKhachHang());

            Cell cellHT = row.createCell(5, CellType.STRING);
            cellHT.setCellValue(bill.getTongTien());

            Cell cellEmail = row.createCell(6, CellType.STRING);
            try {
                cellEmail.setCellValue(bill.getTienKhachTra());
            }catch (NullPointerException ex){
                cellEmail.setCellValue("");
            }
            Cell cellSDT = row.createCell(7, CellType.STRING);
            try{
                cellSDT.setCellValue(bill.getTienTraLaiKhach());
            }catch (NullPointerException ex){
                cellSDT.setCellValue("");
            }

            Cell cellDC = row.createCell(8, CellType.STRING);
            try {
                cellDC.setCellValue(TrangThai.trangThai.get(bill.getTrangThai()));
            }catch (NullPointerException ex){
                cellDC.setCellValue("");
            }

            Cell cellGT = row.createCell(9, CellType.STRING);
            try {
                cellGT.setCellValue(bill.getGhiChu());
            }catch (NullPointerException ex){
                cellGT.setCellValue("");
            }

        }
        return workbook;
    }
    public static XSSFWorkbook createListBillExcelNguoiDungCaLamViec(List<NguoiDungCaLamViec> billList) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Bill Information");
        XSSFFont font = workbook.createFont();
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        //Write Title
        {
            Row row = sheet.createRow(0);
            //STT
            Cell cellSTT = row.createCell(0, CellType.STRING);
            cellSTT.setCellValue("STT");
            cellSTT.setCellStyle(style);
            //Nguoi Dung
            Cell cellNDID = row.createCell(1, CellType.STRING);
            cellNDID.setCellValue("Nguoi dung ID");
            cellNDID.setCellStyle(style);
            //Ca lam viec
            Cell cellCLVID = row.createCell(2, CellType.STRING);
            cellCLVID.setCellValue("Ca lam viec ID");
            cellCLVID.setCellStyle(style);
            //ngay thang
            Cell cellNT = row.createCell(3, CellType.STRING);
            cellNT.setCellValue("Ngay thang");
            cellNT.setCellStyle(style);
            //check in
            Cell cellCI = row.createCell(4, CellType.STRING);
            cellCI.setCellValue("Check in");
            cellCI.setCellStyle(style);
            //check out
            Cell cellCO = row.createCell(5, CellType.STRING);
            cellCO.setCellValue("Check out");
            cellCO.setCellStyle(style);
            //Status check in
            Cell cellSCI = row.createCell(6, CellType.STRING);
            cellSCI.setCellValue("Status check in");
            cellSCI.setCellStyle(style);
            //Status check out
            Cell cellSCO = row.createCell(7, CellType.STRING);
            cellSCO.setCellValue("Status check out");
            cellSCO.setCellStyle(style);
        }
        //Write data
        int rowNum = 1;
        for (NguoiDungCaLamViec bill : billList) {
            Row row = sheet.createRow(rowNum++);
            //STT
            Cell cellSTT = row.createCell(0, CellType.STRING);
            cellSTT.setCellValue(rowNum - 1);

            Cell cellNDID = row.createCell(1, CellType.STRING);
            cellNDID.setCellValue(bill.getNguoiDung().getHoVaTen());

            Cell cellCLVID = row.createCell(2, CellType.STRING);
            cellCLVID.setCellValue(bill.getCaLamViec().getTen());

            Cell cellNT = row.createCell(3, CellType.STRING);
            cellNT.setCellValue(bill.getNgayThang());

            Cell cellCI = row.createCell(4, CellType.STRING);
            cellCI.setCellValue(bill.getCheckin());

            Cell cellCO = row.createCell(5, CellType.STRING);
            cellCO.setCellValue(bill.getCheckout());

            Cell cellSCI = row.createCell(6, CellType.STRING);
            cellSCI.setCellValue(statusCheck(bill.getStatusCheckout()));

            Cell cellSCO = row.createCell(7, CellType.STRING);
            cellSCO.setCellValue(statusCheck(bill.getStatusCheckin()));

        }
        return workbook;
    }

    public static String statusCheck(int status){
        String statusCheck;
        if(status==1){
            statusCheck="Đúng giờ";
        }else{
            statusCheck="Sai giờ";
        }
        return statusCheck;
    }

    public static XSSFWorkbook createDanhSachTaiKhoanExcel(List<NguoiDung> nguoiDungsList) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Thông tin tài khoản");
        XSSFFont font = workbook.createFont();
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        //Write Title
        {
            Row row = sheet.createRow(0);
            //STT
            Cell cellSTT = row.createCell(0, CellType.STRING);
            cellSTT.setCellValue("STT");
            cellSTT.setCellStyle(style);
            //Mã tài khoản
            Cell cellMTK = row.createCell(1, CellType.STRING);
            cellMTK.setCellValue("Mã tài khoản");
            cellMTK.setCellStyle(style);
            //Tên tài khoản
            Cell cellTTK = row.createCell(2, CellType.STRING);
            cellTTK.setCellValue("Tên tài khoản");
            cellTTK.setCellStyle(style);
            //Trạng thái
            Cell cellTT = row.createCell(3, CellType.STRING);
            cellTT.setCellValue("Trạng thái");
            cellTT.setCellStyle(style);
            //Họ và tên
            Cell cellHT = row.createCell(4, CellType.STRING);
            cellHT.setCellValue("Họ và tên");
            cellHT.setCellStyle(style);
            //Email
            Cell cellEmail = row.createCell(5, CellType.STRING);
            cellEmail.setCellValue("Email");
            cellEmail.setCellStyle(style);
            //Số điện thoại
            Cell cellSDT = row.createCell(6, CellType.STRING);
            cellSDT.setCellValue("Số điện thoại");
            cellSDT.setCellStyle(style);
            //Địa chỉ
            Cell cellDC = row.createCell(7, CellType.STRING);
            cellDC.setCellValue("Địa chỉ");
            cellDC.setCellStyle(style);
            //Giới tính
            Cell cellGT = row.createCell(8, CellType.STRING);
            cellGT.setCellValue("Giới tính");
            cellGT.setCellStyle(style);
            //Ngày sinh
            Cell cellNS = row.createCell(9, CellType.STRING);
            cellNS.setCellValue("Ngày sinh");
            cellNS.setCellStyle(style);
            //Thời gian khởi tạo
            Cell cellTGKT = row.createCell(10, CellType.STRING);
            cellTGKT.setCellValue("Thời gian khởi tạo");
            cellTGKT.setCellStyle(style);
            //Thời gian hết hạn
            Cell cellTGHH = row.createCell(11, CellType.STRING);
            cellTGHH.setCellValue("Thời gian hết hạn");
            cellTGHH.setCellStyle(style);
//            //File ảnh đại diện
//            Cell cellAVT = row.createCell(12, CellType.STRING);
//            cellAVT.setCellStyle(style);
//            cellAVT.setCellValue("Ảnh đại diện");
        }
        //Write data
        int rowNum = 1;
        for (NguoiDung nguoiDung : nguoiDungsList) {
            Row row = sheet.createRow(rowNum++);
            //STT
            Cell cellSTT = row.createCell(0, CellType.STRING);
            cellSTT.setCellValue(rowNum - 1);
            //Mã tài khoản
            Cell cellMTK = row.createCell(1, CellType.STRING);
            cellMTK.setCellValue(nguoiDung.getMaTaiKhoan());
            //Tên tài khoản
            Cell cellTTK = row.createCell(2, CellType.STRING);
            cellTTK.setCellValue(nguoiDung.getTaiKhoan());
            //Trạng thái
            Cell cellTT = row.createCell(3, CellType.STRING);
            cellTT.setCellValue(TrangThaiNguoiDung.trangThai.get(nguoiDung.getTrangThai()));
            //Họ và tên
            Cell cellHT = row.createCell(4, CellType.STRING);
            cellHT.setCellValue(nguoiDung.getHoVaTen());
            //Email
            Cell cellEmail = row.createCell(5, CellType.STRING);
            cellEmail.setCellValue(nguoiDung.getEmail());
            //Số điện thoại
            Cell cellSDT = row.createCell(6, CellType.STRING);
            cellSDT.setCellValue(nguoiDung.getSoDienThoai());
            //Địa chỉ
            Cell cellDC = row.createCell(7, CellType.STRING);
            cellDC.setCellValue(nguoiDung.getDiaChi());
            //Giới tính
            Cell cellGT = row.createCell(8, CellType.STRING);
            cellGT.setCellValue(nguoiDung.getGioiTinh());
            //Ngày sinh
            Cell cellNS = row.createCell(9, CellType.STRING);
            cellNS.setCellValue(nguoiDung.getNgaySinh());
            //Thời gian khởi tạo
            Cell cellTGKT = row.createCell(10, CellType.STRING);
            cellTGKT.setCellValue(nguoiDung.getThoiGianKhoiTao());
            //Thời gian hết hạn
            Cell cellTGHH = row.createCell(11, CellType.STRING);
            cellTGHH.setCellValue(nguoiDung.getThoiGianHetHan());
//            //File ảnh đại diện
//            Cell cellAVT = row.createCell(12, CellType.STRING);
//            cellAVT.setCellValue(nguoiDung.getUrlAnhDaiDien());
        }
        return workbook;
    }

    public static XSSFWorkbook createDanhSachPhieuNhapExcel(List<PhieuNhapHang> phieuNhapHangs) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Danh sách phiếu nhập hàng");
        XSSFFont font = workbook.createFont();
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);

        //Write Title

        Row row = sheet.createRow(0);

        Cell cellSTT = row.createCell(0, CellType.STRING);
        cellSTT.setCellValue("STT");
        cellSTT.setCellStyle(style);

        Cell cellMa = row.createCell(1, CellType.STRING);
        cellMa.setCellValue("Mã phiếu");
        cellMa.setCellStyle(style);

        Cell cellNV = row.createCell(2, CellType.STRING);
        cellNV.setCellValue("Nhân viên");
        cellNV.setCellStyle(style);

        Cell cellNCC = row.createCell(3, CellType.STRING);
        cellNCC.setCellValue("Nhà cung cấp");
        cellNCC.setCellStyle(style);

        Cell cellTG = row.createCell(4, CellType.STRING);
        cellTG.setCellValue("Thời gian");
        cellTG.setCellStyle(style);

        Cell cellTongTien = row.createCell(5, CellType.STRING);
        cellTongTien.setCellValue("Tổng tiền");
        cellTongTien.setCellStyle(style);

//        Cell cellGiamGia = row.createCell(6, CellType.STRING);
//        cellGiamGia.setCellValue("Giảm giá");
//        cellGiamGia.setCellStyle(style);

        Cell cellTienPhaiTra = row.createCell(6, CellType.STRING);
        cellTienPhaiTra.setCellValue("Tiền phải trả");
        cellTienPhaiTra.setCellStyle(style);

        Cell cellTienDaTra = row.createCell(7, CellType.STRING);
        cellTienDaTra.setCellValue("Tiền đã trả");
        cellTienDaTra.setCellStyle(style);
        //Note bill
        Cell cellGhiChu = row.createCell(8, CellType.STRING);
        cellGhiChu.setCellValue("Ghi chú");
        cellGhiChu.setCellStyle(style);

        Cell cellStatus = row.createCell(9, CellType.STRING);
        cellStatus.setCellValue("Trạng thái");
        cellStatus.setCellStyle(style);

        //Write data
        int rowNum = 1;
        for (PhieuNhapHang ph : phieuNhapHangs) {
            Row tempRow = sheet.createRow(rowNum++);
            Cell tempCellSTT = tempRow.createCell(0, CellType.STRING);
            tempCellSTT.setCellValue(rowNum - 1);

            Cell tempCellMa = tempRow.createCell(1, CellType.STRING);
            tempCellMa.setCellValue(ph.getMaPhieu());

            Cell tempCellNv = tempRow.createCell(2, CellType.STRING);
            tempCellNv.setCellValue(ph.getNguoiDung().getHoVaTen());

            Cell tempCellNCC = tempRow.createCell(3, CellType.STRING);
            tempCellNCC.setCellValue(ph.getNhaCungCap().getTen());

            Cell tempCellTG = tempRow.createCell(4, CellType.STRING);
            tempCellTG.setCellValue(formatDateTime(ph.getThoiGian()));

            Cell tempCellTongTien = tempRow.createCell(5, CellType.STRING);
            tempCellTongTien.setCellValue(ph.getTongTien());

            Cell tempCellTienPhaiTra = tempRow.createCell(6, CellType.STRING);
            try {
                tempCellTienPhaiTra.setCellValue(ph.getTienPhaiTra());
            } catch (Exception ex) {
                tempCellTienPhaiTra.setCellValue(0);
            }
            Cell tempCellTienDaTra = tempRow.createCell(7, CellType.STRING);
            try {
                tempCellTienDaTra.setCellValue(ph.getTienDaTra());
            } catch (Exception ex) {
                tempCellTienDaTra.setCellValue(0);
            }

//            Cell tempCellGiamGia = tempRow.createCell(8, CellType.STRING);
//            try {
//                tempCellGiamGia.setCellValue(ph.get());
//            } catch (Exception ex) {
//                tempCellGiamGia.setCellValue(0);
//            }

            Cell tempCellGhiChu = tempRow.createCell(8, CellType.STRING);
            try {
                tempCellGhiChu.setCellValue(ph.getGhiChu());
            } catch (Exception ex) {
                tempCellGhiChu.setCellValue("");
            }

            Cell tempCellTrangThai = tempRow.createCell(9, CellType.STRING);
            try {
                tempCellTrangThai.setCellValue(TrangThai.trangThai.get(ph.getTrangThai()));
            } catch (NullPointerException ex) {
                tempCellTrangThai.setCellValue("");
            }
        }
        return workbook;
    }

    public static XSSFWorkbook createDanhSachPhieuTraHangNhapExcel(List<PhieuTraHangNhap> phieuTraHangNhaps) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Danh sách phiếu trả hàng nhập");
        XSSFFont font = workbook.createFont();
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);

        //Write Title

        Row row = sheet.createRow(0);

        Cell cellSTT = row.createCell(0, CellType.STRING);
        cellSTT.setCellValue("STT");
        cellSTT.setCellStyle(style);

        Cell cellMa = row.createCell(1, CellType.STRING);
        cellMa.setCellValue("Mã phiếu");
        cellMa.setCellStyle(style);

        Cell cellNV = row.createCell(2, CellType.STRING);
        cellNV.setCellValue("Nhân viên");
        cellNV.setCellStyle(style);

        Cell cellNCC = row.createCell(3, CellType.STRING);
        cellNCC.setCellValue("Nhà cung cấp");
        cellNCC.setCellStyle(style);

        Cell cellTG = row.createCell(4, CellType.STRING);
        cellTG.setCellValue("Thời gian");
        cellTG.setCellStyle(style);

        Cell cellTongTien = row.createCell(5, CellType.STRING);
        cellTongTien.setCellValue("Tổng tiền");
        cellTongTien.setCellStyle(style);

        Cell cellTienPhaiTra = row.createCell(6, CellType.STRING);
        cellTienPhaiTra.setCellValue("Tiền phải trả");
        cellTienPhaiTra.setCellStyle(style);

        Cell cellTienDaTra = row.createCell(7, CellType.STRING);
        cellTienDaTra.setCellValue("Tiền đã trả");
        cellTienDaTra.setCellStyle(style);

//        Cell cellGhiChu = row.createCell(8, CellType.STRING);
//        cellGhiChu.setCellValue("Ghi chú");
//        cellGhiChu.setCellStyle(style);

        Cell cellLiDo = row.createCell(8, CellType.STRING);
        cellLiDo.setCellValue("Lí do");
        cellLiDo.setCellStyle(style);

        Cell cellStatus = row.createCell(9, CellType.STRING);
        cellStatus.setCellValue("Trạng thái");
        cellStatus.setCellStyle(style);

        //Write data
        int rowNum = 1;
        for (PhieuTraHangNhap ph : phieuTraHangNhaps) {
            Row tempRow = sheet.createRow(rowNum++);
            Cell tempCellSTT = tempRow.createCell(0, CellType.STRING);
            tempCellSTT.setCellValue(rowNum - 1);

            Cell tempCellMa = tempRow.createCell(1, CellType.STRING);
            tempCellMa.setCellValue(ph.getMaPhieu());

            Cell tempCellNv = tempRow.createCell(2, CellType.STRING);
            tempCellNv.setCellValue(ph.getNguoiDung().getHoVaTen());

            Cell tempCellNCC = tempRow.createCell(3, CellType.STRING);
            tempCellNCC.setCellValue(ph.getNhaCungCap().getTen());

            Cell tempCellTG = tempRow.createCell(4, CellType.STRING);
            tempCellTG.setCellValue(formatDateTime(ph.getThoiGian()));

            Cell tempCellTongTien = tempRow.createCell(5, CellType.STRING);
            tempCellTongTien.setCellValue(ph.getTongTien());

            Cell tempCellTienPhaiTra = tempRow.createCell(6, CellType.STRING);
            tempCellTienPhaiTra.setCellValue(ph.getTienPhaiTra());

            Cell tempCellTienDaTra = tempRow.createCell(7, CellType.STRING);
            tempCellTienDaTra.setCellValue(ph.getTienDaTra());


//            Cell tempCellGhiChu = tempRow.createCell(8, CellType.STRING);
//            try {
//                tempCellGhiChu.setCellValue(ph.get());
//            } catch (Exception ex) {
//                tempCellGhiChu.setCellValue("");
//            }

            Cell tempCellLyDo = tempRow.createCell(8, CellType.STRING);
            try {
                tempCellLyDo.setCellValue(ph.getLyDo());
            } catch (Exception ex) {
                tempCellLyDo.setCellValue("");
            }

            Cell tempCellTrangThai = tempRow.createCell(9, CellType.STRING);
            try {
                tempCellTrangThai.setCellValue(TrangThai.trangThai.get(ph.getTrangThai()));
            } catch (NullPointerException ex) {
                tempCellTrangThai.setCellValue("");
            }
        }
        return workbook;
    }

    public static XSSFWorkbook createDanhSachPhieuKhachTraHangExcel(List<PhieuTraKhach> phieuTraKhaches, List<String> tenKhachHangs) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Danh sách phiếu khách trả hàng");
        XSSFFont font = workbook.createFont();
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);

        //Write Title

        Row row = sheet.createRow(0);

        Cell cellSTT = row.createCell(0, CellType.STRING);
        cellSTT.setCellValue("STT");
        cellSTT.setCellStyle(style);

        Cell cellMa = row.createCell(1, CellType.STRING);
        cellMa.setCellValue("Mã phiếu");
        cellMa.setCellStyle(style);

        Cell cellNV = row.createCell(2, CellType.STRING);
        cellNV.setCellValue("Nhân viên");
        cellNV.setCellStyle(style);

        Cell cellNCC = row.createCell(3, CellType.STRING);
        cellNCC.setCellValue("Khách hàng");
        cellNCC.setCellStyle(style);

        Cell cellTG = row.createCell(4, CellType.STRING);
        cellTG.setCellValue("Thời gian");
        cellTG.setCellStyle(style);

        Cell cellTongTien = row.createCell(5, CellType.STRING);
        cellTongTien.setCellValue("Tiền trả khách");
        cellTongTien.setCellStyle(style);

        Cell cellGhiChu = row.createCell(6, CellType.STRING);
        cellGhiChu.setCellValue("Ghi chú");
        cellGhiChu.setCellStyle(style);

        Cell cellLiDo = row.createCell(7, CellType.STRING);
        cellLiDo.setCellValue("Lí do");
        cellLiDo.setCellStyle(style);

        Cell cellStatus = row.createCell(8, CellType.STRING);
        cellStatus.setCellValue("Trạng thái");
        cellStatus.setCellStyle(style);

        //Write data
        int rowNum = 1;
        for (PhieuTraKhach ph : phieuTraKhaches) {
            Row tempRow = sheet.createRow(rowNum++);
            Cell tempCellSTT = tempRow.createCell(0, CellType.STRING);
            tempCellSTT.setCellValue(rowNum - 1);

            Cell tempCellMa = tempRow.createCell(1, CellType.STRING);
            tempCellMa.setCellValue(ph.getMa());

            Cell tempCellNv = tempRow.createCell(2, CellType.STRING);
            tempCellNv.setCellValue(ph.getNguoiDung().getHoVaTen());

            Cell tempCellNCC = tempRow.createCell(3, CellType.STRING);
            tempCellNCC.setCellValue(tenKhachHangs.get(rowNum - 2));

            Cell tempCellTG = tempRow.createCell(4, CellType.STRING);
            tempCellTG.setCellValue(formatDateTime(ph.getThoiGian()));

            Cell tempCellTongTien = tempRow.createCell(5, CellType.STRING);
            tempCellTongTien.setCellValue(ph.getTienTraKhach());


            Cell tempCellGhiChu = tempRow.createCell(6, CellType.STRING);
            try {
                tempCellGhiChu.setCellValue(ph.getGhiChu());
            } catch (Exception ex) {
                tempCellGhiChu.setCellValue("");
            }

            Cell tempCellLyDo = tempRow.createCell(7, CellType.STRING);
            try {
                tempCellLyDo.setCellValue(ph.getLyDo());
            } catch (Exception ex) {
                tempCellLyDo.setCellValue("");
            }

            Cell tempCellTrangThai = tempRow.createCell(8, CellType.STRING);
            try {
                tempCellTrangThai.setCellValue(TrangThai.trangThai.get(ph.getTrangThai()));
            } catch (NullPointerException ex) {
                tempCellTrangThai.setCellValue("");
            }
        }
        return workbook;
    }

    public static XSSFWorkbook createDanhSachKhachHangExcel(List<KhachHang> khachHangs) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Danh sách khách hàng");
        XSSFFont font = workbook.createFont();
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);

        //Write Title

        Row row = sheet.createRow(0);

        Cell cellSTT = row.createCell(0, CellType.STRING);
        cellSTT.setCellValue("STT");
        cellSTT.setCellStyle(style);

        Cell cellMa = row.createCell(1, CellType.STRING);
        cellMa.setCellValue("Mã khách hàng");
        cellMa.setCellStyle(style);

        Cell cellKH = row.createCell(2, CellType.STRING);
        cellKH.setCellValue("Tên khách hàng");
        cellKH.setCellStyle(style);

        Cell cellNCC = row.createCell(3, CellType.STRING);
        cellNCC.setCellValue("Ngày sinh");
        cellNCC.setCellStyle(style);

        Cell cellSDT = row.createCell(4, CellType.STRING);
        cellSDT.setCellValue("Số điện thoại");
        cellSDT.setCellStyle(style);

        Cell cellEmail = row.createCell(5, CellType.STRING);
        cellEmail.setCellValue("Email");
        cellEmail.setCellStyle(style);

        Cell cellFB = row.createCell(6, CellType.STRING);
        cellFB.setCellValue("Facebook");
        cellFB.setCellStyle(style);

        Cell cellDiaChi = row.createCell(7, CellType.STRING);
        cellDiaChi.setCellValue("Địa chỉ");
        cellDiaChi.setCellStyle(style);

        Cell cellGT = row.createCell(8, CellType.STRING);
        cellGT.setCellValue("Giới tính");
        cellGT.setCellStyle(style);

        Cell cellGhiChu = row.createCell(9, CellType.STRING);
        cellGhiChu.setCellValue("Ghi chú");
        cellGhiChu.setCellStyle(style);

        Cell cellTrangThai = row.createCell(10, CellType.STRING);
        cellTrangThai.setCellValue("Trạng thái");
        cellTrangThai.setCellStyle(style);

        int rowNum = 1;
        for (KhachHang kh : khachHangs) {
            Row tempRow = sheet.createRow(rowNum++);
            Cell tempCellSTT = tempRow.createCell(0, CellType.STRING);
            tempCellSTT.setCellValue(rowNum - 1);

            Cell tempCellMa = tempRow.createCell(1, CellType.STRING);
            tempCellMa.setCellValue(kh.getTaiKhoan());

            Cell tempCellTen = tempRow.createCell(2, CellType.STRING);
            tempCellTen.setCellValue(kh.getTenKhachHang());

            Cell tempCellNgaySinh = tempRow.createCell(3, CellType.STRING);
            try {
                tempCellNgaySinh.setCellValue(kh.getNgaySinh());
            } catch (Exception ex) {
                tempCellNgaySinh.setCellValue("Không có thông tin");
            }

            Cell tempCellSDT = tempRow.createCell(4, CellType.STRING);
            try {
                tempCellSDT.setCellValue(kh.getDienThoai());
            } catch (Exception ex) {
                tempCellSDT.setCellValue("Không có thông tin");
            }

            Cell tempCellEmail = tempRow.createCell(5, CellType.STRING);
            try {
                tempCellEmail.setCellValue(kh.getEmail());
            } catch (Exception ex) {
                tempCellEmail.setCellValue("Không có thông tin");
            }

            Cell tempCellFB = tempRow.createCell(6, CellType.STRING);
            try {
                tempCellFB.setCellValue(kh.getFacebook());
            } catch (Exception ex) {
                tempCellEmail.setCellValue("Không có thông tin");
            }

            Cell tempCellDiaChi = tempRow.createCell(7, CellType.STRING);
            try {
                tempCellDiaChi.setCellValue(kh.getDiaChi());
            } catch (Exception ex) {
                tempCellDiaChi.setCellValue("Không có thông tin");
            }

            Cell tempCellGT = tempRow.createCell(8, CellType.STRING);
            try {
                tempCellGT.setCellValue(kh.getGioiTinh() == true ? "Nam" : "Nữ");
            } catch (Exception ex) {
                tempCellGT.setCellValue("Không có thông tin");
            }

            Cell tempCellGhiChu = tempRow.createCell(9, CellType.STRING);
            try {
                tempCellGhiChu.setCellValue(kh.getGhiChu());
            } catch (Exception ex) {
                tempCellGhiChu.setCellValue("Không có thông tin");
            }

            Cell tempCellTrangThai = tempRow.createCell(10, CellType.STRING);
            try {
                tempCellTrangThai.setCellValue(TrangThaiKhachHang.trangThai.get(kh.getTrangThai()));
            } catch (Exception ex) {
                tempCellTrangThai.setCellValue("Không có thông tin");
            }
        }
        return workbook;
    }

    public static XSSFWorkbook createDanhSachNhaCungCapExcel(List<NhaCungCap> nhaCungCaps) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Danh sách nhà cung cấp");
        XSSFFont font = workbook.createFont();
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);

        Row row = sheet.createRow(0);

        Cell cellSTT = row.createCell(0, CellType.STRING);
        cellSTT.setCellValue("STT");
        cellSTT.setCellStyle(style);

        Cell cellMa = row.createCell(1, CellType.STRING);
        cellMa.setCellValue("Tên nhà cung cấp");
        cellMa.setCellStyle(style);

        Cell cellKH = row.createCell(2, CellType.STRING);
        cellKH.setCellValue("Điện thoại");
        cellKH.setCellStyle(style);

        Cell cellNCC = row.createCell(3, CellType.STRING);
        cellNCC.setCellValue("Email");
        cellNCC.setCellStyle(style);

        Cell cellSDT = row.createCell(4, CellType.STRING);
        cellSDT.setCellValue("Địa chỉ");
        cellSDT.setCellStyle(style);

        Cell cellEmail = row.createCell(5, CellType.STRING);
        cellEmail.setCellValue("Tổng nợ");
        cellEmail.setCellStyle(style);

        Cell cellFB = row.createCell(6, CellType.STRING);
        cellFB.setCellValue("Tổng mua");
        cellFB.setCellStyle(style);

        Cell cellDiaChi = row.createCell(7, CellType.STRING);
        cellDiaChi.setCellValue("Ghi chú");
        cellDiaChi.setCellStyle(style);

        Cell cellGT = row.createCell(8, CellType.STRING);
        cellGT.setCellValue("Trạng thái");
        cellGT.setCellStyle(style);

        int rowNum = 1;
        for (NhaCungCap ncc : nhaCungCaps) {
            Row tempRow = sheet.createRow(rowNum++);
            Cell tempCellSTT = tempRow.createCell(0, CellType.STRING);
            tempCellSTT.setCellValue(rowNum - 1);

            Cell tempCellMa = tempRow.createCell(1, CellType.STRING);
            try {
                tempCellMa.setCellValue(ncc.getTen());
            } catch (Exception ex) {
                tempCellMa.setCellValue("Không có thông tin");
            }

            Cell tempCellTen = tempRow.createCell(2, CellType.STRING);
            try {
                tempCellTen.setCellValue(ncc.getDienThoai());

            } catch (Exception ex) {
                tempCellTen.setCellValue("Không có thông tin");
            }
            Cell tempCellNgaySinh = tempRow.createCell(3, CellType.STRING);
            try {
                tempCellNgaySinh.setCellValue(ncc.getEmail());
            } catch (Exception ex) {
                tempCellNgaySinh.setCellValue("Không có thông tin");
            }

            Cell tempCellSDT = tempRow.createCell(4, CellType.STRING);
            try {
                tempCellSDT.setCellValue(ncc.getDiaChi());
            } catch (Exception ex) {
                tempCellSDT.setCellValue("Không có thông tin");
            }

            Cell tempCellEmail = tempRow.createCell(5, CellType.STRING);
            try {
                tempCellEmail.setCellValue(ncc.getTongNo());
            } catch (Exception ex) {
                tempCellEmail.setCellValue("Không có thông tin");
            }

            Cell tempCellFB = tempRow.createCell(6, CellType.STRING);
            try {
                tempCellFB.setCellValue(ncc.getTongMua());
            } catch (Exception ex) {
                tempCellEmail.setCellValue("Không có thông tin");
            }

            Cell tempCellDiaChi = tempRow.createCell(7, CellType.STRING);
            try {
                tempCellDiaChi.setCellValue(ncc.getGhiChu());
            } catch (Exception ex) {
                tempCellDiaChi.setCellValue("Không có thông tin");
            }

            Cell tempCellGT = tempRow.createCell(8, CellType.STRING);
            try {
                tempCellGT.setCellValue(TrangThaiKhachHang.trangThai.get(ncc.getTrangThai()));
            } catch (Exception ex) {
                tempCellGT.setCellValue("Không có thông tin");
            }
        }
        return workbook;
    }

    public static XSSFWorkbook createPhieuNhapExcel(List<PhieuNhapHangChiTiet> phieuNhapHangChiTiets) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Phiếu nhập hàng chi tiết ");
        XSSFFont font = workbook.createFont();
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);

        // write infor bill
        Row row1 = sheet.createRow(0);
        Cell cellTitleMaHD = row1.createCell(0, CellType.STRING);
        cellTitleMaHD.setCellValue("MÃ PHIẾU NHẬP ");
        cellTitleMaHD.setCellStyle(style);
        Cell cellMaHD = row1.createCell(1, CellType.STRING);
        cellMaHD.setCellValue(phieuNhapHangChiTiets.get(0).getPhieuNhapHang().getMaPhieu());

        Row row2 = sheet.createRow(1);
        Cell cellTitleTenNhanVien = row2.createCell(0, CellType.STRING);
        cellTitleTenNhanVien.setCellValue("Nhân Viên ");
        cellTitleTenNhanVien.setCellStyle(style);
        Cell cellTenNhanVien = row2.createCell(1, CellType.STRING);
        cellTenNhanVien.setCellValue(phieuNhapHangChiTiets.get(0).getPhieuNhapHang().getNguoiDung().getHoVaTen());
        Cell cellTitleMaNhanVien = row2.createCell(3, CellType.STRING);
        cellTitleMaNhanVien.setCellValue("Mã nhân viên");
        cellTitleMaNhanVien.setCellStyle(style);
        Cell cellMaNhanVien = row2.createCell(4, CellType.STRING);
        cellMaNhanVien.setCellValue(phieuNhapHangChiTiets.get(0).getPhieuNhapHang().getNguoiDung().getMaTaiKhoan());

        Row row3 = sheet.createRow(2);
        Cell cellTitleTenKhachHang = row3.createCell(0, CellType.STRING);
        cellTitleTenKhachHang.setCellValue("Nhà cung cấp");
        cellTitleTenKhachHang.setCellStyle(style);
        Cell cellTenKhachHang = row3.createCell(1, CellType.STRING);
        cellTenKhachHang.setCellValue(phieuNhapHangChiTiets.get(0).getPhieuNhapHang().getNhaCungCap().getTen());

        Cell cellTitleThoigian = row3.createCell(3, CellType.STRING);
        cellTitleThoigian.setCellValue("Thời gian");
        cellTitleThoigian.setCellStyle(style);
        Cell cellThoiGian = row3.createCell(4, CellType.STRING);
        cellThoiGian.setCellValue(formatDateTime(phieuNhapHangChiTiets.get(0).getPhieuNhapHang().getThoiGian()));

        // write detail bill
        // write title
        Row rowTitle = sheet.createRow(4);
        Cell cellTitleSTT = rowTitle.createCell(0, CellType.STRING);
        cellTitleSTT.setCellValue("STT");
        cellTitleSTT.setCellStyle(style);

        Cell cellTitleTenSanPham = rowTitle.createCell(1, CellType.STRING);
        cellTitleTenSanPham.setCellValue("Sản Phẩm");
        cellTitleTenSanPham.setCellStyle(style);

        Cell cellTitleDonVi = rowTitle.createCell(2, CellType.STRING);
        cellTitleDonVi.setCellValue("Giá nhập");
        cellTitleDonVi.setCellStyle(style);

        Cell cellTitleSoLuong = rowTitle.createCell(3, CellType.STRING);
        cellTitleSoLuong.setCellValue("Số Lượng");
        cellTitleSoLuong.setCellStyle(style);

        Cell cellTitleThanhTien = rowTitle.createCell(4, CellType.STRING);
        cellTitleThanhTien.setCellValue("Thành Tiền");
        cellTitleThanhTien.setCellStyle(style);

        int rowNum = 5;
        for (PhieuNhapHangChiTiet phieuNhapHangChiTiet : phieuNhapHangChiTiets) {
            Row tempRow = sheet.createRow(rowNum++);

            Cell tempCellSTT = tempRow.createCell(0, CellType.STRING);
            tempCellSTT.setCellValue(rowNum - 5);

            Cell tempCellSanPham = tempRow.createCell(1, CellType.STRING);
            tempCellSanPham.setCellValue(phieuNhapHangChiTiet.getChiNhanhHangHoa().getHangHoa().getTenHangHoa());

            Cell tempCellGiaNhap = tempRow.createCell(2, CellType.STRING);
            tempCellGiaNhap.setCellValue(phieuNhapHangChiTiet.getGiaNhap());

            Cell tempCellSoLuong = tempRow.createCell(3, CellType.STRING);
            tempCellSoLuong.setCellValue(phieuNhapHangChiTiet.getSoLuong());

            Cell tempCellThanhTien = tempRow.createCell(4, CellType.STRING);
            tempCellThanhTien.setCellValue(phieuNhapHangChiTiet.getTongTien());
        }

        Row rowTongTien = sheet.createRow(rowNum++);
        Cell cellTitleTongTien = rowTongTien.createCell(3, CellType.STRING);
        cellTitleTongTien.setCellValue("Tổng Tiền ");
        cellTitleTongTien.setCellStyle(style);
        Cell cellTongTien = rowTongTien.createCell(4, CellType.STRING);
        cellTongTien.setCellValue(phieuNhapHangChiTiets.get(0).getPhieuNhapHang().getTongTien());

        Row rowTienKhachDua = sheet.createRow(rowNum++);
        Cell cellTitleTienKhachDua = rowTienKhachDua.createCell(3, CellType.STRING);
        cellTitleTienKhachDua.setCellValue("Tiền Đã Trả ");
        cellTitleTienKhachDua.setCellStyle(style);
        Cell cellTienKhachDua = rowTienKhachDua.createCell(4, CellType.STRING);
        cellTienKhachDua.setCellValue(phieuNhapHangChiTiets.get(0).getPhieuNhapHang().getTienDaTra());

        Row rowTienTraKhach = sheet.createRow(rowNum++);
        Cell cellTitleTienTraKhach = rowTienTraKhach.createCell(3, CellType.STRING);
        cellTitleTienTraKhach.setCellValue("Tiền Còn Phải Trả ");
        cellTitleTienTraKhach.setCellStyle(style);
        Cell cellTienTraKhach = rowTienTraKhach.createCell(4, CellType.STRING);
        cellTienTraKhach.setCellValue(phieuNhapHangChiTiets.get(0).getPhieuNhapHang().getTienPhaiTra());

        return workbook;
    }

    public static XSSFWorkbook createListBillExcelPhieuThu(List<PhieuThu> billList) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Bill Information");
        XSSFFont font = workbook.createFont();
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        //Write Title
        {
            Row row = sheet.createRow(0);
            //STT
            Cell cellSTT = row.createCell(0, CellType.STRING);
            cellSTT.setCellValue("STT");
            cellSTT.setCellStyle(style);
            //Mã phieu thu
            Cell cellMPT = row.createCell(1, CellType.STRING);
            cellMPT.setCellValue("Mã phiếu thu");
            cellMPT.setCellStyle(style);
            //Thoi gian
            Cell cellTG = row.createCell(2, CellType.STRING);
            cellTG.setCellValue("Thời gian");
            cellTG.setCellStyle(style);
            //Loai thu
            Cell cellLT = row.createCell(3, CellType.STRING);
            cellLT.setCellValue("Loai thu");
            cellLT.setCellStyle(style);
            //Nhan vien
            Cell cellNV = row.createCell(4, CellType.STRING);
            cellNV.setCellValue("Nhân viên");
            cellNV.setCellStyle(style);
            //Gia tri
            Cell cellGT = row.createCell(5, CellType.STRING);
            cellGT.setCellValue("Giá trị");
            cellGT.setCellStyle(style);
            //Trang thai
            Cell cellTT = row.createCell(6, CellType.STRING);
            cellTT.setCellValue("Trạng thái");
            cellTT.setCellStyle(style);
        }
        //Write data
        int rowNum = 1;
        for (PhieuThu bill : billList) {
            Row row = sheet.createRow(rowNum++);
            //STT
            Cell cellSTT = row.createCell(0, CellType.STRING);
            cellSTT.setCellValue(rowNum - 1);

            Cell cellMPT = row.createCell(1, CellType.STRING);
            cellMPT.setCellValue(bill.getMaPhieu());

            Cell cellTG = row.createCell(2, CellType.STRING);
            cellTG.setCellValue(formatDateTime(bill.getThoiGian()));

            Cell cellLT = row.createCell(3, CellType.STRING);
            cellLT.setCellValue(bill.getLoaiThu().getTenLoaiThu());

            Cell cellNV = row.createCell(4, CellType.STRING);
            cellNV.setCellValue(bill.getNguoiDung().getHoVaTen());

            Cell cellGT = row.createCell(5, CellType.STRING);
            cellGT.setCellValue(bill.getTienDaTra());

            Cell cellTT = row.createCell(6, CellType.STRING);
            cellTT.setCellValue(trangThaiPhieuThu(bill.getTrangThai()));

        }
        return workbook;
    }
    public static XSSFWorkbook createListBillExcelPhieuChi(List<PhieuChi> billList) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Bill Information");
        XSSFFont font = workbook.createFont();
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        //Write Title
        {
            Row row = sheet.createRow(0);
            //STT
            Cell cellSTT = row.createCell(0, CellType.STRING);
            cellSTT.setCellValue("STT");
            cellSTT.setCellStyle(style);
            //Mã phieu chi
            Cell cellMPC = row.createCell(1, CellType.STRING);
            cellMPC.setCellValue("Mã phiếu chi");
            cellMPC.setCellStyle(style);
            //Thoi gian
            Cell cellTG = row.createCell(2, CellType.STRING);
            cellTG.setCellValue("Thời gian");
            cellTG.setCellStyle(style);
            //Loai chi
            Cell cellLC = row.createCell(3, CellType.STRING);
            cellLC.setCellValue("Loai chi");
            cellLC.setCellStyle(style);
            //Nhan vien
            Cell cellNV = row.createCell(4, CellType.STRING);
            cellNV.setCellValue("Nhân viên");
            cellNV.setCellStyle(style);
            //Gia tri
            Cell cellGT = row.createCell(5, CellType.STRING);
            cellGT.setCellValue("Giá trị");
            cellGT.setCellStyle(style);
            //Trang thai
            Cell cellTT = row.createCell(6, CellType.STRING);
            cellTT.setCellValue("Trạng thái");
            cellTT.setCellStyle(style);
        }
        //Write data
        int rowNum = 1;
        for (PhieuChi bill : billList) {
            Row row = sheet.createRow(rowNum++);
            //STT
            Cell cellSTT = row.createCell(0, CellType.STRING);
            cellSTT.setCellValue(rowNum - 1);

            Cell cellMPC = row.createCell(1, CellType.STRING);
            cellMPC.setCellValue(bill.getMaPhieu());

            Cell cellTG = row.createCell(2, CellType.STRING);
            cellTG.setCellValue(formatDateTime(bill.getThoiGian()));

            Cell cellLC = row.createCell(3, CellType.STRING);
            cellLC.setCellValue(bill.getLoaiChi().getTenLoaiChi());

            Cell cellNV = row.createCell(4, CellType.STRING);
            cellNV.setCellValue(bill.getNguoiDung().getHoVaTen());

            Cell cellGT = row.createCell(5, CellType.STRING);
            cellGT.setCellValue(bill.getDaTra());

            Cell cellTT = row.createCell(6, CellType.STRING);
            cellTT.setCellValue(trangThaiPhieuThu(bill.getTrangThai()));

        }
        return workbook;
    }

    public static String trangThaiPhieuThu(int trangThai){
        if(trangThai==0){
            return "Đã thanh toán";
        }else{
            return "Chưa thanh toán";
        }
    }
    public static XSSFWorkbook createListBillExcelHangHoa(List<ChiNhanhHangHoa> billList) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Bill Information");
        XSSFFont font = workbook.createFont();
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        //Write Title
        {
            Row row = sheet.createRow(0);
            //STT
            Cell cellSTT = row.createCell(0, CellType.STRING);
            cellSTT.setCellValue("STT");
            cellSTT.setCellStyle(style);
            Cell cellCN = row.createCell(1, CellType.STRING);
            cellCN.setCellValue("Chi nhánh");
            cellCN.setCellStyle(style);
            //Mã hàng
            Cell cellMH = row.createCell(2, CellType.STRING);
            cellMH.setCellValue("Mã hàng");
            cellMH.setCellStyle(style);
            //Ten Hang
            Cell cellTH = row.createCell(3, CellType.STRING);
            cellTH.setCellValue("Tên hàng");
            cellTH.setCellStyle(style);
            //mã vạch
            Cell cellMV = row.createCell(4, CellType.STRING);
            cellMV.setCellValue("Mã Vạch");
            cellMV.setCellStyle(style);
            //mã giảm giá
            Cell cellMGG = row.createCell(5, CellType.STRING);
            cellMGG.setCellValue("Mã Giảm giá");
            cellMGG.setCellStyle(style);
            //Mô tả
            Cell cellMT = row.createCell(6, CellType.STRING);
            cellMT.setCellValue("Mô tả");
            cellMT.setCellStyle(style);
            //Giảm giá
            Cell cellGG = row.createCell(7, CellType.STRING);
            cellGG.setCellValue("Giảm giá");
            cellGG.setCellStyle(style);
            //Tích điểm
            Cell cellTD = row.createCell(8, CellType.STRING);
            cellTD.setCellValue("Tích điểm");
            cellTD.setCellStyle(style);
            //URL_anh_1
            Cell cellURLA1 = row.createCell(9, CellType.STRING);
            cellURLA1.setCellValue("URL_anh_1");
            cellURLA1.setCellStyle(style);
            //URL_anh_2
            Cell cellURLA2 = row.createCell(10, CellType.STRING);
            cellURLA2.setCellValue("URL_anh_2");
            cellURLA2.setCellStyle(style);
            //URL_anh_3
            Cell cellURLA3 = row.createCell(11, CellType.STRING);
            cellURLA3.setCellValue("URL_anh_3");
            cellURLA3.setCellStyle(style);
            //Nhóm hàng
            Cell cellNH = row.createCell(12, CellType.STRING);
            cellNH.setCellValue("Nhóm hàng");
            cellNH.setCellStyle(style);
            //Thương hiệu
            Cell cellTHH = row.createCell(13, CellType.STRING);
            cellTHH.setCellValue("Thương hiệu");
            cellTHH.setCellStyle(style);
            //Đơn vị
            Cell cellDV = row.createCell(14, CellType.STRING);
            cellDV.setCellValue("ĐƠn vị");
            cellDV.setCellStyle(style);

        }
        //Write data
        int rowNum = 1;
        for (ChiNhanhHangHoa bill : billList) {
            Row row = sheet.createRow(rowNum++);
            //STT
            Cell cellSTT = row.createCell(0, CellType.STRING);
            cellSTT.setCellValue(rowNum - 1);

            Cell cellCN = row.createCell(1, CellType.STRING);
            cellCN.setCellValue(bill.getChiNhanh().getMaChiNhanh());

            Cell cellMH = row.createCell(2, CellType.STRING);
            cellMH.setCellValue(bill.getHangHoa().getMa());

            Cell cellTH = row.createCell(3, CellType.STRING);
            cellTH.setCellValue(bill.getHangHoa().getTenHangHoa());

            Cell cellMV = row.createCell(4, CellType.STRING);
            cellMV.setCellValue(bill.getHangHoa().getMaVach());

            Cell cellMGG = row.createCell(5, CellType.STRING);
            cellMGG.setCellValue(bill.getHangHoa().getMaGiamGia());

            Cell cellMT = row.createCell(6, CellType.STRING);
            cellMT.setCellValue(bill.getHangHoa().getMoTa());

            Cell cellGG = row.createCell(7, CellType.STRING);
            cellGG.setCellValue(bill.getHangHoa().getPhanTramGiamGia());

            Cell cellTD = row.createCell(8, CellType.STRING);
            if (ObjectUtils.isEmpty(bill.getHangHoa().getTichDiem())) {
                bill.getHangHoa().setTichDiem(0);
                cellTD.setCellValue(bill.getHangHoa().getTichDiem());
            }

            Cell cellURLA1 = row.createCell(9, CellType.STRING);
            cellURLA1.setCellValue(bill.getHangHoa().getUrlHinhAnh1());

            Cell cellURLA2 = row.createCell(10, CellType.STRING);
            cellURLA2.setCellValue(bill.getHangHoa().getUrlHinhAnh2());

            Cell cellURLA3 = row.createCell(11, CellType.STRING);
            cellURLA3.setCellValue(bill.getHangHoa().getUrlHinhAnh3());

            Cell cellNH = row.createCell(12, CellType.STRING);
            cellNH.setCellValue(bill.getHangHoa().getNhomHang().getTenNhomHang());

            Cell cellTHH = row.createCell(13, CellType.STRING);
            cellTHH.setCellValue(bill.getHangHoa().getThuongHieu().getTenThuongHieu());

            Cell cellDV = row.createCell(14, CellType.STRING);
            cellDV.setCellValue(bill.getChiNhanh().getDiaChi());
        }
        return workbook;
    }
}
