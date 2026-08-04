package com.fptpolytechnic.duan1.controller;


import com.fptpolytechnic.duan1.model.Brand;
import com.fptpolytechnic.duan1.service.BrandService;

import com.fptpolytechnic.duan1.utils.StorageService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;

@WebServlet({"/admin/brands", "/admin/brand/add", "/admin/brand/delete"})
@MultipartConfig
public class BrandServlet extends HttpServlet {

    private final BrandService service = new BrandService();

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
            this.handleAddBrand(req, resp);
        }
    }

    private void handleAddBrand(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        Part part = req.getPart("image");

        StorageService storage = new StorageService();
        String imageName = storage.storage(part);

        Brand b = new Brand();
        b.setName(name);
        b.setDescription(description);
        b.setImage(imageName);
        service.add(b);

        resp.sendRedirect(req.getContextPath() + "/admin/brands");
    }
}
