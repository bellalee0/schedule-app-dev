package com.example.scheduleappdev.global.Filter;

import com.example.scheduleappdev.global.Exception.ErrorMessage;
import com.example.scheduleappdev.global.Exception.TodoServiceException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Component
public class LoginCheckFilter implements Filter {
    private static final String[] whitelist = {"/signup", "/login"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String requestURI = httpRequest.getRequestURI();

        if(isLoginCheckPath(requestURI)) {
            HttpSession session = httpRequest.getSession(false);
            if(session == null) {
                throw new TodoServiceException(ErrorMessage.NOT_LOGGED_IN);
            }
        }

        chain.doFilter(request, response);

        // TODO : 로그인된 유저가 삭제되면 로그인이 풀려야 함
    }

    /**
     * whitelist와 URI 대조
     *
     * @apiNote 화이트 리스트에 있는 URL인 경우, 인증 체크 진행 X
     * @return whitelist와 일치하지 않으면 true 반환
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }
}
