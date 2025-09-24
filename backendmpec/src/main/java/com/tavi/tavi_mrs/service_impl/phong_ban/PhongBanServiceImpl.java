package com.tavi.tavi_mrs.service_impl.phong_ban;

import com.amazonaws.services.apigateway.model.Op;
import com.tavi.tavi_mrs.entities.chuc_vu_vai_tro.VaiTro;
import com.tavi.tavi_mrs.entities.dto.SelectResultDto;
import com.tavi.tavi_mrs.entities.dto.SelectResultListDto;
import com.tavi.tavi_mrs.entities.hang_hoa.NhomHang;
import com.tavi.tavi_mrs.entities.phong_ban.PhongBan;
import com.tavi.tavi_mrs.repository.phong_ban.PhongBanRepo;
import com.tavi.tavi_mrs.service.phong_ban.PhongBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PhongBanServiceImpl implements PhongBanService {

    private static final Logger LOGGER = Logger.getLogger(PhongBanServiceImpl.class.getName());

    @Autowired
    private PhongBanRepo phongBanRepo;


    @Override
    public List<PhongBan> findAll() {
        try {
            return phongBanRepo.findAll();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-phong-ban-error : " + ex);
            return null;
        }
    }

    @Override
    public Optional<PhongBan> findByIdAndXoa(int id, boolean xoa) {
        try {
            return phongBanRepo.findByIdAndXoa(id, xoa);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-phong-ban-by-id-error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<PhongBan> save(PhongBan phongBan) {
        try {
            return Optional.ofNullable(phongBanRepo.save(phongBan));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-phong-ban-error: " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Boolean findByMaPhongBan(String maPhongBan) {
        try {
            Optional<PhongBan> phongBan = phongBanRepo.findByMaPhongBan(maPhongBan);
            if (phongBan.get() != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-by-ma-phong-ban-error : " + ex);
            return false;
        }
    }

    @Override
    public Boolean findByTenPhongBan(String tenPhongBan) {
        try {
            Optional<PhongBan> phongBan = phongBanRepo.findByTenPhongBan(tenPhongBan);
            if (phongBan.get() != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-by-ten-phong-ban-error : " + ex);
            return false;
        }
    }

    @Override
    public Page<PhongBan> findAllToPage(Pageable pageable) {
        try {
            return phongBanRepo.findAllToPage(pageable);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-to-page-error : " + ex);
            return null;
        }
    }

    @Override
    public SelectResultListDto selectSearch(String text, Pageable pageable) {
        try {
            Page<PhongBan> phongBanPage = phongBanRepo.search(text, pageable);
            if (phongBanPage.getTotalElements() == 0) {
                return new SelectResultListDto();
            }
            SelectResultListDto selectResultListDto = new SelectResultListDto();
            List<SelectResultDto> resultDtoList = new ArrayList<>();
            for (PhongBan pb : phongBanPage) {
                SelectResultDto sr = new SelectResultDto();
                sr.setId(String.valueOf(pb.getId()));
                sr.setText(pb.getTenPhongBan());
                resultDtoList.add(sr);
            }
            selectResultListDto.setResultDtoList(resultDtoList);
            selectResultListDto.setMore(phongBanPage.getTotalPages() > (pageable.getPageSize() + 1));
            return selectResultListDto;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "seach-phong-ban-err : " + ex);
            return null;
        }
    }

    @Override
    public Boolean deleted(Integer id) {
        try {
            return phongBanRepo.deleted(id) > 0 ? true : false;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "delete-phong-ban-error : " + ex);
            return false;
        }
    }

    @Override
    public Page<PhongBan> findByTenPhongBanAndMaPhongBan(String tenPhongBan, String maPhongBan, Pageable pageable) {
        try {
            return phongBanRepo.findByTenPhongBanAndMaPhongBan(tenPhongBan, maPhongBan, pageable);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-by-ten-phong-ban-error : " + ex);
            return null;
        }
    }
}
