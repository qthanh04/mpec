package com.tavi.tavi_mrs.service_impl.chi_nhanh;

import com.tavi.tavi_mrs.entities.chi_nhanh.ChiNhanh;
import com.tavi.tavi_mrs.entities.chuc_vu_vai_tro.VaiTro;
import com.tavi.tavi_mrs.entities.don_vi.DonVi;
import com.tavi.tavi_mrs.entities.dto.SelectResultDto;
import com.tavi.tavi_mrs.entities.dto.SelectResultListDto;
import com.tavi.tavi_mrs.entities.phong_ban.PhongBan;
import com.tavi.tavi_mrs.repository.chi_nhanh.ChiNhanhRepo;
import com.tavi.tavi_mrs.service.chi_nhanh.ChiNhanhService;
import com.tavi.tavi_mrs.service_impl.phong_ban.PhongBanServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

@Service
public class ChiNhanhServiceImpl implements ChiNhanhService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChiNhanhServiceImpl.class);

    @Autowired
    private ChiNhanhRepo chiNhanhRepo;

    @Override
    public List<ChiNhanh> findAll() {
        try {
            return chiNhanhRepo.findAll();
        } catch (Exception ex) {
            LOGGER.error("find-all-chi-nhanh-error : " + ex);
            return null;
        }
    }

    @Override
    public Page<ChiNhanh> findAllToPage(Pageable pageable) {
        try {
            return chiNhanhRepo.findAll(pageable);
        } catch (Exception ex) {
            LOGGER.error("find-all-chi-nhanh-error : " + ex);
            return null;
        }
    }

    @Override
    public Optional<ChiNhanh> findByIdAndXoa(int id, boolean xoa) {
        try {
            return chiNhanhRepo.findByIdAndXoa(id, xoa);
        } catch (Exception ex) {
            LOGGER.error("find-chi-nhanh-by-id-error : " + ex);
            return Optional.empty();
        }
    }


    @Override
    public List<ChiNhanh> findByTongCty(Integer idTongCty) {
        try {
            return chiNhanhRepo.findByTongCty(idTongCty);
        } catch (Exception ex) {
            LOGGER.error("find-By-TongCty-error", ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<ChiNhanh> save(ChiNhanh chiNhanh) {
        try {
            return Optional.of(chiNhanhRepo.save(chiNhanh));
        } catch (Exception ex) {
            LOGGER.error("save-ChiNhanh-error", ex);
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<ChiNhanh> selectSearch(String text) {
        try {
            return chiNhanhRepo.searchSelect(text);
        } catch (Exception ex) {
            LOGGER.error("search-chi-nhanh-err : " + ex);
            return null;
        }
    }

    @Override
    public Boolean deleted(Integer chiNhanhId) {
        try {
            return chiNhanhRepo.deleted(chiNhanhId) > 0 ? true : false;
        } catch (Exception ex) {
            LOGGER.error("delete-chi-nhanh-error: " + ex);
            return false;
        }
    }

    @Override
    public List<ChiNhanh> findAll(Integer doanhNghiepId) {
        try {
            return chiNhanhRepo.findAllByDoanhNghiep(doanhNghiepId);
        } catch (Exception ex) {
            LOGGER.error("find-all-chi-nhanh-error: " + ex);
            return null;
        }
    }

    @Override
    public Page<ChiNhanh> selectSearch(String diaChi, Pageable pageable) {
        try {
            return chiNhanhRepo.findByDiaChi(diaChi, pageable);
        } catch (Exception ex) {
            LOGGER.error("search-chi-nhanh-error: " + ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<ChiNhanh> search(Integer doanhNghiepId, Pageable pageable) {
        try {
            return chiNhanhRepo.findAllByDoanhNghiep1(doanhNghiepId, pageable);
        } catch (Exception ex) {
            LOGGER.error("search-chi-nhanh-error: " + ex);
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ChiNhanh findByMaChiNhanhExcel(String ma) {
        try {
            ChiNhanh chiNhanh = chiNhanhRepo.findByMaChiNhanhExcel(ma);
            if (chiNhanh != null) {
                return chiNhanh;
            }
        } catch (Exception ex) {
            return null;
        }
        return null;

    }

    @Override
    public SelectResultListDto select2Search(String text, Pageable pageable) {
        try {
            Page<ChiNhanh> chiNhanhPage = chiNhanhRepo.search(text, pageable);
            if (chiNhanhPage.getTotalElements() == 0) {
                return new SelectResultListDto();
            }
            SelectResultListDto selectResultListDto = new SelectResultListDto();
            List<SelectResultDto> resultDtoList = new ArrayList<>();
            for ( ChiNhanh cn: chiNhanhPage) {
                SelectResultDto sr = new SelectResultDto();
                sr.setId(String.valueOf(cn.getId()));
                sr.setText(cn.getDiaChi());
                resultDtoList.add(sr);
            }
            selectResultListDto.setResultDtoList(resultDtoList);
            selectResultListDto.setMore(chiNhanhPage.getTotalPages() > (pageable.getPageSize() + 1));
            return selectResultListDto;
        } catch (Exception ex) {
            LOGGER.error("search-chi-nhanh-error: " + ex);
            return null;
        }
    }


}

