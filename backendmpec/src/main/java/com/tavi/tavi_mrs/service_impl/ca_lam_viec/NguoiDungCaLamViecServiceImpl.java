package com.tavi.tavi_mrs.service_impl.ca_lam_viec;

import com.tavi.tavi_mrs.entities.ca_lam_viec.NguoiDungCaLamViec;
import com.tavi.tavi_mrs.entities.hoa_don.HoaDon;
import com.tavi.tavi_mrs.repository.ca_lam_viec.NguoiDungCaLamViecRepo;
import com.tavi.tavi_mrs.service.ca_lam_viec.NguoiDungCaLamViecService;
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
public class NguoiDungCaLamViecServiceImpl implements NguoiDungCaLamViecService{

    private static final java.util.logging.Logger LOGGER = Logger.getLogger(NguoiDungCaLamViec.class.getName());

    @Autowired
    private NguoiDungCaLamViecRepo nguoiDungCaLamViecRepo;


    @Override
    public List<NguoiDungCaLamViec> findAll() {
        try{
            return nguoiDungCaLamViecRepo.findAll();
        }catch (Exception exception){
            LOGGER.log(Level.SEVERE, "find-all-nguoi-dung-ca-lam-viec-err: " + exception);
            return null;
        }
    }

    @Override
    public Page<NguoiDungCaLamViec> findAll(Pageable pageable) {
        try{
            return nguoiDungCaLamViecRepo.findAll(pageable);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-nguoi-dung-ca-lam-viec-err: " + ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<NguoiDungCaLamViec> search(String text, Pageable pageable) {
        try{
            return nguoiDungCaLamViecRepo.search(text,pageable);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-nguoi-dung-ca-lam-viec-err: " + ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<NguoiDungCaLamViec> findByIdAndXoa(int id, boolean xoa) {
        try{
            return nguoiDungCaLamViecRepo.findByIdAndXoa(id, xoa);
        }catch (Exception ex){
            LOGGER.log(Level.SEVERE, "find-nguoi-dung-ca-lam-viec-by-id-err: " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Page<NguoiDungCaLamViec> findByNguoiDungAndNgayThang(Integer nguoiDung, String ngayDau, String ngayCuoi, int status, Pageable pageable) {
        try {
            return nguoiDungCaLamViecRepo.findByNguoiDungAndNgayThang(nguoiDung, ngayDau, ngayCuoi, status, pageable);
        }catch (Exception ex){
            LOGGER.log(Level.SEVERE, "findByNguoiDungAndNgayThang: " + ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<NguoiDungCaLamViec> findByCaLamViecAndThoiGian(Integer caLamViec, String ngayDau, String ngayCuoi, int status, Pageable pageable) {
        try {
            return nguoiDungCaLamViecRepo.findByCaLamViecAndThoiGian(caLamViec, ngayDau, ngayCuoi, status, pageable);
        }catch (Exception ex){
            LOGGER.log(Level.SEVERE, "findByCaLamViecAndThoiGian: " + ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<NguoiDungCaLamViec> findByNguoiDungAndCaLamViecAndNgayThang(Integer nguoiDungId, Integer calamviecId, String ngayThang) {
        try {
            return nguoiDungCaLamViecRepo.findByNguoiDungAndCaLamViecAndNgayThang(nguoiDungId,calamviecId,ngayThang);
        }catch (Exception ex){
            LOGGER.log(Level.SEVERE, "findByNguoiDungAndCaLamViecAndNgayThang-err: " + ex);
            return Optional.empty();
        }
    }

    @Override
    public List<NguoiDungCaLamViec> findListNguoiDungCaLamViec(List<Integer> listHoaDonId) {
        try {
            List<NguoiDungCaLamViec> nguoiDungCaLamViec = new ArrayList<>();
            for (Integer id : listHoaDonId) {
                Optional<NguoiDungCaLamViec> nguoiDungCaLamViecOptional = nguoiDungCaLamViecRepo.findById(id, false);
                if (nguoiDungCaLamViecOptional.isPresent()) {
                    nguoiDungCaLamViec.add(nguoiDungCaLamViecOptional.get());
                }
            }
            return nguoiDungCaLamViec;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-list-hoa-don-error : " + ex);
            return null;
        }
    }

    @Override
    public Optional<NguoiDungCaLamViec> save(NguoiDungCaLamViec nguoiDungCaLamViec) {
        try {
            return Optional.ofNullable(nguoiDungCaLamViecRepo.save(nguoiDungCaLamViec));
        }catch (Exception ex){
            LOGGER.log(Level.SEVERE, "save-nguoi-dung-ca-lam-viec-err: " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Boolean setStatus(int id, int status) {
        try {
            return nguoiDungCaLamViecRepo.setStatus(id, status) > 0;
        }catch (Exception ex){
            ex.printStackTrace();
            LOGGER.log(Level.SEVERE, "set trang thai error: " + ex);
            return false;
        }
    }

    @Override
    public Boolean delete(int id) {
        try{
            return nguoiDungCaLamViecRepo.delete(id) > 0 ? true : false;
        }catch (Exception ex){
            LOGGER.log(Level.SEVERE, "xoa nguoi dung ca lam viec: " + ex);
            return false;
        }
    }
}
