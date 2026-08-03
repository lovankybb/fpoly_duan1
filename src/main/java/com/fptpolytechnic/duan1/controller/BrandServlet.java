package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.model.Brand;
import com.fptpolytechnic.duan1.service.BrandService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet({"/admin/brands", "/admin/brands/add", "/admin/brand/delete"})
@MultipartConfig
public class BrandServlet extends HttpServlet {

    private final BrandService service = new BrandService();
    private static final String UPLOAD_DIR = "D:/du_an1/images";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String uri = req.getRequestURI();

        if (uri.contains("delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            service.delete(id);
            resp.sendRedirect(req.getContextPath() + "/admin/brands");
            return;
        }

        String editId = req.getParameter("editId");
        if (editId != null && !editId.isEmpty()) {
            req.setAttribute("editBrand", service.getById(Integer.parseInt(editId)));
        }

        req.setAttribute("brandList", service.getAll());
        req.getRequestDispatcher("/views/admin/brand.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (req.getRequestURI().contains("add")) {
            String idParam = req.getParameter("id");
            String name = req.getParameter("name");
            String description = req.getParameter("description");

            String imageName = null;
            Part filePart = req.getPart("image");

            if (filePart != null && filePart.getSubmittedFileName() != null
                    && !filePart.getSubmittedFileName().trim().isEmpty()) {

                String fileName = filePart.getSubmittedFileName();
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                String safeFileName = new File(fileName).getName();
                filePart.write(UPLOAD_DIR + File.separator + safeFileName);
                imageName = safeFileName;
            }

            if (idParam != null && !idParam.isEmpty()) {
                Brand existing = service.getById(Integer.parseInt(idParam));
                // Nếu không upload ảnh mới, giữ nguyên ảnh cũ
                String finalImage = (imageName != null) ? imageName : existing.getImage();
                service.update(new Brand(Integer.parseInt(idParam), name, description, finalImage));
            } else {
                service.add(new Brand(name, description, imageName));
            }

            resp.sendRedirect(req.getContextPath() + "/admin/brands");
            return;
        }

        doGet(req, resp);
    }
}
