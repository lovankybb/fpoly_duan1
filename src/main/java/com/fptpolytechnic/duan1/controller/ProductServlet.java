package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.dto.response.SimpleProdResponse;
import com.fptpolytechnic.duan1.enums.ProductStatus;
import com.fptpolytechnic.duan1.model.Product;
import com.fptpolytechnic.duan1.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@WebServlet({"/admin/products", "/admin/products/add", "/products"})

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,  // 2MB - Temporary saving
        maxFileSize = 1024 * 1024 * 10,       // 10MB - Size of each file
        maxRequestSize = 1024 * 1024 * 50     // 50MB - Size of all file in a request
)
public class ProductServlet  extends HttpServlet{


    private ProductService productService;

    public ProductServlet(){
        productService = new ProductService();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws jakarta.servlet.ServletException, java.io.IOException {

        String path = req.getServletPath();

        switch (path) {
            case "/admin/products":
                responseProductManagement(req, resp);
                break;
            case "/products":
                returnProducts(req, resp);
                break;

            case "/admin/products/add":
                responseProductCreation(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getServletPath();

        switch (path){
            case "/admin/products/add":
                handleProductCreation(req, resp);
                break;
        }
    }

    private void responseProductManagement(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String offSet = request.getParameter("offset");

        List<SimpleProdResponse> products = productService.getAll(Integer.parseInt(offSet));

        request.setAttribute("products", products);
        request.setAttribute("offset", offSet);
        request.getRequestDispatcher("/views/admin/product.jsp").forward(request, response);
    }

    private void returnProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/product.jsp").forward(request, response);
    }

    private void responseProductCreation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> categories = new ArrayList<>();
        categories.add(1);
        categories.add(2);
        categories.add(3);

        List<Integer> brands = new ArrayList<>();
        brands.add(1);
        brands.add(2);
        brands.add(3);

        request.setAttribute("categories", categories);
        request.setAttribute("brands", brands);

        request.getRequestDispatcher("/views/admin/create-product.jsp").forward(request, response);
    }


    private void handleProductCreation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        ProductStatus status = ProductStatus.valueOf(request.getParameter("status"));
        BigDecimal price = BigDecimal.valueOf(Long.valueOf(request.getParameter("price")));
        BigDecimal salePrice = BigDecimal.valueOf(Long.valueOf(request.getParameter("salePrice")));
        Long categoryId = Long.valueOf(request.getParameter("categoryId"));
        Long brandId = Long.valueOf(request.getParameter("brandId"));
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();




        Product prod = Product.builder()
                .name(name)
                .description(description)
                .status(status)
                .price(price)
                .salePrice(salePrice)
                .categoryId(categoryId)
                .brandId(brandId)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        Collection<Part> parts = request.getParts();

        if (parts == null || parts.isEmpty()) {
            System.out.println("Error: Part is empty");
        }
        else {
            for(Part part : parts){
                System.out.println("Info: Part name: " + part.getName());
                System.out.println("Info: Part size: " + part.getSize());
                System.out.println("Info: Part content type: " + part.getContentType());
                System.out.println("Info: Part submitted file name: " + part.getSubmittedFileName());
            }
        }

        productService.create(prod, parts);

        response.sendRedirect("/admin/products/add");
    }
}
