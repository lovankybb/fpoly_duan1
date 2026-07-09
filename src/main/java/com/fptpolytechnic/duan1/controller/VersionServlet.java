package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.model.Version;
import com.fptpolytechnic.duan1.service.VersionVervice;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/versions")
public class VersionServlet  extends HttpServlet {
    private  final VersionVervice service = new VersionVervice();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.contains("delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            service.delete(id);
            resp.sendRedirect(req.getContextPath() + "/admin/version");
        } else {
            req.setAttribute("version", service.getAll());
            req.getRequestDispatcher("/views/admin/version.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.contains("add")) {
            String name = req.getParameter("name");

            Version v = new Version();
            v.setName(name);

            service.add(v);
            resp.sendRedirect(req.getContextPath() + "/admin/version");
        }
    }
}
