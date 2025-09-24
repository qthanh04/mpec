package com.tavi.tavi_mrs.service_impl.nguoi_dung;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tavi.tavi_mrs.entities.nguoi_dung.NguoiDung;
import com.tavi.tavi_mrs.repository.nguoi_dung.NguoiDungRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tavi.tavi_mrs.security.SecurityConstants;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class JWTService {

    @Autowired
    private NguoiDungRepo nguoiDungRepo;

    private static final Logger LOGGER = Logger.getLogger(JWTService.class.getName());

    //Tạo token
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME);
        return SecurityConstants.TOKEN_PREFIX + JWT.create()
                .withSubject(username)
                .withExpiresAt(expiryDate)
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
//        Map<String,Object> claims = new HashMap<>();
//        return SecurityConstants.TOKEN_PREFIX + Jwts.builder().setClaims(claims)
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(expiryDate)
//                .signWith(SignatureAlgorithm.HS256, SecurityConstants.SECRET).compact();
    }

    //Giải mã token
    public String decode(String token) {
        try {
            return JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()))
                    .build().verify(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                    .getSubject();
        } catch (Exception ex) {
            System.err.println(ex);
            return null;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token).getBody();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean isExpried(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, int id) {
        try {
            Optional<NguoiDung> nguoiDungOptional = nguoiDungRepo.findById(id);
            if (!nguoiDungOptional.isPresent()) {
                return false;
            }
            String username = nguoiDungOptional.get().getTaiKhoan();
            try {
                token = token.substring(SecurityConstants.TOKEN_PREFIX.length());
                Boolean result = username.equals(extractUsername(token)) && !isExpried(token);
                return result;
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Claim read token: " + ex);
                return false;
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-nguoi-dung-by-id-err: " + ex);
            return false;
        }
    }


}
