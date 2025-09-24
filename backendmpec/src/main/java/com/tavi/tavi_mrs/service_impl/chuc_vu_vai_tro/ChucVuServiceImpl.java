package com.tavi.tavi_mrs.service_impl.chuc_vu_vai_tro;

import com.tavi.tavi_mrs.entities.chuc_vu_vai_tro.ChucVu;
import com.tavi.tavi_mrs.entities.chuc_vu_vai_tro.VaiTro;
import com.tavi.tavi_mrs.entities.dto.SelectResultDto;
import com.tavi.tavi_mrs.entities.dto.SelectResultListDto;
import com.tavi.tavi_mrs.entities.hang_hoa.ThuongHieu;
import com.tavi.tavi_mrs.repository.chuc_vu_vai_tro.ChucVuRepo;
import com.tavi.tavi_mrs.service.chuc_vu_vai_tro.ChucVuService;
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
public class ChucVuServiceImpl implements ChucVuService {

    private static final Logger LOGGER = Logger.getLogger(ChucVuServiceImpl.class.getName());

    @Autowired
    private ChucVuRepo chucVuRepo;

    @Override
    public List<ChucVu> findAll() {
        try {
            return chucVuRepo.findAll();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-chuc-vu-error : " + ex);
            return null;
        }
    }

    @Override
    public Optional<ChucVu> findByIdAndXoa(int id, boolean xoa) {
        try {
            return chucVuRepo.findByIdAndXoa(id, xoa);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-chuc-vu-by-id-error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<ChucVu> save(ChucVu chucVu) {
        try {
            return Optional.ofNullable(chucVuRepo.save(chucVu));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    public Boolean findByTenChucVuCheck(String tenChucVu) {
        try {
            Optional<ChucVu> chucVu = chucVuRepo.findByChucVuCheck(tenChucVu);
            if (chucVu.get() != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public Page<ChucVu> findByTenChucVu(String tenChucVu, Pageable pageable) {
        try {
            return chucVuRepo.findByTenChucVu(tenChucVu, pageable);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-chuc-vu-by-id-error : " + ex);
            return null;
        }
    }

    @Override
    public SelectResultListDto selectSearch(String tenChucVu, Pageable pageable) {
        try {
            Page<ChucVu> chucVuPage = chucVuRepo.findByTenChucVu(tenChucVu, pageable);
            if (chucVuPage.getTotalElements() == 0) {
                return new SelectResultListDto();
            }
            SelectResultListDto selectResultListDto = new SelectResultListDto();
            List<SelectResultDto> resultDtoList = new ArrayList<>();
            for (ChucVu cv : chucVuPage) {
                SelectResultDto sr = new SelectResultDto();
                sr.setId(String.valueOf(cv.getId()));
                sr.setText(cv.getTenChucVu());
                resultDtoList.add(sr);
            }
            selectResultListDto.setResultDtoList(resultDtoList);
            selectResultListDto.setMore(chucVuPage.getTotalPages() > (pageable.getPageSize() + 1));
            return selectResultListDto;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "search-chuc-vu-err : " + ex);
            return null;
        }
    }

    @Override
    public Page<ChucVu> findAllToPage(Pageable pageable) {
        try {
            return chucVuRepo.findAllToPage(pageable);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-to-page-error : " + ex);
            return null;
        }
    }

    @Override
    public Boolean deleted(Integer id) {
        try {
            return chucVuRepo.deleted(id) > 0 ? true : false;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "delete-chuc-vu-error : " + ex);
            return false;
        }
    }


}
