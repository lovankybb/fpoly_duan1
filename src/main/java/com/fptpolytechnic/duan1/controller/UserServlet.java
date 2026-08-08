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
        "/admin/user/add",
        "/admin/user/update",
        "/admin/user/delete",
        "/profile",
        "/user/info",
        "/user/info/save",
        "/user/info/delete",
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
        String uri = req.getRequestURI();

        if (uri.contains("/admin/user/delete")) {
            handleAdminDeleteUser(req, resp);
            return;
        }

        switch (path) {
            case "/sign-up":
                req.getRequestDispatcher("/views/sign-up.jsp").forward(req, resp);
                break;
            case "/admin/users":
                handleAdminUsersPage(req, resp);
                break;
            case "/profile":
                try {
                    handleResponseProfile(req, resp);
                } catch (SQLException e) {
                    resp.sendRedirect(req.getContextPath() + "/error?code=UNAUTHORIZED");
                }
                break;
            case "/user/info":
                handlePersonalInfoPage(req, resp);
                break;
            case "/user/change-pwd":
                responseChangePassword(req, resp);
                break;
            default:
                req.getRequestDispatcher("/views/sign-up.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        switch (path) {
            case "/sign-up":
                handleSignup(req, resp);
                break;
            case "/user/change-pwd":
                handleChangePwd(req, resp);
                break;
            case "/admin/user/add":
                handleAdminAddUser(req, resp);
                break;
            case "/admin/user/update":
                handleAdminUpdateUser(req, resp);
                break;
            case "/user/info/save":
                handlePersonalInfoSave(req, resp);
                break;
            case "/user/info/delete":
                handlePersonalInfoDelete(req, resp);
                break;
            default:
                resp.sendRedirect(req.getContextPath() + "/");
                break;
        }
    }

    private void handleAdminUsersPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String editId = req.getParameter("editId");
        if (editId != null && !editId.isBlank()) {
            User editUser = userService.findById(editId);
            if (editUser.getId() != null) {
                req.setAttribute("editUser", editUser);
            }
        }

        String msg = req.getParameter("msg");
        if ("created".equals(msg)) {
            req.setAttribute("successMsg", "Thêm người dùng thành công.");
        } else if ("updated".equals(msg)) {
            req.setAttribute("successMsg", "Cập nhật người dùng thành công.");
        } else if ("deleted".equals(msg)) {
            req.setAttribute("successMsg", "Xóa người dùng thành công.");
        } else if ("duplicate".equals(msg)) {
            req.setAttribute("errorMsg", "Tên đăng nhập đã tồn tại.");
        }

        req.setAttribute("users", userService.findAll());
        req.getRequestDispatcher("/views/admin/user.jsp").forward(req, resp);
    }

    private void handleAdminAddUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = trimParam(req.getParameter("username"));
        String password = req.getParameter("password");

        if (username == null || username.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/admin/users?msg=duplicate");
            return;
        }
        if (userService.existsByUsername(username)) {
            resp.sendRedirect(req.getContextPath() + "/admin/users?msg=duplicate");
            return;
        }
        if (password == null || password.length() < 6) {
            resp.sendRedirect(req.getContextPath() + "/admin/users");
            return;
        }

        User user = buildUserFromRequest(req);
        user.setUsername(username);
        user.setPassword(password);
        userService.create(user);

        resp.sendRedirect(req.getContextPath() + "/admin/users?msg=created");
    }

    private void handleAdminUpdateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = trimParam(req.getParameter("id"));
        if (id == null || id.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/admin/users");
            return;
        }

        User existing = userService.findById(id);
        if (existing.getId() == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/users");
            return;
        }

        String username = trimParam(req.getParameter("username"));
        if (username == null || username.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/admin/users?editId=" + id);
            return;
        }
        if (!username.equals(existing.getUsername()) && userService.existsByUsername(username)) {
            resp.sendRedirect(req.getContextPath() + "/admin/users?msg=duplicate&editId=" + id);
            return;
        }

        User user = buildUserFromRequest(req);
        user.setId(id);
        user.setUsername(username);
        String newPassword = req.getParameter("password");
        userService.update(user, newPassword);

        resp.sendRedirect(req.getContextPath() + "/admin/users?msg=updated");
    }

    private void handleAdminDeleteUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = trimParam(req.getParameter("id"));
        if (id != null && !id.isEmpty()) {
            userService.delete(id);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/users?msg=deleted");
    }

    private User buildUserFromRequest(HttpServletRequest req) {
        User user = new User();
        user.setEmail(trimParam(req.getParameter("email")));
        user.setPhone(trimParam(req.getParameter("phone")));
        user.setAddress(trimParam(req.getParameter("address")));
        return user;
    }

    private String trimParam(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private void handleSignup(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPsw = request.getParameter("confirmPassword");

        boolean hasError = false;

        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("usernameError", "Tên đăng nhập không được để trống");
            hasError = true;
        }

        if (password == null || password.length() < 6) {
            request.setAttribute("passwordError", "Mật khẩu phải có ít nhất 6 ký tự");
            hasError = true;
        }

        if (password != null && !password.equals(confirmPsw)) {
            request.setAttribute("confirmPswError", "Xác nhận mật khẩu không khớp");
            hasError = true;
        }

        if (!hasError) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            userService.create(user);
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            request.getRequestDispatcher("/views/sign-up.jsp").forward(request, response);
        }
    }

    private void handleResponseProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        Authentication auth = (Authentication) request.getAttribute("authentication");
        if (auth == null) {
            response.sendRedirect(request.getContextPath() + "/sign-in");
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
            request.setAttribute("profileMenuActive", "profile");

            request.getRequestDispatcher("/views/profile.jsp").forward(request, response);
        }
    }

    private void handlePersonalInfoPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Authentication auth = (Authentication) req.getAttribute("authentication");
        if (auth == null) {
            resp.sendRedirect(req.getContextPath() + "/sign-in");
            return;
        }

        User user = userService.findByUsername(auth.getUsername());
        req.setAttribute("user", user);
        req.setAttribute("profileMenuActive", "info");

        String msg = req.getParameter("msg");
        if ("added".equals(msg)) {
            req.setAttribute("successMsg", "Thêm thông tin cá nhân thành công.");
        } else if ("updated".equals(msg)) {
            req.setAttribute("successMsg", "Cập nhật thông tin cá nhân thành công.");
        } else if ("deleted".equals(msg)) {
            req.setAttribute("successMsg", "Đã xóa thông tin liên hệ.");
        } else if ("empty".equals(msg)) {
            req.setAttribute("errorMsg", "Chưa có thông tin liên hệ để xóa.");
        }

        req.getRequestDispatcher("/views/user-info.jsp").forward(req, resp);
    }

    private void handlePersonalInfoSave(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Authentication auth = (Authentication) req.getAttribute("authentication");
        if (auth == null) {
            resp.sendRedirect(req.getContextPath() + "/sign-in");
            return;
        }

        User before = userService.findByUsername(auth.getUsername());
        boolean hadInfo = hasContactInfo(before);

        userService.savePersonalInfo(
                auth.getUsername(),
                trimParam(req.getParameter("email")),
                trimParam(req.getParameter("phone")),
                trimParam(req.getParameter("address"))
        );

        String msg = hadInfo ? "updated" : "added";
        resp.sendRedirect(req.getContextPath() + "/user/info?msg=" + msg);
    }

    private void handlePersonalInfoDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Authentication auth = (Authentication) req.getAttribute("authentication");
        if (auth == null) {
            resp.sendRedirect(req.getContextPath() + "/sign-in");
            return;
        }

        User before = userService.findByUsername(auth.getUsername());
        if (!hasContactInfo(before)) {
            resp.sendRedirect(req.getContextPath() + "/user/info?msg=empty");
            return;
        }

        userService.clearPersonalInfo(auth.getUsername());
        resp.sendRedirect(req.getContextPath() + "/user/info?msg=deleted");
    }

    private boolean hasContactInfo(User user) {
        return (user.getEmail() != null && !user.getEmail().isBlank())
                || (user.getPhone() != null && !user.getPhone().isBlank())
                || (user.getAddress() != null && !user.getAddress().isBlank());
    }

    public void responseChangePassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Authentication auth = (Authentication) request.getAttribute("authentication");
        if (auth == null) {
            response.sendRedirect(request.getContextPath() + "/sign-in");
        } else {
            request.setAttribute("user", userService.findByUsername(auth.getUsername()));
            request.setAttribute("profileMenuActive", "change-pwd");
            request.getRequestDispatcher("/views/change-pwd.jsp").forward(request, response);
        }
    }

    public void handleChangePwd(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String password = request.getParameter("password");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        Authentication auth = (Authentication) request.getAttribute("authentication");
        if (auth == null) {
            response.sendRedirect(request.getContextPath() + "/sign-in");
            return;
        }

        if (password == null || password.trim().isEmpty()
                || newPassword == null || newPassword.trim().isEmpty()
                || confirmPassword == null || confirmPassword.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/user/change-pwd?msg=EMPTY");
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            response.sendRedirect(request.getContextPath() + "/user/change-pwd?msg=WRONG");
            return;
        }
        if (!userService.changePassword(auth.getUsername(), password, newPassword)) {
            response.sendRedirect(request.getContextPath() + "/user/change-pwd?msg=WRONG");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/user/change-pwd?msg=SUCCESS");
    }
}
