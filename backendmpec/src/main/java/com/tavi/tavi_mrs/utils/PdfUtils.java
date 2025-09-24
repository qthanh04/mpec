package com.tavi.tavi_mrs.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.tavi.tavi_mrs.entities.chi_nhanh.ChiNhanh;
import com.tavi.tavi_mrs.entities.constant.DatabaseConstant;
import com.tavi.tavi_mrs.entities.hoa_don.HoaDon;
import com.tavi.tavi_mrs.entities.hoa_don.HoaDonChiTiet;
import com.tavi.tavi_mrs.entities.phieu_nhap_hang.PhieuNhapHang;
import com.tavi.tavi_mrs.entities.phieu_nhap_hang.PhieuNhapHangChiTiet;
import com.tavi.tavi_mrs.entities.phieu_tra_hang_nhap.PhieuTraHangNhap;
import com.tavi.tavi_mrs.entities.phieu_tra_hang_nhap.PhieuTraHangNhapChiTiet;
import com.tavi.tavi_mrs.entities.phieu_tra_khach.PhieuTraKhach;
import com.tavi.tavi_mrs.entities.phieu_tra_khach.PhieuTraKhachChiTiet;
import com.tavi.tavi_mrs.entities.so_quy.PhieuChi;
import com.tavi.tavi_mrs.entities.so_quy.PhieuThu;
import com.tavi.tavi_mrs.entities.so_quy.PhieuThuHoaDon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Component
public class PdfUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(PdfUtils.class);


    @Value("${spring.upload.folder-upload}")
    private String UPLOAD_DIRECTORY;

    @PostConstruct
    void test(){
        System.out.println("UPLOAD_DIRECTORY" + UPLOAD_DIRECTORY);
    }

    //deploy lên server thì dùng thư mục này
//  private String UPLOAD_DIRECTORY = DatabaseConstant.Directory.URL_DIRECTORY;



    private final static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN,14,Font.BOLD);
    private final static Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,Font.BOLD );
    private final static Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,Font.BOLD);

    private com.itextpdf.text.Paragraph createPara(String value, Font font, int align,
                                                   int indenLeft, int indenRight, int spacing) {
        Paragraph para = new Paragraph(value, font);
        para.setAlignment(align);
        para.setIndentationLeft(indenLeft);
        para.setIndentationRight(indenRight);
        para.setSpacingAfter(spacing);
        return para;
    }

    private PdfPCell createHeaderCell(String value, Font font, int align) {
        PdfPCell cell = new PdfPCell(new Paragraph(value, font));
//        cell.setPaddingLeft(10);
        cell.setPaddingRight(10);
        cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
        cell.setBorderWidth(2);
        cell.setHorizontalAlignment(align);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setExtraParagraphSpace(5f);
        return cell;
    }

    private PdfPCell createCell(String value, Font font, int align, int border) {
        PdfPCell cell = new PdfPCell(new Paragraph(value, font));
//        cell.setPaddingLeft(10);
        cell.setPaddingRight(10);
        cell.setBorder(border);
        cell.setBorderWidth(0.5F);
        cell.setHorizontalAlignment(align);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setExtraParagraphSpace(5f);
        return cell;
    }

    public File createHoaDonPDF(HoaDon hoaDon, List<HoaDonChiTiet> hoaDonChiTietList) {
        Rectangle rectangle = new Rectangle(268, 720);
        Document document = new Document(rectangle, 5, 5, 15, 30);
        try {
            String fileName = hoaDon.getMa() + ".pdf";
            File file = new File(UPLOAD_DIRECTORY + fileName);
            file.getParentFile().mkdirs();
            FileOutputStream outFile = new FileOutputStream(file);
            PdfWriter writer = PdfWriter.getInstance(document, outFile);
            document.open();


            Image image = Image.getInstance(hoaDon.getChiNhanh().getTongCongTy().getLogo2());
            image.scaleToFit(70,70);
            image.setAlignment(Element.ALIGN_LEFT);

            PdfPTable header = new PdfPTable(2);
            float[] cols = {1,1};
            header.setWidths(cols);
            PdfPCell cellImg = new PdfPCell(image);
            cellImg.setBorder(Rectangle.NO_BORDER);
            header.addCell(cellImg);
            PdfPCell cellCongTy = new PdfPCell();
            Paragraph tenPara = new Paragraph(StringUtils.replacingAllAccents(hoaDon.getChiNhanh().getTongCongTy().getTenDoanhNghiep()), titleFont);
            Paragraph maCNPara = new Paragraph(StringUtils.replacingAllAccents(hoaDon.getChiNhanh().getMaChiNhanh()));
            Paragraph diaChiPara = new Paragraph(StringUtils.replacingAllAccents(hoaDon.getChiNhanh().getDiaChi()), normalFont);
            cellCongTy.setPaddingLeft(10);
            cellCongTy.setBorder(Rectangle.NO_BORDER);
            cellCongTy.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCongTy.setVerticalAlignment(Element.ALIGN_CENTER);
            cellCongTy.addElement(tenPara);
            cellCongTy.addElement(maCNPara);
            cellCongTy.addElement(diaChiPara);
            header.addCell(cellCongTy);
            document.add(header);

            Paragraph paragraphTitle = createPara("HOA DON", titleFont, Element.ALIGN_CENTER,
                    0, 0, 10);
            document.add(paragraphTitle);

            Paragraph maHD = createPara("Ma hoa don : " + StringUtils.replacingAllAccents(hoaDon.getMa()), normalFont, Element.ALIGN_LEFT,
                    5, 0, 10);
            document.add(maHD);
            Paragraph thoiGian = createPara("Thoi gian : " + DateTimeUtils.convertDateToString(hoaDon.getThoiGian(), DatabaseConstant.DateTime.DATE_TIME_FORMAT_1),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(thoiGian);
            Paragraph khachHang = createPara("Khach hang : " + StringUtils.replacingAllAccents(hoaDon.getKhachHang().getTenKhachHang()),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(khachHang);
            Paragraph nguoiDung = createPara("Nhan vien : " + StringUtils.replacingAllAccents(hoaDon.getNguoiDung().getHoVaTen()),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(nguoiDung);

            PdfPTable tableList = new PdfPTable(4);
            tableList.setWidthPercentage(100);
            float[] columnlistWidths = {8, 4, 3, 5};
            tableList.setWidths(columnlistWidths);

            PdfPCell spCell = createHeaderCell("SP", boldFont, Element.ALIGN_LEFT);
            tableList.addCell(spCell);
            PdfPCell giaCell = createHeaderCell("G", boldFont, Element.ALIGN_CENTER);
            tableList.addCell(giaCell);
            PdfPCell soLuongCell = createHeaderCell("SL", boldFont, Element.ALIGN_RIGHT);
            tableList.addCell(soLuongCell);
            PdfPCell thanhTienCell = createHeaderCell("TT", boldFont, Element.ALIGN_CENTER);
            tableList.addCell(thanhTienCell);

            for (HoaDonChiTiet hdct : hoaDonChiTietList) {
                PdfPCell spCellValue = createCell(StringUtils.replacingAllAccents(hdct.getLichSuGiaBan().getDonViHangHoa().getHangHoa().getTenHangHoa()),
                        normalFont, Element.ALIGN_LEFT, Rectangle.BOTTOM);
                tableList.addCell(spCellValue);
                PdfPCell giaCellValue = createCell(String.valueOf(hdct.getLichSuGiaBan().getGiaBan()),
                        normalFont, Element.ALIGN_RIGHT, Rectangle.BOTTOM);
                tableList.addCell(giaCellValue);
                PdfPCell soLuongCellValue = createCell(String.valueOf(hdct.getSoLuong()),
                        normalFont, Element.ALIGN_RIGHT, Rectangle.BOTTOM);
                tableList.addCell(soLuongCellValue);
                PdfPCell thanhTienCellValue = createCell(String.valueOf(hdct.getTongGia()),
                        normalFont, Element.ALIGN_RIGHT, Rectangle.BOTTOM);
                tableList.addCell(thanhTienCellValue);
            }
            document.add(tableList);

            PdfPTable tableFooter = new PdfPTable(2);
            tableFooter.setWidthPercentage(100);
            float[] columnlistWidthsTable = {1, 1};
            tableFooter.setWidths(columnlistWidthsTable);

            PdfPCell total = createCell("Tong", boldFont,
                    Element.ALIGN_LEFT, Rectangle.NO_BORDER);
            tableFooter.addCell(total);
            PdfPCell totalValue = createCell(String.valueOf(hoaDon.getTongTien()), normalFont,
                    Element.ALIGN_RIGHT, Rectangle.NO_BORDER);
            tableFooter.addCell(totalValue);

            PdfPCell tkt = createCell("Tien khach tra", boldFont,
                    Element.ALIGN_LEFT, Rectangle.NO_BORDER);
            tableFooter.addCell(tkt);
            PdfPCell tktValue = createCell(String.valueOf(hoaDon.getTienKhachTra()), normalFont,
                    Element.ALIGN_RIGHT, Rectangle.NO_BORDER);
            tableFooter.addCell(tktValue);

            PdfPCell trlk = createCell("Tien tra lai khach",
                    boldFont, Element.ALIGN_LEFT, Rectangle.NO_BORDER);
            tableFooter.addCell(trlk);
            PdfPCell trlkValue = createCell(String.valueOf(hoaDon.getTienTraLaiKhach()), normalFont,
                    Element.ALIGN_RIGHT, Rectangle.NO_BORDER);
            tableFooter.addCell(trlkValue);
            document.add(tableFooter);

            PdfPTable tableMessage = new PdfPTable(2);
            tableMessage.setWidthPercentage(100);
            float[] columnlistWidthsTableMessage = {4, 6};
            tableMessage.setWidths(columnlistWidthsTableMessage);
            PdfPCell messageCell = new PdfPCell(new Paragraph("CAM ON QUY KHACH VA HEN GAP LAI",normalFont));
            messageCell.setColspan(2);
            messageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            messageCell.setBorder(Rectangle.TOP);
            PdfPCell hotLineCell = createCell("Hotline : " + hoaDon.getChiNhanh().getTongCongTy().getSoDienThoai(),
                    normalFont,Element.ALIGN_RIGHT,Rectangle.BOTTOM);
            String website = hoaDon.getChiNhanh().getTongCongTy().getWebsite() != null ? hoaDon.getChiNhanh().getTongCongTy().getWebsite() : "https://facebook.com";
            PdfPCell websitCell = createCell("Website : " + website,
                    normalFont,Element.ALIGN_LEFT, Rectangle.BOTTOM);
            tableMessage.addCell(messageCell);
            tableMessage.addCell(hotLineCell);
            tableMessage.addCell(websitCell);
            document.add(tableMessage);

            Paragraph paragraphQR = new Paragraph();
//            String qrStr = DatabaseConstant.Host.URL_DEPLOY_BACKEND + "api/v1/public/hoa-don/find-by-ma/" + hoaDon.getMa();
            String qrStr = hoaDon.getMa();
            byte[] bytes = ZXingHelper.getQRCodeImage(qrStr, 100, 100);
            Image qrCode = Image.getInstance(bytes);
            paragraphQR.add(qrCode);
            paragraphQR.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraphQR);

            document.close();
            writer.close();
            return file;
        } catch (Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }


    public File createPhieuNhapPDF(PhieuNhapHang phieuNhapHang, List<PhieuNhapHangChiTiet> phieuNhapHangChiTietList) {
        Rectangle rectangle = new Rectangle(268, 720);
        Document document = new Document(rectangle, 5, 5, 15, 30);
        try {
            String fileName = phieuNhapHang.getMaPhieu() + ".pdf";
            File file = new File(UPLOAD_DIRECTORY + fileName);
            file.getParentFile().mkdirs();
            FileOutputStream outFile = new FileOutputStream(file);
            PdfWriter writer = PdfWriter.getInstance(document, outFile);
            document.open();

            ChiNhanh chiNhanh = phieuNhapHangChiTietList.get(0).getChiNhanhHangHoa().getChiNhanh();
            Image image = Image.getInstance(chiNhanh.getTongCongTy().getLogo2());
            image.scaleToFit(70,70);
            image.setAlignment(Element.ALIGN_LEFT);

            PdfPTable header = new PdfPTable(2);
            float[] cols = {1,1};
            header.setWidths(cols);
            PdfPCell cellImg = new PdfPCell(image);
            cellImg.setBorder(Rectangle.NO_BORDER);
            header.addCell(cellImg);
            PdfPCell cellCongTy = new PdfPCell();
            Paragraph tenPara = new Paragraph(StringUtils.replacingAllAccents(chiNhanh.getTongCongTy().getTenDoanhNghiep()), titleFont);
            Paragraph maCNPara = new Paragraph(StringUtils.replacingAllAccents(chiNhanh.getMaChiNhanh()),normalFont);
            Paragraph diaChiPara = new Paragraph(StringUtils.replacingAllAccents(chiNhanh.getDiaChi()), normalFont);
            cellCongTy.setPaddingLeft(10);
            cellCongTy.setBorder(Rectangle.NO_BORDER);
            cellCongTy.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCongTy.setVerticalAlignment(Element.ALIGN_CENTER);
            cellCongTy.addElement(tenPara);
            cellCongTy.addElement(maCNPara);
            cellCongTy.addElement(diaChiPara);
            header.addCell(cellCongTy);
            document.add(header);

            Paragraph paragraphTitle = createPara("Phieu nhap hang", titleFont, Element.ALIGN_CENTER,
                    5, 0, 10);
            document.add(paragraphTitle);

            Paragraph maHD = createPara("Ma phieu : " + StringUtils.replacingAllAccents(phieuNhapHang.getMaPhieu()), normalFont, Element.ALIGN_LEFT,
                    5, 0, 10);
            document.add(maHD);
            Paragraph thoiGian = createPara("Thoi gian : " + DateTimeUtils.convertDateToString(phieuNhapHang.getThoiGian(), DatabaseConstant.DateTime.DATE_TIME_FORMAT_1),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(thoiGian);
            Paragraph khachHang = createPara("Nha cung cap: " + StringUtils.replacingAllAccents(phieuNhapHang.getNhaCungCap().getTen()),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(khachHang);
            Paragraph nguoiDung = createPara("Nhan vien : " + StringUtils.replacingAllAccents(phieuNhapHang.getNguoiDung().getHoVaTen()),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(nguoiDung);

            PdfPTable tableList = new PdfPTable(4);
            tableList.setWidthPercentage(100);
            float[] columnlistWidths = {8, 4, 3, 5};
            tableList.setWidths(columnlistWidths);

            PdfPCell spCell = createHeaderCell("SP", boldFont, Element.ALIGN_LEFT);
            tableList.addCell(spCell);
            PdfPCell giaCell = createHeaderCell("G", boldFont, Element.ALIGN_CENTER);
            tableList.addCell(giaCell);
            PdfPCell soLuongCell = createHeaderCell("SL", boldFont, Element.ALIGN_RIGHT);
            tableList.addCell(soLuongCell);
            PdfPCell thanhTienCell = createHeaderCell("TT", boldFont, Element.ALIGN_CENTER);
            tableList.addCell(thanhTienCell);
            for (PhieuNhapHangChiTiet pnhct : phieuNhapHangChiTietList) {
                PdfPCell spCellValue = createCell(StringUtils.replacingAllAccents(pnhct.getChiNhanhHangHoa().getHangHoa().getTenHangHoa()),
                        normalFont, Element.ALIGN_LEFT, Rectangle.BOTTOM);
                tableList.addCell(spCellValue);
                PdfPCell giaCellValue = createCell(String.valueOf(pnhct.getGiaNhap()),
                        normalFont, Element.ALIGN_RIGHT, Rectangle.BOTTOM);
                tableList.addCell(giaCellValue);
                PdfPCell soLuongCellValue = createCell(String.valueOf(pnhct.getSoLuong()),
                        normalFont, Element.ALIGN_RIGHT, Rectangle.BOTTOM);
                tableList.addCell(soLuongCellValue);
                PdfPCell thanhTienCellValue = createCell(String.valueOf(pnhct.getTongTien()),
                        normalFont, Element.ALIGN_RIGHT, Rectangle.BOTTOM);
                tableList.addCell(thanhTienCellValue);
            }
            document.add(tableList);

            PdfPTable tableFooter = new PdfPTable(2);
            tableFooter.setWidthPercentage(100);
            float[] columnlistWidthsTable = {1, 1};
            tableFooter.setWidths(columnlistWidthsTable);

            PdfPCell total = createCell("Tong", boldFont,
                    Element.ALIGN_LEFT, Rectangle.NO_BORDER);
            tableFooter.addCell(total);
            PdfPCell totalValue = createCell(String.valueOf(phieuNhapHang.getTongTien()), normalFont,
                    Element.ALIGN_RIGHT, Rectangle.NO_BORDER);
            tableFooter.addCell(totalValue);

            PdfPCell tkt = createCell("Tien da tra", boldFont,
                    Element.ALIGN_LEFT, Rectangle.NO_BORDER);
            tableFooter.addCell(tkt);
            PdfPCell tktValue = createCell(String.valueOf(phieuNhapHang.getTienDaTra()), normalFont,
                    Element.ALIGN_RIGHT, Rectangle.NO_BORDER);
            tableFooter.addCell(tktValue);

            PdfPCell trlk = createCell("Tien phai tra",
                    boldFont, Element.ALIGN_LEFT, Rectangle.NO_BORDER);
            tableFooter.addCell(trlk);
            PdfPCell trlkValue = createCell(String.valueOf(phieuNhapHang.getTienPhaiTra()), normalFont,
                    Element.ALIGN_RIGHT, Rectangle.NO_BORDER);
            tableFooter.addCell(trlkValue);
            document.add(tableFooter);


            PdfPTable tableMessage = new PdfPTable(2);
            tableMessage.setWidthPercentage(100);
            float[] columnlistWidthsTableMessage = {4, 6};
            tableMessage.setWidths(columnlistWidthsTableMessage);
            PdfPCell messageCell = new PdfPCell(new Paragraph("CAM ON QUY KHACH VA HEN GAP LAI",normalFont));
            messageCell.setColspan(2);
            messageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            messageCell.setBorder(Rectangle.TOP);
            PdfPCell hotLineCell = createCell("Hotline : " + chiNhanh.getTongCongTy().getSoDienThoai(),
                    normalFont,Element.ALIGN_RIGHT,Rectangle.BOTTOM);
            String website = chiNhanh.getTongCongTy().getWebsite() != null ? chiNhanh.getTongCongTy().getWebsite() : "https://facebook.com";
            PdfPCell websitCell = createCell("Website : " + website,
                    normalFont,Element.ALIGN_LEFT, Rectangle.BOTTOM);
            tableMessage.addCell(messageCell);
            tableMessage.addCell(hotLineCell);
            tableMessage.addCell(websitCell);
            document.add(tableMessage);

            Paragraph paragraphQR = new Paragraph();
            String qrStr = phieuNhapHang.getMaPhieu();
            byte[] bytes = ZXingHelper.getQRCodeImage(qrStr, 100, 100);
            Image qrCode = Image.getInstance(bytes);
            paragraphQR.add(qrCode);
            paragraphQR.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraphQR);

            document.close();
            writer.close();
            return file;
        }catch (Exception ex){
            LOGGER.error(ex.toString());
            return null;
        }
    }


    public File createPhieuTraKhachPDF(PhieuTraKhach phieuTraKhach, List<PhieuTraKhachChiTiet> phieuTraKhachChiTietList) {
        Rectangle rectangle = new Rectangle(268, 720);
        Document document = new Document(rectangle, 5, 5, 15, 30);
        try {
            HoaDon hoaDon = phieuTraKhachChiTietList.get(0).getHoaDonChiTiet().getHoaDon();
            String fileName = phieuTraKhach.getMa() + ".pdf";
            File file = new File(UPLOAD_DIRECTORY + fileName);
            file.getParentFile().mkdirs();
            FileOutputStream outFile = new FileOutputStream(file);
            PdfWriter writer = PdfWriter.getInstance(document, outFile);
            document.open();

            Image image = Image.getInstance(hoaDon.getChiNhanh().getTongCongTy().getLogo2());
            image.scaleToFit(70,70);
            image.setAlignment(Element.ALIGN_LEFT);

            PdfPTable header = new PdfPTable(2);
            float[] cols = {1,1};
            header.setWidths(cols);
            PdfPCell cellImg = new PdfPCell(image);
            cellImg.setBorder(Rectangle.NO_BORDER);
            header.addCell(cellImg);
            PdfPCell cellCongTy = new PdfPCell();
            Paragraph tenPara = new Paragraph(StringUtils.replacingAllAccents(hoaDon.getChiNhanh().getTongCongTy().getTenDoanhNghiep()), titleFont);
            Paragraph maCNPara = new Paragraph(StringUtils.replacingAllAccents(hoaDon.getChiNhanh().getMaChiNhanh()));
            Paragraph diaChiPara = new Paragraph(StringUtils.replacingAllAccents(hoaDon.getChiNhanh().getDiaChi()), normalFont);
            cellCongTy.setPaddingLeft(10);
            cellCongTy.setBorder(Rectangle.NO_BORDER);
            cellCongTy.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCongTy.setVerticalAlignment(Element.ALIGN_CENTER);
            cellCongTy.addElement(tenPara);
            cellCongTy.addElement(maCNPara);
            cellCongTy.addElement(diaChiPara);
            header.addCell(cellCongTy);
            document.add(header);

            Paragraph paragraphTitle = createPara("Phieu tra khach", titleFont, Element.ALIGN_CENTER,
                    0, 0, 10);
            document.add(paragraphTitle);

            Paragraph maHD = createPara("Ma phieu tra khach : " + StringUtils.replacingAllAccents(phieuTraKhach.getMa()), normalFont, Element.ALIGN_LEFT,
                    5, 0, 10);
            document.add(maHD);
            Paragraph maHD2 = createPara("Ma phieu hoa don : " + StringUtils.replacingAllAccents(hoaDon.getMa()), normalFont, Element.ALIGN_LEFT,
                    5, 0, 10);
            document.add(maHD2);
            Paragraph thoiGian = createPara("Thoi gian : " + DateTimeUtils.convertDateToString(phieuTraKhach.getThoiGian(), DatabaseConstant.DateTime.DATE_TIME_FORMAT_1),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(thoiGian);
            Paragraph khachHang = createPara("Khach hang : " + StringUtils.replacingAllAccents(hoaDon.getKhachHang().getTenKhachHang()),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(khachHang);
            Paragraph nguoiDung = createPara("Nhan vien : " + StringUtils.replacingAllAccents(phieuTraKhach.getNguoiDung().getHoVaTen()),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(nguoiDung);

            PdfPTable tableList = new PdfPTable(4);
            tableList.setWidthPercentage(100);
            float[] columnlistWidths = {4, 2, 2, 2};
            tableList.setWidths(columnlistWidths);

            PdfPCell spCell = createHeaderCell("SP", boldFont, Element.ALIGN_LEFT);
            tableList.addCell(spCell);
            PdfPCell giaCell = createHeaderCell("G", boldFont, Element.ALIGN_LEFT);
            tableList.addCell(giaCell);
            PdfPCell soLuongCell = createHeaderCell("SL", boldFont, Element.ALIGN_LEFT);
            tableList.addCell(soLuongCell);
            PdfPCell thanhTienCell = createHeaderCell("TT", boldFont, Element.ALIGN_LEFT);
            tableList.addCell(thanhTienCell);

            for (PhieuTraKhachChiTiet ptkct : phieuTraKhachChiTietList) {
                PdfPCell spCellValue = createCell(StringUtils.replacingAllAccents(ptkct.getHoaDonChiTiet().getLichSuGiaBan().getDonViHangHoa().getHangHoa().getTenHangHoa()),
                        normalFont, Element.ALIGN_LEFT, Rectangle.BOTTOM);
                tableList.addCell(spCellValue);
                PdfPCell giaCellValue = createCell(String.valueOf(ptkct.getHoaDonChiTiet().getLichSuGiaBan().getGiaBan()),
                        normalFont, Element.ALIGN_LEFT, Rectangle.BOTTOM);
                tableList.addCell(giaCellValue);
                PdfPCell soLuongCellValue = createCell(ptkct.getSoLuong() + "/" + ptkct.getHoaDonChiTiet().getSoLuong(),
                        normalFont, Element.ALIGN_LEFT, Rectangle.BOTTOM);
                tableList.addCell(soLuongCellValue);
                PdfPCell thanhTienCellValue = createCell(String.valueOf(ptkct.getTongTien()),
                        normalFont, Element.ALIGN_RIGHT, Rectangle.BOTTOM);
                tableList.addCell(thanhTienCellValue);
            }
            document.add(tableList);

            PdfPTable tableFooter = new PdfPTable(2);
            tableFooter.setWidthPercentage(100);
            float[] columnlistWidthsTable = {1, 1};
            tableFooter.setWidths(columnlistWidthsTable);

            PdfPCell total = createCell("Tong", boldFont,
                    Element.ALIGN_LEFT, Rectangle.NO_BORDER);
            tableFooter.addCell(total);
            PdfPCell totalValue = createCell(String.valueOf(hoaDon.getTongTien()), normalFont,
                    Element.ALIGN_RIGHT, Rectangle.NO_BORDER);
            tableFooter.addCell(totalValue);

            PdfPCell tkt = createCell("Tien hoan lai", boldFont,
                    Element.ALIGN_LEFT, Rectangle.NO_BORDER);
            tableFooter.addCell(tkt);
            PdfPCell tktValue = createCell(String.valueOf(phieuTraKhach.getTienTraKhach()), normalFont,
                    Element.ALIGN_RIGHT, Rectangle.NO_BORDER);
            tableFooter.addCell(tktValue);

            document.add(tableFooter);

            String lyDo = phieuTraKhach.getLyDo() != null ? phieuTraKhach.getLyDo() : "Khong co";
            Paragraph lydoPara = createPara("Ly do : " + StringUtils.replacingAllAccents(lyDo),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(lydoPara);

            PdfPTable tableMessage = new PdfPTable(2);
            tableMessage.setWidthPercentage(100);
            float[] columnlistWidthsTableMessage = {4, 6};
            tableMessage.setWidths(columnlistWidthsTableMessage);
            PdfPCell messageCell = new PdfPCell(new Paragraph("CAM ON QUY KHACH VA HEN GAP LAI",normalFont));
            messageCell.setColspan(2);
            messageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            messageCell.setBorder(Rectangle.TOP);
            PdfPCell hotLineCell = createCell("Hotline : " + hoaDon.getChiNhanh().getTongCongTy().getSoDienThoai(),
                    normalFont,Element.ALIGN_RIGHT,Rectangle.BOTTOM);
            String website = hoaDon.getChiNhanh().getTongCongTy().getWebsite() != null ? hoaDon.getChiNhanh().getTongCongTy().getWebsite() : "https://facebook.com";
            PdfPCell websitCell = createCell("Website : " + website,
                    normalFont,Element.ALIGN_LEFT, Rectangle.BOTTOM);
            tableMessage.addCell(messageCell);
            tableMessage.addCell(hotLineCell);
            tableMessage.addCell(websitCell);
            document.add(tableMessage);

            Paragraph paragraphQR = new Paragraph();
            String qrStr = phieuTraKhach.getMa();
            byte[] bytes = ZXingHelper.getQRCodeImage(qrStr, 100, 100);
            Image qrCode = Image.getInstance(bytes);
            paragraphQR.add(qrCode);
            paragraphQR.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraphQR);
            document.close();
            writer.close();
            return file;
        } catch (Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }

    public File createPhieuTraHangNhapPDF(PhieuTraHangNhap phieuTraHangNhap, List<PhieuTraHangNhapChiTiet> phieuTraHangNhapChiTietList) {
        Rectangle rectangle = new Rectangle(268, 720);
        Document document = new Document(rectangle, 5, 5, 15, 30);        BaseFont nf = null;
        try {
            ChiNhanh chiNhanh = phieuTraHangNhapChiTietList.get(0).getChiNhanhHangHoa().getChiNhanh();
            String fileName = phieuTraHangNhap.getMaPhieu() + ".pdf";
            File file = new File(UPLOAD_DIRECTORY + fileName);
            file.getParentFile().mkdirs();
            FileOutputStream outFile = new FileOutputStream(file);
            PdfWriter writer = PdfWriter.getInstance(document, outFile);
            document.open();

            Image image = Image.getInstance(chiNhanh.getTongCongTy().getLogo2());
            image.scaleToFit(70,70);
            image.setAlignment(Element.ALIGN_LEFT);

            PdfPTable header = new PdfPTable(2);
            float[] cols = {1,1};
            header.setWidths(cols);
            PdfPCell cellImg = new PdfPCell(image);
            cellImg.setBorder(Rectangle.NO_BORDER);
            header.addCell(cellImg);
            PdfPCell cellCongTy = new PdfPCell();
            Paragraph tenPara = new Paragraph(StringUtils.replacingAllAccents(chiNhanh.getTongCongTy().getTenDoanhNghiep()), titleFont);
            Paragraph maCNPara = new Paragraph(StringUtils.replacingAllAccents(chiNhanh.getMaChiNhanh()));
            Paragraph diaChiPara = new Paragraph(StringUtils.replacingAllAccents(chiNhanh.getDiaChi()), normalFont);
            cellCongTy.setPaddingLeft(10);
            cellCongTy.setBorder(Rectangle.NO_BORDER);
            cellCongTy.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCongTy.setVerticalAlignment(Element.ALIGN_CENTER);
            cellCongTy.addElement(tenPara);
            cellCongTy.addElement(maCNPara);
            cellCongTy.addElement(diaChiPara);
            header.addCell(cellCongTy);
            document.add(header);

            Paragraph paragraphTitle = createPara("Phieu tra hang nha cung cap", titleFont, Element.ALIGN_CENTER,
                    0, 0, 10);
            document.add(paragraphTitle);

            Paragraph maHDPara = createPara("Ma phieu : " + StringUtils.replacingAllAccents(phieuTraHangNhap.getMaPhieu()), normalFont, Element.ALIGN_LEFT,
                    5, 0, 10);
            document.add(maHDPara);
            Paragraph chiNhanhPara = createPara("Chi nhanh : " + StringUtils.replacingAllAccents(phieuTraHangNhapChiTietList.get(0).getChiNhanhHangHoa().getChiNhanh().getDiaChi()), normalFont, Element.ALIGN_LEFT,
                    5, 0, 10);
            document.add(chiNhanhPara);
            Paragraph thoiGianPara = createPara("Thoi gian : " + DateTimeUtils.convertDateToString(phieuTraHangNhap.getThoiGian(), DatabaseConstant.DateTime.DATE_TIME_FORMAT_1),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(thoiGianPara);
            Paragraph nhaCungCapPara = createPara("Nha cung cap : " + StringUtils.replacingAllAccents(phieuTraHangNhap.getNhaCungCap().getTen()),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(nhaCungCapPara);
            Paragraph nguoiDungPara = createPara("Nhan vien: " + StringUtils.replacingAllAccents(phieuTraHangNhap.getNguoiDung().getHoVaTen()),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(nguoiDungPara);

            PdfPTable tableList = new PdfPTable(3);
            tableList.setWidthPercentage(100);
            float[] columnlistWidths = {5, 2, 3};
            tableList.setWidths(columnlistWidths);

            PdfPCell spCell = createHeaderCell("SP", boldFont, Element.ALIGN_LEFT);
            tableList.addCell(spCell);
            PdfPCell soLuongCell = createHeaderCell("SL", boldFont, Element.ALIGN_LEFT);
            tableList.addCell(soLuongCell);
            PdfPCell thanhTienCell = createHeaderCell("TT", boldFont, Element.ALIGN_CENTER);
            tableList.addCell(thanhTienCell);

            for (PhieuTraHangNhapChiTiet ptkct : phieuTraHangNhapChiTietList) {
                PdfPCell spCellValue = createCell(StringUtils.replacingAllAccents(ptkct.getChiNhanhHangHoa().getHangHoa().getTenHangHoa()),
                        normalFont, Element.ALIGN_LEFT, Rectangle.BOTTOM);
                tableList.addCell(spCellValue);
                PdfPCell soLuongCellValue = createCell(String.valueOf(ptkct.getSoLuong()),
                        normalFont, Element.ALIGN_CENTER, Rectangle.BOTTOM);
                tableList.addCell(soLuongCellValue);
                PdfPCell thanhTienCellValue = createCell(String.valueOf(ptkct.getTongTien()),
                        normalFont, Element.ALIGN_RIGHT, Rectangle.BOTTOM);
                tableList.addCell(thanhTienCellValue);
            }
            document.add(tableList);

            PdfPTable tableFooter = new PdfPTable(2);
            tableFooter.setWidthPercentage(100);
            float[] columnlistWidthsTable = {1, 1};
            tableFooter.setWidths(columnlistWidthsTable);

            PdfPCell total = createCell("Tong", boldFont,
                    Element.ALIGN_LEFT, Rectangle.NO_BORDER);
            tableFooter.addCell(total);
            PdfPCell totalValue = createCell(String.valueOf(phieuTraHangNhap.getTongTien()), normalFont,
                    Element.ALIGN_RIGHT, Rectangle.NO_BORDER);
            tableFooter.addCell(totalValue);

            PdfPCell tkt = createCell("Tien hoan lai", boldFont,
                    Element.ALIGN_LEFT, Rectangle.NO_BORDER);
            tableFooter.addCell(tkt);
            PdfPCell tktValue = createCell(String.valueOf(phieuTraHangNhap.getTienDaTra()), normalFont,
                    Element.ALIGN_RIGHT, Rectangle.NO_BORDER);
            tableFooter.addCell(tktValue);
            document.add(tableFooter);

            String lyDo =phieuTraHangNhap.getLyDo() != null ? phieuTraHangNhap.getLyDo() : "Khong co";
            Paragraph lydo = createPara("Ly do : " + StringUtils.replacingAllAccents(lyDo),
                    normalFont, Element.ALIGN_LEFT, 20, 0, 10);
            document.add(lydo);

            PdfPTable tableMessage = new PdfPTable(2);
            tableMessage.setWidthPercentage(100);
            float[] columnlistWidthsTableMessage = {4, 6};
            tableMessage.setWidths(columnlistWidthsTableMessage);
            PdfPCell messageCell = new PdfPCell(new Paragraph("CAM ON QUY KHACH VA HEN GAP LAI",normalFont));
            messageCell.setColspan(2);
            messageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            messageCell.setBorder(Rectangle.TOP);
            PdfPCell hotLineCell = createCell("Hotline : " + chiNhanh.getTongCongTy().getSoDienThoai(),
                    normalFont,Element.ALIGN_RIGHT,Rectangle.BOTTOM);
            String website = chiNhanh.getTongCongTy().getWebsite() != null ? chiNhanh.getTongCongTy().getWebsite() : "https://facebook.com";
            PdfPCell websitCell = createCell("Website : " + website,
                    normalFont,Element.ALIGN_LEFT, Rectangle.BOTTOM);
            tableMessage.addCell(messageCell);
            tableMessage.addCell(hotLineCell);
            tableMessage.addCell(websitCell);
            document.add(tableMessage);

            Paragraph paragraphQR = new Paragraph();
            String qrStr = phieuTraHangNhap.getMaPhieu();
            byte[] bytes = ZXingHelper.getQRCodeImage(qrStr, 100, 100);
            Image qrCode = Image.getInstance(bytes);
            paragraphQR.add(qrCode);
            paragraphQR.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraphQR);
            document.close();
            writer.close();
            return file;
        } catch (Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }

    public File createPhieuThuPDF(PhieuThu phieuThu, HoaDon hoaDon){
        Rectangle rectangle = new Rectangle(268, 720);
        Document document = new Document(rectangle, 5, 5, 15, 30);
        try {
            String fileName = phieuThu.getMaPhieu() + ".pdf";
            File file = new File(UPLOAD_DIRECTORY + fileName);
            file.getParentFile().mkdirs();
            FileOutputStream outFile = new FileOutputStream(file);
            PdfWriter writer = PdfWriter.getInstance(document, outFile);
            document.open();


            Image image = Image.getInstance(phieuThu.getChiNhanh().getTongCongTy().getLogo2());
            image.scaleToFit(70,70);
            image.setAlignment(Element.ALIGN_LEFT);

            PdfPTable header = new PdfPTable(2);
            float[] cols = {1,1};
            header.setWidths(cols);
            PdfPCell cellImg = new PdfPCell(image);
            cellImg.setBorder(Rectangle.NO_BORDER);
            header.addCell(cellImg);
            PdfPCell cellCongTy = new PdfPCell();
            Paragraph tenPara = new Paragraph(StringUtils.replacingAllAccents(phieuThu.getChiNhanh().getTongCongTy().getTenDoanhNghiep()), titleFont);
            Paragraph maCNPara = new Paragraph(StringUtils.replacingAllAccents(phieuThu.getChiNhanh().getMaChiNhanh()));
            Paragraph diaChiPara = new Paragraph(StringUtils.replacingAllAccents(phieuThu.getChiNhanh().getDiaChi()), normalFont);
            cellCongTy.setPaddingLeft(10);
            cellCongTy.setBorder(Rectangle.NO_BORDER);
            cellCongTy.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCongTy.setVerticalAlignment(Element.ALIGN_CENTER);
            cellCongTy.addElement(tenPara);
            cellCongTy.addElement(maCNPara);
            cellCongTy.addElement(diaChiPara);
            header.addCell(cellCongTy);
            document.add(header);

            Paragraph paragraphTitle = createPara("PHIEU THU", titleFont, Element.ALIGN_CENTER,
                    0, 0, 10);
            document.add(paragraphTitle);

            Paragraph maHD = createPara("Ma phieu thu : " + StringUtils.replacingAllAccents(phieuThu.getMaPhieu()), normalFont, Element.ALIGN_LEFT,
                    5, 0, 10);
            document.add(maHD);
            Paragraph thoiGian = createPara("Thoi gian : " + DateTimeUtils.convertDateToString(phieuThu.getThoiGian(), DatabaseConstant.DateTime.DATE_TIME_FORMAT_1),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(thoiGian);
            Paragraph khachHang = createPara("Khach hang : " + StringUtils.replacingAllAccents(hoaDon.getKhachHang().getTenKhachHang()),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(khachHang);
            Paragraph nguoiDung = createPara("Nhan vien : " + StringUtils.replacingAllAccents(phieuThu.getNguoiDung().getHoVaTen()),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(nguoiDung);

            Paragraph tienDaTra = createPara("Tien thu : " + StringUtils.replacingAllAccents(phieuThu.getTienDaTra().toString()),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(tienDaTra);

//            PdfPTable tableList = new PdfPTable(4);
//            tableList.setWidthPercentage(100);
//            float[] columnlistWidths = {8, 4, 3, 5};
//            tableList.setWidths(columnlistWidths);
//
//            PdfPCell spCell = createHeaderCell("SP", boldFont, Element.ALIGN_LEFT);
//            tableList.addCell(spCell);
//            PdfPCell giaCell = createHeaderCell("G", boldFont, Element.ALIGN_CENTER);
//            tableList.addCell(giaCell);
//            PdfPCell soLuongCell = createHeaderCell("SL", boldFont, Element.ALIGN_RIGHT);
//            tableList.addCell(soLuongCell);
//            PdfPCell thanhTienCell = createHeaderCell("TT", boldFont, Element.ALIGN_CENTER);
//            tableList.addCell(thanhTienCell);
//
//            for (HoaDonChiTiet hdct : hoaDonChiTietList) {
//                PdfPCell spCellValue = createCell(StringUtils.replacingAllAccents(hdct.getLichSuGiaBan().getDonViHangHoa().getHangHoa().getTenHangHoa()),
//                        normalFont, Element.ALIGN_LEFT, Rectangle.BOTTOM);
//                tableList.addCell(spCellValue);
//                PdfPCell giaCellValue = createCell(String.valueOf(hdct.getLichSuGiaBan().getGiaBan()),
//                        normalFont, Element.ALIGN_RIGHT, Rectangle.BOTTOM);
//                tableList.addCell(giaCellValue);
//                PdfPCell soLuongCellValue = createCell(String.valueOf(hdct.getSoLuong()),
//                        normalFont, Element.ALIGN_RIGHT, Rectangle.BOTTOM);
//                tableList.addCell(soLuongCellValue);
//                PdfPCell thanhTienCellValue = createCell(String.valueOf(hdct.getTongGia()),
//                        normalFont, Element.ALIGN_RIGHT, Rectangle.BOTTOM);
//                tableList.addCell(thanhTienCellValue);
//            }
//            document.add(tableList);

            PdfPTable tableFooter = new PdfPTable(2);
            tableFooter.setWidthPercentage(100);
            float[] columnlistWidthsTable = {1, 1};
            tableFooter.setWidths(columnlistWidthsTable);

            PdfPCell total = createCell("Tong", boldFont,
                    Element.ALIGN_LEFT, Rectangle.NO_BORDER);
            tableFooter.addCell(total);
            PdfPCell totalValue = createCell(String.valueOf(phieuThu.getTienDaTra()), normalFont,
                    Element.ALIGN_RIGHT, Rectangle.NO_BORDER);
            tableFooter.addCell(totalValue);

            PdfPTable tableMessage = new PdfPTable(2);
            tableMessage.setWidthPercentage(100);
            float[] columnlistWidthsTableMessage = {4, 6};
            tableMessage.setWidths(columnlistWidthsTableMessage);
            PdfPCell messageCell = new PdfPCell(new Paragraph("CAM ON QUY KHACH VA HEN GAP LAI",normalFont));
            messageCell.setColspan(2);
            messageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            messageCell.setBorder(Rectangle.TOP);
            PdfPCell hotLineCell = createCell("Hotline : " + phieuThu.getChiNhanh().getTongCongTy().getSoDienThoai(),
                    normalFont,Element.ALIGN_RIGHT,Rectangle.BOTTOM);
            String website = phieuThu.getChiNhanh().getTongCongTy().getWebsite() != null ? phieuThu.getChiNhanh().getTongCongTy().getWebsite() : "https://facebook.com";
            PdfPCell websitCell = createCell("Website : " + website,
                    normalFont,Element.ALIGN_LEFT, Rectangle.BOTTOM);
            tableMessage.addCell(messageCell);
            tableMessage.addCell(hotLineCell);
            tableMessage.addCell(websitCell);
            document.add(tableMessage);

            Paragraph paragraphQR = new Paragraph();
//            String qrStr = DatabaseConstant.Host.URL_DEPLOY_BACKEND + "api/v1/public/hoa-don/find-by-ma/" + hoaDon.getMa();
            String qrStr = phieuThu.getMaPhieu();
            byte[] bytes = ZXingHelper.getQRCodeImage(qrStr, 100, 100);
            Image qrCode = Image.getInstance(bytes);
            paragraphQR.add(qrCode);
            paragraphQR.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraphQR);

            document.close();
            writer.close();
            return file;
        } catch (Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }

    public File createPhieuThuPhieuTraHangNhapPDF(PhieuThu phieuThu, PhieuTraHangNhap phieuTraHangNhap){
        Rectangle rectangle = new Rectangle(268, 720);
        Document document = new Document(rectangle, 5, 5, 15, 30);
        try {
            String fileName = phieuThu.getMaPhieu() + ".pdf";
            File file = new File(UPLOAD_DIRECTORY + fileName);
            file.getParentFile().mkdirs();
            FileOutputStream outFile = new FileOutputStream(file);
            PdfWriter writer = PdfWriter.getInstance(document, outFile);
            document.open();


            Image image = Image.getInstance(phieuThu.getChiNhanh().getTongCongTy().getLogo2());
            image.scaleToFit(70,70);
            image.setAlignment(Element.ALIGN_LEFT);

            PdfPTable header = new PdfPTable(2);
            float[] cols = {1,1};
            header.setWidths(cols);
            PdfPCell cellImg = new PdfPCell(image);
            cellImg.setBorder(Rectangle.NO_BORDER);
            header.addCell(cellImg);
            PdfPCell cellCongTy = new PdfPCell();
            Paragraph tenPara = new Paragraph(StringUtils.replacingAllAccents(phieuThu.getChiNhanh().getTongCongTy().getTenDoanhNghiep()), titleFont);
            Paragraph maCNPara = new Paragraph(StringUtils.replacingAllAccents(phieuThu.getChiNhanh().getMaChiNhanh()));
            Paragraph diaChiPara = new Paragraph(StringUtils.replacingAllAccents(phieuThu.getChiNhanh().getDiaChi()), normalFont);
            cellCongTy.setPaddingLeft(10);
            cellCongTy.setBorder(Rectangle.NO_BORDER);
            cellCongTy.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCongTy.setVerticalAlignment(Element.ALIGN_CENTER);
            cellCongTy.addElement(tenPara);
            cellCongTy.addElement(maCNPara);
            cellCongTy.addElement(diaChiPara);
            header.addCell(cellCongTy);
            document.add(header);

            Paragraph paragraphTitle = createPara("PHIEU THU", titleFont, Element.ALIGN_CENTER,
                    0, 0, 10);
            document.add(paragraphTitle);

            Paragraph maHD = createPara("Ma phieu thu : " + StringUtils.replacingAllAccents(phieuThu.getMaPhieu()), normalFont, Element.ALIGN_LEFT,
                    5, 0, 10);
            document.add(maHD);
            Paragraph thoiGian = createPara("Thoi gian : " + DateTimeUtils.convertDateToString(phieuThu.getThoiGian(), DatabaseConstant.DateTime.DATE_TIME_FORMAT_1),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(thoiGian);
            Paragraph khachHang = createPara("Nha cung cap : " + StringUtils.replacingAllAccents(phieuTraHangNhap.getNhaCungCap().getTen()),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(khachHang);
            Paragraph nguoiDung = createPara("Nhan vien : " + StringUtils.replacingAllAccents(phieuThu.getNguoiDung().getHoVaTen()),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(nguoiDung);

            Paragraph tienDaTra = createPara("Tien thu : " + StringUtils.replacingAllAccents(phieuThu.getTienDaTra().toString()),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(tienDaTra);

            PdfPTable tableFooter = new PdfPTable(2);
            tableFooter.setWidthPercentage(100);
            float[] columnlistWidthsTable = {1, 1};
            tableFooter.setWidths(columnlistWidthsTable);

            PdfPCell total = createCell("Tong", boldFont,
                    Element.ALIGN_LEFT, Rectangle.NO_BORDER);
            tableFooter.addCell(total);
            PdfPCell totalValue = createCell(String.valueOf(phieuThu.getTienDaTra()), normalFont,
                    Element.ALIGN_RIGHT, Rectangle.NO_BORDER);
            tableFooter.addCell(totalValue);

            PdfPTable tableMessage = new PdfPTable(2);
            tableMessage.setWidthPercentage(100);
            float[] columnlistWidthsTableMessage = {4, 6};
            tableMessage.setWidths(columnlistWidthsTableMessage);
            PdfPCell messageCell = new PdfPCell(new Paragraph("CAM ON QUY KHACH VA HEN GAP LAI",normalFont));
            messageCell.setColspan(2);
            messageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            messageCell.setBorder(Rectangle.TOP);
            PdfPCell hotLineCell = createCell("Hotline : " + phieuThu.getChiNhanh().getTongCongTy().getSoDienThoai(),
                    normalFont,Element.ALIGN_RIGHT,Rectangle.BOTTOM);
            String website = phieuThu.getChiNhanh().getTongCongTy().getWebsite() != null ? phieuThu.getChiNhanh().getTongCongTy().getWebsite() : "https://facebook.com";
            PdfPCell websitCell = createCell("Website : " + website,
                    normalFont,Element.ALIGN_LEFT, Rectangle.BOTTOM);
            tableMessage.addCell(messageCell);
            tableMessage.addCell(hotLineCell);
            tableMessage.addCell(websitCell);
            document.add(tableMessage);

            Paragraph paragraphQR = new Paragraph();
//            String qrStr = DatabaseConstant.Host.URL_DEPLOY_BACKEND + "api/v1/public/hoa-don/find-by-ma/" + hoaDon.getMa();
            String qrStr = phieuThu.getMaPhieu();
            byte[] bytes = ZXingHelper.getQRCodeImage(qrStr, 100, 100);
            Image qrCode = Image.getInstance(bytes);
            paragraphQR.add(qrCode);
            paragraphQR.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraphQR);

            document.close();
            writer.close();
            return file;
        } catch (Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }

    public File createPhieuChiPhieuNHapHangPDF(PhieuChi phieuChi, PhieuNhapHang phieuNhapHang){
        Rectangle rectangle = new Rectangle(268, 720);
        Document document = new Document(rectangle, 5, 5, 15, 30);
        try {
            String fileName = phieuChi.getMaPhieu() + ".pdf";
            File file = new File(UPLOAD_DIRECTORY + fileName);
            file.getParentFile().mkdirs();
            FileOutputStream outFile = new FileOutputStream(file);
            PdfWriter writer = PdfWriter.getInstance(document, outFile);
            document.open();


            Image image = Image.getInstance(phieuChi.getChiNhanh().getTongCongTy().getLogo2());
            image.scaleToFit(70,70);
            image.setAlignment(Element.ALIGN_LEFT);

            PdfPTable header = new PdfPTable(2);
            float[] cols = {1,1};
            header.setWidths(cols);
            PdfPCell cellImg = new PdfPCell(image);
            cellImg.setBorder(Rectangle.NO_BORDER);
            header.addCell(cellImg);
            PdfPCell cellCongTy = new PdfPCell();
            Paragraph tenPara = new Paragraph(StringUtils.replacingAllAccents(phieuChi.getChiNhanh().getTongCongTy().getTenDoanhNghiep()), titleFont);
            Paragraph maCNPara = new Paragraph(StringUtils.replacingAllAccents(phieuChi.getChiNhanh().getMaChiNhanh()));
            Paragraph diaChiPara = new Paragraph(StringUtils.replacingAllAccents(phieuChi.getChiNhanh().getDiaChi()), normalFont);
            cellCongTy.setPaddingLeft(10);
            cellCongTy.setBorder(Rectangle.NO_BORDER);
            cellCongTy.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCongTy.setVerticalAlignment(Element.ALIGN_CENTER);
            cellCongTy.addElement(tenPara);
            cellCongTy.addElement(maCNPara);
            cellCongTy.addElement(diaChiPara);
            header.addCell(cellCongTy);
            document.add(header);

            Paragraph paragraphTitle = createPara("PHIEU CHI", titleFont, Element.ALIGN_CENTER,
                    0, 0, 10);
            document.add(paragraphTitle);

            Paragraph maHD = createPara("Ma phieu chi : " + StringUtils.replacingAllAccents(phieuChi.getMaPhieu()), normalFont, Element.ALIGN_LEFT,
                    5, 0, 10);
            document.add(maHD);
            Paragraph thoiGian = createPara("Thoi gian : " + DateTimeUtils.convertDateToString(phieuChi.getThoiGian(), DatabaseConstant.DateTime.DATE_TIME_FORMAT_1),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(thoiGian);
            Paragraph khachHang = createPara("Nha cung cap : " + StringUtils.replacingAllAccents(phieuNhapHang.getNhaCungCap().getTen()),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(khachHang);
            Paragraph nguoiDung = createPara("Nhan vien : " + StringUtils.replacingAllAccents(phieuChi.getNguoiDung().getHoVaTen()),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(nguoiDung);

            Paragraph tienDaTra = createPara("Tien thu : " + StringUtils.replacingAllAccents(phieuChi.getDaTra().toString()),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(tienDaTra);

            PdfPTable tableFooter = new PdfPTable(2);
            tableFooter.setWidthPercentage(100);
            float[] columnlistWidthsTable = {1, 1};
            tableFooter.setWidths(columnlistWidthsTable);

            PdfPCell total = createCell("Tong", boldFont,
                    Element.ALIGN_LEFT, Rectangle.NO_BORDER);
            tableFooter.addCell(total);
            PdfPCell totalValue = createCell(String.valueOf(phieuChi.getDaTra()), normalFont,
                    Element.ALIGN_RIGHT, Rectangle.NO_BORDER);
            tableFooter.addCell(totalValue);

            PdfPTable tableMessage = new PdfPTable(2);
            tableMessage.setWidthPercentage(100);
            float[] columnlistWidthsTableMessage = {4, 6};
            tableMessage.setWidths(columnlistWidthsTableMessage);
            PdfPCell messageCell = new PdfPCell(new Paragraph("CAM ON QUY KHACH VA HEN GAP LAI",normalFont));
            messageCell.setColspan(2);
            messageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            messageCell.setBorder(Rectangle.TOP);
            PdfPCell hotLineCell = createCell("Hotline : " + phieuChi.getChiNhanh().getTongCongTy().getSoDienThoai(),
                    normalFont,Element.ALIGN_RIGHT,Rectangle.BOTTOM);
            String website = phieuChi.getChiNhanh().getTongCongTy().getWebsite() != null ? phieuChi.getChiNhanh().getTongCongTy().getWebsite() : "https://facebook.com";
            PdfPCell websitCell = createCell("Website : " + website,
                    normalFont,Element.ALIGN_LEFT, Rectangle.BOTTOM);
            tableMessage.addCell(messageCell);
            tableMessage.addCell(hotLineCell);
            tableMessage.addCell(websitCell);
            document.add(tableMessage);

            Paragraph paragraphQR = new Paragraph();
//            String qrStr = DatabaseConstant.Host.URL_DEPLOY_BACKEND + "api/v1/public/hoa-don/find-by-ma/" + hoaDon.getMa();
            String qrStr = phieuChi.getMaPhieu();
            byte[] bytes = ZXingHelper.getQRCodeImage(qrStr, 100, 100);
            Image qrCode = Image.getInstance(bytes);
            paragraphQR.add(qrCode);
            paragraphQR.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraphQR);

            document.close();
            writer.close();
            return file;
        } catch (Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }

    public File createPhieuChiPhieuTraKhachPDF(PhieuChi phieuChi, HoaDon hoaDon){
        Rectangle rectangle = new Rectangle(268, 720);
        Document document = new Document(rectangle, 5, 5, 15, 30);
        try {
            String fileName = phieuChi.getMaPhieu() + ".pdf";
            File file = new File(UPLOAD_DIRECTORY + fileName);
            file.getParentFile().mkdirs();
            FileOutputStream outFile = new FileOutputStream(file);
            PdfWriter writer = PdfWriter.getInstance(document, outFile);
            document.open();


            Image image = Image.getInstance(phieuChi.getChiNhanh().getTongCongTy().getLogo2());
            image.scaleToFit(70,70);
            image.setAlignment(Element.ALIGN_LEFT);

            PdfPTable header = new PdfPTable(2);
            float[] cols = {1,1};
            header.setWidths(cols);
            PdfPCell cellImg = new PdfPCell(image);
            cellImg.setBorder(Rectangle.NO_BORDER);
            header.addCell(cellImg);
            PdfPCell cellCongTy = new PdfPCell();
            Paragraph tenPara = new Paragraph(StringUtils.replacingAllAccents(phieuChi.getChiNhanh().getTongCongTy().getTenDoanhNghiep()), titleFont);
            Paragraph maCNPara = new Paragraph(StringUtils.replacingAllAccents(phieuChi.getChiNhanh().getMaChiNhanh()));
            Paragraph diaChiPara = new Paragraph(StringUtils.replacingAllAccents(phieuChi.getChiNhanh().getDiaChi()), normalFont);
            cellCongTy.setPaddingLeft(10);
            cellCongTy.setBorder(Rectangle.NO_BORDER);
            cellCongTy.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCongTy.setVerticalAlignment(Element.ALIGN_CENTER);
            cellCongTy.addElement(tenPara);
            cellCongTy.addElement(maCNPara);
            cellCongTy.addElement(diaChiPara);
            header.addCell(cellCongTy);
            document.add(header);

            Paragraph paragraphTitle = createPara("PHIEU CHI", titleFont, Element.ALIGN_CENTER,
                    0, 0, 10);
            document.add(paragraphTitle);

            Paragraph maHD = createPara("Ma phieu chi : " + StringUtils.replacingAllAccents(phieuChi.getMaPhieu()), normalFont, Element.ALIGN_LEFT,
                    5, 0, 10);
            document.add(maHD);
            Paragraph thoiGian = createPara("Thoi gian : " + DateTimeUtils.convertDateToString(phieuChi.getThoiGian(), DatabaseConstant.DateTime.DATE_TIME_FORMAT_1),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(thoiGian);
            Paragraph khachHang = createPara("Khach hang : " + StringUtils.replacingAllAccents(hoaDon.getKhachHang().getTenKhachHang()),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(khachHang);
            Paragraph nguoiDung = createPara("Nhan vien : " + StringUtils.replacingAllAccents(phieuChi.getNguoiDung().getHoVaTen()),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(nguoiDung);

            Paragraph tienDaTra = createPara("Tien chi : " + StringUtils.replacingAllAccents(phieuChi.getDaTra().toString()),
                    normalFont, Element.ALIGN_LEFT, 5, 0, 10);
            document.add(tienDaTra);

            PdfPTable tableFooter = new PdfPTable(2);
            tableFooter.setWidthPercentage(100);
            float[] columnlistWidthsTable = {1, 1};
            tableFooter.setWidths(columnlistWidthsTable);

            PdfPCell total = createCell("Tong", boldFont,
                    Element.ALIGN_LEFT, Rectangle.NO_BORDER);
            tableFooter.addCell(total);
            PdfPCell totalValue = createCell(String.valueOf(phieuChi.getDaTra()), normalFont,
                    Element.ALIGN_RIGHT, Rectangle.NO_BORDER);
            tableFooter.addCell(totalValue);

            PdfPTable tableMessage = new PdfPTable(2);
            tableMessage.setWidthPercentage(100);
            float[] columnlistWidthsTableMessage = {4, 6};
            tableMessage.setWidths(columnlistWidthsTableMessage);
            PdfPCell messageCell = new PdfPCell(new Paragraph("CAM ON QUY KHACH VA HEN GAP LAI",normalFont));
            messageCell.setColspan(2);
            messageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            messageCell.setBorder(Rectangle.TOP);
            PdfPCell hotLineCell = createCell("Hotline : " + phieuChi.getChiNhanh().getTongCongTy().getSoDienThoai(),
                    normalFont,Element.ALIGN_RIGHT,Rectangle.BOTTOM);
            String website = phieuChi.getChiNhanh().getTongCongTy().getWebsite() != null ? phieuChi.getChiNhanh().getTongCongTy().getWebsite() : "https://facebook.com";
            PdfPCell websitCell = createCell("Website : " + website,
                    normalFont,Element.ALIGN_LEFT, Rectangle.BOTTOM);
            tableMessage.addCell(messageCell);
            tableMessage.addCell(hotLineCell);
            tableMessage.addCell(websitCell);
            document.add(tableMessage);

            Paragraph paragraphQR = new Paragraph();
//            String qrStr = DatabaseConstant.Host.URL_DEPLOY_BACKEND + "api/v1/public/hoa-don/find-by-ma/" + hoaDon.getMa();
            String qrStr = phieuChi.getMaPhieu();
            byte[] bytes = ZXingHelper.getQRCodeImage(qrStr, 100, 100);
            Image qrCode = Image.getInstance(bytes);
            paragraphQR.add(qrCode);
            paragraphQR.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraphQR);

            document.close();
            writer.close();
            return file;
        } catch (Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }

}