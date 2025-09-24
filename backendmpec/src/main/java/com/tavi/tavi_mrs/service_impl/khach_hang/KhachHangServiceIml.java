package com.tavi.tavi_mrs.service_impl.khach_hang;

import com.tavi.tavi_mrs.entities.khach_hang.KhachHang;
import com.tavi.tavi_mrs.repository.khach_hang.KhachHangRepo;
import com.tavi.tavi_mrs.service.khach_hang.KhachHangService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class KhachHangServiceIml implements KhachHangService {

    private static final Logger LOGGER = Logger.getLogger(KhachHangServiceIml.class.getName());

    @Autowired
    private KhachHangRepo khachHangRepo;

    @Override
    public List<KhachHang> findAll() {
        try {
            return khachHangRepo.findAll();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-khach-hang-error : " + ex);
            return null;
        }
    }

    @Override
    public Optional<KhachHang> findByIdAndXoa(int id, boolean xoa) {
        try {
            return khachHangRepo.findByIdAndXoa(id, xoa);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-khach-hang-by-id-error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Page<KhachHang> findByTenKhachHangAndDienThoaiAndEmail(String tenKhachHang, String dienThoai, String email, String facebook, String diaChi, Pageable pageable) {
        try {
            return khachHangRepo.findByTenKhachHangAndDienThoaiAndEmail(tenKhachHang, dienThoai, email, facebook, diaChi, pageable);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "findByTenKhachHangAndDienThoaiAndEmail : " + ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<KhachHang> findAll(Pageable pageable) {
        try {
            return khachHangRepo.findAll(pageable);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "findAll : " + ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<KhachHang> save(KhachHang khachHang) {
        try {
            return Optional.ofNullable(khachHangRepo.save(khachHang));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-khach-hang-error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public List<KhachHang> findListKhachHang(List<Integer> list) {
        try {
            List<KhachHang> khachHangList = new ArrayList<>();
            for (Integer i : list) {
                Optional<KhachHang> khachHangOptional = khachHangRepo.findByIdAndXoa(i, false);
                if (khachHangOptional.isPresent()) {
                    khachHangList.add(khachHangOptional.get());
                }
            }
            return khachHangList;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-list-khach-hang-error : " + ex);
            return null;
        }
    }

    @Override
    public Page<KhachHang> search(String text, Pageable pageable) {
        try {
            return khachHangRepo.search(text, pageable);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Integer countCustomer() {
        try {
            Integer count = khachHangRepo.countCustomer();
            return count == null ? 0 : count;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Integer countCustomerTransaction(Date start, Date end) {
        try {
            Integer count = khachHangRepo.countCustomerTransaction(start, end);
            return count == null ? 0 : count;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Integer countNewMember(Date start, Date end) {
        try {
            Integer count = khachHangRepo.countNewMember(start, end);
            return count == null ? 0 : count;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Boolean deleted(int id) {
        try {
            return khachHangRepo.deleted(id) > 0 ? true : false;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "delete-khach-hang-error : " + ex);
            return false;
        }
    }
}
