package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.model.Color;
import com.fptpolytechnic.duan1.service.ColorService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet({"/admin/colors", "/admin/colors/add", "/admin/colors/delete"})
public class ColorServlet extends HttpServlet {

    private final ColorService service = new ColorService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String uri = req.getRequestURI();

        if (uri.contains("delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            boolean success = service.delete(id);

            if (success) {
                resp.sendRedirect(req.getContextPath() + "/admin/colors");
            } else {
                resp.sendRedirect(req.getContextPath() + "/admin/colors?error=inuse");
            }
            return;
        }

        List<Color> colorList = service.getAll();
        req.setAttribute("colorList", colorList);
        req.getRequestDispatcher("/views/admin/color.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (req.getRequestURI().contains("add")) {
            String idParam = req.getParameter("id");
            String name = req.getParameter("name");
            String hex = req.getParameter("hex");

            if (idParam != null && !idParam.isEmpty()) {
                service.update(new Color(Integer.parseInt(idParam), name, hex));
            } else {
                service.add(new Color(name, hex));
            }

            resp.sendRedirect(req.getContextPath() + "/admin/colors");
            return;
        }

        doGet(req, resp);
    }
}

