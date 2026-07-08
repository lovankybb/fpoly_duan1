package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.model.Brand;
import com.fptpolytechnic.duan1.service.BrandService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet({"/admin/brands", "/admin/brand/add", "/admin/brand/delete"})
public class BrandServlet extends HttpServlet {
    private final  BrandService service = new BrandService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     String uri = req.getRequestURI();
     if (uri.contains("delete")) {
         int id = Integer.parseInt(req.getParameter("id"));
         service.delete(id);
         resp.sendRedirect(req.getContextPath() + "/admin/brands");
     } else {
         req.setAttribute("brands", service.getAll());
         req.getRequestDispatcher("/views/admin/brand.jsp").forward(req, resp);
     }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String uri = req.getRequestURI();
      if (uri.contains("add")) {
          String name = req.getParameter("name");
          String description = req.getParameter("description");

          Brand b = new Brand();
          b.setName(name);
          b.setDescription(description);
          service.add(b);
          resp.sendRedirect(req.getContextPath() + "/admin/brands");
      }

    }
}
