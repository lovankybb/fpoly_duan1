package com.fptpolytechnic.duan1.controller;


import com.fptpolytechnic.duan1.exception.GlobalExceptionHandler;
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
import java.text.ParseException;

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

                    System.out.println("Token: " + token);
                    System.out.println("Generate token and save cookie success ");

                    resp.addCookie(cookie);
                    resp.sendRedirect(req.getContextPath() + "/");
                } catch (JOSEException | IOException e) {
                    GlobalExceptionHandler.getInstance().responseErrorPage(req, resp);
                }
                // Authentication successful
            } else {
                req.setAttribute("loginError", "Tên đăng nhập hoặc mật khẩu không đúng");
            }
        }
    }


    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            authenticationService.logout(request, response);
            response.sendRedirect("/");
        }catch ( Exception e) {
            GlobalExceptionHandler.getInstance().responseErrorPage(request, response);
        }
    }
}
