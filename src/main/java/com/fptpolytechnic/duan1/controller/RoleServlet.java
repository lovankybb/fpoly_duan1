package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.repository.RoleRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/roles")
public class RoleServlet extends HttpServlet {

    RoleRepository roleRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        roleRepository = new RoleRepository();
        req.setAttribute("roles", roleRepository.findAll());
        req.getRequestDispatcher("/views/admin/roles.jsp").forward(req, resp);
        super.doGet(req, resp);
    }
}
