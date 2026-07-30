package com.fptpolytechnic.duan1.controller;


import com.fptpolytechnic.duan1.model.User;
import com.fptpolytechnic.duan1.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet({"/sign-in", "/logout"})
public class AuthenticationServlet extends HttpServlet {


    private final AuthenticationService authenticationService;


    public AuthenticationServlet(){
        authenticationService = new AuthenticationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/sign-in":
                req.getRequestDispatcher("/views/sign-in.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String path = req.getServletPath();
        switch (path) {
            case "/sign-in":
                authenticate(req, resp);
                break;
            case "/logout" :
                logout(req, resp);

        }
    }



    private void authenticate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        boolean hasError = false;

        if(username == null || username.trim().isEmpty()){
            req.setAttribute("usernameError", "Tên đăng nhập không được để trống");
            hasError = true;
        }

        if(password == null || password.trim().isEmpty()){
            req.setAttribute("passwordError", "Mật khẩu không được để trống");
            hasError = true;
        }

        if(!hasError){
            User user = authenticationService.authenticate(username, password);
            if(user != null){

                try {
                    String token = authenticationService.generateToken(user);
                    Cookie cookie = new Cookie("token", token);

                    cookie.setHttpOnly(true);
                    cookie.setSecure(false);
                    cookie.setPath("/");

                    resp.addCookie(cookie);
                    resp.sendRedirect(req.getContextPath() + "/");
                } catch (JOSEException | IOException e) {
                    resp.sendRedirect(req.getContextPath() + "/error?code=UNAUTHENTICATED");
                }
                // Authentication successful
            } else {
                req.setAttribute("alertMsg", "Thông tin đăng nhập không chính xác!");
                req.getRequestDispatcher("/views/sign-in.jsp").forward(req, resp);
            }
        }
    }


    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            authenticationService.logout(request, response);
            response.sendRedirect("/");
        }catch ( Exception e) {
           response.sendRedirect(request.getContextPath() + "/error?code=UNCATEGORIZED");
        }
    }
}
