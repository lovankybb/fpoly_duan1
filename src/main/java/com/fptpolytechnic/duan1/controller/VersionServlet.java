package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.model.Version;
import com.fptpolytechnic.duan1.service.VersionService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet({"/admin/versions", "/admin/version/add", "/admin/version/delete"})
public class VersionServlet extends HttpServlet {

    private final VersionService service = new VersionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String uri = req.getRequestURI();

        if (uri.contains("delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            service.delete(id);
            resp.sendRedirect(req.getContextPath() + "/admin/versions");
            return;
        }

        String editId = req.getParameter("editId");
        if (editId != null && !editId.isEmpty()) {
            req.setAttribute("editVersion", service.getById(Integer.parseInt(editId)));
        }

        req.setAttribute("versions", service.getAll());
        req.getRequestDispatcher("/views/admin/version.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (req.getRequestURI().contains("add")) {
            String idParam = req.getParameter("id");
            String name = req.getParameter("name");

            if (idParam != null && !idParam.isEmpty()) {
                service.update(new Version(Integer.parseInt(idParam), name));
            } else {
                service.add(new Version(name));
            }

            resp.sendRedirect(req.getContextPath() + "/admin/versions");
            return;
        }

        doGet(req, resp);
    }
}
