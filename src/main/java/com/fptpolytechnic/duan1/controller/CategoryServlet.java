package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.model.Category;
import com.fptpolytechnic.duan1.service.CategoryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/categories")
public class CategoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoryService service = new CategoryService();
        req.setAttribute("categories", service.getAll());

        req.getRequestDispatcher("/views/admin/category.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoryService service = new CategoryService();
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        service.add(new Category(0L, name, description));
        resp.sendRedirect(req.getContextPath() + "/admin/categories");
    }
}
