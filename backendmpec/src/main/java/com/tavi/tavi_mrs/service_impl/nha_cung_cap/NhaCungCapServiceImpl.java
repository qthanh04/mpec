package com.tavi.tavi_mrs.service_impl.nha_cung_cap;

import com.tavi.tavi_mrs.entities.nha_cung_cap.NhaCungCap;
import com.tavi.tavi_mrs.repository.nha_cung_cap.NhaCungCapRepo;
import com.tavi.tavi_mrs.service.nha_cung_cap.NhaCungCapService;
import com.tavi.tavi_mrs.service_impl.hang_hoa.HangHoaServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NhaCungCapServiceImpl implements NhaCungCapService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HangHoaServiceImpl.class);

    @Autowired
    private NhaCungCapRepo nhaCungCapRepo;

    @Override
    public Page<NhaCungCap> findAllToPage(Pageable pageable) {
        try {
            return nhaCungCapRepo.findAllToPage(pageable);
        } catch (Exception ex) {
            LOGGER.error("find-all-nha-cung-cap-to-page-error : " + ex);
            return null;
        }
    }

    @Override
    public Optional<NhaCungCap> findByIdAndXoa(int id, boolean xoa) {
        try {
            return nhaCungCapRepo.findByIdAndXoa(id, xoa);
        } catch (Exception ex) {
            LOGGER.error("find-nha-cung-cap-by-id : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Page<NhaCungCap> findByTenAndDiaChiAndEmailAndDienThoaiAndFacebook(String ten, String diaChi, String email, String dienThoai, String facebook, Pageable pageable) {
        try {
            return nhaCungCapRepo.findByTenAndDiaChiAndDienThoaiAndEmailAndFacebook(ten, diaChi, email, dienThoai, facebook, pageable);
        } catch (Exception ex) {
            LOGGER.error("find-nha-cung-cap-by-ten-dien-thoai-email-facebook : " + ex);
            return null;
        }
    }

    @Override
    public Page<NhaCungCap> search(String text, Pageable pageable) {
        try {
            return nhaCungCapRepo.search(text, pageable);
        } catch (Exception ex) {
            LOGGER.error("search-nha-cung-cap-error :", ex);
            return null;
        }
    }

    @Override
    public List<NhaCungCap> findListNhaCungCap(List<Integer> list) {
        try {
            List<NhaCungCap> nhaCungCapList = new ArrayList<>();
            for (Integer i : list) {
                Optional<NhaCungCap> nhaCungCapOptional = nhaCungCapRepo.findByIdAndXoa(i, false);
                if (nhaCungCapOptional.isPresent()) {
                    nhaCungCapList.add(nhaCungCapOptional.get());
                }
            }
            return nhaCungCapList;
        } catch (Exception ex) {
            LOGGER.error("find-list-khach-hang-error : " + ex);
            return null;
        }
    }

    @Override
    public Optional<NhaCungCap> save(NhaCungCap nhaCungCap) {
        try {
            return Optional.ofNullable(nhaCungCapRepo.save(nhaCungCap));
        } catch (Exception ex) {
            LOGGER.error("save-nha-cung-cap-error : " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Boolean deleted(Integer id) {
        try {
            return nhaCungCapRepo.deleted(id) > 0 ? true : false;
        } catch (Exception ex) {
            LOGGER.error("delete-thuong-hieu-error : " + ex);
            return false;
        }
    }
}
