package com.fptpolytechnic.duan1.filters;

import com.fptpolytechnic.duan1.exception.GlobalExceptionHandler;
import com.fptpolytechnic.duan1.model.Authentication;
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


@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    IntrospectService introspectService;

    public AuthenticationFilter(){
        introspectService = new IntrospectService();
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {



        HttpServletRequest req = (HttpServletRequest) request;

        String uri = req.getRequestURI();
//        ignore if they are static files
        if (uri.matches(".*\\.(css|js|png|jpg|jpeg|gif|svg|webp)$")) {
            chain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    try {
                        if(introspectService.verifyToken(token)){

                            SignedJWT signedJWT = SignedJWT.parse(token);
                            String username = signedJWT.getJWTClaimsSet().getSubject();
                            String roles = signedJWT.getJWTClaimsSet().getStringClaim("roles");

                            Authentication authentication = new Authentication();
                            authentication.setUsername(username);
                            authentication.setRoles(roles);

                            req.setAttribute("authentication", authentication);
                        }

                    } catch (ParseException e) {
                        GlobalExceptionHandler.getInstance().responseErrorPage(req, (HttpServletResponse) response);
                    } catch (JOSEException e) {
                        GlobalExceptionHandler.getInstance().responseErrorPage(req, (HttpServletResponse) response);
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }
}

