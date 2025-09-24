package com.tavi.tavi_mrs.service_impl.hang_hoa;

import com.tavi.tavi_mrs.entities.chi_nhanh.ChiNhanh;
import com.tavi.tavi_mrs.entities.hang_hoa.ChiNhanhHangHoa;
import com.tavi.tavi_mrs.entities.hang_hoa.HangHoa;
import com.tavi.tavi_mrs.entities.so_quy.PhieuThu;
import com.tavi.tavi_mrs.entities.hang_hoa.ThongSoChiTiet;
import com.tavi.tavi_mrs.repository.chi_nhanh.ChiNhanhRepo;
import com.tavi.tavi_mrs.repository.hang_hoa.ChiNhanhHangHoaRepo;
import com.tavi.tavi_mrs.repository.hang_hoa.HangHoaRepo;
import com.tavi.tavi_mrs.service.chi_nhanh.ChiNhanhService;
import com.tavi.tavi_mrs.service.hang_hoa.ChiNhanhHangHoaService;
import com.tavi.tavi_mrs.service_impl.so_quy.PhieuChiServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

@Service
public class ChiNhanhHangHoaServiceImpl implements ChiNhanhHangHoaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChiNhanhHangHoaServiceImpl.class);

    @Autowired
    private ChiNhanhHangHoaRepo chiNhanhHangHoaRepo;

    @Autowired
    private ChiNhanhRepo chiNhanhRepo;

    @Autowired
    private HangHoaRepo hangHoaRepo;

    @Override
    public Optional<ChiNhanhHangHoa> findById(int id, boolean xoa) {
        try {
            return chiNhanhHangHoaRepo.findById(id, xoa);
        } catch (Exception ex) {
            LOGGER.error("findById error", ex);
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Optional<ChiNhanhHangHoa> save(int chiNhanhId, HangHoa hangHoa) {
        try {
            StringBuilder maHangHoa = new StringBuilder(hangHoa.getNhomHang().getMaNhomHang() + "-");
            Optional<HangHoa> hangHoaOptional = null;
            try {
                hangHoaOptional = hangHoaRepo.findTheLastHH(maHangHoa.toString());
            } catch (Exception ex) {
                LOGGER.error("find hang hoa err", ex);
                return Optional.empty();
            }
            // System.out.println(hangHoaOptional.get().getMa());
            if (hangHoaOptional.isPresent()) {
                String lastCode = hangHoaOptional.get().getMa();
                String lastIndex = lastCode.split("-")[1];
                Integer currentIndex = Integer.valueOf(lastIndex) + 1;
                int temp = currentIndex;
                while (temp < 1000000) {
                    maHangHoa.append(0);
                    temp *= 10;
                }
                maHangHoa.append(currentIndex);
            } else {
                maHangHoa.append("0000001");
            }
            hangHoa.setMa(maHangHoa.toString());
            if (hangHoa.getMaGiamGia() == null || hangHoa.getMaGiamGia().equals("")) {
                hangHoa.setMaGiamGia("0");
            }
            if (hangHoa.getMoTa() == null || hangHoa.getMoTa().equals("")) {
                hangHoa.setMoTa("Không có mô tả");
            }
            if (hangHoa.getTichDiem() == null) {
                hangHoa.setTichDiem(0);
            }
            if (hangHoa.getPhanTramGiamGia() == null) {
                hangHoa.setPhanTramGiamGia((float) 0);
            }
            hangHoa.setXoa(false);
            HangHoa hh = hangHoaRepo.save(hangHoa);
            if (hh != null) {
                ChiNhanhHangHoa chiNhanhHangHoa = new ChiNhanhHangHoa();
                chiNhanhHangHoa.setHangHoa(hangHoa);
                Optional<ChiNhanh> chiNhanh = chiNhanhRepo.findByIdAndXoa(chiNhanhId, false);
                if (chiNhanh.isPresent()) {
                    chiNhanhHangHoa.setChiNhanh(chiNhanh.get());
                    chiNhanhHangHoa.setXoa(false);
                    return Optional.ofNullable(chiNhanhHangHoaRepo.save(chiNhanhHangHoa));
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception ex) {
            LOGGER.error("save chi nhanh hang hoa error", ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<ChiNhanhHangHoa> save(int chiNhanhId, int hangHoaId) {
        Optional<HangHoa> hangHoaOptional = hangHoaRepo.findById(hangHoaId, false);
        if (!hangHoaOptional.isPresent()) {
            return Optional.empty();
        }
        Optional<ChiNhanh> chiNhanhOptional = chiNhanhRepo.findByIdAndXoa(chiNhanhId, false);
        if (!chiNhanhOptional.isPresent()) {
            return Optional.empty();
        }
        ChiNhanhHangHoa chiNhanhHangHoa = new ChiNhanhHangHoa();
        chiNhanhHangHoa.setHangHoa(hangHoaOptional.get());
        chiNhanhHangHoa.setChiNhanh(chiNhanhOptional.get());
        chiNhanhHangHoa.setXoa(false);
        return Optional.ofNullable(chiNhanhHangHoaRepo.save(chiNhanhHangHoa));
    }


    @Override
    public Page<ChiNhanhHangHoa> findAllToPage(Pageable pageable) {
        try {
            return chiNhanhHangHoaRepo.findAllToPage(pageable);
        } catch (Exception ex) {
            LOGGER.error("findAllToPage error", ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean setTonKho(int soLuong, int id) {
        try {
            return chiNhanhHangHoaRepo.setTonKho(soLuong, id) > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.error("setTonKho error", ex);
            return false;
        }
    }

    @Override
    public Page<ChiNhanhHangHoa> findByChiNhanh(int chiNhanhId, Pageable pageable) {
        try {
            return chiNhanhHangHoaRepo.findByChiNhanh(chiNhanhId, pageable);
        } catch (Exception ex) {
            LOGGER.error("findByChiNhanh error", ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<ChiNhanhHangHoa> findByChiNhanhAndText(int chiNhanhId, String text, Pageable pageable) {
        try {
            return chiNhanhHangHoaRepo.findByChiNhanhAndText(chiNhanhId, text, pageable);
        } catch (Exception ex) {
            LOGGER.error("findAllToPage error", ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ChiNhanhHangHoa> findListChiNhanhHangHoa(List<Integer> listChiNhanhHangHoaId) {
        try {
            List<ChiNhanhHangHoa> chiNhanhHangHoa = new ArrayList<>();
            for (Integer id : listChiNhanhHangHoaId) {
                Optional<ChiNhanhHangHoa> chiNhanhHangHoaOptional = chiNhanhHangHoaRepo.findByIdAndXoa(id,false);
                if (chiNhanhHangHoaOptional.isPresent()) {
                    chiNhanhHangHoa.add(chiNhanhHangHoaOptional.get());
                }
            }
            return chiNhanhHangHoa;
        } catch (Exception ex) {
            LOGGER.error("find-list-chi-nhanh-hang-hoa-error : " + ex);
            return null;
        }
    }

    @Override
    public Optional<ChiNhanhHangHoa> findByChiNhanhIdAndHangHoaId(int chiNhanhId, int hangHoaId) {
        try {
            return chiNhanhHangHoaRepo.findByChiNhanhIdAndHangHoaId(chiNhanhId, hangHoaId);
        } catch (Exception ex) {
            LOGGER.error("find-chi-nhanh-hang-hoa-error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<ChiNhanhHangHoa> saveObject(ChiNhanhHangHoa chiNhanhHangHoa) {
        try {
            return Optional.ofNullable(chiNhanhHangHoaRepo.save(chiNhanhHangHoa));
        }catch (Exception ex) {
            LOGGER.error("saveObject-error : " + ex);
            return Optional.empty();
        }
    }


}
