package com.fptpolytechnic.duan1.filters;

import com.fptpolytechnic.duan1.service.IntrospectService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;

@WebFilter("/admin/*")
public class AdminFilter implements Filter {


    private IntrospectService introspectService;

    public AdminFilter() {
        introspectService = new IntrospectService();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        Cookie[] cookies = req.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    try {
                        introspectService.verifyToken(token);
                        SignedJWT signedJWT = SignedJWT.parse(token);
                        String roles = signedJWT.getJWTClaimsSet().getClaim("roles").toString();
                        if (roles.contains("ADMIN")) {
                            chain.doFilter(request, response);
                        } else {
                            responseForbidden(req, resp);
                        }
                    } catch (ParseException | JOSEException e) {
                        responseForbidden(req, resp);
                    }
                }
                else {
                    responseUnauthenticated(req, resp);
                }
            }
        }
        else {
            responseUnauthenticated(req, resp);
        }
    }

    private void responseForbidden(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int errorCode = 403;
        String errTitle = "Forbidden";
        String errDesc = "Bạn không có quyền truy cập vào trang này.";
        req.setAttribute("errorCode", errorCode);
        req.setAttribute("errorTitle", errTitle);
        req.setAttribute("errorDesc", errDesc);
        req.getRequestDispatcher("/views/error-page.jsp").forward(req, resp);
    }


    private void responseUnauthenticated(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int errorCode = 401;
        String errTitle = "Unauthenticated";
        String errDesc = "Bạn không có quyền truy cập vào trang này.";
        req.setAttribute("errorCode", errorCode);
        req.setAttribute("errorTitle", errTitle);
        req.setAttribute("errorDesc", errDesc);
        req.getRequestDispatcher("/views/error-page.jsp").forward(req, resp);
    }
}
