package com.tavi.tavi_mrs.service_impl.hoa_don;

import com.tavi.tavi_mrs.entities.bieu_do.BieuDo;
import com.tavi.tavi_mrs.entities.don_vi.LichSuGiaBan;
import com.tavi.tavi_mrs.entities.dto.HoaDonDto;
import com.tavi.tavi_mrs.entities.bao_cao.ThongKeDto;
import com.tavi.tavi_mrs.entities.hoa_don.HoaDon;
import com.tavi.tavi_mrs.entities.hoa_don.HoaDonChiTiet;
import com.tavi.tavi_mrs.entities.khach_hang.KhachHangDto;
import com.tavi.tavi_mrs.repository.don_vi.LichSuGiaBanRepo;
import com.tavi.tavi_mrs.repository.hoa_don.HoaDonChiTietRepo;
import com.tavi.tavi_mrs.repository.hoa_don.HoaDonRepo;
import com.tavi.tavi_mrs.service.hoa_don.HoaDonService;
import com.tavi.tavi_mrs.utils.PdfUtils;
import com.tavi.tavi_mrs.utils.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class HoaDonServiceImpl implements HoaDonService {

    private static final java.util.logging.Logger LOGGER = Logger.getLogger(HoaDonServiceImpl.class.getName());

    @Autowired
    private HoaDonRepo hoaDonRepo;

    @Autowired
    private HoaDonChiTietRepo hoaDonChiTietRepo;

    @Autowired
    private LichSuGiaBanRepo lichSuGiaBanRepo;

    @Autowired
    private PdfUtils pdfUtils;

    @Override
    public List<HoaDon> findAll() {
        try {
            return hoaDonRepo.findAll();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-hoa-don-err: " + ex);
            return null;
        }
    }

    @Override
    public Optional<HoaDon> findById(int id) {
        return Optional.empty();
    }

    @Override
    public HoaDon findByIdAndXoa(int id, Boolean xoa) {
        try {
            return hoaDonRepo.findByIdAndXoa(id, xoa);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-hoa-don-error : " + ex);
            ex.printStackTrace();
            return null;
        }
    }


    @Override
    public Optional<HoaDon> findById(int id, boolean xoa) {
        try {
            return hoaDonRepo.findById(id, xoa);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-by-id-hoa-don-error : " + ex);
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Page<HoaDon> findByIdKhachHang(int id, boolean xoa, Pageable pageable) {
        try {
            return hoaDonRepo.findByIdKhachHang(id, xoa, pageable);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-by-id-khach-hang-error : " + ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<HoaDon> findByIdKhachHangForAll(int id, boolean xoa, Pageable pageable) {
        try {
            return hoaDonRepo.findByIdKhachHangForAll(id, xoa, pageable);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-by-id-khach-hang-for-all-error :" + ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateTienKhachTra(Float tienKhachTra, int hoaDonId, float conNo) {
        try {
            return hoaDonRepo.updateTienKhachTra(tienKhachTra, hoaDonId, conNo) > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.log(Level.SEVERE, "set trang thai error : " + ex);
            return false;
        }
    }

    @Override
    public Optional<HoaDon> findByMa(String ma, boolean xoa) {
        try {
            return hoaDonRepo.findByMa(ma, xoa);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-hoa-don-error : " + ex);
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<HoaDon> findListHoaDon(List<Integer> listHoaDonId) {
        try {
            List<HoaDon> hoaDonList = new ArrayList<>();
            for (Integer id : listHoaDonId) {
                Optional<HoaDon> hoaDonOptional = hoaDonRepo.findById(id, false);
                if (hoaDonOptional.isPresent()) {
                    hoaDonList.add(hoaDonOptional.get());
                }
            }
            return hoaDonList;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-list-hoa-don-error : " + ex);
            return null;
        }
    }


    @Override
    public Page<HoaDon> findAllToPage(Pageable pageable) {
        try {
            return hoaDonRepo.findAllToPage(pageable);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-thuong-hieu-error : " + ex);
            ex.printStackTrace();
            return null;
        }
    }


    @Override
    public boolean setTrangThai(int id, int trangThai) {
        try {
            return hoaDonRepo.setTrangThai(id, trangThai) > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.log(Level.SEVERE, "set trang thai error : " + ex);
            return false;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String saveHoaDonDto(HoaDonDto hoaDonDto) {
        try {
            if (hoaDonDto.getHoaDonChiTietList().size() != hoaDonDto.getLichSuGiaBanIdList().size()) {
                return "Bad Request";
            } else {
                LocalDateTime now = LocalDateTime.now();
                Date out = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
                hoaDonDto.getHoaDon().setThoiGian(out);
                hoaDonDto.getHoaDon().setXoa(false);
                String code = "";
                while (true) {
                    code = "HD-" + Random.randomCode();
                    if (!hoaDonRepo.findByMa(code, false).isPresent()) {
                        break;
                    }
                }
                hoaDonDto.getHoaDon().setMa(code);
                HoaDon hd = hoaDonRepo.save(hoaDonDto.getHoaDon());
                if (hd != null) {
                    for (int i = 0; i < hoaDonDto.getHoaDonChiTietList().size(); i++) {
                        HoaDonChiTiet hdct = hoaDonDto.getHoaDonChiTietList().get(i);
                        hdct.setHoaDon(hd);
                        LichSuGiaBan lichSuGiaBan = lichSuGiaBanRepo.findById(hoaDonDto.getLichSuGiaBanIdList().get(i)).get();
                        hdct.setLichSuGiaBan(lichSuGiaBan);
                        hdct.setXoa(false);
                        if (hdct.getGiamGia() != null) {
                            hdct.setGiamGia((double) 0);
                        }
                        hoaDonChiTietRepo.save(hdct);
                    }
                    return "Success";
                } else {
                    return "Bad Request";
                }
            }
        } catch (Exception ex) {
            return "Server Error";
        }
    }

    @Override
    public Page<HoaDon> findByMaHoaDonAndThoiGianAndTrangThai(Integer chiNhanhId, String maHoaDon, String tenKhachHang, String tenNhanVien, Date ngayDau, Date ngayCuoi, int trangThai, Pageable pageable) {
        try {
            return hoaDonRepo.findByMaHoaDonAndThoiGianAndTrangThai(chiNhanhId, maHoaDon, tenKhachHang, tenNhanVien, ngayDau, ngayCuoi, trangThai, pageable);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-thuong-hieu-error : " + ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<HoaDon> findByChiNhanhAndText(int chiNhanhId, String text, Pageable pageable) {
        try {
            return hoaDonRepo.findByChiNhanhAndText(chiNhanhId, text, pageable);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "findByChiNhanhAndText : " + ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer countBillByTime(Date start, Date end) {
        try {
            return hoaDonRepo.countBillByTime(start, end);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Integer sumBillByTime(Date start, Date end) {
        try {
            Integer sum = hoaDonRepo.sumBillByTime(start, end);
            return sum == null ? 0 : sum;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public File createPdf(HoaDon hoaDon, List<HoaDonChiTiet> hoaDonChiTietList) {
        try {
            return pdfUtils.createHoaDonPDF(hoaDon, hoaDonChiTietList);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<BieuDo> bieuDoDoanhThuTong(Date ngayDau, Date ngayCuoi, boolean xoa) {
        try {
            return hoaDonRepo.bieuDoDoanhThuTong(ngayDau, ngayCuoi, xoa);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Bieu-do-doanh-thu-tong-err : " + ex);
            return null;
        }
    }

    @Override
    public List<BieuDo> bieuDoDoanhThuTrongTuan(int week, int year, boolean xoa) {
        try {
            return hoaDonRepo.bieuDoDoanhThuTrongTuan(week, year, xoa);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Bieu-do-doanh-thu-trong-thang-err : " + ex);
            return null;
        }
    }

    @Override
    public List<BieuDo> bieuDoDoanhThuTrongThang(int month, int year, boolean xoa) {
        try {
            return hoaDonRepo.bieuDoDoanhThuTrongThang(month, year, xoa);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Bieu-do-doanh-thu-trong-thang-err : " + ex);
            return null;
        }
    }

    @Override
    public List<BieuDo> bieuDoDoanhThuGioTrongThang(int month, int year, boolean xoa) {
        try {
            return hoaDonRepo.bieuDoDoanhThuGioTrongThang(month, year, xoa);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Bieu-do-doanh-thu-trong-thang-err : " + ex);
            return null;
        }
    }

    @Override
    public List<BieuDo> bieuDoDoanhThuTrongNam(int year, boolean xoa) {
        try {
            return hoaDonRepo.bieuDoDoanhThuTrongNam(year, xoa);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Bieu-do-doanh-thu-trong-nam-err : " + ex);
            return null;
        }
    }

    @Override
    public List<BieuDo> bieuDoDoanhThuByNV(Date ngayDau, Date ngayCuoi, int nguoiDungId, boolean xoa) {
        try {
            return hoaDonRepo.bieuDoDoanhThuByNV(ngayDau, ngayCuoi, nguoiDungId, xoa);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Bieu-do-doanh-thu-by-nhan-vien-err : " + ex);
            return null;
        }
    }

    @Override
    public List<HoaDon> findRecentOrder() {
        try {
            return hoaDonRepo.findRecentOrder();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "findRecentOrder-hoa-don-err: " + ex);
            return null;
        }
    }

    @Override
    public Page<HoaDon> searchHoaDonHangChuaGiao(Integer chiNhanhId, int trangThai, Pageable pageable) {
        try {
            return hoaDonRepo.searchHoaDonHangChuaGiao(chiNhanhId, trangThai, pageable);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "searchHoaDonHangChuaGiao : " + ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ThongKeDto> findTopNhanVienTrongThang(int month, int year, boolean top, Pageable pageable) {
        try {
            return top ? hoaDonRepo.topDoanhThuNhanVienTheoThang(month, year, pageable)
                    : hoaDonRepo.bottomDoanhThuNhanVienTheoThang(month, year, pageable);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-top-doanh-thu-nhan-vien-trong-thang-err: " + ex);
            return null;
        }
    }

    @Override
    public List<ThongKeDto> findTopChiNhanhTrongThang(int month, int year, boolean top, Pageable pageable) {
        try {
            return top ? hoaDonRepo.topDoanhThuChiNhanhTheoThang(month, year, pageable)
                    : hoaDonRepo.bottomDoanhThuChiNhanhTheoThang(month, year, pageable);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-top-doanh-thu-nhan-vien-trong-thang-err: " + ex);
            return null;
        }
    }

    @Override
    public Page<KhachHangDto> topKhachHangTheoThang(Date ngayDau, Date ngayCuoi, Pageable pageable) {
        try {
            return hoaDonRepo.topKhachHangTheoThang(ngayDau, ngayCuoi, pageable);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-top-khach-hang-trong-thang-err: " + ex);
            return null;
        }
    }

    @Override
    public Page<KhachHangDto> searchTopKhachHangTheoThang(Date ngayDau, Date ngayCuoi, String text, Pageable pageable) {
        try {
            return hoaDonRepo.searchTopKhachHangTheoThang(ngayDau, ngayCuoi,text, pageable);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-top-khach-hang-trong-thang-err: " + ex);
            return null;
        }
    }
}
