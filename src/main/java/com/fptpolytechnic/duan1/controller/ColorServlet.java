package com.fptpolytechnic.duan1.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet("/admin/colors")
public class ColorServlet extends HttpServlet {

    @Override
    protected void doGet(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp) throws jakarta.servlet.ServletException, java.io.IOException {
        req.getRequestDispatcher("/views/admin/color.jsp").forward(req, resp);
    }
}
