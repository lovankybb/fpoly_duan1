package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.model.Category;
import com.fptpolytechnic.duan1.service.CategoryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet({"/admin/categories", "/admin/category/add", "/admin/category/delete"})
public class CategoryServlet extends HttpServlet {
    CategoryService service = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String uri = req.getRequestURI();
      if (uri.contains("delete")){
          int id = Integer.parseInt(req.getParameter("id"));
          service.delete(id);
          resp.sendRedirect(req.getContextPath() + "/admin/categories");
      } else {
          req.getRequestDispatcher("/views/admin/category.jsp").forward(req, resp);
      }



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        if (uri.contains("add")) {
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            Category c = new Category();
            c.getName();
            c.getName();
            service.add(c);

            resp.sendRedirect(req.getContextPath() + "/admin/categories");
        }


    }
}
