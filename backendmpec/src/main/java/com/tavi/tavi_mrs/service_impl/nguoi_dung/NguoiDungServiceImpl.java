package com.tavi.tavi_mrs.service_impl.nguoi_dung;

import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDung;
import com.tavi.tavi_mrs.payload.user.RegisterForm;
import com.tavi.tavi_mrs.repository.nguoi_dung.NguoiDungRepo;
import com.tavi.tavi_mrs.service.aws.AWSS3Service;
import com.tavi.tavi_mrs.service.mail.MailService;
import com.tavi.tavi_mrs.service.nguoi_dung.NguoiDungService;
import com.tavi.tavi_mrs.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import static com.tavi.tavi_mrs.utils.EncodeUtils.getSHA256;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class NguoiDungServiceImpl implements NguoiDungService {

    @Autowired
    private AWSS3Service awss3Service;

    @Autowired
    NguoiDungRepo nguoiDungRepo;

    private static final Logger LOGGER = Logger.getLogger(NguoiDungServiceImpl.class.getName());

    @Autowired
    private MailService mailService;

    @Override
    public Optional<NguoiDung> findById(int id, boolean xoa) {
        try {
            return nguoiDungRepo.findById(id, xoa);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-by-id-error: {0}", ex.getMessage());
            return null;
        }
    }

    @Override
    public Optional<NguoiDung> findByEmail(String email) {
        try {
            return nguoiDungRepo.findByEmail(email);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-email-error: {0}", ex.getMessage());
            return null;
        }
    }

    @Override
    public NguoiDung findNguoiDungByEmail(String email) {
        try {
            return nguoiDungRepo.findNguoiDungByEmail(email);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-email-error: {0}", ex.getMessage());
            return null;
        }
    }

    @Override
    public NguoiDung updateNguoiDung(NguoiDung nguoiDungDTO) {
        try {
            Optional<NguoiDung> nguoiDungOptional = nguoiDungRepo.findById(nguoiDungDTO.getId(), false);
            if (!nguoiDungOptional.isPresent()) {
                return null;
            }
            NguoiDung nguoiDung = nguoiDungOptional.get();
            nguoiDung.setEmail(nguoiDungDTO.getEmail());
            nguoiDung.setHoVaTen(nguoiDungDTO.getHoVaTen());
            nguoiDung.setSoDienThoai(nguoiDungDTO.getSoDienThoai());
            nguoiDung.setNgaySinh(nguoiDungDTO.getNgaySinh());
            nguoiDung.setDiaChi(nguoiDungDTO.getDiaChi());
            nguoiDung.setGioiTinh(nguoiDungDTO.getGioiTinh());
            nguoiDung.setThoiGianHetHan(nguoiDungDTO.getThoiGianHetHan());
            nguoiDung.setTrangThai(nguoiDungDTO.getTrangThai());

            if (nguoiDung.getUrlAnhDaiDien() != null && !nguoiDung.getUrlAnhDaiDien().equals(nguoiDungDTO.getUrlAnhDaiDien())) {
                if (nguoiDung.getUrlAnhDaiDien() != null && !nguoiDung.getUrlAnhDaiDien().equals("")) {
                    awss3Service.deleteFileFromS3Bucket(nguoiDung.getUrlAnhDaiDien());
                }
            }
            nguoiDung.setUrlAnhDaiDien(nguoiDungDTO.getUrlAnhDaiDien());
            return nguoiDungRepo.save(nguoiDung);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "update-nguoi-dung-error: {0}", ex.getMessage());
            return null;
        }
    }

    @Override
    public NguoiDung findByTaiKhoan(String taiKhoan) {
        try {
            return nguoiDungRepo.findByTaiKhoan(taiKhoan);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-tai-khoan-error: {0}", ex.getMessage());
            return null;
        }
    }

    @Override
    public NguoiDung findBySoDienThoai(String soDienThoai) {
        try {
            return nguoiDungRepo.findBySoDienThoai(soDienThoai);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-so-dien-thoai-error: {0}", ex.getMessage());
            return null;
        }
    }

    @Override
    public NguoiDung register(RegisterForm registerForm) {
        try {
            NguoiDung nguoiDung = new NguoiDung();
            nguoiDung.setTaiKhoan(registerForm.getUserName());
            nguoiDung.setMatKhau(registerForm.getPassWord());
            nguoiDung.setMatKhau(getSHA256(registerForm.getPassWord()));
            nguoiDung.setEmail(registerForm.getEmail());
            nguoiDung.setXoa(false);
            System.out.println(nguoiDung.getId() + " " + nguoiDung.getTaiKhoan() + " " + nguoiDung.getMatKhau());
            return nguoiDungRepo.save(nguoiDung);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "register error: {0}", ex.getMessage());
            return null;
        }
    }


    @Override
    public NguoiDung register(NguoiDung nguoiDung) {
        try {
            nguoiDung.setId(null);
            nguoiDung.setXoa(false);
            return nguoiDungRepo.save(nguoiDung);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "register error: {0}", ex.getMessage());
            return null;
        }
    }

    @Override
    public NguoiDung findByTaiKhoanAndMatKhauAndXoa(String taiKhoan, String matKhau, boolean xoa) {
        try {
            return nguoiDungRepo.findByTaiKhoanAndMatKhauAndXoa(taiKhoan, getSHA256(matKhau), xoa);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-by-username-and-password-and-deleted error: {0}", ex.getMessage());
            return null;
        }
    }

    @Override
    public Integer findIdByTaiKhoan(String taiKhoan) {
        try {
            System.out.println("gọi id by name");
            return nguoiDungRepo.findIdByTaiKhoan(taiKhoan);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-tai-khoan-id: {0}", ex.getMessage());
            return null;
        }
    }

    @Override
    public Page<NguoiDung> findAll(Boolean xoa, Pageable pageable) {
        try {
            return nguoiDungRepo.findAll(xoa, pageable);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-pageable error: {0}", ex.getMessage());
            return null;
        }
    }

    @Override
    public Optional<NguoiDung> findByTK(String taiKhoan) {
        try {
            return nguoiDungRepo.findByTK(taiKhoan);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-tai-khoan-error: {0}", ex.getMessage());
            return null;
        }
    }

    @Override
    public Optional<NguoiDung> save(NguoiDung nguoiDung) {
        try {
            Optional<NguoiDung> nguoiDungOptional = null;
            while (true) {
                String maTK = "TK-" + new Random().nextInt(1000000) + 1;
                try {
                    nguoiDungOptional = nguoiDungRepo.findByMaTK(maTK);
                    if (!nguoiDungOptional.isPresent()) {
                        nguoiDung.setMaTaiKhoan(maTK);
                        break;
                    }
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, "find-nguoi_dung-by-ma-tai-khoan-err : " + ex);
                    return null;
                }
            }
            String name = StringUtils.replacingAllAccents(nguoiDung.getHoVaTen()).toLowerCase();
            List<String> nameList = Arrays.asList(name.split(" "));
            StringBuilder username = new StringBuilder(nameList.get(nameList.size() - 1));
            for (int i = 0; i < nameList.size() - 1; i++) {
                username.append(nameList.get(i).toLowerCase().charAt(0));
            }
            Optional<NguoiDung> nguoiDungOptionalByTK = null;
            try {
                nguoiDungOptionalByTK = nguoiDungRepo.findTheLastTK(username.toString() + "[0-9]");
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "find-nguoi_dung-by-tai-khoan-err : " + ex);
                return null;
            }
            if (nguoiDungOptionalByTK.isPresent()) {
                String numberStr = nguoiDungOptionalByTK.get().getTaiKhoan().substring(username.length());
                Integer number = 1;
                try {
                    number = Integer.valueOf(numberStr) + 1;
                } catch (NumberFormatException ee) {
                    numberStr = numberStr.substring(numberStr.length() - 2, numberStr.length() - 1);
                    number = Integer.valueOf(numberStr) + 1;
                    System.out.println(number);
                    return Optional.empty();
                }
                username.append(number);
            } else {
                username.append("1");
            }
            System.out.println(username.toString());
            nguoiDung.setTaiKhoan(username.toString());
            String password = com.tavi.tavi_mrs.utils.Random.randomPassword();
            nguoiDung.setMatKhau(getSHA256(password));
            nguoiDung.setThoiGianKhoiTao(new Date());
            if (nguoiDung.getUrlAnhDaiDien() == null) {
                nguoiDung.setUrlAnhDaiDien("https://mpec.s3.us-east-2.amazonaws.com/default_ava.jpg");
            }
            nguoiDung.setXoa(false);
            try {
                String content = "Tài khoản : " + username
                        + "\nMật khẩu : " + password;
                String[] mails = {nguoiDung.getEmail()};
                mailService.sendMail(mails, "Thông báo tài khoản được đăng ký:", content);
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "send-mail-err : " + ex);
                return null;
            }
            return Optional.ofNullable(nguoiDungRepo.save(nguoiDung));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-tk-by-ma-err : " + ex);
            return Optional.empty();
        }
    }


    @Override
    public long countByTaiKhoanAndXoa(String taiKhoan, boolean xoa) {
        try {
            return nguoiDungRepo.countByTaiKhoanAndXoa(taiKhoan, xoa);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "count-by-tai-khoan-and-deleted error: {0}", ex.getMessage());
            return 0;
        }
    }

    @Override
    public long countTaiKhoanAndXoa(boolean xoa) {
        try {
            return nguoiDungRepo.countTaiKhoanAndXoa(xoa);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "count-tai-khoan-and-deleted error: {0}", ex.getMessage());
            return 0;
        }
    }

    @Override
    public long countByEmailAndXoa(String email, boolean xoa) {
        try {
            return nguoiDungRepo.countByEmailAndXoa(email, xoa);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "count-by-email-and-deleted error: {0}", ex.getMessage());
            return 0;
        }
    }

    @Override
    public long countBySoDienThoaiAndXoa(String soDienThoai, boolean xoa) {
        try {
            return nguoiDungRepo.countBySoDienThoaiAndXoa(soDienThoai, xoa);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "count-by-so-dien-thoai-and-deleted error: {0}", ex.getMessage());
            return 0;
        }
    }

    @Override
    public boolean setTrangThai(int trangThai, int id) {
        try {
            return nguoiDungRepo.setTrangThai(trangThai, id) > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.log(Level.SEVERE, "setMatKhau error: {0}", ex.getMessage());
            return false;
        }
    }


    @Override
    public boolean delete(int id) {
        try {
            return nguoiDungRepo.delete(id) > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.log(Level.SEVERE, "register error: {0}", ex.getMessage());
            return false;
        }
    }
}