package com.tavi.tavi_mrs.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/* kiểm tra username/password đính kèm theo request có hợp lệ hay không.
Theo mặc định, BasicAuthenticationEntryPoint do Spring Security cung cấp trả về một trang đầy đủ cho phản hồi trái phép 401 trở
 lại máy khách. Biểu diễn lỗi HTML này biểu hiện tốt trong trình duyệt, 
nhưng nó không phù hợp với các tình huống khác, chẳng hạn như API REST trong đó biểu diễn json có thể được ưu tiên. 
Không gian tên đủ linh hoạt cho yêu cầu mới này - để giải quyết vấn đề này - điểm vào có thể bị ghi đè:
*/

@Component
public class AuthenticationEntryPointImpl extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException {
        response.addHeader("WWW-Authenticate", "Basic realm = " + getRealmName());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 401 - " + authEx.getMessage());
    }

    @Override
    public void afterPropertiesSet() {
        // RealmName xuất hiện trên cửa sổ đăng nhập (Firefox).
        setRealmName("Mpec Lab");
        super.afterPropertiesSet();
    }
}
