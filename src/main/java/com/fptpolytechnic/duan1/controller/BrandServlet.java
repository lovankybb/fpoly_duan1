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
import jakarta.servlet.annotation.MultipartConfig;

import java.io.File;
import java.io.IOException;

@WebServlet({"/admin/brands", "/admin/brand/add", "/admin/brand/delete"})
@MultipartConfig
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

        String name = req.getParameter("name");
        String description = req.getParameter("description");

        Part filePart = req.getPart("image");

        if (filePart != null && filePart.getSubmittedFileName() != null) {
            String fileName = filePart.getSubmittedFileName();

            if (!fileName.trim().isEmpty()) {
                String uploadPath = "D:/du_an1/images";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                String safeFileName = new File(fileName).getName();
                filePart.write(uploadPath + File.separator + safeFileName);
            }



        }

        resp.sendRedirect(req.getContextPath() + "/admin/brands");

    }
}
