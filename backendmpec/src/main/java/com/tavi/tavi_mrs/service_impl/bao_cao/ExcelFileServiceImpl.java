package com.tavi.tavi_mrs.service_impl.bao_cao;

import com.tavi.tavi_mrs.entities.bao_cao.ExcelFile;
import com.tavi.tavi_mrs.repository.bao_cao.ExcelFileRepo;
import com.tavi.tavi_mrs.service.bao_cao.ExcelFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ExcelFileServiceImpl implements ExcelFileService {

    @Autowired
    private ExcelFileRepo excelFileRepo;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelFileServiceImpl.class);

    @Override
    public Page<ExcelFile> findAll(Pageable pageable) {
        try {
            return excelFileRepo.findAll(pageable);
        } catch (Exception ex) {
            LOGGER.error("find-all-file-excel-err : " + ex);
            return null;
        }
    }

    @Override
    public Optional<ExcelFile> findById(int id) {
        try {
            return excelFileRepo.findById(id);
        } catch (Exception ex) {
            LOGGER.error("find-by-id-file-excel-err : " + ex);
            return null;
        }
    }

    @Override
    public Optional<ExcelFile> save(ExcelFile excelFile) {
        try {
            Date now = new Date();
            excelFile.setThoiGianTao(now);
            excelFile.setXoa(false);
            return Optional.ofNullable(excelFileRepo.save(excelFile));
        } catch (Exception ex) {
            LOGGER.error("save-file-excel-err : " + ex);
            return null;
        }
    }

    @Override
    public Page<ExcelFile> findByTenLoai(String tenLoai, Pageable pageable) {
        try {
            return excelFileRepo.findByTenLoai(tenLoai, pageable);
        } catch (Exception ex) {
            LOGGER.error("find-by-ten-loai-excel-err : " + ex);
            return null;
        }
    }

    @Override
    public Page<ExcelFile> findByTenLoaiAndMaPhieuAndThoiGian(String tenLoai, String maPhieu, Date ngayDau, Date ngayCuoi, boolean xoa, Pageable pageable) {
        try {
            return excelFileRepo.findByTenLoaiAndMaPhieuAndThoiGian(tenLoai, maPhieu, ngayDau, ngayCuoi, xoa, pageable);
        } catch (Exception ex) {
            LOGGER.error("search-excel-err" + ex);
            return null;
        }
    }


    @Override
    public Boolean deleted(Integer id) {
        try {
            return excelFileRepo.deleted(id) > 0 ? true : false;
        } catch (Exception ex) {
            LOGGER.error("deleted-excel-err : " + ex);
            return null;
        }
    }


}
