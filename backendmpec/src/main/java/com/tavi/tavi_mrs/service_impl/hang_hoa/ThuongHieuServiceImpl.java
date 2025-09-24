package com.tavi.tavi_mrs.service_impl.hang_hoa;

import com.tavi.tavi_mrs.entities.hang_hoa.NhomHang;
import com.tavi.tavi_mrs.entities.hang_hoa.ThuongHieu;
import com.tavi.tavi_mrs.repository.hang_hoa.ThuongHieuRepo;
import com.tavi.tavi_mrs.service.hang_hoa.ThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ThuongHieuServiceImpl implements ThuongHieuService {

    private static final Logger LOGGER = Logger.getLogger(ThuongHieuServiceImpl.class.getName());

    @Autowired
    ThuongHieuRepo thuongHieuRepo;

    @Override
    public Optional<ThuongHieu> findAll() {
        return Optional.empty();
    }

    @Override
    public Optional<ThuongHieu> findByIdAndXoa(int id, boolean xoa) {
        try {
            return thuongHieuRepo.findByIdAndXoa(id, xoa);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-thuong-hieu-by-id-error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<ThuongHieu> save(ThuongHieu thuongHieu) {
        try {
            return Optional.ofNullable(thuongHieuRepo.save(thuongHieu));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-thuong-hieu-error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Page<ThuongHieu> findByTenThuongHieu(String tenThuongHieu, Pageable pageable) {
        try {
            return thuongHieuRepo.findByTenThuongHieu(tenThuongHieu, pageable);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-by-ten-thuong-hieu-error : " + ex);
            return null;
        }
    }

    public Page<ThuongHieu> findAllToPage(Pageable pageable) {
        try {
            return thuongHieuRepo.findAllToPage(pageable);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-thuong-hieu-error : " + ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean deleted(Integer id) {
        try {
            return thuongHieuRepo.deleted(id) > 0 ? true : false;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "delete-thuong-hieu-error : " + ex);
            return false;
        }
    }

    @Override
    public Boolean findByTenThuongHieu(String tenThuongHieu) {
        try {
            Optional<ThuongHieu> thuongHieu = thuongHieuRepo.findByTenThuongHieu(tenThuongHieu);
            if (thuongHieu.get() != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "findByTenThuongHieu-error : " + ex);
            return false;
        }
    }

    @Override
    public ThuongHieu findByTenThuongHieuExcel(String tenThuongHieu) {
        try {
            Optional<ThuongHieu> thuongHieu = thuongHieuRepo.findByTenThuongHieu(tenThuongHieu);
            if (thuongHieu.get() != null) {
                return thuongHieu.get();
            }
        } catch (Exception ex) {
            ThuongHieu thuongHieu1= new ThuongHieu();
            thuongHieu1.setTenThuongHieu(tenThuongHieu);
            thuongHieu1.setXoa(false);
            return thuongHieuRepo.save(thuongHieu1);
        }
        return null;
    }



}
