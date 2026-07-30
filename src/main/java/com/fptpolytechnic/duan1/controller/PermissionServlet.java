package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.repository.PermissionRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/permissions")
public class PermissionServlet extends HttpServlet {

    PermissionRepository permissionRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        permissionRepository = new PermissionRepository();
        req.setAttribute("permissions", permissionRepository.findAll());

        req.getRequestDispatcher("/views/admin/permissions.jsp").forward(req, resp);
    }
}
