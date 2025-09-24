package com.tavi.tavi_mrs.service_impl.nguoi_dung;

import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDung;
import com.tavi.tavi_mrs.entities.quyen.Quyen;
import com.tavi.tavi_mrs.repository.nguoi_dung.NguoiDungRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsService_impl implements UserDetailsService {

    @Autowired
    private NguoiDungRepo appUserRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        NguoiDung appUser = appUserRepo.findByTaiKhoan(s);
        if (appUser == null) throw new UsernameNotFoundException(s);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Quyen q : appUser.getQuyenList()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(q.getTenQuyen()));

        }
        return new User(appUser.getTaiKhoan(), appUser.getMatKhau(), grantedAuthorities);
    }
}
