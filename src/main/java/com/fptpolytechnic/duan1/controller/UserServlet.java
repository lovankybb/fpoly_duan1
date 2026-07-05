package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.model.Authentication;
import com.fptpolytechnic.duan1.model.User;
import com.fptpolytechnic.duan1.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.io.IOException;

@WebServlet({"/sign-up", "/admin/users", "/profile"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServlet  extends HttpServlet {


    UserService userService;

    public UserServlet(){
        userService = new UserService();
    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        switch (path) {
            case "/sign-up":
                req.getRequestDispatcher("/views/sign-up.jsp").forward(req, resp);
                break;
            case "/profile":
                handleResponseProfile(req, resp);
                break;
            default:
                req.getRequestDispatcher("/views/sign-up.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getContextPath();

        switch (path){
            case "/sign-up": handleSignup(req, resp); break;
            default: handleSignup(req, resp); break;
        }

    }

    private void handleSignup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPsw = request.getParameter("confirmPassword");

        boolean hasError = false;

        if(username == null || username.trim().isEmpty()){
            request.setAttribute("usernameError", "Tên đăng nhập không được để trống");
            hasError = true;
        }

        if(password.length() < 6){
            request.setAttribute("passwordError", "Mật khẩu phải có ít nhất 6 ký tự");
            hasError = true;
        }

        if(!password.equals(confirmPsw)){
            request.setAttribute("confirmPswError", "Xác nhận mật khẩu không khớp");
            hasError = true;
        }

        if(!hasError){
            // Proceed with user registration logic
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            userService.create(user);
            response.sendRedirect("/");
        }
        else {
            request.getRequestDispatcher("/views/sign-up.jsp").forward(request, response);
        }

    }

    private void handleResponseProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Authentication auth = (Authentication) request.getAttribute("authentication");
        if(auth == null){
            response.sendRedirect("/sign-in");
        }
        else {
            request.getRequestDispatcher("/views/profile.jsp").forward(request, response);
        }
    }
}

