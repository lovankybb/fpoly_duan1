package com.fptpolytechnic.duan1.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/contact")
public class ContactServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String senderErr = req.getParameter("senderErr");
        String emailErr = req.getParameter("emailErr");
        String titleErr = req.getParameter("titleErr");
        String messageErr = req.getParameter("messageErr");
        String successMsg = req.getParameter("success");

        if(senderErr != null){
            req.setAttribute("senderErr", senderErr);
        }
        if(emailErr != null){
            req.setAttribute("emailErr", emailErr);
        }
        if(titleErr != null){
            req.setAttribute("titleErr", titleErr);
        }
        if(messageErr != null){
            req.setAttribute("messageErr", messageErr);
        }

        if(successMsg != null){
            req.setAttribute("successMsg", successMsg);
        }

        req.getRequestDispatcher("/views/contact.jsp").forward(req, resp);
    }
}
