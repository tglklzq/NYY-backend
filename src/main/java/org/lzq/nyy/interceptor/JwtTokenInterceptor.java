package org.lzq.nyy.interceptor;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lzq.nyy.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Date;

@Component
public class JwtTokenInterceptor implements HandlerInterceptor {

    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public JwtTokenInterceptor(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取 token
        String token = request.getHeader("Authorization");

        // 验证 token 的合法性
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7); // 去掉 Bearer 后面的空格，获取实际的 token
            Claims claims = JwtTokenUtil.getClaimsFromToken(jwtToken);

            if (claims != null) {
                // token 合法，将 token 中的信息放入 request 中，方便后续使用
                request.setAttribute("claims", claims);

                // 检查 token 是否即将过期（例如剩余不足 5 分钟）
                Date expiration = claims.getExpiration();
                long expirationTimeMillis = expiration.getTime();
                long currentTimeMillis = System.currentTimeMillis();

                if (expirationTimeMillis - currentTimeMillis < 5 * 60 * 1000) {
                    // 生成新的 token，并设置到响应头中
                    String newToken = JwtTokenUtil.generateToken(claims.getSubject());
                    System.out.println("newToken: " + newToken);
                    response.setHeader("new-token", newToken);
                }

                return true;
            }
        }

        // 如果 token 不合法，返回401未授权错误
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        System.out.println("token 不合法");
        return false;
    }
}
