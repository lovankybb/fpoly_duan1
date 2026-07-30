package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.utils.AppConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;

@WebServlet("/image")
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = AppConfig.getInstance().getProperty("app.static.image.path");
        String name = req.getParameter("name");

        File file = new File(path + name);

        String contentType = getServletContext().getMimeType(file.getName());
        resp.setContentType(contentType != null ? contentType : "image/jpeg");

        try (InputStream input = new FileInputStream(file);
             OutputStream output = resp.getOutputStream();) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        }

    }
}
