package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.dto.response.SimpleProdResponse;
import com.fptpolytechnic.duan1.enums.ProductStatus;
import com.fptpolytechnic.duan1.model.Brand;
import com.fptpolytechnic.duan1.model.Category;
import com.fptpolytechnic.duan1.model.Product;
import com.fptpolytechnic.duan1.model.ProductImage;
import com.fptpolytechnic.duan1.service.ProductImageService;
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
import java.util.*;
import java.util.stream.Collectors;

@WebServlet({
        "/admin/products",
        "/admin/product/add",
        "/admin/product/update",
        "/admin/product/delete",
        "/products"
})

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,  // 2MB - Temporary saving
        maxFileSize = 1024 * 1024 * 10,       // 10MB - Size of each file
        maxRequestSize = 1024 * 1024 * 50     // 50MB - Size of all file in a request
)
public class ProductServlet extends HttpServlet {


    private final ProductService productService;

    private final ProductImageService productImageService;

    public ProductServlet() {
        productService = new ProductService();
        productImageService = new ProductImageService();
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

            case "/admin/product/add":
                responseProductCreation(req, resp);
                break;
            case "/admin/product/delete":
                handleDelete(req, resp);
                break;
            case "/admin/product/update":
                responseUpdateProduct(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getServletPath();

        switch (path) {
            case "/admin/product/add":
                handleProductCreation(req, resp);
                break;
            case "/admin/product/update":
                handleUpdate(req, resp);
                break;
        }
    }

    public void responseUpdateProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String message = req.getParameter("message");

        if ("errorMsg".equals(message)) {
            req.setAttribute("errorMsg", "Cập nhập sản phẩm thất bại!");
        }
        if ("successMsg".equals(message)) {
            req.setAttribute("successMsg", "Cập nhật sản phẩm thành công!");
        }

        String id = req.getParameter("id");
        Product product = productService.findById(Long.parseLong(id));
        List<ProductImage> productImages = productImageService.findByProdId(Long.parseLong(id));
        req.setAttribute("productImages", productImages);

        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1l, "Điện thoại", ""));
        categories.add(new Category(2l, "Máy tính", ""));
        categories.add(new Category(3l, "PC", ""));


        List<Brand> brands = new ArrayList<>();
        brands.add(new Brand(1, "Apple", ""));
        brands.add(new Brand(2, "Oppo", ""));
        brands.add(new Brand(3, "Samsung", ""));

        req.setAttribute("product", product);

        req.setAttribute("categories", categories);
        req.setAttribute("brands", brands);

        req.getRequestDispatcher("/views/admin/update-product.jsp").forward(req, resp);

    }


    private void handleUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("INFO: Start handleUpdate");
        boolean hasErrors = false;

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        ProductStatus status = ProductStatus.valueOf(request.getParameter("status"));
        BigDecimal price = null;
        BigDecimal salePrice = null;
        try {
            price = BigDecimal.valueOf(Double.parseDouble(request.getParameter("price")));
            salePrice = BigDecimal.valueOf(Double.parseDouble(request.getParameter("salePrice")));
        } catch (Exception e) {
            hasErrors = true;
            request.setAttribute("priceError", "Giá không hợp lệ");
        }
        Long categoryId = Long.valueOf(request.getParameter("categoryId"));
        Long brandId = Long.valueOf(request.getParameter("brandId"));
        LocalDateTime updatedAt = LocalDateTime.now();

//        validate
        if (name == null || name.trim().equals("")) {
            hasErrors = true;
            request.setAttribute("nameError", "Tên sản phẩm không được để trống.");
        }
        if (status == null) {
            hasErrors = true;
            request.setAttribute("statusError", "Trạng thái đang trống.");
        }

        if (categoryId == null) {
            hasErrors = true;
            request.setAttribute("categoryError", "Danh mục đang trống.");
        }

        if (brandId == null) {
            hasErrors = true;
            request.setAttribute("brandError", "Thương hiệu đang trống");
        }

        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0 || salePrice == null || salePrice.compareTo(BigDecimal.ZERO) <= 0) {
            hasErrors = true;
            request.setAttribute("priceError", "Giá không hợp lệ");
        }

        if (hasErrors) {
            request.getRequestDispatcher("/views/admin/create-product.jsp").forward(request, response);
            return;
        }


        Collection<Part> parts = request.getParts();

        parts.stream()
                .filter(p ->
                             "image".equals(p.getName())
                                    && p.getSubmittedFileName() != null
                                    && !p.getSubmittedFileName().trim().isEmpty()
                                    && p.getSize() > 1

                ).forEach(p -> {
                    System.out.println("p.getName(): " + p.getName());
                    System.out.println("p.getContentType(): " + p.getContentType());
                    System.out.println("p.getSize(): " + p.getContentType());
                    System.out.println("p.getSubmittedName(): " + p.getSubmittedFileName());
                });

        Set<Part> images = parts.stream()
                .filter(p -> "image".equals(p.getName())
                        && p.getSubmittedFileName() != null
                        && !p.getSubmittedFileName().trim().isEmpty()
                        && p.getSize() > 1
                ).collect(Collectors.toSet());

        System.out.println("INFO: Validate update product successful!");

        Product product = Product.builder()
                .id(Long.parseLong(id))
                .name(name)
                .description(description)
                .price(price)
                .salePrice(salePrice)
                .categoryId(categoryId)
                .brandId(brandId)
                .status(ProductStatus.valueOf(request.getParameter("status")))
                .updatedAt(updatedAt)
                .build();


        Product updateProd = productService.update(product, images);
        if (updateProd == null) {
            response.sendRedirect("/admin/product/update?id=" + updateProd.getId() + "&message=errorMsg");
        }

        response.sendRedirect("/admin/product/update?id=" + updateProd.getId() + "&message=successMsg");
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String id = req.getParameter("id");
        productService.delete(Long.parseLong(id));

        resp.sendRedirect("/admin/products?offset=0&alertMsg=deleted");
    }

    private void responseProductManagement(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String offSet = request.getParameter("offset");

        String alertMsg = request.getParameter("alertMsg");

        if (alertMsg != null) {
            if ("deleted".equals(alertMsg)) {
                request.setAttribute("alertMsg", "Xóa sản phẩm thành công!");
            }
        }

        List<SimpleProdResponse> products = productService.getAll(Integer.parseInt(offSet));

        request.setAttribute("products", products);
        request.setAttribute("offset", offSet);
        request.getRequestDispatcher("/views/admin/product.jsp").forward(request, response);
    }

    private void returnProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/product.jsp").forward(request, response);
    }

    private void responseProductCreation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1l, "Điện thoại", ""));
        categories.add(new Category(2l, "Máy tính", ""));
        categories.add(new Category(3l, "PC", ""));


        List<Brand> brands = new ArrayList<>();
        brands.add(new Brand(1, "Apple", ""));
        brands.add(new Brand(2, "Oppo", ""));
        brands.add(new Brand(3, "Samsung", ""));


        request.setAttribute("categories", categories);
        request.setAttribute("brands", brands);

        request.getRequestDispatcher("/views/admin/create-product.jsp").forward(request, response);
    }


    private void handleProductCreation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        boolean hasErrors = false;

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        ProductStatus status = ProductStatus.valueOf(request.getParameter("status"));
        BigDecimal price = null;
        BigDecimal salePrice = null;
        try {
            price = BigDecimal.valueOf(Double.parseDouble(request.getParameter("price")));
            salePrice = BigDecimal.valueOf(Double.parseDouble(request.getParameter("salePrice")));
        } catch (Exception e) {
            hasErrors = true;
            request.setAttribute("priceError", "Giá không hợp lệ");
        }
        Long categoryId = Long.valueOf(request.getParameter("categoryId"));
        Long brandId = Long.valueOf(request.getParameter("brandId"));
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

//        validate
        if (name == null || name.trim().equals("")) {
            hasErrors = true;
            request.setAttribute("nameError", "Tên sản phẩm không được để trống.");
        }
        if (status == null) {
            hasErrors = true;
            request.setAttribute("statusError", "Trạng thái đang trống.");
        }

        if (categoryId == null) {
            hasErrors = true;
            request.setAttribute("categoryError", "Danh mục đang trống.");
        }

        if (brandId == null) {
            hasErrors = true;
            request.setAttribute("brandError", "Thương hiệu đang trống");
        }

        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0 || salePrice == null || salePrice.compareTo(BigDecimal.ZERO) <= 0) {
            hasErrors = true;
            request.setAttribute("priceError", "Giá không hợp lệ");
        }

        if (hasErrors) {
            request.getRequestDispatcher("/views/admin/create-product.jsp").forward(request, response);
            return;
        }


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

        Product creationProd = productService.create(prod, parts);
        if (!Objects.isNull(creationProd)) {
            request.setAttribute("successMsg", "Tạo sản phẩm thành công!");
        } else {
            request.setAttribute("errorMsg", "Tạo sản phẩm mới thất bại");
        }

        request.getRequestDispatcher("/views/admin/create-product.jsp").forward(request, response);
    }

}
