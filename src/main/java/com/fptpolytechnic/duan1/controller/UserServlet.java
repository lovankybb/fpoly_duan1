package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.dto.response.OrderHistoryResponse;
import com.fptpolytechnic.duan1.model.Authentication;
import com.fptpolytechnic.duan1.model.User;
import com.fptpolytechnic.duan1.service.OrderService;
import com.fptpolytechnic.duan1.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet({
        "/sign-up",
        "/admin/users",
        "/profile",
        "/user/change-pwd"
})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServlet extends HttpServlet {


    private final UserService userService;
    private final OrderService orderService;

    public UserServlet() {
        userService = new UserService();
        orderService = new OrderService();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        switch (path) {
            case "/sign-up":
                req.getRequestDispatcher("/views/sign-up.jsp").forward(req, resp);
                break;
            case "/profile":
                try {
                    handleResponseProfile(req, resp);
                } catch (SQLException e) {
                    resp.sendRedirect(req.getContextPath() + "/error?code=UNAUTHORIZED");
                }
                break;
            case "/user/change-pwd":
                this.responseChangePassword(req, resp);
                break;
            default:
                req.getRequestDispatcher("/views/sign-up.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        System.out.println("INFO: Path: " + path);
        switch (path) {
            case "/sign-up":
                handleSignup(req, resp);
                break;

            case "/user/change-pwd":
                this.handleChangePwd(req, resp);
                break;

            default:
                handleSignup(req, resp);
                break;
        }

    }

    private void handleSignup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPsw = request.getParameter("confirmPassword");

        boolean hasError = false;

        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("usernameError", "Tên đăng nhập không được để trống");
            hasError = true;
        }

        if (password.length() < 6) {
            request.setAttribute("passwordError", "Mật khẩu phải có ít nhất 6 ký tự");
            hasError = true;
        }

        if (!password.equals(confirmPsw)) {
            request.setAttribute("confirmPswError", "Xác nhận mật khẩu không khớp");
            hasError = true;
        }

        if (!hasError) {
            // Proceed with user registration logic
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            userService.create(user);
            response.sendRedirect("/");
        } else {
            request.getRequestDispatcher("/views/sign-up.jsp").forward(request, response);
        }

    }

    private void handleResponseProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        Authentication auth = (Authentication) request.getAttribute("authentication");
        if (auth == null) {
            response.sendRedirect("/sign-in");
        } else {

            String offset = request.getParameter("offset");
            if (offset == null || offset.trim().isEmpty()) {
                offset = "0";
            }

            User user = userService.findByUsername(auth.getUsername());
            List<OrderHistoryResponse> histories = orderService.getOrderHistory(user.getId(), Integer.parseInt(offset));

            request.setAttribute("user", user);
            request.setAttribute("offset", offset);
            request.setAttribute("histories", histories);

            request.getRequestDispatcher("/views/profile.jsp").forward(request, response);
        }

    }

    public void responseChangePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Authentication auth = (Authentication) request.getAttribute("authentication");

        if (auth == null) {
            response.sendRedirect("/sign-in");
        } else {

//            message = {SUCCESS, EMPTY, INVALID_PASSWORD}
            String message = request.getParameter("msg");
            if (message != null && !message.trim().isEmpty()) {
                request.setAttribute("message", message);
            }

            request.getRequestDispatcher("/views/change-pwd.jsp").forward(request, response);
        }
    }

    private void handleChangePwd(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String password = request.getParameter("password");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        Authentication auth = (Authentication) request.getAttribute("authentication");

        if (auth == null) {
            response.sendRedirect(request.getContextPath() + "/sign-in");
        }

        if (password == null || password.trim().isEmpty()
                || newPassword == null || newPassword.trim().isEmpty()
                || confirmPassword == null || confirmPassword.trim().isEmpty()
        ) {
            response.sendRedirect(request.getContextPath() + "/user/change-pwd?msg=EMPTY");
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            response.sendRedirect(request.getContextPath() + "/user/change-pwd?msg=INVALID_PASSWORD");
            return;
        }
        if (!userService.changePassword(auth.getUsername(), password, newPassword)) {
            response.sendRedirect(request.getContextPath() + "/user/change-pwd?msg=INVALID_PASSWORD");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/user/change-pwd?msg=SUCCESS");
    }
}

