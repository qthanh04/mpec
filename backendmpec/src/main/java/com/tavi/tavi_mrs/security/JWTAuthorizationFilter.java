package com.tavi.tavi_mrs.security;

import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDung;
import com.tavi.tavi_mrs.repository.nguoi_dung.NguoiDungRepo;
import com.tavi.tavi_mrs.service_impl.nguoi_dung.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private NguoiDungRepo appUserRepo;

    private JWTService jwtService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, NguoiDungRepo appUserRepo, JWTService jwtService) {
        super(authenticationManager);
        this.appUserRepo = appUserRepo;
        this.jwtService = jwtService;
    }

    // Xác nhận dua user vao he thong
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(SecurityConstants.HEADER_STRING);
        if (header != null && header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(request, response);
        } else {
         //   System.out.println("no authorization");
            chain.doFilter(request , response);
        }
    }

    //  read token và cấp quyền
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);
        if (token != null) {
            String username = jwtService.decode(token);
            if (username != null) {
                NguoiDung appUser = appUserRepo.findByTaiKhoan(username);
                System.out.println("User Principal: " + appUser.getTaiKhoan());
                System.out.println("User role: "+ appUser.grantedAuthorities());
                return new UsernamePasswordAuthenticationToken(username, null, appUser.grantedAuthorities());
            }
            return null;
        }
        return null;
    }

}
