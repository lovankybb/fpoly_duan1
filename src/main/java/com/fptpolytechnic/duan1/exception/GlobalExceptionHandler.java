package com.fptpolytechnic.duan1.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/error")
public class GlobalExceptionHandler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ErrorCode errorCode = ErrorCode.valueOf(req.getParameter("code"));
        int code = errorCode.getCode();
        String message = errorCode.getMessage();
        String description = errorCode.getDescription();

        req.setAttribute("code", code);
        req.setAttribute("message", message);
        req.setAttribute("description", description);

        req.getRequestDispatcher("/views/error-page.jsp").forward(req, resp);
    }
}
