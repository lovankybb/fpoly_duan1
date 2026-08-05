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

@WebServlet({
        "/admin/brands",
        "/admin/brand/add",
        "/admin/brand/update",
        "/admin/brand/delete",

})
@MultipartConfig
public class BrandServlet extends HttpServlet {

    private final BrandService service = new BrandService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String uri = req.getRequestURI();

        if (uri.contains("delete")) {
            this.handleDelete(req, resp);
            return;
        }
        String actionUrl = req.getRequestURL() + "/admin/brand/add";


        String editId = req.getParameter("editId");
        if (editId != null && !editId.isEmpty()) {
            Brand editBrand = service.getById(Integer.parseInt(editId));
            req.setAttribute("editBrand", editBrand);
            actionUrl = req.getContextPath() + "/admin/brand/update";
        }


        req.setAttribute("actionUrl", actionUrl);
        req.setAttribute("brandList", service.getAll());
        req.getRequestDispatcher("/views/admin/brand.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (req.getRequestURI().contains("add")) {
            this.handleAddBrand(req, resp);
        }
        if (req.getRequestURI().contains("update")) {
            this.handleUpdateBrand(req, resp);
        }
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        service.delete(id);
        resp.sendRedirect(req.getContextPath() + "/admin/brands");
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


    private void handleUpdateBrand(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        Part part = req.getPart("image");

        Brand existingBrand = service.getById(id);
        if (existingBrand == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/brands");
            return;
        }

        StorageService storage = new StorageService();
        String imageName = existingBrand.getImage(); // Keep the existing image if no new image is uploaded

        if (part != null && part.getSize() > 0) {
            storage.delete(existingBrand.getImage()); // Delete the old image
            imageName = storage.storage(part);
        }

        existingBrand.setName(name);
        existingBrand.setDescription(description);
        existingBrand.setImage(imageName);
        service.update(existingBrand);

        resp.sendRedirect(req.getContextPath() + "/admin/brands");
    }
}
