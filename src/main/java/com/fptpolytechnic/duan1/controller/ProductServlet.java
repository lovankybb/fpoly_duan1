package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.dto.ProductFormDTO;
import com.fptpolytechnic.duan1.dto.response.ProductDetailResponse;
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
        "/products",
        "/product/detail",
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
                responseProducts(req, resp);
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
            case "/product/detail":
                responseProductDetail(req, resp);
                break;

            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String path = req.getServletPath();

            switch (path) {
                case "/admin/product/add":
                    handleProductCreation(req, resp);
                    break;
                case "/admin/product/update":
                    handleUpdate(req, resp);
                    break;
            }
        } catch (Exception e) {
            resp.sendRedirect("/error?code=UNCATEGORIZED");
        }
    }


    private void responseUpdateProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String alertMsg = req.getParameter("alertMsg");
        if (alertMsg != null) {
            switch (alertMsg) {
                case "success":
                    req.setAttribute("successMsg", "Thêm sản phẩm mới thành công!");
                    break;

                case "error":
                    req.setAttribute("errorMsg", "Thêm sản phẩm mới thất bại!");
                    break;
            }
        }


        if (req.getParameter("nameError") != null) {
            req.setAttribute("nameError", "Tên sản phẩm không hợp lệ");
        }

        if (req.getParameter("descError") != null) {
            req.setAttribute("descError", "Mô tả sản phẩm không hợp lệ");
        }

        if (req.getParameter("statusError") != null) {
            req.setAttribute("statusError", "Trạng thái sản phẩm không hợp lệ");
        }

        if (req.getParameter("priceError") != null) {
            req.setAttribute("priceError", "Giá sản phẩm không hợp lệ");
        }

        if (req.getParameter("catError") != null) {
            req.setAttribute("catError", "Danh mục không hợp lệ");
        }

        if (req.getParameter("brandError") != null) {
            req.setAttribute("brandError", "Thương hiệu không hợp lệ");
        }


        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1l, "Điện thoại", ""));
        categories.add(new Category(2l, "Máy tính", ""));
        categories.add(new Category(3l, "PC", ""));


        List<Brand> brands = new ArrayList<>();
        brands.add(new Brand(1, "Apple", ""));
        brands.add(new Brand(2, "Oppo", ""));
        brands.add(new Brand(3, "Samsung", ""));


        req.setAttribute("categories", categories);
        req.setAttribute("brands", brands);


        String id = req.getParameter("id");
        SimpleProdResponse product = productService.findById(Long.parseLong(id));
        List<ProductImage> productImages = productImageService.findByProdId(Long.parseLong(id));
        req.setAttribute("productImages", productImages);

        req.setAttribute("product", product);

        req.getRequestDispatcher("/views/admin/update-product.jsp").forward(req, resp);

    }


    private void handleUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String productId = request.getParameter("id");

        if (productId == null || productId.trim().isEmpty()) {
            response.sendRedirect("/error?code=UNCATEGORIZED");
        }

        ProductFormDTO form = ProductFormDTO.builder()
                .id(productId)
                .name(request.getParameter("name"))
                .description(request.getParameter("description"))
                .categoryId(request.getParameter("categoryId"))
                .brandId(request.getParameter("brandId"))
                .status(request.getParameter("status"))
                .price(request.getParameter("price"))
                .build();

        StringBuilder url = new StringBuilder("/admin/product/update?id=").append(productId).append("&");
        Map<String, String> errors = this.validate(form, true);
        if (!errors.isEmpty()) {
            errors.forEach((key, value) -> url.append(key).append("=").append(value).append("&"));
        } else {

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

            BigDecimal price = new BigDecimal(request.getParameter("price"));
            BigDecimal salePrice = request.getParameter("salePrice") == null ? price : new BigDecimal(request.getParameter("salePrice"));

            Product product = Product.builder()
                    .id(Long.parseLong(request.getParameter("id")))
                    .name(request.getParameter("name"))
                    .description(request.getParameter("description"))
                    .price(price)
                    .salePrice(salePrice)
                    .categoryId(Long.parseLong(request.getParameter("categoryId")))
                    .brandId(Long.parseLong(request.getParameter("brandId")))
                    .status(ProductStatus.valueOf(request.getParameter("status")))
                    .updatedAt(LocalDateTime.now())
                    .build();


            if (productService.update(product, images) == null) {
                url.append("alertMsg=error&");
            } else {
                url.append("alertMsg=success&");
            }

            response.sendRedirect(request.getContextPath() + url.toString());
        }
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

    private void responseProductDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String id = request.getParameter("id");
        if(id == null || id.trim().isEmpty()) {
            response.sendRedirect("/error?code=UNCATEGORIZED");
        }


        ProductDetailResponse product = this.productService.getProductDetail(Long.parseLong(id));
        request.setAttribute("product", product);


        request.getRequestDispatcher("/views/prod-detail.jsp").forward(request, response);
    }

    private void responseProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        String offSet = request.getParameter("offset");
//        String categoryId = request.getParameter("categoryId");
//        String brandId = request.getParameter("brandId");
//        String price = request.getParameter("price");


        int offset = Integer.parseInt(request.getParameter("offset"));
        request.setAttribute("offset", offset);


        List<SimpleProdResponse> products = productService.findAllActiveProduct(offset);
        request.setAttribute("products", products);

        request.getRequestDispatcher("/views/product.jsp").forward(request, response);
    }

    private void responseProductCreation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String alertMsg = request.getParameter("alertMsg");
        if (alertMsg != null) {
            switch (alertMsg) {
                case "success":
                    request.setAttribute("successMsg", "Thêm sản phẩm mới thành công!");
                    break;

                case "error":
                    request.setAttribute("errorMsg", "Thêm sản phẩm mới thất bại!");
                    break;
            }
        }


        if (request.getParameter("nameError") != null) {
            request.setAttribute("nameError", "Tên sản phẩm không hợp lệ");
        }

        if (request.getParameter("descError") != null) {
            request.setAttribute("descError", "Mô tả sản phẩm không hợp lệ");
        }

        if (request.getParameter("statusError") != null) {
            request.setAttribute("statusError", "Trạng thái sản phẩm không hợp lệ");
        }

        if (request.getParameter("priceError") != null) {
            request.setAttribute("priceError", "Giá sản phẩm không hợp lệ");
        }

        if (request.getParameter("catError") != null) {
            request.setAttribute("catError", "Danh mục không hợp lệ");
        }

        if (request.getParameter("brandError") != null) {
            request.setAttribute("brandError", "Thương hiệu không hợp lệ");
        }


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

        ProductFormDTO form = ProductFormDTO.builder()
                .name(request.getParameter("name"))
                .description(request.getParameter("description"))
                .categoryId(request.getParameter("categoryId"))
                .brandId(request.getParameter("brandId"))
                .status(request.getParameter("status"))
                .price(request.getParameter("price"))
                .build();

        StringBuilder url = new StringBuilder("/admin/product/add?");
        Map<String, String> errors = this.validate(form, false);
        if (!errors.isEmpty()) {
            errors.forEach((key, value) -> url.append(key).append("=").append(value).append("&"));
        } else {

            BigDecimal price = new BigDecimal(request.getParameter("price"));
            BigDecimal salePrice = request.getParameter("salePrice") == null
                    ? price
                    : BigDecimal.valueOf(Double.parseDouble(request.getParameter("salePrice")));

            Product prod = Product.builder()
                    .name(request.getParameter("name"))
                    .description(request.getParameter("description"))
                    .status(ProductStatus.valueOf(request.getParameter("status")))
                    .price(price)
                    .salePrice(salePrice)
                    .categoryId(Long.parseLong(request.getParameter("categoryId")))
                    .brandId(Long.parseLong(request.getParameter("brandId")))
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            Collection<Part> parts = request.getParts();

            Product creationProd = productService.create(prod, parts);
            if (!Objects.isNull(creationProd)) {
                url.append("alertMsg=success&");
            } else {
                url.append("alertMsg=error&");
            }

            response.sendRedirect(request.getContextPath() + url.toString());
        }
    }

    private Map<String, String> validate(ProductFormDTO form, boolean isUpdate) {
        Map<String, String> errors = new HashMap<>();
        if (isUpdate) {
            if (form.getId() == null || form.getId().trim().isEmpty()) {
                errors.put("idError", "true");
            }
        }

        if (form.getName() == null || form.getName().trim().isEmpty()) {
            errors.put("nameError", "true");
        }

        if (form.getDescription() == null || form.getDescription().trim().isEmpty()) {
            errors.put("descError", "true");
        }

        if (form.getCategoryId() == null || form.getCategoryId().trim().isEmpty()) {
            errors.put("catError", "true");
        }

        if (form.getBrandId() == null || form.getBrandId().trim().isEmpty()) {
            errors.put("brandError", "true");
        }

        try {
            ProductStatus status = ProductStatus.valueOf(form.getStatus());
        } catch (Exception e) {
            errors.put("statusError", "true");
        }

        try {
            BigDecimal price = BigDecimal.valueOf(Double.parseDouble(form.getPrice()));
            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                errors.put("priceError", "true");
            }
        } catch (Exception e) {
            errors.put("priceError", "true");
        }


        return errors;
    }
}
