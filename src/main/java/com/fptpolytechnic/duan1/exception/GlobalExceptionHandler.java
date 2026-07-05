package com.fptpolytechnic.duan1.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GlobalExceptionHandler {

    public static GlobalExceptionHandler getInstance(){
        return new GlobalExceptionHandler();
    }

    public void responseErrorPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int errorCode = 500;
        String errTitle = "Internal Server Error";
        String errDesc = "Có lỗi trong khi thao tác vui lòng thử lại sau.";
        req.setAttribute("errorCode", errorCode);
        req.setAttribute("errorTitle", errTitle);
        req.setAttribute("errorDesc", errDesc);
        req.getRequestDispatcher("/views/error-page.jsp").forward(req, resp);
    }

}
