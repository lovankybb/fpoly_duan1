package com.fptpolytechnic.duan1.test;

import com.fptpolytechnic.duan1.utils.AppConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/test")
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String message = AppConfig.getInstance().getProperty("app.jwt.secret-key");

        req.setAttribute("mess", message);
        req.getRequestDispatcher("views/test.jsp").forward(req, resp);
    }
}
