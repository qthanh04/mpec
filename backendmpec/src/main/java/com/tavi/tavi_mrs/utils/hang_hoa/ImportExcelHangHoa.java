package com.tavi.tavi_mrs.utils.hang_hoa;

import com.tavi.tavi_mrs.entities.chi_nhanh.ChiNhanh;
import com.tavi.tavi_mrs.entities.don_vi.DonVi;
import com.tavi.tavi_mrs.entities.don_vi.DonViHangHoa;
import com.tavi.tavi_mrs.entities.don_vi.LichSuGiaBan;
import com.tavi.tavi_mrs.entities.hang_hoa.ChiNhanhHangHoa;
import com.tavi.tavi_mrs.entities.hang_hoa.HangHoa;
import com.tavi.tavi_mrs.entities.hang_hoa.NhomHang;
import com.tavi.tavi_mrs.entities.hang_hoa.ThuongHieu;
import com.tavi.tavi_mrs.repository.don_vi.DonViHangHoaRepo;
import com.tavi.tavi_mrs.repository.don_vi.LichSuGiaBanRepo;
import com.tavi.tavi_mrs.repository.hang_hoa.ChiNhanhHangHoaRepo;
import com.tavi.tavi_mrs.repository.hang_hoa.HangHoaRepo;
import com.tavi.tavi_mrs.service.chi_nhanh.ChiNhanhService;
import com.tavi.tavi_mrs.service.don_vi.DonViService;
import com.tavi.tavi_mrs.service.hang_hoa.NhomHangService;
import com.tavi.tavi_mrs.service.hang_hoa.ThuongHieuService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class ImportExcelHangHoa {

    @Autowired
    private NhomHangService nhomHangService;

    @Autowired
    private ThuongHieuService thuongHieuService;

    @Autowired
    private DonViService donViService;

    @Autowired
    private ChiNhanhService chiNhanhService;

    @Autowired
    private HangHoaRepo hangHoaRepo;

    @Autowired
    private DonViHangHoaRepo donViHangHoaRepo;

    @Autowired
    private LichSuGiaBanRepo lichSuGiaBanRepo;

    @Autowired
    private ChiNhanhHangHoaRepo chiNhanhHangHoaRepo;

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "Id", "ma", "ten_hang_hoa", "ma_vach" , "ma_giam_gia"};
    static String SHEET = "Files";

    public static boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public boolean excelHangHoas(InputStream is) {
        try {

            Workbook workbook = new XSSFWorkbook(is);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> rows = datatypeSheet.iterator();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                HangHoa hangHoa = new HangHoa();
                DonViHangHoa donViHangHoa= new DonViHangHoa();
                LichSuGiaBan lichSuGiaBan = new LichSuGiaBan();
                ChiNhanhHangHoa chiNhanhHangHoa = new ChiNhanhHangHoa();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            ChiNhanh chiNhanh = chiNhanhService.findByMaChiNhanhExcel(currentCell.getStringCellValue());
                            if (chiNhanh!=null){
                                chiNhanhHangHoa.setChiNhanh(chiNhanh);
                            }
                            break;

                        case 1:
                            hangHoa.setMa(currentCell.getStringCellValue());
                            break;

                        case 2:
                            hangHoa.setTenHangHoa(currentCell.getStringCellValue());
                            break;

                        case 3:
                            hangHoa.setMaVach(currentCell.getStringCellValue());
                            break;

                        case 4:
                            hangHoa.setMaGiamGia(currentCell.getStringCellValue());
                            break;

                        case 5:
                            hangHoa.setMoTa(currentCell.getStringCellValue());
                            break;

                        case 6:
                            hangHoa.setPhanTramGiamGia((float)currentCell.getNumericCellValue());
                            break;

                        case 7:
                            hangHoa.setTichDiem((int)currentCell.getNumericCellValue());
                            break;

                        case 8:
                            hangHoa.setUrlHinhAnh1(currentCell.getStringCellValue());
                            break;

                        case 9:
                            hangHoa.setUrlHinhAnh2(currentCell.getStringCellValue());
                            break;

                        case 10:
                            hangHoa.setUrlHinhAnh3(currentCell.getStringCellValue());
                            break;

                        case 11:
                            NhomHang nhomHang = nhomHangService.findByTenNhomHangExcel(currentCell.getStringCellValue());
                            if (nhomHang!=null){
                                hangHoa.setNhomHang(nhomHang);
                            }
                            break;
                        case 12:
                            ThuongHieu thuongHieu=thuongHieuService.findByTenThuongHieuExcel(currentCell.getStringCellValue());
                            if (thuongHieu!=null){
                                hangHoa.setThuongHieu(thuongHieu);
                            }
                            break;

                        case 13:
                            DonVi donVi=donViService.findByTenDonViExcel(currentCell.getStringCellValue());
                            if (donVi!=null){
                                //hangHoa.setThuongHieu(thuongHieu);
                                donViHangHoa.setDonVi(donVi);
                            }
                            break;

                        case 14:
                            donViHangHoa.setTyLe((float) currentCell.getNumericCellValue());
                            break;

                        case 15:
                            lichSuGiaBan.setGiaBan((float) currentCell.getNumericCellValue());
                            break;

                        default:
                            break;
                    }

                    cellIdx++;
                }

                //save hang hoa
                hangHoa.setXoa(false);
                System.out.println(hangHoa);
                HangHoa hangHoa1 = hangHoaRepo.save(hangHoa);

                //save chi nhanh hang hoa
                chiNhanhHangHoa.setXoa(false);
                chiNhanhHangHoa.setHangHoa(hangHoa1);
                chiNhanhHangHoa.setTonKho(0);
                chiNhanhHangHoaRepo.save(chiNhanhHangHoa);

                //save donViHangHoa
                donViHangHoa.setHangHoa(hangHoa1);
                donViHangHoa.setXoa(false);
                System.out.println(donViHangHoa);
                DonViHangHoa donViHangHoa1= donViHangHoaRepo.save(donViHangHoa);

                lichSuGiaBan.setDonViHangHoa(donViHangHoa1);
                LocalDateTime now = LocalDateTime.now();
                Date out = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
                lichSuGiaBan.setThoiGianBatDau(out);
                System.out.println(lichSuGiaBan);
                lichSuGiaBanRepo.save(lichSuGiaBan);
            }

            workbook.close();

            return true;
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
    }
}
