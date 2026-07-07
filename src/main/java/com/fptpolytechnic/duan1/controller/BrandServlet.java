package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.model.Brand;
import com.fptpolytechnic.duan1.service.BrandService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/brands")
public class BrandServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BrandService service = new BrandService();
        String action = req.getServletPath();

        if (action.equals("/admin/brand/delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            service.delete(id);
            resp.sendRedirect(req.getContextPath() + "/admin/brands");
        } else {
            req.setAttribute("brands", service.getAll());
        }
        req.getRequestDispatcher("/views/admin/brand.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BrandService service = new BrandService();
        String name = req.getParameter("name");
        String description = req.getParameter("description");

        Brand b = new Brand(0, name, description);
        service.add(b);
        resp.sendRedirect(req.getContextPath() + "/admin/brands");
    }
}
