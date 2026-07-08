package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.model.Color;
import com.fptpolytechnic.duan1.service.ColorService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/colors")
public class ColorServlet extends HttpServlet {
    private final ColorService service = new ColorService();

    @Override
    protected void doGet(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp) throws jakarta.servlet.ServletException, java.io.IOException {
        String uri = req.getRequestURI();
        if (uri.contains("delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            service.delete(id);
            resp.sendRedirect(req.getContextPath() + "/admin/colors");
        } else {
            req.setAttribute("colors", service.getAll());
            req.getRequestDispatcher("/views/admin/color.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String uri = req.getRequestURI();
       if (uri.contains("add")) {
           String name = req.getParameter("name");
           String hex = req.getParameter("hex");

           Color c = new Color();
           c.setName(name);
           c.setHex(hex);

           service.add(c);
           resp.sendRedirect(req.getContextPath() + "/admin/colors");
       }
    }
}
