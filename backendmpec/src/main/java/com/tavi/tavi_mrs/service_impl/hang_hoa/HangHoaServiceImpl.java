package com.tavi.tavi_mrs.service_impl.hang_hoa;

import com.tavi.tavi_mrs.entities.hang_hoa.HangHoa;
import com.tavi.tavi_mrs.repository.hang_hoa.HangHoaRepo;
import com.tavi.tavi_mrs.service.aws.AWSS3Service;
import com.tavi.tavi_mrs.service.hang_hoa.HangHoaService;
import com.tavi.tavi_mrs.utils.Random;
import com.tavi.tavi_mrs.utils.hang_hoa.ImportExcelHangHoa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class HangHoaServiceImpl implements HangHoaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HangHoaServiceImpl.class);

    @Autowired
    private HangHoaRepo hangHoaRepo;

    @Autowired
    private AWSS3Service awss3Service;

    @Autowired
    private ImportExcelHangHoa importExcelHangHoa;

    @Override
    public Optional<HangHoa> findById(int id, boolean xoa) {
        try {
            return hangHoaRepo.findById(id, xoa);
        } catch (Exception ex) {
            LOGGER.error("findById error", ex);
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<HangHoa> findByMaHangHoa(String ma, boolean xoa) {
        try {
            return hangHoaRepo.findByMaHangHoa(ma, xoa);
        } catch (Exception ex) {
            LOGGER.error("findByMaHangHoa error", ex);
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<HangHoa> findByMaVach(String ma, boolean xoa) {
        try {
            return hangHoaRepo.findByMaVach(ma, xoa);
        } catch (Exception ex) {
            LOGGER.error("findByMaVach error", ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<HangHoa> findAllToPage(Pageable pageable) {
        try {
            return hangHoaRepo.findAllToPage(pageable);
        } catch (Exception ex) {
            LOGGER.error("findAllToPage error", ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<HangHoa> save(HangHoa hangHoa) {
        try {
            hangHoa.setXoa(false);
            String code = Random.randomCode();
            String maHH = hangHoa.getTenHangHoa().substring(0, 1) +
                    hangHoa.getNhomHang().getTenNhomHang().substring(0, 1) +
                    hangHoa.getThuongHieu().getTenThuongHieu().substring(0, 1) + " - " + code;
            hangHoa.setMa(maHH);
            return Optional.ofNullable(hangHoaRepo.save(hangHoa));
        } catch (Exception ex) {
            LOGGER.error("save hang hoa error", ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<HangHoa> update(HangHoa hangHoaDto) {
        try {
            Optional<HangHoa> hangHoaOptional = hangHoaRepo.findById(hangHoaDto.getId());
            if (!hangHoaOptional.isPresent()) {
                return null;
            }
            HangHoa hangHoa = hangHoaOptional.get();
            hangHoa.setTenHangHoa(hangHoaDto.getTenHangHoa());
            hangHoa.setTichDiem(hangHoaDto.getTichDiem());
            hangHoa.setPhanTramGiamGia(hangHoaDto.getPhanTramGiamGia());
            hangHoa.setMaGiamGia(hangHoaDto.getMaGiamGia());
            hangHoa.setMaVach(hangHoaDto.getMaVach());
            hangHoa.setMoTa(hangHoaDto.getMoTa());

            if (hangHoa.getUrlHinhAnh1() != null && !hangHoa.getUrlHinhAnh1().equals(hangHoaDto.getUrlHinhAnh1())) {
                if (hangHoa.getUrlHinhAnh1() != null && !hangHoa.getUrlHinhAnh1().equals("")) {
                    awss3Service.deleteFileFromS3Bucket(hangHoa.getUrlHinhAnh1());
                }
            }
            hangHoa.setUrlHinhAnh1(hangHoaDto.getUrlHinhAnh1());
            if (hangHoa.getUrlHinhAnh2() != null && !hangHoa.getUrlHinhAnh2().equals(hangHoaDto.getUrlHinhAnh2())) {
                if (hangHoa.getUrlHinhAnh2() != null && !hangHoa.getUrlHinhAnh2().equals("")) {
                    awss3Service.deleteFileFromS3Bucket(hangHoa.getUrlHinhAnh2());
                }
            }
            hangHoa.setUrlHinhAnh2(hangHoaDto.getUrlHinhAnh2());
            if (hangHoa.getUrlHinhAnh3() != null && !hangHoa.getUrlHinhAnh3().equals(hangHoaDto.getUrlHinhAnh3())) {
                if (hangHoa.getUrlHinhAnh3() != null && !hangHoa.getUrlHinhAnh3().equals("")) {
                    awss3Service.deleteFileFromS3Bucket(hangHoa.getUrlHinhAnh3());
                }
            }
            hangHoa.setUrlHinhAnh3(hangHoaDto.getUrlHinhAnh3());
            hangHoa.setThuongHieu(hangHoaDto.getThuongHieu());
            hangHoa.setNhomHang(hangHoaDto.getNhomHang());
            hangHoa.setXoa(false);
            return Optional.ofNullable(hangHoaRepo.save(hangHoa));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    public Page<HangHoa> findByTenHangHoaAndNhomHangAndThuongHieu(String tenHangHoa, int nhomHangId, int thuongHieuId, Pageable pageable) {
        try {
            return hangHoaRepo.findByTenHangHoaAndNhomHangAndThuongHieu(tenHangHoa, nhomHangId, thuongHieuId, pageable);
        } catch (Exception ex) {
            LOGGER.error("findByTenHangHoaAndNhomHangAndThuongHieu error", ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<HangHoa> search(String text, Pageable pageable) {
        try {
            return hangHoaRepo.search(text, pageable);
        } catch (Exception ex) {
            LOGGER.error("findByTenHangHoaAndNhomHangAndThuongHieu error", ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean deleted(Integer id) {
        try {
            return hangHoaRepo.deleted(id) > 0 ? true : false;
        } catch (Exception ex) {
            LOGGER.error("detete-hang-hoa-error : " + ex);
            return false;
        }
    }

    @Override
    public void saveByExcel(MultipartFile file) {
        try {
            importExcelHangHoa.excelHangHoas(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

}
