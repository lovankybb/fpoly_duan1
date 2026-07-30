package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.model.Brand;
import com.fptpolytechnic.duan1.service.BrandService;
import com.fptpolytechnic.duan1.utils.StorageService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

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
         handleAddBrand(req, resp);
      }

    }



    private void handleAddBrand(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        resp.sendRedirect(req.getContextPath() + "/admin/brands");

    }
}
