package com.tavi.tavi_mrs.service_impl.hang_hoa;

import com.tavi.tavi_mrs.entities.hang_hoa.NhomHang;
import com.tavi.tavi_mrs.repository.hang_hoa.NhomHangRepo;
import com.tavi.tavi_mrs.service.hang_hoa.NhomHangService;
import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class NhomHangServiceImpl implements NhomHangService {

    private static final java.util.logging.Logger LOGGER = Logger.getLogger(NhomHangServiceImpl.class.getName());

    @Autowired
    NhomHangRepo nhomHangRepo;

    @Override
    public List<NhomHang> findAll() {
        try {
            return nhomHangRepo.findAll();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-nhom-hang-error : " + ex);
            return null;
        }
    }

    @Override
    public Optional<NhomHang> findByIdAndXoa(int id, boolean xoa) {
        try {
            return nhomHangRepo.findByIdAndXoa(id, xoa);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-nhom-hang-by-id-error : " + ex);
            return Optional.empty();
        }
    }


    @Override
    public Optional<NhomHang> save(NhomHang nhomHang) {
        try {
            return Optional.ofNullable(nhomHangRepo.save(nhomHang));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-nhom-hang-error : " + ex);
            return Optional.empty();
        }
    }


    @Override
    public Optional<NhomHang> findById(int id, boolean xoa) {
        try {
            return nhomHangRepo.findById(id, xoa);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-nhom-hang-error : " + ex);
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Boolean findByMaNhomHang(String maNhomHang) {
        try {
            Optional<NhomHang> nhomHang = nhomHangRepo.findByMaNhomHangAndXoa(maNhomHang);
            if (nhomHang.get() != null) {
                System.out.println(nhomHang.get().getMaNhomHang());
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
//            LOGGER.log(Level.SEVERE,"find-nhom-hang-by-ma-error : "+ ex);
            return false;
        }
    }

    @Override
    public Boolean findByTenNhomHang(String tenNhomHang) {
        try {
            Optional<NhomHang> nhomHang = nhomHangRepo.findByTenNhomHangAndXoa(tenNhomHang);
            if (nhomHang.get() != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public NhomHang findByTenNhomHangExcel(String tenNhomHang) {
        try {
            Optional<NhomHang> nhomHang = nhomHangRepo.findByTenNhomHangAndXoa(tenNhomHang);
            if (nhomHang.get() != null) {
                return nhomHang.get();
            }
        } catch (Exception ex) {
            String[] myName = tenNhomHang.split(" ");
            String ma = "";
            for (int i = 0; i < myName.length; i++) {
                String s = myName[i];
                System.out.println(s.charAt(0));
                ma+=Character.toUpperCase(s.charAt(0));

            }
            NhomHang nhomHang1= new NhomHang();
            nhomHang1.setTenNhomHang(tenNhomHang);
            nhomHang1.setMaNhomHang(ma);
            nhomHang1.setXoa(false);
            return nhomHangRepo.save(nhomHang1);
        }
        return null;
    }

    @Override
    public Page<NhomHang> findAllToPage(Pageable pageable) {
        try {
            return nhomHangRepo.findAllToPage(pageable);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-nhom-hang-error : " + ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean deleted(Integer id) {
        try {
            return nhomHangRepo.deleted(id) > 0 ? true : false;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "delete-nhom-hang-error : " + ex);
            return false;
        }
    }

    @Override
    public Page<NhomHang> findByTenNhomHangAndMaNhomHang(String tenNhomHang, String maNhomHang, Pageable pageable) {
        try {
            return nhomHangRepo.findByTenNhomHangAndMaNhomHang(tenNhomHang, maNhomHang, pageable);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-by-ten-nhom-hang-error : " + ex);
            return null;
        }
    }

}
